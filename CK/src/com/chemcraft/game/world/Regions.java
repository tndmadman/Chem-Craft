package com.chemcraft.game.world;

import com.chemcraft.game.math.WorldNoise;

public class Regions {
	public static float x = 0;
	public static float y = 0;
	public static double REGION_WATER = .1;

	public static double REGION_SAND = .2;

	public static double REGION_GRASS = .9;

	
	
	public static final int ITEM_GRASS = 1;
	public static final int ITEM_STONE = 0;
	public static final int ITEM_DIRT = 2;
	public static final int ITEM_WATER = 3;
	public static final int ITEM_SAND = 4;
	
	
	
	
	
	
	
	
	
	public static int getRegionTypeSurfice(float xx, float yy, WorldNoise noiseTop)
	{
		x = xx/16;
		y = yy/16;
		//sand region
		if ((noiseTop.turbulence2(x, y, 1) > REGION_WATER) && (noiseTop.turbulence2(x, y, 1) < REGION_SAND)) {
		return ITEM_SAND;
		}
		//water region
		if ((noiseTop.turbulence2(x, y, 1) > REGION_SAND) && (noiseTop.turbulence2(x, y, 1) < REGION_GRASS)) {
		return ITEM_WATER;
		}
		//grass region
		if ((noiseTop.turbulence2(x, y, 1) < REGION_GRASS)) {
			return ITEM_GRASS;
		}
		
		
		
		
		return 0;
	}
	
}
