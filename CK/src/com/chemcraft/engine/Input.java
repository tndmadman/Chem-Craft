package com.chemcraft.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private GameContainer gc;
	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];
	
	private final int NUM_BUTTONS = 5;
	private boolean[] button = new boolean[NUM_BUTTONS];
	private boolean[] buttonLast = new boolean[NUM_BUTTONS];
	
	private int mouseX = 0, mouseY = 0;
	private int scroll = 0;
	
	public Input(GameContainer gc)
	{
		this.gc = gc;
		
		gc.getWindow().getCanvas().addKeyListener(this);
		gc.getWindow().getCanvas().addMouseMotionListener(this);
		gc.getWindow().getCanvas().addMouseListener(this);
		gc.getWindow().getCanvas().addMouseWheelListener(this);
	}
	public void update(){
		scroll = 0;
		for (int i = 0; i < NUM_KEYS; i++) {
			keysLast[i] = keys[i];
		}
		for (int i = 0; i < NUM_BUTTONS; i++) {
			buttonLast[i] = button[i];
		}
	}
	public boolean isKey(int keyCode){
		return keys[keyCode];
	}
	public boolean isKeyUp(int keyCode){
		return !keys[keyCode] && keysLast[keyCode];
	}
	public boolean isKeyDown(int keyCode){
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public boolean isButton(int Button){
		return button[Button];
	}
	public boolean isButtonUp(int Button){
		return !button[Button] && buttonLast[Button];
	}
	public boolean isButtonDown(int Button){
		return button[Button] && !buttonLast[Button];
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		scroll = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseX = (int)(e.getX() / gc.getScale());
		mouseY = (int)(e.getY() / gc.getScale());
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		mouseX = (int)(e.getX() / gc.getScale());
		mouseY = (int)(e.getY() / gc.getScale());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		button[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		button[e.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
	public int getMouseX() {
		return mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
	public int getScroll() {
		return scroll;
	}
}
