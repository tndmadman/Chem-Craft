package com.chemcraft.game.world;

import java.util.Random;


public class BuldableOres {
	public static int xoff = 0;
	public static int yoff = 0;
	public static int randPower;
	public static int randOreType;
	public static int ore_tiny_01[][] = new int[][]{
		{1,0,0,0},
		{0,1,0,0},
		{0,0,0,0},
		{0,0,0,0}
	};
	public static int ore_tiny_02[][] = new int[][]{
		{0,1,0,0},
		{1,0,0,0},
		{0,0,0,0},
		{0,0,0,0}
	};
	public static int ore_small_01[][] = new int[][]{
		{0,1,0,0},
		{0,1,1,0},
		{0,0,0,0},
		{0,0,0,0}
	};
	public static int ore_small_02[][] = new int[][]{
		{0,1,0,0},
		{0,1,0,0},
		{1,0,0,0},
		{0,0,0,0}
	};
	public static int ore_medium_01[][] = new int[][]{
		{0,0,0,0},
		{0,1,1,0},
		{0,0,1,1},
		{1,1,0,0}
		
	};
	public static int ore_medium_02[][] = new int[][]{
		{1,1,0,0},
		{0,1,1,0},
		{0,1,1,1},
		{0,1,0,0}
		
	};
	public static int blockTable[][] = new int[4][4];
	
	
	
	
	
	
	
	public static int[][] getOre()
	{
		Random random = new Random();
		randPower = random.nextInt(3);
		randOreType = random.nextInt(2);
		
		if (randPower == 1) {
			if (randOreType == 1) {
				return ore_tiny_01;
			}
			if (randOreType == 2) {
				return ore_tiny_02;
			}
		}
		if (randPower == 2) {
			if (randOreType == 1) {
				return ore_small_01;
			}
			if (randOreType == 2) {
				return ore_small_02;
			}
		}
		if (randPower == 3) {
			if (randOreType == 1) {
				return ore_medium_02;
			}
			if (randOreType == 2) {
				return ore_medium_02;
			}
		}

		return ore_tiny_01;
	}
}
