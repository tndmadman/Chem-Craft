package com.tndmadman.rts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Tiny 2D top-down RTS starter with P2P UDP multiplayer.
 *
 * Controls:
 *   WASD / Arrow keys: pan camera
 *   Mouse wheel: zoom
 *   Left click: select one unit
 *   Left drag: box select
 *   Right click: move selected units
 *
 * Run solo:
 *   ./gradlew run
 *
 * Host:
 *   ./gradlew run --args="--host 50000"
 *
 * Join from another machine/client:
 *   ./gradlew run --args="--join HOST_IP 50000"
 */
public final class RtsGame {
    public static void main(String[] args) {
        Config config = Config.parse(args);
        SwingUtilities.invokeLater(() -> {
            World world = new World(config.localPlayerId, config.hostMode);
            PeerNetwork network = null;
            try {
                network = PeerNetwork.start(config, world);
            } catch (IOException e) {
                System.err.println("Network disabled: " + e.getMessage());
            }

            JFrame frame = new JFrame("Java P2P RTS Starter - " + config.localPlayerId);
            GamePanel panel = new GamePanel(world, network);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setContentPane(panel);
            frame.setSize(1280, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            panel.start();
        });
    }

    static final class Config {
        final String localPlayerId;
        final boolean hostMode;
        final int localPort;
        final InetSocketAddress peerAddress;

        private Config(String localPlayerId, boolean hostMode, int localPort, InetSocketAddress peerAddress) {
            this.localPlayerId = localPlayerId;
            this.hostMode = hostMode;
            this.localPort = localPort;
            this.peerAddress = peerAddress;
        }

        static Config parse(String[] args) {
            String id = "P" + Integer.toHexString(new SecureRandom().nextInt(0xFFFF)).toUpperCase(Locale.ROOT);
            boolean host = false;
            int localPort = 0;
            InetSocketAddress peer = null;

            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--id" -> id = require(args, ++i, "--id needs a value");
                    case "--host" -> {
                        host = true;
                        localPort = Integer.parseInt(require(args, ++i, "--host needs a port"));
                    }
                    case "--join" -> {
                        host = false;
                        String hostName = require(args, ++i, "--join needs host ip");
                        int peerPort = Integer.parseInt(require(args, ++i, "--join needs peer port"));
                        peer = new InetSocketAddress(hostName, peerPort);
                        localPort = peerPort + 1 + new SecureRandom().nextInt(1000);
                    }
                    case "--local-port" -> localPort = Integer.parseInt(require(args, ++i, "--local-port needs a port"));
                    default -> throw new IllegalArgumentException("Unknown arg: " + args[i]);
                }
            }

            return new Config(id, host, localPort, peer);
        }

        private static String require(String[] args, int index, String message) {
            if (index >= args.length) throw new IllegalArgumentException(message);
            return args[index];
        }
    }

    static final class GamePanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
        private final World world;
        private final PeerNetwork network;
        private final Set<Integer> keys = new HashSet<>();
        private final javax.swing.Timer timer;
        private double cameraX = 0;
        private double cameraY = 0;
        private double zoom = 1.0;
        private Point dragStart;
        private Point dragNow;
        private long lastNanos = System.nanoTime();

        GamePanel(World world, PeerNetwork network) {
            this.world = world;
            this.network = network;
            setBackground(new Color(20, 26, 28));
            setFocusable(true);
            addMouseListener(this);
            addMouseMotionListener(this);
            addMouseWheelListener(this);
            addKeyListener(this);
            timer = new javax.swing.Timer(16, e -> tick());
        }

        void start() {
            requestFocusInWindow();
            timer.start();
        }

        private void tick() {
            long now = System.nanoTime();
            double dt = Math.min(0.05, (now - lastNanos) / 1_000_000_000.0);
            lastNanos = now;
            handleCamera(dt);
            if (network != null) network.drainMessages();
            world.update(dt);
            repaint();
        }

        private void handleCamera(double dt) {
            double speed = 800 / zoom;
            if (keys.contains(KeyEvent.VK_W) || keys.contains(KeyEvent.VK_UP)) cameraY -= speed * dt;
            if (keys.contains(KeyEvent.VK_S) || keys.contains(KeyEvent.VK_DOWN)) cameraY += speed * dt;
            if (keys.contains(KeyEvent.VK_A) || keys.contains(KeyEvent.VK_LEFT)) cameraX -= speed * dt;
            if (keys.contains(KeyEvent.VK_D) || keys.contains(KeyEvent.VK_RIGHT)) cameraX += speed * dt;
            cameraX = clamp(cameraX, -200, world.width - 200);
            cameraY = clamp(cameraY, -200, world.height - 200);
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            AffineTransform old = g2.getTransform();
            g2.scale(zoom, zoom);
            g2.translate(-cameraX, -cameraY);

            drawMap(g2);
            world.draw(g2);

            if (dragStart != null && dragNow != null) {
                Rectangle2D box = screenRectToWorldRect(dragStart, dragNow);
                g2.setColor(new Color(80, 170, 255, 60));
                g2.fill(box);
                g2.setColor(new Color(120, 205, 255));
                g2.draw(box);
            }

            g2.setTransform(old);
            drawHud(g2);
            g2.dispose();
        }

        private void drawMap(Graphics2D g2) {
            g2.setColor(new Color(33, 48, 42));
            g2.fillRect(0, 0, world.width, world.height);
            g2.setColor(new Color(45, 64, 56));
            for (int x = 0; x <= world.width; x += 80) g2.drawLine(x, 0, x, world.height);
            for (int y = 0; y <= world.height; y += 80) g2.drawLine(0, y, world.width, y);

            g2.setColor(new Color(69, 88, 70));
            for (ResourceNode node : world.resources) {
                g2.fillOval((int) node.x - 18, (int) node.y - 18, 36, 36);
            }
        }

        private void drawHud(Graphics2D g2) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(12, 12, 520, 76, 14, 14);
            g2.setColor(Color.WHITE);
            g2.drawString("Player: " + world.localPlayerId + " | Selected: " + world.selectedCount(), 28, 36);
            g2.drawString("WASD pan | Wheel zoom | Left select/drag | Right move", 28, 58);
            String net = network == null ? "Network: offline" : network.statusLine();
            g2.drawString(net, 28, 78);
        }

        private Point2D screenToWorld(Point p) {
            return new Point2D.Double(p.x / zoom + cameraX, p.y / zoom + cameraY);
        }

        private Rectangle2D screenRectToWorldRect(Point a, Point b) {
            Point2D aw = screenToWorld(a);
            Point2D bw = screenToWorld(b);
            double x = Math.min(aw.getX(), bw.getX());
            double y = Math.min(aw.getY(), bw.getY());
            double w = Math.abs(aw.getX() - bw.getX());
            double h = Math.abs(aw.getY() - bw.getY());
            return new Rectangle2D.Double(x, y, w, h);
        }

        @Override public void mouseClicked(MouseEvent e) { }
        @Override public void mousePressed(MouseEvent e) {
            requestFocusInWindow();
            if (SwingUtilities.isLeftMouseButton(e)) {
                dragStart = e.getPoint();
                dragNow = e.getPoint();
            }
        }
        @Override public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && dragStart != null) {
                Rectangle2D box = screenRectToWorldRect(dragStart, e.getPoint());
                if (box.getWidth() < 6 && box.getHeight() < 6) {
                    Point2D p = screenToWorld(e.getPoint());
                    world.selectSingle(p.getX(), p.getY());
                } else {
                    world.selectBox(box);
                }
                dragStart = null;
                dragNow = null;
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                Point2D p = screenToWorld(e.getPoint());
                List<MoveCommand> commands = world.issueMoveSelected(p.getX(), p.getY());
                if (network != null) {
                    for (MoveCommand command : commands) network.sendMove(command);
                }
            }
        }
        @Override public void mouseEntered(MouseEvent e) { }
        @Override public void mouseExited(MouseEvent e) { }
        @Override public void mouseDragged(MouseEvent e) { dragNow = e.getPoint(); }
        @Override public void mouseMoved(MouseEvent e) { }
        @Override public void mouseWheelMoved(MouseWheelEvent e) { zoom = clamp(zoom - e.getPreciseWheelRotation() * 0.08, 0.45, 2.2); }
        @Override public void keyTyped(KeyEvent e) { }
        @Override public void keyPressed(KeyEvent e) { keys.add(e.getKeyCode()); }
        @Override public void keyReleased(KeyEvent e) { keys.remove(e.getKeyCode()); }
    }

    static final class World {
        final int width = 2200;
        final int height = 1400;
        final String localPlayerId;
        final List<Unit> units = new ArrayList<>();
        final List<ResourceNode> resources = new ArrayList<>();
        private int nextUnitId = 1;

        World(String localPlayerId, boolean hostMode) {
            this.localPlayerId = localPlayerId;
            resources.add(new ResourceNode(620, 370));
            resources.add(new ResourceNode(1080, 720));
            resources.add(new ResourceNode(1640, 1030));
            spawnPlayer(localPlayerId, hostMode ? 220 : 1840, hostMode ? 260 : 1020, true);
        }

        synchronized void spawnPlayer(String playerId, double startX, double startY, boolean local) {
            if (hasPlayer(playerId)) return;
            Color color = local ? new Color(80, 190, 255) : new Color(255, 95, 85);
            for (int i = 0; i < 6; i++) {
                double x = startX + (i % 3) * 46;
                double y = startY + (i / 3) * 46;
                units.add(new Unit(playerId, nextUnitId++, x, y, color, local));
            }
        }

        synchronized boolean hasPlayer(String playerId) {
            return units.stream().anyMatch(u -> u.playerId.equals(playerId));
        }

        synchronized void update(double dt) {
            for (Unit u : units) u.update(dt, width, height);
        }

        synchronized void draw(Graphics2D g2) {
            for (Unit u : units) u.draw(g2);
        }

        synchronized int selectedCount() {
            int count = 0;
            for (Unit u : units) if (u.selected) count++;
            return count;
        }

        synchronized void selectSingle(double x, double y) {
            Unit best = null;
            double bestDist = 999999;
            for (Unit u : units) {
                if (!u.local) continue;
                double d = distance(x, y, u.x, u.y);
                if (d < 28 && d < bestDist) {
                    bestDist = d;
                    best = u;
                }
            }
            for (Unit u : units) u.selected = false;
            if (best != null) best.selected = true;
        }

        synchronized void selectBox(Rectangle2D box) {
            for (Unit u : units) {
                u.selected = u.local && box.contains(u.x, u.y);
            }
        }

        synchronized List<MoveCommand> issueMoveSelected(double x, double y) {
            List<Unit> selected = units.stream().filter(u -> u.local && u.selected).toList();
            List<MoveCommand> out = new ArrayList<>();
            int n = selected.size();
            if (n == 0) return out;
            double spacing = 42;
            int cols = (int) Math.ceil(Math.sqrt(n));
            for (int i = 0; i < n; i++) {
                Unit u = selected.get(i);
                int col = i % cols;
                int row = i / cols;
                double tx = x + (col - cols / 2.0) * spacing;
                double ty = y + row * spacing;
                u.moveTo(tx, ty);
                out.add(new MoveCommand(localPlayerId, u.unitId, tx, ty));
            }
            return out;
        }

        synchronized void applyRemoteMove(MoveCommand command) {
            Unit found = null;
            for (Unit u : units) {
                if (u.playerId.equals(command.playerId) && u.unitId == command.unitId) {
                    found = u;
                    break;
                }
            }
            if (found != null) found.moveTo(command.x, command.y);
        }

        synchronized void ensureRemotePlayer(String playerId) {
            if (!playerId.equals(localPlayerId) && !hasPlayer(playerId)) {
                spawnPlayer(playerId, 1840, 1020, false);
            }
        }
    }

    static final class Unit {
        final String playerId;
        final int unitId;
        double x;
        double y;
        double targetX;
        double targetY;
        final Color color;
        final boolean local;
        boolean selected;
        double hp = 100;

        Unit(String playerId, int unitId, double x, double y, Color color, boolean local) {
            this.playerId = playerId;
            this.unitId = unitId;
            this.x = x;
            this.y = y;
            this.targetX = x;
            this.targetY = y;
            this.color = color;
            this.local = local;
        }

        void moveTo(double x, double y) {
            this.targetX = x;
            this.targetY = y;
        }

        void update(double dt, int mapW, int mapH) {
            double speed = 185;
            double dx = targetX - x;
            double dy = targetY - y;
            double d = Math.hypot(dx, dy);
            if (d > 2) {
                double step = Math.min(d, speed * dt);
                x += dx / d * step;
                y += dy / d * step;
            }
            x = clamp(x, 0, mapW);
            y = clamp(y, 0, mapH);
        }

        void draw(Graphics2D g2) {
            int r = 16;
            g2.setColor(new Color(0, 0, 0, 130));
            g2.fillOval((int) x - r - 2, (int) y - r + 4, r * 2 + 4, r * 2);
            g2.setColor(color);
            g2.fillOval((int) x - r, (int) y - r, r * 2, r * 2);
            g2.setColor(Color.BLACK);
            g2.drawOval((int) x - r, (int) y - r, r * 2, r * 2);

            g2.setColor(new Color(20, 20, 20));
            g2.fillRect((int) x - 18, (int) y - 28, 36, 5);
            g2.setColor(new Color(80, 230, 90));
            g2.fillRect((int) x - 18, (int) y - 28, (int) (36 * hp / 100.0), 5);

            if (selected) {
                g2.setColor(new Color(255, 245, 120));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval((int) x - 23, (int) y - 23, 46, 46);
            }
        }
    }

    record ResourceNode(double x, double y) { }
    record MoveCommand(String playerId, int unitId, double x, double y) { }

    static final class PeerNetwork {
        private final Config config;
        private final World world;
        private final DatagramSocket socket;
        private final ConcurrentLinkedQueue<String> inbox = new ConcurrentLinkedQueue<>();
        private volatile InetSocketAddress peerAddress;
        private volatile boolean running = true;
        private long lastHello = 0;
        private long lastPacketAt = 0;

        private PeerNetwork(Config config, World world, DatagramSocket socket) {
            this.config = config;
            this.world = world;
            this.socket = socket;
            this.peerAddress = config.peerAddress;
        }

        static PeerNetwork start(Config config, World world) throws IOException {
            if (config.localPort == 0 && config.peerAddress == null) return null;
            DatagramSocket socket = config.localPort == 0 ? new DatagramSocket() : new DatagramSocket(config.localPort);
            socket.setSoTimeout(300);
            PeerNetwork network = new PeerNetwork(config, world, socket);
            Thread thread = new Thread(network::listenLoop, "p2p-udp-listener");
            thread.setDaemon(true);
            thread.start();
            network.sendHello();
            return network;
        }

        String statusLine() {
            String peer = peerAddress == null ? "waiting for peer" : peerAddress.toString();
            String age = lastPacketAt == 0 ? "no packets yet" : ((System.currentTimeMillis() - lastPacketAt) + "ms since packet");
            return "Network: UDP " + socket.getLocalPort() + " -> " + peer + " | " + age;
        }

        void drainMessages() {
            long now = System.currentTimeMillis();
            if (now - lastHello > 1500) {
                sendHello();
                lastHello = now;
            }
            String msg;
            while ((msg = inbox.poll()) != null) handleMessage(msg);
        }

        void sendMove(MoveCommand command) {
            send("MOVE|" + command.playerId() + "|" + command.unitId() + "|" + command.x() + "|" + command.y());
        }

        private void sendHello() {
            send("HELLO|" + config.localPlayerId);
        }

        private void send(String message) {
            InetSocketAddress target = peerAddress;
            if (target == null) return;
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, target.getAddress(), target.getPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.err.println("send failed: " + e.getMessage());
            }
        }

        private void listenLoop() {
            byte[] buffer = new byte[2048];
            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    String message = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
                    peerAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());
                    lastPacketAt = System.currentTimeMillis();
                    inbox.add(message);
                } catch (SocketTimeoutException ignored) {
                    // keep loop alive so the daemon can stop when app exits
                } catch (IOException e) {
                    if (running) System.err.println("listen failed: " + e.getMessage());
                }
            }
        }

        private void handleMessage(String message) {
            String[] parts = message.split("\\|");
            if (parts.length == 0) return;
            try {
                switch (parts[0]) {
                    case "HELLO" -> {
                        if (parts.length >= 2) {
                            String playerId = parts[1];
                            world.ensureRemotePlayer(playerId);
                            send("HELLO_ACK|" + config.localPlayerId);
                        }
                    }
                    case "HELLO_ACK" -> {
                        if (parts.length >= 2) world.ensureRemotePlayer(parts[1]);
                    }
                    case "MOVE" -> {
                        if (parts.length >= 5) {
                            MoveCommand command = new MoveCommand(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
                            if (!command.playerId().equals(config.localPlayerId)) {
                                world.ensureRemotePlayer(command.playerId());
                                world.applyRemoteMove(command);
                            }
                        }
                    }
                    default -> System.err.println("unknown packet: " + message);
                }
            } catch (RuntimeException e) {
                System.err.println("bad packet: " + message + " | " + e.getMessage());
            }
        }
    }

    static double distance(double ax, double ay, double bx, double by) {
        return Math.hypot(ax - bx, ay - by);
    }

    static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
