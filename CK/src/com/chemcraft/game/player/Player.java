package com.chemcraft.game.player;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Renderer;
import com.chemcraft.engine.gfx.Image;
import com.chemcraft.engine.gfx.ImageTile;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.Gui.GuiBase;
import com.chemcraft.game.blocks.BlockRegistry;
import com.chemcraft.game.items.ItemRegistry;
import com.chemcraft.game.math.ItemStack;
import com.chemcraft.game.tileentity.TileEntityRegistry;
import com.chemcraft.game.world.World;

public class Player 
{
	public static int selected = 0;

	public static int invsX = 11;
	public static int invsY = 4;

	public static ItemStack[][] invArray = new ItemStack[11][4];
	public static ItemStack[][] craftingGrid = new ItemStack[3][3];
	public static ItemStack[][] hotbar = new ItemStack[10][1];
	public static ItemStack grid;

	public static ItemStack output;
	
	protected static int drawoffx = 0;
	protected static int drawoffy = 0;
	public static Image hotbar_selected_img;
	
	protected static ImageTile playerImage;
	public static int x = 0;
	public static int y = 0;
	protected static int currentDirection = 0;
	public static int mapX = 0;
	public static int mapY = 0;
	protected static int timer = 0;
	protected static int maxTimer = 20;

	public static Image hotbar_img;
	public static void initPlayer(){

		playerImage = new ImageTile("/images/player.png", 16, 16);
		hotbar_img = new Image("/images/inv.png");
		hotbar_selected_img = new Image("/images/selected.png");
		for (int xx = 0; xx < invsX; xx++)
		{
			for (int yy = 0; yy < invsY; yy++)
			{
				invArray[xx][yy] = grid = new ItemStack(xx * 16, yy * 16, -1);
			}
		}
		for (int xx = 0; xx < 3; xx++)
		{
			for (int yy = 0; yy < 3; yy++)
			{
				craftingGrid[xx][yy] = grid = new ItemStack(xx * 16, yy * 16, -1);
			}
		}
		output = new ItemStack(128 + 33, 128 + 97, -1);
		for (int xx = 0; xx < 10; xx++)
		{
			hotbar[xx][0] = grid = new ItemStack(xx * 16, 0, -1);
		}
		
		
		
	}
	
	public static void PlayerLogic(GameManager gm, GameContainer gc)
	{

		//handle picking up items from the world
		for (int i = 0; i < World.getLoadedChunk().getItemsInChunk().size(); i++) {
			int iX = World.getLoadedChunk().getItemsInChunk().get(i).getX();
			int iY = World.getLoadedChunk().getItemsInChunk().get(i).getY();
			
			if (x*16-8 <= iX && x*16+20 >= iX && y*16-8 < iY && y*16+20 > iY) {
				addItemToInv(World.getLoadedChunk().getItemsInChunk().get(i).getID());
				World.getLoadedChunk().getItemsInChunk().remove(i);
			}
		}
		
		
		//simple timer for moving and clicking later
		timer += 1;
		if (timer == maxTimer) {
			timer = 0;
		}
		//handling key events
		if (timer == 6)
		{
			if (gc.getInput().isKey(KeyEvent.VK_A))
			{
				moveLeft();
			}
			if (gc.getInput().isKey(KeyEvent.VK_D))
			{
				moveRight();
			}
			if (gc.getInput().isKey(KeyEvent.VK_W))
			{
				moveUp();
			}
			if (gc.getInput().isKey(KeyEvent.VK_S))
			{
				moveDown();
			}
			if (gc.getInput().isKey(KeyEvent.VK_SHIFT)) {
				System.out.println(World.currentZ);
				System.out.println(World.getLoadedChunk().getBlockID(x, y, 63));
				if (World.getLoadedChunk().getBlockID(x, y, 63) == 23) {
					if (World.currentZ < 64) {
						World.currentZ += 1;
					}
				}
			}
			if (gc.getInput().isKey(KeyEvent.VK_CONTROL)) {
				System.out.println(World.currentZ);
				System.out.println(World.getLoadedChunk().getBlockID(x, y, 63));
				if (World.getLoadedChunk().getBlockID(x, y, 63) == 23) {
					if (World.currentZ > 1) {
						World.currentZ -= 1;
					}
				}
			}
			
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_1))
		{
			selected = 0;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_2))
		{
			selected = 1;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_3))
		{
			selected = 2;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_4))
		{
			selected = 3;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_5))
		{
			selected = 4;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_6))
		{
			selected = 5;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_7))
		{
			selected = 6;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_8))
		{
			selected = 7;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_9))
		{
			selected = 8;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_0))
		{
			selected = 9;
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_E))
		{
			GuiBase.guis.get(0).setCanGuiRender(true);
		}
		if (gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE))
		{
			for (int i = 0; i < GuiBase.guis.size(); i++) {
				GuiBase.guis.get(i).setCanGuiRender(false);
			}
		}
		
		
		//right click stuff
		if (gc.getInput().isButtonDown(MouseEvent.BUTTON3)) {
			for (int i = 0; i < GuiBase.guis.size(); i++) {
				if (GuiBase.guis.get(i).canGuiRender() == false) {
					if (!(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ) == -1)) {
						BlockRegistry.registeredBlocks.get(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ)).doWhenRightClick(gm.cX, gm.cY, World.currentZ);;

					}
				}else{
					GuiBase.guis.get(i).handleRightClick(gm, gc);
				}
			}
		}
		
		//left click stuff
		if (gc.getInput().isButtonDown(MouseEvent.BUTTON1))
		{
			timer = 0;
			
			
			for (int i = 0; i < GuiBase.guis.size(); i++) {
				if (GuiBase.guis.get(i).canGuiRender()) {
					GuiBase.guis.get(i).handleLeftClick(gm, gc);
					return;
				}
			}
			

			if (World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ-1 ) == -1 && isEnoughInSlot()) {
				//place block at first level
					World.getLoadedChunk().setBlockID(gm.cX, gm.cY, World.currentZ-1, ItemRegistry.getBlockTypeFromID(hotbar[selected][0].getID()));
					BlockRegistry.registeredBlocks.get(ItemRegistry.getBlockTypeFromID(hotbar[selected][0].getID())).doOnCreate(gm.cX, gm.cY, World.currentZ, gm, gc);;
					removeItem();
			}else if (World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ) == -1 && isEnoughInSlot()) {
				//place block at second level
					World.getLoadedChunk().setBlockID(gm.cX, gm.cY, World.currentZ, ItemRegistry.getBlockTypeFromID(hotbar[selected][0].getID()));
					BlockRegistry.registeredBlocks.get(ItemRegistry.getBlockTypeFromID(hotbar[selected][0].getID())).doOnCreate(gm.cX, gm.cY, World.currentZ, gm, gc);;
					removeItem();
			}
			
			
			//only tunnle 1 when mining
			else if (!(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ) == -1) && World.currentZ < 64) {
				//break block at first level
				BlockRegistry.registeredBlocks.get(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ)).doWhenDestroyed(gm, gc);
				World.getLoadedChunk().setBlockID(gm.cX, gm.cY, World.currentZ, -1);
				return;
			}
			
			
			
			//act as normal on the serfice
			else if (!(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ+1) == -1)) {
				//break block at second level
				BlockRegistry.registeredBlocks.get(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ+1)).doWhenDestroyed(gm, gc);
				World.getLoadedChunk().setBlockID(gm.cX, gm.cY, World.currentZ+1, -1);
			}else if (!(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ) == -1)) {
				//break block at first level
				BlockRegistry.registeredBlocks.get(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ)).doWhenDestroyed(gm, gc);
				World.getLoadedChunk().setBlockID(gm.cX, gm.cY, World.currentZ, -1);
			}else if (!(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ-1) == -1)) {
				//break block at first level
				BlockRegistry.registeredBlocks.get(World.getLoadedChunk().getBlockID(gm.cX, gm.cY, World.currentZ-1)).doWhenDestroyed(gm, gc);
				World.getLoadedChunk().setBlockID(gm.cX, gm.cY, World.currentZ-1, -1);
			}


			
			}
			
		
		
	}
	
	
	
	public static void moveLeft()
	{
		currentDirection = 3;
		if (!(x == 0)) {
			if (!(checkColideable(x - 1, y, World.currentZ)))
			{
				mapX -= 1;
				x -= 1;
			}
		}else{
			//load or generate south chunk
			World.genW();
			x = 31;
		}
	}
	public static void moveRight()
	{
		currentDirection = 1;

		if (!(x == 31)) {
			if (!(checkColideable(x + 1, y, World.currentZ)))
			{
				mapX += 1;
				x += 1;
			}
		}else{
			//load or generate east chunk
			World.genE();
			x = 0;
		}
	}
	public static void moveUp()
	{
		currentDirection = 0;
		if (!(y == 0)) {
			if (!(checkColideable(x, y - 1, World.currentZ)))
			{
				mapY -= 1;
				y -= 1;
			}
		}else{
			//load or generate north chunk
			World.genN();
			y = 31;
		}
	}
	public static void moveDown()
	{
		currentDirection = 2;
		if (!(y == 31)) {
			if (!(checkColideable(x, y + 1, World.currentZ)))
			{
				mapY += 1;
				y += 1;

			}
		}else{
			//load or generate south chunk
			World.genS();
			y = 0;
		}
		
	}
	
	public static void handleInvsLeftClick(GameManager gm, GameContainer gc, ItemStack[][] inv, int offx, int offy, int invX, int invY) {
		drawoffx = 0;
		drawoffy = 0;
		for (int xx = 0; xx < inv.length; xx++)
		{
			for (int yy = 0; yy < inv[0].length; yy++)
			{
				if (gc.getInput().getMouseX() >= drawoffx + invX + inv[xx][yy].getX() + offx && gc.getInput().getMouseX() <= drawoffx + invX + inv[xx][yy].getX() + offx + 16 && gc.getInput().getMouseY() >= drawoffy + invY + inv[xx][yy].getY() + offy && gc.getInput().getMouseY() <= drawoffy + invY + inv[xx][yy].getY() + offy + 16)
				{
					if (!(inv[xx][yy].getID() == -1))
					{
						// if the slot is has something in it do this stuff
						if (gm.itemIDInHand == -1)
						{
							// if the hand is empty transfer the item to the
							// hand
							gm.itemIDInHand = inv[xx][yy].getID();
							gm.itemCountInHand = inv[xx][yy].getStack();
							inv[xx][yy].setID(-1);
							inv[xx][yy].setStack(0);
						} else
						{
							// if the hand has something in it that matches then
							// add it
							if (gm.itemIDInHand == inv[xx][yy].getID())
							{
								inv[xx][yy].setStack(inv[xx][yy].getStack() + gm.itemCountInHand);
								gm.itemCountInHand = 0;
								gm.itemIDInHand = -1;
							} else
							{
								// if the hand has something and does not match,
								// swap the items
								int item = gm.itemIDInHand;
								int count = gm.itemCountInHand;
								gm.itemIDInHand = inv[xx][yy].getID();
								gm.itemCountInHand = inv[xx][yy].getStack();
								inv[xx][yy].setID(item);
								inv[xx][yy].setStack(count);
							}
						}

					} else
					{
						// if the slot has nothing in it do this stuff
						if (!(gm.itemIDInHand == -1))
						{
							inv[xx][yy].setID(gm.itemIDInHand);
							inv[xx][yy].setStack(gm.itemCountInHand);
							gm.itemIDInHand = -1;
							gm.itemCountInHand = 0;
						}
					}
				}
				drawoffy += 4;
			}
			drawoffy = 0;
			drawoffx += 4;
		}
	}
	
	
	
	public static void handleInvsLeftClick(GameManager gm, GameContainer gc, ItemStack inv, int offx, int offy, int invX, int invY) {
		if (gc.getInput().getMouseX() >=  invX  + offx && gc.getInput().getMouseX() <=  invX +  offx + 16 && gc.getInput().getMouseY() >=  invY +  offy && gc.getInput().getMouseY() <=  invY +  offy + 16)
		{
			if (!(inv.getID() == -1))
			{
				// if the slot is has something in it do this stuff
				if (gm.itemIDInHand == -1)
				{
					// if the hand is empty transfer the item to the
					// hand
					gm.itemIDInHand = inv.getID();
					gm.itemCountInHand = inv.getStack();
					inv.setID(-1);
					inv.setStack(0);
				} else
				{
					// if the hand has something in it that matches then
					// add it
					if (gm.itemIDInHand == inv.getID())
					{
						inv.setStack(inv.getStack() + gm.itemCountInHand);
						gm.itemCountInHand = 0;
						gm.itemIDInHand = -1;
					} else
					{
						// if the hand has something and does not match,
						// swap the items
						int item = gm.itemIDInHand;
						int count = gm.itemCountInHand;
						gm.itemIDInHand = inv.getID();
						gm.itemCountInHand = inv.getStack();
						inv.setID(item);
						inv.setStack(count);
					}
				}

			} else
			{
				// if the slot has nothing in it do this stuff
				if (!(gm.itemIDInHand == -1))
				{
					inv.setID(gm.itemIDInHand);
					inv.setStack(gm.itemCountInHand);
					gm.itemIDInHand = -1;
					gm.itemCountInHand = 0;
				}
			}
		}
	}

	public static void handleInvsLeftClick(GameManager gm, GameContainer gc, ItemStack[] inv, int offx, int offy, int invX, int invY) {
		drawoffx = 0;
		drawoffy = 0;

		for (int yy = 0; yy < inv.length; yy++)
		{
			if (gc.getInput().getMouseX() >= drawoffx + invX + inv[yy].getX() + offx && gc.getInput().getMouseX() <= drawoffx + invX + inv[yy].getX() + offx + 16 && gc.getInput().getMouseY() >= drawoffy + invY + inv[yy].getY() + offy && gc.getInput().getMouseY() <= drawoffy + invY + inv[yy].getY() + offy + 16)
			{
				if (!(inv[yy].getID() == -1))
				{
					// if the slot is has something in it do this stuff
					if (gm.itemIDInHand == -1)
					{
						// if the hand is empty transfer the item to the
						// hand
						gm.itemIDInHand = inv[yy].getID();
						gm.itemCountInHand = inv[yy].getStack();
						inv[yy].setID(-1);
						inv[yy].setStack(0);
					} else
					{
						// if the hand has something in it that matches then
						// add it
						if (gm.itemIDInHand == inv[yy].getID())
						{
							inv[yy].setStack(inv[yy].getStack() + gm.itemCountInHand);
							gm.itemCountInHand = 0;
							gm.itemIDInHand = -1;
						} else
						{
							// if the hand has something and does not match,
							// swap the items
							int item = gm.itemIDInHand;
							int count = gm.itemCountInHand;
							gm.itemIDInHand = inv[yy].getID();
							gm.itemCountInHand = inv[yy].getStack();
							inv[yy].setID(item);
							inv[yy].setStack(count);
						}
					}

				} else
				{
					// if the slot has nothing in it do this stuff
					if (!(gm.itemIDInHand == -1))
					{
						inv[yy].setID(gm.itemIDInHand);
						inv[yy].setStack(gm.itemCountInHand);
						gm.itemIDInHand = -1;
						gm.itemCountInHand = 0;
					}
				}
			}
			drawoffy += 4;
		}
		drawoffy = 0;
		drawoffx += 4;
	
	}
	public static void handleRightClickInvs(ItemStack[][] inventory, GameManager gm, GameContainer gc, int offx, int offy, int invX, int invY) {
		drawoffx = 0;
		drawoffy = 0;
		for (int xx = 0; xx < inventory.length; xx++)
		{
			for (int yy = 0; yy < inventory[0].length; yy++)
			{
				if (gc.getInput().getMouseX() >= drawoffx + invX + inventory[xx][yy].getX() + offx && gc.getInput().getMouseX() <= drawoffx + invX + inventory[xx][yy].getX() + offx + 16 && gc.getInput().getMouseY() >= drawoffy + invY + inventory[xx][yy].getY() + offy && gc.getInput().getMouseY() <= drawoffy + invY + inventory[xx][yy].getY() + offy + 16)
				{
					if (inventory[xx][yy].getID() == -1 && !(gm.itemIDInHand == -1))
					{
						// if the hand has stuff and the slot does not add 1 to
						// the slot
						inventory[xx][yy].setID(gm.itemIDInHand);
						inventory[xx][yy].setStack(1);
						gm.itemCountInHand -= 1;
						if (gm.itemCountInHand == 0)
						{
							// just remove the item from the hand once it hits 0
							gm.itemIDInHand = -1;
						}
					}

				}

				drawoffy += 4;
			}
			drawoffy = 0;
			drawoffx += 4;
		}
	}
	
	
	public static void handleRightClickInvs(ItemStack[] inventory, GameManager gm, GameContainer gc, int offx, int offy, int invX, int invY) {
		drawoffx = 0;
		drawoffy = 0;

		for (int yy = 0; yy < inventory.length; yy++)
		{
			if (gc.getInput().getMouseX() >= drawoffx + invX + inventory[yy].getX() + offx && gc.getInput().getMouseX() <= drawoffx + invX + inventory[yy].getX() + offx + 16 && gc.getInput().getMouseY() >= drawoffy + invY + inventory[yy].getY() + offy && gc.getInput().getMouseY() <= drawoffy + invY + inventory[yy].getY() + offy + 16)
			{
				if (inventory[yy].getID() == -1 && !(gm.itemIDInHand == -1))
				{
					// if the hand has stuff and the slot does not add 1 to
					// the slot
					inventory[yy].setID(gm.itemIDInHand);
					inventory[yy].setStack(1);
					gm.itemCountInHand -= 1;
					if (gm.itemCountInHand == 0)
					{
						// just remove the item from the hand once it hits 0
						gm.itemIDInHand = -1;
					}
				}

			}

			drawoffy += 4;
		}
		drawoffy = 0;
		drawoffx += 4;
	
	}
	
	public static void addItemToInv(int ID) {
		for (int xx = 0; xx < invArray.length; xx++)
		{
			for (int yy = 0; yy < invArray[0].length; yy++)
			{
				if (invArray[xx][yy].getID() == -1)
				{
					invArray[xx][yy].setID(ID);
					invArray[xx][yy].setStack(1);
					return;
				}
				if (invArray[xx][yy].getID() == ID)
				{
					invArray[xx][yy].setStack(invArray[xx][yy].getStack() + 1);
					return;
				}
			}
		}
	}

	public static void handleInvsOutLeftClick(GameManager gm, GameContainer gc, ItemStack output) {
		if (gc.getInput().getMouseX() >= output.getX() && gc.getInput().getMouseX() <= +output.getX() + 16 && gc.getInput().getMouseY() >= output.getY() && gc.getInput().getMouseY() <= output.getY() + 16)
		{
			if (!(output.getID() == -1) && gm.itemIDInHand == -1)
			{
				gm.itemIDInHand = output.getID();
				gm.itemCountInHand = output.getStack();
				output.setID(-1);
				output.setStack(0);
				for (int xx = 0; xx < 3; xx++)
				{
					for (int yy = 0; yy < 3; yy++)
					{
						if (craftingGrid[xx][yy].getStack() > 1)
						{
							craftingGrid[xx][yy].setStack(craftingGrid[xx][yy].getStack() - 1);
						} else if (craftingGrid[xx][yy].getStack() == 1)
						{
							craftingGrid[xx][yy].setID(-1);
							craftingGrid[xx][yy].setStack(0);
						}

					}
				}

			}
			if (output.getID() == gm.itemIDInHand)
			{
				gm.itemCountInHand = gm.itemCountInHand + output.getStack();
				output.setID(-1);
				output.setStack(0);
				for (int xx = 0; xx < 3; xx++)
				{
					for (int yy = 0; yy < 3; yy++)
					{
						if (craftingGrid[xx][yy].getStack() > 1)
						{
							craftingGrid[xx][yy].setStack(craftingGrid[xx][yy].getStack() - 1);
						} else if (craftingGrid[xx][yy].getStack() == 1)
						{
							craftingGrid[xx][yy].setID(-1);
							craftingGrid[xx][yy].setStack(0);
						}

					}
				}

			}

		}
	}

	
	
	
	public static int getSelectedID() {
		int blockID = -1;
		int itemID = hotbar[selected][0].getID();

		if (!(itemID == -1))
		{
			blockID = ItemRegistry.registeredItems.get(itemID).getBlockType();
		}
		System.out.println(blockID);

		return blockID;
	}

	public static boolean isEnoughInSlot() {
		if (hotbar[selected][0].getID() == -1) {
			return false;
		}
		if (hotbar[selected][0].getStack() >= 1 && !(ItemRegistry.getBlockTypeFromID(hotbar[selected][0].getID()) == -1))
		{
			return true;
		}
		return false;
	}

	public static void removeItem() {
		hotbar[selected][0].setStack(hotbar[selected][0].getStack() - 1);
		if (hotbar[selected][0].getStack() == 0)
		{
			hotbar[selected][0].setID(-1);
		}
	}
	public static void drawinv(Renderer r, GameContainer gc, ItemStack[][] invArray, int offx, int offy, int invX, int invY) {

		drawoffx = 0;
		drawoffy = 0;
		for (int xx = 0; xx < invArray.length; xx++)
		{
			for (int yy = 0; yy < invArray[0].length; yy++)
			{

				if (!(invArray[xx][yy].getID() == -1))
				{
					r.drawImage(ItemRegistry.getImageFromID(invArray[xx][yy].getID()), drawoffx + invX + invArray[xx][yy].getX() + offx, drawoffy + invY + invArray[xx][yy].getY() + offy);
					if (gc.getInput().getMouseX() >= drawoffx + invX + invArray[xx][yy].getX() + offx && gc.getInput().getMouseX() <= drawoffx + invX + invArray[xx][yy].getX() + offx + 16 && gc.getInput().getMouseY() >= drawoffy + invY + invArray[xx][yy].getY() + offy && gc.getInput().getMouseY() <= drawoffy + invY + invArray[xx][yy].getY() + offy + 16)
					{
						if (!(invArray[xx][yy].getID() == -1))
						{
							r.drawText(ItemRegistry.getNameFromID(invArray[xx][yy].getID()), invX + 17, invY + 137, 0XFF000000);
							r.drawText(("Weight: " + Float.toString(invArray[xx][yy].getStack() * ItemRegistry.getWeightFromID(invArray[xx][yy].getID()))) + "Kg", invX + 17, invY + 148, 0XFF000000);

						}

					}

				}
				drawoffy += 4;
			}
			drawoffy = 0;
			drawoffx += 4;
		}
	}
	public static void drawinv(Renderer r, GameContainer gc, ItemStack[] invArray, int offx, int offy, int invX, int invY) {

		drawoffx = 0;
		drawoffy = 0;
		for (int xx = 0; xx < invArray.length; xx++)
		{
			for (int yy = 0; yy < invArray.length; yy++)
			{

				if (!(invArray[yy].getID() == -1))
				{
					r.drawImage(ItemRegistry.getImageFromID(invArray[yy].getID()), drawoffx + invX + invArray[yy].getX() + offx, drawoffy + invY + invArray[yy].getY() + offy);
					if (gc.getInput().getMouseX() >= drawoffx + invX + invArray[yy].getX() + offx && gc.getInput().getMouseX() <= drawoffx + invX + invArray[yy].getX() + offx + 16 && gc.getInput().getMouseY() >= drawoffy + invY + invArray[yy].getY() + offy && gc.getInput().getMouseY() <= drawoffy + invY + invArray[yy].getY() + offy + 16)
					{
						if (!(invArray[yy].getID() == -1))
						{
							r.drawText(ItemRegistry.getNameFromID(invArray[yy].getID()), invX + 17, invY + 137, 0XFF000000);
							r.drawText(("Weight: " + Float.toString(invArray[yy].getStack() * ItemRegistry.getWeightFromID(invArray[yy].getID()))) + "Kg", invX + 17, invY + 148, 0XFF000000);

						}

					}

				}
				drawoffy += 4;
			}
			drawoffy = 0;
			drawoffx += 4;
		}
	}
	public static void drawinv(Renderer r, GameContainer gc, ItemStack invArray, int offx, int offy, int invX, int invY) {

		if (!(invArray.getID() == -1))
		{
			r.drawImage(ItemRegistry.getImageFromID(invArray.getID()),  invX + invArray.getX() + offx,  invY + invArray.getY() + offy);
			if (gc.getInput().getMouseX() >=  invX + invArray.getX() + offx && gc.getInput().getMouseX() <=  invX + invArray.getX() + offx + 16 && gc.getInput().getMouseY() >=  invY + invArray.getY() + offy && gc.getInput().getMouseY() <=  invY + invArray.getY() + offy + 16)
			{
				if (!(invArray.getID() == -1))
				{
					r.drawText(ItemRegistry.getNameFromID(invArray.getID()), invX + 17, invY + 137, 0XFF000000);
					r.drawText(("Weight: " + Float.toString(invArray.getStack() * ItemRegistry.getWeightFromID(invArray.getID()))) + "Kg", invX + 17, invY + 148, 0XFF000000);

				}

			}

		}
	}
	public static void drawinv(Renderer r, GameContainer gc, ItemStack invArray, int invX, int invY) {

		if (!(invArray.getID() == -1))
		{
			r.drawImage(ItemRegistry.getImageFromID(invArray.getID()),  invArray.getX() ,  invArray.getY());
			if (gc.getInput().getMouseX() >=  invArray.getX() && gc.getInput().getMouseX() <=  invArray.getX() + 16 && gc.getInput().getMouseY() >=  invArray.getY()  && gc.getInput().getMouseY() <=   invArray.getY() + 16)
			{
				if (!(invArray.getID() == -1))
				{
					r.drawText(ItemRegistry.getNameFromID(invArray.getID()), invX + 17, invY + 137, 0XFF000000);
					r.drawText(("Weight: " + Float.toString(invArray.getStack() * ItemRegistry.getWeightFromID(invArray.getID()))) + "Kg", invX + 17, invY + 148, 0XFF000000);

				}

			}

		}
	}
	
	public static void drawPlayerStuff(Renderer r)
	{
		//draw the player image
		r.drawImageTile(playerImage, x*16, y*16, currentDirection, 0);
	}
	
	protected static boolean checkColideable(int x, int y, int z)
	{
		//check floor first
		int floor = World.getLoadedChunk().getBlockID(x, y, z-1);
		if (!(floor == -1) && World.currentZ >= 64) {
			if (BlockRegistry.registeredBlocks.get(floor).isColidable()) {
				return true;
			}
		}
		//then check tile entitys
		if (TileEntityRegistry.isColidable(x, y, z)) {
			return true;
		}
		//then check wall
		int wall = World.getLoadedChunk().getBlockID(x, y, z);
		if (!(wall == -1)) {
			if (BlockRegistry.registeredBlocks.get(wall).isColidable() == false) {
				return false;
			}
		}
		//dunno why this is here but eh
		if (!(wall == -1)) {
			return true;
		}
		
		return false;
	}
}
