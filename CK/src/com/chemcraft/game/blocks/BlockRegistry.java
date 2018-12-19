package com.chemcraft.game.blocks;

import java.util.ArrayList;

import com.chemcraft.engine.Light;
import com.chemcraft.engine.gfx.Image;

public class BlockRegistry 
{
	protected static BlockBase block;
	
	protected static final Light LIGHT_WHITE = new Light(256, 0XFFFFFFFF);
	public static ArrayList<BlockBase> registeredBlocks = new ArrayList<BlockBase>();
	
	public static void RegisterBlocks(){
		//0
		registeredBlocks.add(block = new BlockBase("Stone", "stone", 0).setColidable(true));
		//1
		registeredBlocks.add(block = new BlockBase("Grass", "grass", 1));
		//2
		registeredBlocks.add(block = new BlockBase("Dirt", "dirt", 1));
		//3
		registeredBlocks.add(block = new BlockBase("Water", "water", -1).setColidable(true));
		//4
		registeredBlocks.add(block = new BlockBase("Sand", "sand", 2));
		//5
		registeredBlocks.add(block = new BlockBase("Log", "log", 3).setColidable(true).setCanOverPlace(true));
		//6
		registeredBlocks.add(block = new BlockBase("Log Top", "logtop", 3).setColidable(true).setCanOverPlace(true));
		//7
		registeredBlocks.add(block = new BlockBase("Planks", "planks", 4).setColidable(true).setCanOverPlace(true).setLightBlock(Light.FULL));
		//8
		registeredBlocks.add(block = new BlockLeavs("Leaves", "leaves", -1).setCanOverPlace(true));
		//9
		registeredBlocks.add(block = new BlockBase("Coal Ore", "coalore", 5));
		//10
		registeredBlocks.add(block = new BlockBase("Iron Ore", "ironore", 6));
		//11
		registeredBlocks.add(block = new BlockBase("Gold Ore", "goldore", 7));
		//12
		registeredBlocks.add(block = new BlockBase("Diamond Ore", "diamondore", 8));
		//13
		registeredBlocks.add(block = new BlockBase("Emerald Ore", "emeraldore", 9));
		//14
		registeredBlocks.add(block = new BlockBase("Cobalt Ore", "cobaltore", 10));
		//15
		registeredBlocks.add(block = new BlockBase("Copper Ore", "copperore", 11));
		//16
		registeredBlocks.add(block = new BlockBase("Lithium Ore", "lithiumore", 12));
		//17
		registeredBlocks.add(block = new BlockBase("Tin Ore", "tinore", 13));
		//18
		registeredBlocks.add(block = new BlockBase("Uranium Ore", "uraniumore", 14));
		//19
		registeredBlocks.add(block = new BlockBase("Zinc Ore", "zincore", 15));
		//20
		registeredBlocks.add(block = new BlockBase("Torch", "torch", 16).setLight(LIGHT_WHITE));
		//21
		registeredBlocks.add(block = new BlockBase("Wood Floor", "woodfloor", -1));
		//22
		registeredBlocks.add(block = new BlockBase("Glass", "glass", 17).setColidable(true));
		//23
		registeredBlocks.add(block = new BlockMineHatch("Mine Hatch", "minehatch", 18).setLight(LIGHT_WHITE));
		//24
		registeredBlocks.add(block = new BlockBase("Cobble Stone", "cobblestone", 0));
		//25
		registeredBlocks.add(block = new BlockDoor("Door", "blank", 38));
		//26
		registeredBlocks.add(block = new BlockBase("Ash Log", "ashlog", 39).setColidable(true).setLightBlock(Light.FULL));
		//27
		registeredBlocks.add(block = new BlockBase("Birch Log", "birchlog", 40).setColidable(true).setLightBlock(Light.FULL));
		//28
		registeredBlocks.add(block = new BlockBase("Cedar Log", "cedarlog", 41).setColidable(true).setLightBlock(Light.FULL));		
		//29
		registeredBlocks.add(block = new BlockBase("Cherry Log", "cherrylog", 42).setColidable(true).setLightBlock(Light.FULL));		
		//30
		registeredBlocks.add(block = new BlockBase("Pine Log", "pinelog", 43).setColidable(true).setLightBlock(Light.FULL));		
		//31
		registeredBlocks.add(block = new BlockBase("Ash Plank", "ashplank", 44).setColidable(true).setLightBlock(Light.FULL));		
		//32
		registeredBlocks.add(block = new BlockBase("Birch Plank", "birchplank", 45).setColidable(true).setLightBlock(Light.FULL));		
		//33
		registeredBlocks.add(block = new BlockBase("Cedar Plank", "cedarplank", 46).setColidable(true).setLightBlock(Light.FULL));		
		//34
		registeredBlocks.add(block = new BlockBase("Cherry Plank", "cherryplank", 47).setColidable(true).setLightBlock(Light.FULL));		
		//35
		registeredBlocks.add(block = new BlockBase("Pine Plank", "pineplank", 48).setColidable(true).setLightBlock(Light.FULL));
		//36
		registeredBlocks.add(block = new BlockFurnace("Furnace", "blank", 49).setColidable(true));
		
		
		
		
		
	}
	public static Image getImageFromID(int ID)
	{
		return BlockRegistry.registeredBlocks.get(ID).getImage();
	}
	public static boolean getCanOverPlace(int ID)
	{
		if (ID == -1 ) {
			return false;
		}
		return registeredBlocks.get(ID).canOverPlace();
	}
	
}
