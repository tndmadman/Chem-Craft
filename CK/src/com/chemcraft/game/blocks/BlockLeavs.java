package com.chemcraft.game.blocks;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.world.World;

public class BlockLeavs extends BlockBase{

	public BlockLeavs(String name, String unlocalized_name, int itemDropID) {
		super(name, unlocalized_name, itemDropID);
	}
	
	
	@Override
	public void doWhenDestroyed(GameManager gm, GameContainer gc) {
		for (int xx = 0; xx < 5; xx++) {
			for (int yy = 0; yy < 5; yy++) {
				if (gm.cX+xx < 32 && gm.cY+yy < 32 && gm.cX+xx > 1 && gm.cY+yy > 1) {
					World.getLoadedChunk().setBlockID(gm.cX+xx-2, gm.cY+yy-2, World.currentZ+1, -1);
				}
			}
		}
		super.doWhenDestroyed(gm, gc);
	}

}
