package com.chemcraft.game.math;

import java.util.ArrayList;

public class Smelting {
	public static ArrayList<ItemStack> smelting = new ArrayList<ItemStack>();
	public static ArrayList<Integer> output = new ArrayList<Integer>();
	public static ArrayList<Float> fueltake = new ArrayList<Float>();
	protected static ItemStack itemstack;
	public static void initFurnaceRecipies()
	{
		addRecipie(6, 28, 4);//iron smelt
		addRecipie(7, 27, 4);//gold smelt
		addRecipie(10, 25, 4);//cobalt smelt
		addRecipie(11, 26, 4);//copper smelt
		addRecipie(13, 35, 4);//tin smelt
		addRecipie(14, 36, 4);//uranium smelt
		addRecipie(2, 17, 4);//uranium smelt
		
	}
	protected static void addRecipie(int itemID, int outputID, float fuelTake)
	{
		itemstack = new ItemStack(0, 0, itemID);
		smelting.add(itemstack);
		output.add(outputID);
		fueltake.add(fuelTake);
	}
	public static int testRecipie(int itemID)
	{
		for (int i = 0; i < smelting.size(); i++) {
			if (smelting.get(i).getID() == itemID) {
				return output.get(i);
			}
		}
		
		
		return -1;
	}
}
