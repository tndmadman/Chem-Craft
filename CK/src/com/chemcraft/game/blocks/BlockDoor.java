package com.chemcraft.game.blocks;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.tileentity.TileEntityRegistry;
import com.chemcraft.game.world.World;

public class BlockDoor extends BlockBase 
{

	public BlockDoor(String name, String unlocalized_name, int itemDropID) {
		super(name, unlocalized_name, itemDropID);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void doWhenRightClick(int x, int y, int z) {
		TileEntityRegistry.handleTileRightClick(x, y, z);
		
	}
	@Override
	public void doOnCreate(int x, int y, int z, GameManager gm, GameContainer gc) {
		TileEntityRegistry.createTile(x, y, z, TileEntityRegistry.TILE_DOOR, gm, gc);
		
	}
	@Override
	public void doWhenDestroyed(GameManager gm, GameContainer gc) {
		TileEntityRegistry.removeTile(gm.cX, gm.cY, World.currentZ);
		super.doWhenDestroyed(gm, gc);
		
	}

}
