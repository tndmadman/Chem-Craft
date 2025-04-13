package com.chemcraft.game.tileentity;

import java.util.ArrayList;
import java.util.Random;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Renderer;
import com.chemcraft.engine.gfx.ImageTile;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.Gui.GuiBase;
import com.chemcraft.game.inventorys.InventoryBase;
import com.chemcraft.game.inventorys.InvetoryRegistry;
import com.chemcraft.game.math.Smelting;
import com.chemcraft.game.world.World;

public class TileSmelter extends TileEntityBase
{
	protected int counter = 0;
	protected int counterMax = 20;
	protected int rx = 0;
	protected int ry = 0;
	protected int invoffx = 16;
	protected int invoffy = 160;
	protected int hotbaroffx = 161;
	protected int hotbaroffy = 493;
	protected int resourcex = 107;
	protected int resourcey = 47;	
	protected int fuelx = 107;
	protected int fuely = 80;
	protected int outputx = 144;
	protected int outputy = 63;
	protected ImageTile tile = new ImageTile("/images/blocks/furnacetile.png", 16, 16);
	protected boolean isActive = false;
	protected InventoryBase inventory;
	public static InventoryBase currentinventory;
	protected float fuellevel = 0f;
	protected int craftingtime = 0;
	public static int publiccraftingtime;
	public static int publicfuellevel;
	protected int craftingtimemax = 200;
	public TileSmelter()
	{
		
	}
	
	
	
	@Override
	public void doOnCreate(GameManager gm, GameContainer gc) {
		//create the inv for later
		InvetoryRegistry.SMELT_INVENTORY = null;
		World.getLoadedChunk().createInventory(gm.cX, gm.cY, World.currentZ, InvetoryRegistry.SMELT_INVENTORY  = new InventoryBase(2));
		inventory = World.getLoadedChunk().getInventoryXYZ(gm.cX, gm.cY, World.currentZ);
		InvetoryRegistry.SMELT_INVENTORY.getOutput().setLoc(128 + outputx, 128 + outputy);
		InvetoryRegistry.SMELT_INVENTORY.getInternal()[0].setLoc(128 + resourcex, 128 + resourcey);
		InvetoryRegistry.SMELT_INVENTORY.getInternal()[1].setLoc(128 + fuelx, 128 + fuely);
	}
	
	
	
	@Override
	public void tileRightClick(int x, int y, int z) {
		//handle setting the registered gui to this tile
		currentinventory = this.inventory;
		GuiBase.guis.get(1).setCanGuiRender(true);;
	}
	
	
	
	
	@Override
	public void tileGraphics(GameManager gm, Renderer r) {
		//handle drawing the fun stuffs of the buitifull furnace
		counter ++;
		if (counter > counterMax) {
			counter = 0;
		}
		
		if (counter == 10) {
			Random rand = new Random();
			this.rx = rand.nextInt(4);
			this.ry = rand.nextInt(3);
		}
		
		
		if (this.isActive) {
			r.drawImageTile(this.tile, super.getX()*16, super.getY()*16, this.rx, this.ry);
		}else{
			r.drawImageTile(this.tile, super.getX()*16, super.getY()*16, 0, 3);
		}
		
	}
	
	
	
	@Override
	public void tileLogic(GameManager gm, GameContainer gc) {
		//handle the crafting/smelting shit
		if (this.fuellevel < 20) {
			float fuelput = Smelting.isFuel(inventory.getInternal()[1].getID());
			if (!(fuelput == -1)) {
				if (inventory.getInternal()[1].getStack() <= 0) {
					this.fuellevel += fuelput;
					inventory.getInternal()[1].setID(-1);
					inventory.getInternal()[1].setStack(0);
				}else{
					this.fuellevel += fuelput;
					inventory.getInternal()[1].setStack(inventory.getInternal()[1].getStack() -1);
				}
			}
		}
		if (fuellevel > 4) {
			publiccraftingtime = this.craftingtime;
			publicfuellevel = (int) this.fuellevel;
			int testOutputID = Smelting.testRecipie(inventory.getInternalStackID(0));
			if (testOutputID == -1) {
				return;
			}else{
				this.isActive = true;
			}
			if (isActive) {
				craftingtime ++;
				if (craftingtime >= craftingtimemax) {
					craftingtime = 0;
					
					if (inventory.getInternal()[0].getStack() >1) {
						inventory.getInternal()[0].setStack(inventory.getInternal()[0].getStack() -1);
					}else if (inventory.getInternal()[0].getStack() == 1) {
						inventory.getInternal()[0].setID(-1);
						inventory.getInternal()[0].setStack(0);
					}
					
					
					
					inventory.getOutput().setID(testOutputID);
					inventory.getOutput().setStack(inventory.getOutput().getStack()+1);
					this.fuellevel -= 4;
					isActive = false;
					
				}
			}
		}
		
	}
	
}
