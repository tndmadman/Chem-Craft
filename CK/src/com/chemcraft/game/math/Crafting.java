package com.chemcraft.game.math;

import java.util.ArrayList;
import java.util.Arrays;

public class Crafting
{

	public static int[][] craftint;

	public static ArrayList<int[][]> recpipies = new ArrayList<int[][]>();
	public static ArrayList<Integer> output = new ArrayList<Integer>();
	public static ArrayList<Integer> outputcount = new ArrayList<Integer>();
	public static ItemStack itemstack;

	public static void addRecipie(int[][] rec, int out, int count) {
		if (!(recpipies.contains(rec)))
		{
			recpipies.add(rec);
			output.add(out);
			outputcount.add(count);
		}
	}

	public static void initRecipies() {
		// addRecipie(craftint = new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1,
		// 1 } }, 10, 10);example
		//these IDS corespond to item IDS only not block IDS
		addRecipie(craftint = new int[][] { { 3, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 4, 8);// oak
																									// planks
		addRecipie(craftint = new int[][] { { 4, 4, 4 }, { 4, -1, 4 }, { 4, 4, 4 } }, 18, 1);// mine
		
		addRecipie(craftint = new int[][] { { 44, 44, 44 }, { 44, -1, 44 }, { 44, 44, 44 } }, 18, 1);// mine
		
		addRecipie(craftint = new int[][] { { 45, 45, 45 }, { 45, -1, 45 }, { 45, 45, 45 } }, 18, 1);// mine
		
		addRecipie(craftint = new int[][] { { 46, 46, 46 }, { 46, -1, 46 }, { 46, 46, 46 } }, 18, 1);// mine
		
		addRecipie(craftint = new int[][] { { 47, 47, 47 }, { 47, -1, 47 }, { 47, 47, 47 } }, 18, 1);// mine
		
		addRecipie(craftint = new int[][] { { 48, 48, 48 }, { 48, -1, 48 }, { 48, 48, 48 } }, 18, 1);// mine
		
		addRecipie(craftint = new int[][] { { 4, -1, -1 }, { 4, -1, -1 }, { 4, -1, -1 } }, 18, 4);// floor
		
		addRecipie(craftint = new int[][] { { 44, -1, -1 }, { 44, -1, -1 }, { 44, -1, -1 } }, 18, 4);// floor
		
		addRecipie(craftint = new int[][] { { 45, -1, -1 }, { 45, -1, -1 }, { 45, -1, -1 } }, 18, 4);// floor
		
		addRecipie(craftint = new int[][] { { 46, -1, -1 }, { 4,6 -1, -1 }, { 46, -1, -1 } }, 18, 4);// floor
		
		addRecipie(craftint = new int[][] { { 47, -1, -1 }, { 47, -1, -1 }, { 47, -1, -1 } }, 18, 4);// floor
		
		addRecipie(craftint = new int[][] { { 48, -1, -1 }, { 48, -1, -1 }, { 48, -1, -1 } }, 18, 4);// floor
		
		addRecipie(craftint = new int[][] { { 5, -1, -1 }, { 0, -1, -1 }, { -1, -1, -1 } }, 20, 4);// coal dust
		
		addRecipie(craftint = new int[][] { { 4, 4, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//stick
		
		addRecipie(craftint = new int[][] { { 44, 44, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//stick
		
		addRecipie(craftint = new int[][] { { 45, 45, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//stick

		addRecipie(craftint = new int[][] { { 46, 46, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//stick
		
		addRecipie(craftint = new int[][] { { 47, 47, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//stick
		
		addRecipie(craftint = new int[][] { { 48, 48, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//stick
		
		
		addRecipie(craftint = new int[][] { { 20, 37, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 16, 1);//torch
		
		addRecipie(craftint = new int[][] { { 4, 4, 4 }, { 4, 4, 4 }, { -1, -1, -1 } }, 38, 1);//door
		
		addRecipie(craftint = new int[][] { { 44, 44, 44 }, { 44, 44, 44 }, { -1, -1, -1 } }, 38, 1);//door

		addRecipie(craftint = new int[][] { { 45, 45, 45 }, { 45, 45, 45 }, { -1, -1, -1 } }, 38, 1);//door

		addRecipie(craftint = new int[][] { { 46, 46, 46 }, { 46, 46, 46 }, { -1, -1, -1 } }, 38, 1);//door

		addRecipie(craftint = new int[][] { { 47, 47, 47 }, { 47, 47, 47 }, { -1, -1, -1 } }, 38, 1);//door

		addRecipie(craftint = new int[][] { { 48, 48, 48 }, { 48, 48, 48 }, { -1, -1, -1 } }, 38, 1);//door

		
		addRecipie(craftint = new int[][] { { 39, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 44, 8);//ash plank
		
		addRecipie(craftint = new int[][] { { 40, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 45, 8);//birch plank
		
		addRecipie(craftint = new int[][] { { 41, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 46, 8);//cedar plank
		
		addRecipie(craftint = new int[][] { { 42, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 47, 8);//cherry plank
		
		addRecipie(craftint = new int[][] { { 43, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 48, 8);//pine plank
		
		addRecipie(craftint = new int[][] { { 39, 39, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//ash stick

		addRecipie(craftint = new int[][] { { 44, 44, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//birch stick

		addRecipie(craftint = new int[][] { { 45, 45, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//cedar stick

		addRecipie(craftint = new int[][] { { 46, 46, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//cherry stick
		
		addRecipie(craftint = new int[][] { { 47, 47, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }, 37, 4);//pine stick

		addRecipie(craftint = new int[][] { { 0, 0, 0 }, { 0, -1, 0 }, { 0, 0, 0 } }, 49, 1);//furnace

		

		
		
	}

	public static ItemStack testRecpipie(ItemStack[][] invetory, int x, int y) {
		itemstack = new ItemStack(x, y, -1);
		// turn the intput into a 2d array of ints
		craftint = new int[3][3];
		for (int yy = 0; yy < invetory[0].length; yy++)
		{
			for (int xx = 0; xx < invetory.length; xx++)
			{
				craftint[xx][yy] = invetory[xx][yy].getID();
			}
		}
		int itemID = -1;
		for (int i = 0; i < recpipies.size(); i++)
		{
			if (Arrays.deepEquals(recpipies.get(i), craftint))
			{
				itemID = i;

			}
		}
		System.out.println(itemID);
		if (!(itemID == -1))
		{
			itemstack.setID(output.get(itemID));
			itemstack.setStack(outputcount.get(itemID));
			
		}
		return itemstack;
	}
}
