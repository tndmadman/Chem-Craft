package com.chemcraft.game.world;


import java.util.ArrayList;
import java.util.Random;

import com.chemcraft.game.inventorys.InventoryBase;
import com.chemcraft.game.inventorys.InvetoryRegistry;
import com.chemcraft.game.items.ItemBase;
import com.chemcraft.game.items.ItemRegistry;
import com.chemcraft.game.math.WorldNoise;
import com.chemcraft.game.tileentity.TileEntityBase;

public class Chunk {
	private int x = 0;
	private int y = 0;
	private int[][][] blocks = new int[32][32][66];
	private ArrayList<ItemBase> items = new ArrayList<ItemBase>();
	private ArrayList<TileEntityBase> tiles = new ArrayList<TileEntityBase>();
	private ArrayList<InventoryBase> inventorys = new ArrayList<InventoryBase>();

	public Chunk(int x, int y, WorldNoise surficenoise, WorldNoise biomenoise, int offX, int offY){
		this.x = x;
		this.y = y;
		for (int xx = 0; xx < 32; xx++) {
			for (int yy = 0; yy < 32; yy++) {
				//gen the top layer
					blocks[xx][yy][63] = Regions.getRegionTypeSurfice(offX + xx, offY + yy, surficenoise);
			}
		}
		Random rand = new Random(0);
		for (int xx = 0; xx < 32; xx++) {
			for (int yy = 0; yy < 32; yy++) {
				//clear the current and top layer
				blocks[xx][yy][64] = -1;
				blocks[xx][yy][65] = -1;
				
			
			}
		}
		
		for (int zz = 0; zz < 62; zz++) {
			for (int xx = 0; xx < 32; xx++) {
				for (int yy = 0; yy < 32; yy++) {
					int r = rand.nextInt(60);
					if (r == 1) {
						int[][] ore = BuldableOres.getOre();
						for (int xxx = 0; xxx < 4; xxx++) {
							for (int yyy = 0; yyy < 4; yyy++) {
								if (xxx+xx <= 31 && yyy+yy <= 31) {
									if (!(ore[xxx][yyy] == 0)) {
										blocks[xxx+xx][yyy+yy][zz] = OreRegions.getOreTier(zz);	
									}
								}
							}
						}
					
					}
				}
			}
		}
		for (int xx = 0; xx < 32; xx++) {
			for (int yy = 0; yy < 32; yy++) {
				int r = rand.nextInt(60);
				if (r == 1 && blocks[xx][yy][63] == 1) {
					int[][] treetop = BuldableTrees.getTop();
					int[][] treebase = BuldableTrees.getBase();
					for (int xxx = 0; xxx < 5; xxx++) {
						for (int yyy = 0; yyy < 5; yyy++) {
							if (xxx+xx <= 28 && yyy+yy <= 28 && xxx-3+xx >= 0 && yyy-3+yy >= 0) {
								if (!(treetop[xxx][yyy] == 0)) {
									blocks[xxx-3+xx][yyy-3+yy][65] = TreeRegions.BLOCK_LEAVES;
								}
								if (!(treebase[xxx][yyy] == 0)) {
									blocks[xxx-3+xx][yyy-3+yy][64] = TreeRegions.getRegionTypeBiome(xxx + xx + offX, yyy + yy + offY, biomenoise);
									
								}
							}
						}
					}
				
				}
			}
		}
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public int getBlockID(int x, int y, int z)
	{
		return this.blocks[x][y][z];
	}
	public void setBlockID(int x, int y, int z, int ID)
	{
		this.blocks[x][y][z] = ID;
	}
	public void createItemInChunk(int ID, int x, int y, int z)
	{
		ItemBase item = ItemRegistry.registeredItems.get(ID);
		ItemBase newItem = new ItemBase(ID, item.getName(), item.getUnlocalizedName(), item.getBlockType(), item.getWeight());
		newItem.setLoc(x, y, z);
		items.add(newItem);
		items.get(items.size()-1).doOnCreate();
		item = null;
		newItem = null;
		
	}
	public ArrayList<ItemBase> getItemsInChunk()
	{
		return this.items;
	}
	public ArrayList<TileEntityBase> getTilesInChunk(){
		return this.tiles;
	}
	
	
	public void removeInv(int x, int y, int z)
	{
		for (int i = 0; i < inventorys.size(); i++) {
			if (inventorys.get(i).testLoc(x, y, z)) {
				System.out.println("removed");
				inventorys.remove(i);
				return;
			}
		}
	}
	public void createInventory(int x, int y, int z, InventoryBase inventory)
	{
		try {
			inventory.setLoc(x, y, z);
			System.out.println(inventory.getInternalStackCount(1));
			inventorys.add((InventoryBase) inventory.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	public InventoryBase getInventoryXYZ(int x, int y, int z)
	{
		for (int i = 0; i < inventorys.size(); i++) {
			if (inventorys.get(i).testLoc(x, y, z)) {
				return inventorys.get(i);
			}
		}
		return null;
	}

}
