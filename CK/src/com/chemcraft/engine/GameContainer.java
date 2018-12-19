package com.chemcraft.engine;

public class GameContainer implements Runnable
{
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;

	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;
	private int width = 512, height = 512;
	private float scale = 1.8f;
	private String title = "Chem Craft 1.7B";

	public GameContainer(AbstractGame game)
	{
		this.game = game;
	}

	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		// start main thread
		thread = new Thread(this);
		thread.run();
	}

	public void stop() {

	}

	public void run() {
		running = true;

		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double pastTime = 0;
		double unprocesedTime = 0;

		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		while (running)
		{
			render = false;
			firstTime = System.nanoTime() / 1000000000.0;
			pastTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocesedTime += pastTime;
			frameTime += pastTime;

			while (unprocesedTime >= UPDATE_CAP)
			{

				unprocesedTime -= UPDATE_CAP;
				render = true;
				game.update(this, (float) UPDATE_CAP, renderer);

				input.update();
				if (frameTime >= 1.0)
				{
					game.render(this, renderer);
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}
			if (render)
			{
				// game render
				renderer.clear();
				game.render(this, renderer);
				// debug stuff
				renderer.procces();

				renderer.drawText("FPS: " + fps, 0, 0, 0xffffff00);

				// renderer.drawText("MOUSE X: " + input.getMouseX(), 0, 8,
				// 0xffffff00);
				// renderer.drawText("MOUSE y: " + input.getMouseY(), 0, 16,
				// 0xffffff00);

				window.update();
				frames++;
			} else
			{
				try
				{
					Thread.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	public void dispose() {

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getScale() {
		return scale;
	}

	public String getTitle() {
		return title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
