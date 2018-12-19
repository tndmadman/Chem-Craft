package com.chemcraft.game.world;

import java.util.ArrayList;

public class OreObject {
	
	
	protected static OreObject ore;
	
	private int oreID = 0;
	private int oreFreq = 0;
	private String oreName = "";
	public static ArrayList<OreObject> ores = new ArrayList<OreObject>();
	
	public static void addOre(int id, int freq, String name)
	{
		ore = new OreObject().setOreFreq(freq).setOreID(id).setOreName(name);
		ores.add(ore);
		ore = null;
	}
	
	
	
	public static int getFreqByName(String name)
	{
		int loc = 0;
		for (int i = 0; i < ores.size(); i++) {
			if (ores.get(i).getOreName().equals(name)) {
				loc = i;
			}
		}
		if (loc >= 0) {
			return ores.get(loc).getOreFreq();
		}
		return 1;
	}
	
	
	public static int getIDByName(String name){
		int loc = 0;
		for (int i = 0; i < ores.size(); i++) {
			if (ores.get(i).getOreName().equals(name)) {
				loc = i;
			}
		}
		if (loc >= 0) {
			return ores.get(loc).getOreID();
		}
		return 5;
	}
	
	public String getOreName()
	{
		return this.oreName;
	}
	public OreObject setOreName(String name){
		this.oreName = name;
		return this;
	}
	public int getOreID()
	{
		return this.oreID;
	}
	public OreObject setOreID(int id)
	{
		this.oreID = id;
		return this;
	}
	public int getOreFreq()
	{
		return this.oreFreq;
	}
	public  OreObject setOreFreq(int oreFreq)
	{
		this.oreFreq = oreFreq;
		return this;
	}
	
	
	
}
