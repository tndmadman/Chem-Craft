package com.chemcraft.game.math;

import java.util.ArrayList;


public class Smelting {
	protected static ArrayList<ItemStack> smelting = new ArrayList<ItemStack>();
	protected static ArrayList<Integer> output = new ArrayList<Integer>();
	protected static ArrayList<Float> fueltake = new ArrayList<Float>();
	protected static ArrayList<Integer>  fueltype  = new ArrayList<Integer>();
	protected static ArrayList<Float> fuelput = new ArrayList<Float>();
	

	protected static ItemStack itemstack;
	public static void initFurnaceRecipies()
	{
		//add the fuel types
		addFuelType(20, 20f);//coal
		addFuelType(50, 10f);//charcoal
		addFuelType(39, 5f);//log
		addFuelType(40, 5f);//log
		addFuelType(41, 5f);//log
		addFuelType(42, 5f);//log
		addFuelType(43, 5f);//log
		addFuelType(44, 5f);//log
		addFuelType(45, 5f);//log
		addFuelType(46, 5f);//log
		addFuelType(47, 5f);//log
		addFuelType(48, 5f);//log
		
		
		
		//add the crafting types
		addRecipie(6, 28, 4);//iron smelt
		addRecipie(7, 27, 4);//gold smelt
		addRecipie(10, 25, 4);//cobalt smelt
		addRecipie(11, 26, 4);//copper smelt
		addRecipie(13, 35, 4);//tin smelt
		addRecipie(14, 36, 4);//uranium smelt
		addRecipie(2, 17, 4);//glass smelt
		
		addRecipie(3, 50, 4);//charcoal smelt
		addRecipie(39, 50, 4);//charcoal smelt
		addRecipie(40, 50, 4);//charcoal smelt
		addRecipie(41, 50, 4);//charcoal smelt
		addRecipie(42, 50, 4);//charcoal smelt
		addRecipie(43, 50, 4);//charcoal smelt
		addRecipie(44, 50, 4);//charcoal smelt
		addRecipie(45, 50, 4);//charcoal smelt
		addRecipie(46, 50, 4);//charcoal smelt
		addRecipie(47, 50, 4);//charcoal smelt
		addRecipie(48, 50, 4);//charcoal smelt
		
		
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
	public static Float isFuel(int IDFuelType) {
		for (int i = 0; i < fueltype.size(); i++) {
			if (fueltype.get(i).equals(IDFuelType)) {
				return fuelput.get(i);
			}
		}
		return -1f;
	}
	private static void addFuelType(int IDFuelType, float fuelPut) {
		fueltype.add(IDFuelType);
		fuelput.add(fuelPut);
	}
}
