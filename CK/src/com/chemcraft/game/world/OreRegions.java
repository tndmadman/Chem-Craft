package com.chemcraft.game.world;

import java.util.Random;


public class OreRegions {
	
	
	
	public static final int t1 = 10;
	public static final int t2 = 20;
	public static final int t3 = 30;
	public static final int t4 = 40;
	public static final int t5 = 50;
	public static final int t6 = 60;
	
	protected static final int ORE_COAL = 9;
	protected static final int ORE_IRON = 10;
	protected static final int ORE_GOLD = 11;
	protected static final int ORE_DIAMOND = 12;
	protected static final int ORE_EMERALD = 13;
	protected static final int ORE_COBALT = 14;
	protected static final int ORE_COPPER = 15;
	protected static final int ORE_LITHIUM = 16;
	protected static final int ORE_TIN = 17;
	protected static final int ORE_URANIUM = 18;
	protected static final int ORE_ZINC = 19;
	protected static int rx = 0;
	protected static int ry = 0;
	
	
	public static void registerOres()
	{
		OreObject.addOre(9, 26,"coal");
		OreObject.addOre(10, 26,"iron");
		OreObject.addOre(11, 8,"gold");
		OreObject.addOre(12, 6,"diamond");
		OreObject.addOre(13, 4,"emerald");
		OreObject.addOre(14, 12,"cobalt");
		OreObject.addOre(15, 14,"copper");
		OreObject.addOre(16, 8,"lithium");
		OreObject.addOre(17, 12,"tin");
		OreObject.addOre(18, 8,"uranium");
		OreObject.addOre(19, 8,"zinc");
		
		
	}
	
	public static int getOreTier(int z){
		Random rand = new Random();
		
		
		if (z >= 0 && z <= t1)
		{
			//tier 1 ore gen
			int r = rand.nextInt(4);
			if (r == 1) {
				return ORE_GOLD;
			}
			if (r == 2) {
				return ORE_EMERALD;
			}
			if (r == 3) {
				return ORE_DIAMOND;
			}
			if (r == 4) {
				return ORE_LITHIUM;
			}
			
			
		}
		if (z >= t1 && z <= t2)
		{
			//tier 2 ore gen
			int r = rand.nextInt(3);
			if (r == 1) {
				return ORE_ZINC;
			}
			if (r == 2) {
				return ORE_URANIUM;
			}
			if (r == 3) {
				return ORE_COBALT;
			}
			
		}if (z >= t2 && z <= t3)
		{
			//tier 3 ore gen
			
			int r = rand.nextInt(3);
			if (r == 1) {
				return ORE_COPPER;
			}
			if (r == 2) {
				return ORE_TIN;
			}
			if (r == 3) {
				return ORE_URANIUM;
			}
			
			
		}if (z >= t3 && z <= t4)
		{
			//tier 4 ore gen
			int r = rand.nextInt(3);
			if (r == 1) {
				return ORE_COPPER;
			}
			if (r == 2) {
				return ORE_IRON;
			}
			if (r == 3) {
				return ORE_TIN;
			}
			
		}if (z >= t4 && z <= t5)
		{
			//tier 5 ore gen
			
			int r = rand.nextInt(3);
			if (r == 1) {
				return ORE_TIN;
			}
			if (r == 2) {
				return ORE_IRON;
			}
			if (r == 3) {
				return ORE_COAL;
			}
			
			
			
		}if (z >= t5 && z <= t6)
		{
			//tier 6 ore gen
			int r = rand.nextInt(2);
			if (r == 1) {
				return ORE_IRON;
			}
			if (r == 2) {
				return ORE_COAL;
			}
			
			
			
		}
		
		if (z >= t6 && z <= 64)
		{
			//tier 6 ore gen
			return ORE_COAL;
			
		}
		//z out of bounds return a "void"
		return 0;
	}
}
