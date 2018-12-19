package com.chemcraft.game;

import com.chemcraft.engine.AbstractGame;
import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Light;
import com.chemcraft.engine.Renderer;
import com.chemcraft.engine.gfx.ImageTile;
import com.chemcraft.game.Gui.GuiBase;
import com.chemcraft.game.blocks.BlockRegistry;
import com.chemcraft.game.items.ItemRegistry;
import com.chemcraft.game.math.Crafting;
import com.chemcraft.game.math.Smelting;
import com.chemcraft.game.player.Player;
import com.chemcraft.game.tileentity.TileEntityRegistry;
import com.chemcraft.game.world.Chunk;
import com.chemcraft.game.world.World;


public class GameManager extends AbstractGame
{
	private ImageTile courser;
	public int cX = 0;
	public int cY = 0;
	
	public int mouseState = 0;
	private ImageTile background;
	public int itemIDInHand = -1;
	public int itemCountInHand = 0;
	protected int tickcount = 0;
	protected int tickcap = 20;
	protected int rendercount = 0;
	protected int rendercap = 20;
	protected static final Light sun = new Light(1536, 0XFFFFFFFF);
	protected static final Light torch = new Light(32, 0XFFFFFFFF);
	protected int sunY = 0;
	protected static Chunk currentChunk;
	

	public GameManager()
	{
		// settup stuff
		courser = new ImageTile("/courser.png", 16, 16);
		background = new ImageTile("/images/blocks/backgroundtile.png", 16, 16);

		BlockRegistry.RegisterBlocks();
		World.worldStartup();
		ItemRegistry.RegisterItems();
		Crafting.initRecipies();
		Smelting.initFurnaceRecipies();
		GuiBase.initGui();
		Player.initPlayer();



	}

	@Override
	public void update(GameContainer gc, float dt, Renderer r) {
		//sun
		this.tickcount ++;
		if (this.tickcount == this.tickcap) {
			this.tickcount = 0;
			this.sunY ++;
			if (this.sunY > 1024) {
				this.sunY = 0;
			}
		}
		
		//world amb color thing
		if (World.currentZ >= 64) {
			Renderer.desAmbColor = Renderer.normalAmbColor;
		}else {
			Renderer.desAmbColor = Renderer.miningAmbColor;
		}
		if (GuiBase.isGuiOpen) {
			Renderer.ambColor = Renderer.desAmbColor;
		}
		//handle player logic
		Player.PlayerLogic(this, gc);
		TileEntityRegistry.tileLogics(this, gc);

		//courser stuff
		cX = gc.getInput().getMouseX()/16;
		cY = gc.getInput().getMouseY()/16;
		
		
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		//draw the blocks
		rendercount += 1;
		if (rendercount == rendercap) {
			rendercount = 0;
			
		}
		//why is this here jeff...?
		currentChunk = World.getLoadedChunk();
		
		if (GuiBase.isGuiOpen == false) {
			for (int xx = 0; xx < 32; xx++) {
				for (int yy = 0; yy < 32; yy++) {
					int floor = currentChunk.getBlockID(xx, yy, World.currentZ-1);
					int blockatlevel = currentChunk.getBlockID(xx, yy, World.currentZ);
					if (!(floor == -1) && World.currentZ >= 64) {
						r.drawImage(BlockRegistry.getImageFromID(floor), xx*16, yy*16);
					}else{
						r.drawImageTile(background, xx*16, yy*16, 1, 0);
					}
					if (!(blockatlevel == -1)) {
						r.drawImage(BlockRegistry.getImageFromID(blockatlevel), xx*16, yy*16);
					}

				}
			}
			for (int i = 0; i < currentChunk.getItemsInChunk().size(); i++) {
				if (currentChunk.getItemsInChunk().get(i).getZ() == World.currentZ || currentChunk.getItemsInChunk().get(i).getZ() == World.currentZ-1) {
					r.drawImage(currentChunk.getItemsInChunk().get(i).getImage(), currentChunk.getItemsInChunk().get(i).getX(), currentChunk.getItemsInChunk().get(i).getY());

				}
			}
			//draw the player
			Player.drawPlayerStuff(r);
			//draw tile graphics if there are any
			TileEntityRegistry.renderTiles(this, r);
			//draw lights
			
			if (rendercount == rendercap-1 && GuiBase.isGuiOpen == false) {
				r.clearLM();
				if (World.currentZ >= 64) {
					r.drawLight(1, this.sunY, sun);
				}else if(Player.hotbar[Player.selected][0].getID() == 16) {
					r.drawLight(Player.x*16+8, Player.y *16+8, torch);
				}
				for (int xx = 0; xx < 32; xx++) {
					for (int yy = 0; yy < 32; yy++) {
						for (int zz = 0; zz < 66; zz++) {
							if (!(World.getLoadedChunk().getBlockID(xx, yy, World.currentZ) == -1)) {
								if (zz == World.currentZ) {
									if (BlockRegistry.registeredBlocks.get(World.getLoadedChunk().getBlockID(xx, yy, World.currentZ)).hasLight()) {
										r.drawLight(xx*16, yy*16, BlockRegistry.registeredBlocks.get(World.getLoadedChunk().getBlockID(xx, yy, World.currentZ)).getLight());
										
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		
		
		if (GuiBase.isGuiOpen) {
			r.clearLM();
		}
		//draw blocks over the player on the surfice only
				if (GuiBase.isGuiOpen == false) {
					for (int xx = 0; xx < 32; xx++) {
						for (int yy = 0; yy < 32; yy++) {
							int ID = currentChunk.getBlockID(xx, yy, World.currentZ+1);
							if (!(ID == -1) && World.currentZ >= 64 ) {
								r.drawImage(BlockRegistry.getImageFromID(ID), xx*16, yy*16);
							}

						}
					}
				}
				//draw guis
				GuiBase.renderGuis(r, gc, this);
				//if there is a item in hand draw it
				if (!(itemIDInHand == -1)) {
					r.drawImage(ItemRegistry.getImageFromID(itemIDInHand), gc.getInput().getMouseX()-8, gc.getInput().getMouseY()-8);
				}
				//draw courser
				r.drawImageTile(courser, gc.getInput().getMouseX()-8, gc.getInput().getMouseY()-8, 0,0);
				
	}
	
	
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		// set settings here
		gc.start();
	}

	
}
