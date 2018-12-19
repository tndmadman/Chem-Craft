package com.chemcraft.game.blocks;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.world.World;

public class BlockMineHatch extends BlockBase
{
	public BlockMineHatch(String name, String unlocalized_name, int itemDropID) {
		super(name, unlocalized_name, itemDropID);
	}
	
	
@Override
	public void doOnCreate(int x, int y, int z, GameManager gm, GameContainer gc) {
		for (int zz = 0; zz < 63; zz++) {
			World.getLoadedChunk().setBlockID(x, y, zz, -1);
		}
	}
	
}
