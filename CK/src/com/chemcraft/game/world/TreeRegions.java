package com.chemcraft.game.world;

import com.chemcraft.game.math.WorldNoise;

public class TreeRegions 
{
	//lveavs will be a thing later
	
	protected static float x = 0;
	protected static float y = 0;
	
	public static double REGION_MARSH = .1;

	public static double REGION_DESERT = .2;

	public static double REGION_JUNGLE = .9;
	
	public static final int BLOCK_ASH_LOG = 26;
	public static final int BLOCK_BIRCH_LOG = 27;
	public static final int BLOCK_CEDAR_LOG = 28;
	public static final int BLOCK_CHERRY_LOG = 29;
	public static final int BLOCK_PINE_LOG = 30;
	
	public static final int BLOCK_LEAVES = 8;
	
	
	public static int getRegionTypeBiome(float xx, float yy, WorldNoise biomenoise)
	{
		x = xx/32;
		y = yy/32;
		//marsh region
		if ((biomenoise.turbulence2(x, y, 1) > REGION_MARSH) && (biomenoise.turbulence2(x, y, 1) < REGION_DESERT)) {
			return BLOCK_CEDAR_LOG;
			}
			//desert region
			if ((biomenoise.turbulence2(x, y, 1) > REGION_DESERT) && (biomenoise.turbulence2(x, y, 1) < REGION_JUNGLE)) {
			return BLOCK_ASH_LOG;
			}
			//jungle region
			if ((biomenoise.turbulence2(x, y, 1) < REGION_JUNGLE)) {
				return BLOCK_PINE_LOG;
			}
			return 0;
		
	}
}
