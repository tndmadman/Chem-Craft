package com.chemcraft.game.tileentity;


import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Renderer;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.world.World;

public class TileEntityRegistry 
{
	public static final TileDoor TILE_DOOR = new TileDoor();
	public static final TileSmelter TILE_SMELTER = new TileSmelter();
	
	
	
	public static void createTile(int x, int y, int z, TileEntityBase tile, GameManager gm, GameContainer gc)
	{
		try {
			tile.setLoc(x, y, z);
			World.getLoadedChunk().getTilesInChunk().add((TileEntityBase) tile.clone());
			World.getLoadedChunk().getTilesInChunk().get(World.getLoadedChunk().getTilesInChunk().size()-1).doOnCreate(gm, gc);;

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	public static void renderTiles(GameManager gm, Renderer r)
	{
		for (int i = 0; i < World.getLoadedChunk().getTilesInChunk().size(); i++) {
			
			if (World.getLoadedChunk().getTilesInChunk().get(0).getZ() == World.currentZ) {
				World.getLoadedChunk().getTilesInChunk().get(i).tileGraphics(gm, r);
			}
			
		}
	}
	public static void tileLogics(GameManager gm, GameContainer gc)
	{
		for (int i = 0; i < World.getLoadedChunk().getTilesInChunk().size(); i++) {
			World.getLoadedChunk().getTilesInChunk().get(i).tileLogic(gm, gc);
		}
	}
	public static void handleTileRightClick(int x, int y, int z)
	{
		int ID = -1;
		for (int i = 0; i < World.getLoadedChunk().getTilesInChunk().size(); i++) {
			if (World.getLoadedChunk().getTilesInChunk().get(i).testLoc(x, y, z)) {
				ID = i;
			}
		}
		if (!(ID == -1)) {
			World.getLoadedChunk().getTilesInChunk().get(ID).tileRightClick(x, y, z);
		}
	}
	public static boolean isColidable(int x, int y, int z)
	{
		for (int i = 0; i < World.getLoadedChunk().getTilesInChunk().size(); i++) {
			if (World.getLoadedChunk().getTilesInChunk().get(i).testLoc(x, y, z)) {
				if (World.getLoadedChunk().getTilesInChunk().get(i).isColidable()) {
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	public static void removeTile(int x, int y, int z)
	{
		for (int i = 0; i < World.getLoadedChunk().getTilesInChunk().size(); i++) {
			if (World.getLoadedChunk().getTilesInChunk().get(i).testLoc(x, y, z)) {
				World.getLoadedChunk().getTilesInChunk().remove(i);
				return;
			}
		}
	}
}
