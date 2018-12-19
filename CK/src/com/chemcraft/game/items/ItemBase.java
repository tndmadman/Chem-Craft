package com.chemcraft.game.items;

import java.util.Random;

import com.chemcraft.engine.gfx.Image;

public class ItemBase {
	private Image image;
	private String unlocalized_name = "";
	private String name = "";
	private int blockType = -0;
	private float weight = 0f;
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private int ID = 0;
	
	
	public ItemBase(int ID,String name, String unlocalizedname, int blocktype, float weight)
	{
		this.ID = ID;
		image = new Image("/images/items/" + unlocalizedname + ".png");
		this.name = name;
		this.unlocalized_name = unlocalizedname;
		this.blockType = blocktype;
		this.weight = weight;
	}
	public void doOnCreate()
	{
		Random rand = new Random();
		
		int maxMove = rand.nextInt(16);
		for (int i = 0; i < maxMove; i++) {
			int r = rand.nextInt(4);
			if (r == 1) {
				this.x -= 1;
			}
			if (r == 2) {
				this.x += 1;
			}
			if (r == 3) {
				this.y -= 1;
			}
			if (r == 4) {
				this.y += 1;
			}
		}
	}
	public Image getImage()
	{
		return this.image;
	}
	public String getUnlocalizedName()
	{
		return this.unlocalized_name;
	}
	public String getName()
	{
		return this.name;
	}
	public int getBlockType()
	{
		return this.blockType;
	}
	public float getWeight()
	{
		return this.weight;
	}
	public void setLoc(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public int getZ()
	{
		return this.z;
	}
	public int getID()
	{
		return this.ID;
	}
	public void setID(int ID)
	{
		this.ID = ID;
	}
	public void doOnUse(){
		
	}
}