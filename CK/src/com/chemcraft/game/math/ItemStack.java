package com.chemcraft.game.math;

public class ItemStack {
	private int x = 0;
	private int y = 0;
	private int ID = 0;
	private int stack = 0;
	private int maxStack = 32;

	public ItemStack(int x, int y, int ID) {
		this.x = x;
		this.y = y;
		this.ID = ID;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getID() {
		return this.ID;
	}
	
	public void setLoc(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public ItemStack setID(int ID) {
		this.ID = ID;
		return this;
	}

	public void setStack(int stackCount) {
		this.stack = stackCount;
	}

	public int getStack() {
		return this.stack;
	}

	public int getMaxStack() {
		return this.maxStack;
	}
}
