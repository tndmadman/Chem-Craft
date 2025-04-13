package com.chemcraft.game.items;

import java.util.ArrayList;

import com.chemcraft.engine.gfx.Image;

public class ItemRegistry {
	
	protected static ItemBase item;
	public static ArrayList<ItemBase> registeredItems = new ArrayList<ItemBase>();
	
	
	
	public static void RegisterItems()
	{
		registeredItems.add(item = new ItemBase(0, "Cobble", "cobble", 24, 1.0f));
		registeredItems.add(item = new ItemBase(1, "Dirt", "dirt", 2, 1.0f));
		registeredItems.add(item = new ItemBase(2, "Sand", "sand", 4, 1.0f));
		registeredItems.add(item = new ItemBase(3, "Oak Log", "oaklog", 5, 1.0f));
		registeredItems.add(item = new ItemBase(4, "Planks", "oakplank", 7, 1.0f));
		registeredItems.add(item = new ItemBase(5, "Coal ore", "coalore", 9, 1.0f));
		registeredItems.add(item = new ItemBase(6, "Iron Ore", "ironore", 10, 1.0f));
		registeredItems.add(item = new ItemBase(7, "Gold Ore", "goldore", 11, 1.0f));
		registeredItems.add(item = new ItemBase(8, "Diamond Ore", "diamondore", 12, 1.0f));
		registeredItems.add(item = new ItemBase(9, "Emerald Ore", "emeraldore", 13, 1.0f));
		registeredItems.add(item = new ItemBase(10, "Cobalt Ore", "cobaltore", 14, 1.0f));
		registeredItems.add(item = new ItemBase(11, "Copper Ore", "copperore", 15, 1.0f));
		registeredItems.add(item = new ItemBase(12, "Lithium Ore", "lithiumore", 16, 1.0f));
		registeredItems.add(item = new ItemBase(13, "Tin Ore", "tinore", 17, 1.0f));
		registeredItems.add(item = new ItemBase(14, "Uranium Ore", "uraniumore", 18, 1.0f));
		registeredItems.add(item = new ItemBase(15, "Zinc Ore", "zincore", 19, 1.0f));
		registeredItems.add(item = new ItemBase(16, "Torch", "torch", 20, 1.0f));
		registeredItems.add(item = new ItemBase(17, "Glass", "glass", 22, 1.0f));
		registeredItems.add(item = new ItemBase(18, "Hatch", "hatch", 23, 1.0f));
		registeredItems.add(item = new ItemBase(19, "Bismuth Dust", "bismuthdust", 22, 1.0f));
		registeredItems.add(item = new ItemBase(20, "Coal Dust", "coaldust", -1, 1.0f));
		registeredItems.add(item = new ItemBase(21, "Salt Dust", "saltdust", -1, 1.0f));
		registeredItems.add(item = new ItemBase(22, "Sulfer Dust", "sulferdust", -1, 1.0f));
		registeredItems.add(item = new ItemBase(23, "Aluminium Ingot", "aluminiumingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(24, "Bronze Ingot", "bronzeingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(25, "Cobalt Ingot", "cobaltingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(26, "Copper Ingot", "copperingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(27, "Gold Ingot", "goldingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(28, "Iron Ingot", "ironingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(29, "Lead Ingot", "leadingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(30, "Nicle Ingot", "nicleingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(31, "Obsidian Ingot", "obsidianingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(32, "Platinum Ingot", "platinumingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(33, "Silver Ingot", "silveringot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(34, "Steal Ingot", "stealingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(35, "Tin Ingot", "tiningot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(36, "Uranium Ingot", "uraniumingot", -1, 1.0f));
		registeredItems.add(item = new ItemBase(37, "Stick", "stick", -1, 1.0f));
		registeredItems.add(item = new ItemBase(38, "Door", "door", 25, 1.0f));
		registeredItems.add(item = new ItemBase(39, "Ash Log", "ashlog", 26, 1.0f));
		registeredItems.add(item = new ItemBase(40, "Birch Log", "birchlog", 27, 1.0f));
		registeredItems.add(item = new ItemBase(41, "Cedar Log", "cedarlog", 28, 1.0f));
		registeredItems.add(item = new ItemBase(42, "Cherry Log", "cherrylog", 29, 1.0f));
		registeredItems.add(item = new ItemBase(43, "Pine Log", "pinelog", 30, 1.0f));
		registeredItems.add(item = new ItemBase(44, "Ash Plank", "ashplank", 31, 1.0f));
		registeredItems.add(item = new ItemBase(45, "Birch Plank", "birchplank", 32, 1.0f));
		registeredItems.add(item = new ItemBase(46, "Cedar Plank", "cedarplank", 33, 1.0f));
		registeredItems.add(item = new ItemBase(47, "Cherry Plank", "cherryplank", 34, 1.0f));
		registeredItems.add(item = new ItemBase(48, "Pine Plank", "pineplank", 35, 1.0f));
		registeredItems.add(item = new ItemBase(49, "Furnace", "furnace", 36, 1.0f));
		registeredItems.add(item = new ItemBase(50, "Charcoal", "charcoaldust", -1, 1.0f));
		registeredItems.add(item = new ItemBase(51, "Wood Pick", "woodpick", -1, 1.0f).setToolTier(2).setToolEffectiveness(2).setToolHealth(20).setCanStack(false));
		registeredItems.add(item = new ItemBase(52, "Stone Pick", "stonepick", -1, 1.0f).setToolTier(3).setToolEffectiveness(4).setToolHealth(40).setCanStack(false));
		registeredItems.add(item = new ItemBase(53, "Iron Pick", "ironpick", -1, 1.0f).setToolTier(4).setToolEffectiveness(10).setToolHealth(60).setCanStack(false));
		registeredItems.add(item = new ItemBase(54, "Copper Pick", "copperpick", -1, 1.0f).setToolTier(5).setToolEffectiveness(10).setToolHealth(70).setCanStack(false));
		registeredItems.add(item = new ItemBase(55, "Steel Pick", "steelpick", -1, 1.0f).setToolTier(6).setToolEffectiveness(15).setToolHealth(140).setCanStack(false));
		
		
		


	}
	
	public static Image getImageFromID(int ID){
		return registeredItems.get(ID).getImage();
	}
	public static String getNameFromID(int ID)
	{
		if(ID == -1)
		{
			return null;
		}
		return registeredItems.get(ID).getName();
	}
	public static float getWeightFromID(int ID)
	{
		if(ID == -1)
		{
			return -1;
		}
		return registeredItems.get(ID).getWeight();
	}
	public static int getBlockTypeFromID(int ID)
	{
		if(ID == -1)
		{
			return -1;
		}
		return registeredItems.get(ID).getBlockType();
	}
	public static int getToolTier(int ID)
	{
		if(ID == -1)
		{
			return -1;
		}
		return registeredItems.get(ID).getToolTier();
	}
	public static int getToolEffectiveness(int ID)
	{
		if(ID == -1)
		{
			return -1;
		}
		return registeredItems.get(ID).getToolEffectivness();
	}
	public static int getNewToolHealth(int ID)
	{
		return registeredItems.get(ID).getToolHealth();
	}
	public static boolean canStack(int ID)
	{
		return registeredItems.get(ID).canStack();
	}
}
