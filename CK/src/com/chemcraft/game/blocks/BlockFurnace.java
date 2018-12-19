package com.chemcraft.game.blocks;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.gfx.ImageTile;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.tileentity.TileEntityRegistry;
import com.chemcraft.game.tileentity.TileSmelter;
import com.chemcraft.game.world.World;

public class BlockFurnace extends BlockBase 
{
	public BlockFurnace(String name, String unlocalized_name, int itemDropID) {
		super(name, unlocalized_name, itemDropID);
		
	}
	@Override
	public void doOnCreate(int x, int y, int currentZ, GameManager gm, GameContainer gc) {
		//this needs to be put in the chunk class
		TileEntityRegistry.createTile(x, y, currentZ, TileEntityRegistry.TILE_SMELTER, gm, gc);
		
	}
	@Override
	public void doWhenDestroyed(GameManager gm, GameContainer gc) {
		TileEntityRegistry.removeTile(gm.cX, gm.cY, World.currentZ);
		World.getLoadedChunk().removeInv(gm.cX, gm.cY, World.currentZ);
		TileSmelter.currentinventory = null;
		super.doWhenDestroyed(gm, gc);
	}
	@Override
	public void doWhenRightClick(int x, int y, int z) {
		TileEntityRegistry.handleTileRightClick(x, y, z);
	}
	

}
