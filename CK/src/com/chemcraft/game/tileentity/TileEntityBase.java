package com.chemcraft.game.tileentity;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Renderer;
import com.chemcraft.game.GameManager;

public class TileEntityBase implements Cloneable
{
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private boolean iscolidable = false;
	
	public TileEntityBase()
	{
		
	}
	public void doOnCreate(GameManager gm, GameContainer gc)
	{
		
	}
	
	public TileEntityBase setLoc(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	public void tileLogic(GameManager gm, GameContainer gc)
	{
		//for sub classes only
	}
	public void tileRightClick(int x, int y, int z)
	{
		//for sub classes only
	}
	public void tileGraphics(GameManager gm, Renderer r)
	{
		//for sub classes only
	}
	
	public boolean testLoc(int x, int y, int z)
	{
		if (this.x == x && this.y == y && this.z == z) {
			return true;
		}else{
			return false;
		}
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public int getZ()
	{
		return this.z;
	}
	public TileEntityBase setIsColidable(boolean iscolidable)
	{
		this.iscolidable = iscolidable;
		return this;
	}
	public boolean isColidable()
	{
		return this.iscolidable;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
