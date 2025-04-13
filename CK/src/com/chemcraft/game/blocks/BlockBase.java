package com.chemcraft.game.blocks;


import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Light;
import com.chemcraft.engine.gfx.Image;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.world.World;

public class BlockBase {
private int itemDropID = 0;
private Image image;
private String unlocalized_name = "";
private String name = "";
private boolean colidable = false;
private boolean canoverplace = false;
private Light light;
private boolean haslight = false;
private int toolTierNeeded = -1;
private int blockHardness = 5;


	public BlockBase(String name, String unlocalized_name, int itemDropID)
	{
		image = new Image("/images/blocks/" + unlocalized_name + ".png");
		this.name = name;
		this.unlocalized_name = unlocalized_name;
		this.itemDropID = itemDropID;
	}
	public int getDropItemID()
	{
		return this.itemDropID;
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
	public BlockBase setColidable(boolean colidable)
	{
		this.colidable = colidable;
		return this;
	}
	public boolean isColidable()
	{
		return this.colidable;
	}
	public BlockBase setCanOverPlace(boolean canoverplace)
	{
		this.canoverplace = canoverplace;
		return this;
	}
	public boolean canOverPlace()
	{
		return this.canoverplace;
	}
	public void doWhenDestroyed(GameManager gm, GameContainer gc)
	{
		//make the block around them able to render for mining
		for (int xx = 0; xx < 3; xx++) {
			for (int yy = 0; yy < 3; yy++) {
				if (gm.cX+xx-1 < 32 && gm.cX+xx-1 >= 0 && gm.cY+yy-1 < 32 && gm.cY+yy-1 >= 0) {
					World.getLoadedChunk().setBlockRndr(gm.cX+xx-1, gm.cY+yy-1, World.currentZ, 1);
				}
			}
		}
		//drop the item relivent to the block
		if (!(this.itemDropID == -1)) {
			World.getLoadedChunk().createItemInChunk(this.itemDropID, gm.cX*16, gm.cY*16, World.currentZ);
		}
	}
	public void doWhenRightClick(int x, int y, int z)
	{
		//just right click stuff for uf.. DOOOOOOOOOOOOOOORS
	}
	public void doOnCreate(int x, int y, int currentZ, GameManager gm, GameContainer gc)
	{
		//just stuff to do when its created
	}
	
	public BlockBase setLight(Light light)
	{
		this.light = light;
		this.haslight = true;
		return this;
	}
	
	public boolean hasLight()
	{
		return this.haslight;
	}
	public Light getLight(){
		return this.light;
	}
	public BlockBase setLightBlock(int lightblock)
	{
		image.setLightBlock(lightblock);
		return this;
	}
	public int getToolTier()
	{
		return this.toolTierNeeded;
	}
	public BlockBase setToolTierNeeded(int toolTierNeeded) 
	{
		this.toolTierNeeded = toolTierNeeded;
		return this;
	}
	public int getBlockHardness()
	{
		return this.blockHardness;
	}
	public BlockBase setBlockHardness(int blockhardness)
	{
		this.blockHardness = blockhardness;
		return this;
	}
	
}
