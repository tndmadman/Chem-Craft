package com.chemcraft.game.inventorys;

import com.chemcraft.game.math.ItemStack;

public class InventoryBase implements Cloneable
{
	private ItemStack[] internal;
	private ItemStack output;
	private int x = 0;
	private int y = 0;
	private int z = 0;
	
	public InventoryBase(int size)
	{
		internal = new ItemStack[size];
		output = new ItemStack(0, 0, -1);
		for (int yy = 0; yy < internal.length; yy++)
		{
			internal[yy] = new ItemStack(0, 0, -1);
		}
	}
	
	public void setInternalStackLoc(int slotID, int x, int y)
	{
		internal[slotID].setLoc(x, y);
	}
	public void setOutputStackLoc(int x, int y)
	{
		output.setLoc(x, y);
	}
	public void setInternalStackID(int slotID, int itemID)
	{
		internal[slotID].setID(itemID);
	}
	public void setOutputStackID(int itemID)
	{
		output.setID(itemID);
	}
	public int getInternalStackID(int slotID)
	{
		return internal[slotID].getID();
	}
	public int getInternalStackCount(int slotID)
	{
		return internal[slotID].getStack();
	}
	
	public void removeItemInternal(int slotID){
		if (!(internal[slotID].getID() == -1)) {
			//handle if slot has 1 item first
			if (internal[slotID].getStack() == 1) {
				internal[slotID].setID(-1);
				internal[slotID].setStack(0);
			}
			//if there is more then 1 just remove 1
			if (internal[slotID].getStack() > 1) {
				internal[slotID].setStack(internal[slotID].getStack()-1);
			}
			
		}
	}
	public void removeItemOutput()
	{
		if (!(output.getID() == -1)) {
			//handle if slot has 1 item first
			if (output.getStack() == 1) {
				output.setID(-1);
				output.setStack(0);
			}
			//if there is more then 1 just remove 1
			if (output.getStack() > 1) {
				output.setStack(output.getStack()-1);
			}
			
		}
	}
	
	
	
	public void setLoc(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public boolean testLoc(int x, int y, int z)
	{
		
		

		if (this.x == x && this.y == y && this.z == z) {
			return true;
		}else{
			return false;
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public ItemStack[] getInternal()
	{
		return this.internal;
	}
	public ItemStack getOutput()
	{
		return this.output;
	}
	
}