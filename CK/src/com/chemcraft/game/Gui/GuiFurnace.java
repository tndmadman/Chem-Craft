package com.chemcraft.game.Gui;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Renderer;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.inventorys.InvetoryRegistry;
import com.chemcraft.game.player.Player;
import com.chemcraft.game.tileentity.TileSmelter;

public class GuiFurnace extends GuiBase
{
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
	public GuiFurnace(int x, int y, String imageName) {
		super(x, y, imageName);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void handleLeftClick(GameManager gm, GameContainer gc) {
		Player.handleInvsLeftClick(gm, gc, Player.invArray, invoffx, invoffy, super.getX(), super.getY());
		Player.handleInvsLeftClick(gm, gc, Player.hotbar, hotbaroffx - super.getX(), hotbaroffy - super.getY(), super.getX(), super.getY());
		Player.handleInvsLeftClick(gm, gc, TileSmelter.currentinventory.getInternal()[0], resourcex, resourcey, super.getX(), super.getY());
		Player.handleInvsLeftClick(gm, gc, TileSmelter.currentinventory.getInternal()[1], fuelx, fuely, super.getX(), super.getY());
		
		Player.handleInvsOutLeftClick(gm, gc, TileSmelter.currentinventory.getOutput());
	}
	
	@Override
	public void renderGui(Renderer r, GameContainer gc, GameManager gm) {
		Player.drawinv(r, gc, Player.invArray, invoffx, invoffy, super.getX(), super.getY());
		r.drawImage(Player.hotbar_img, hotbaroffx, hotbaroffy);
		Player.drawinv(r, gc, Player.hotbar, hotbaroffx - super.getX(), hotbaroffy - super.getY(), super.getX(), super.getY());//draw hotbar shit
		Player.drawinv(r, gc, TileSmelter.currentinventory.getInternal()[0], super.getX(), super.getY());//draw crafting item
		Player.drawinv(r, gc, TileSmelter.currentinventory.getInternal()[1], super.getX(), super.getY());//draw fuel
		
		Player.drawinv(r, gc, TileSmelter.currentinventory.getOutput(), super.getX(), super.getY());//draw output
		r.drawFillRect(super.getX()+126, super.getY()+68, 4, TileSmelter.publiccraftingtime / 16 -1, 0XFFFF0000);
		r.drawFillRect(super.getX()+17, super.getY()+131, 4, TileSmelter.publicfuellevel*5, 0XFFFF0000);
	}
	@Override
	public void handleRightClick(GameManager gm, GameContainer gc) {
		Player.handleRightClickInvs(Player.invArray, gm, gc, invoffx, invoffy, super.getX(), super.getY());
	}
}
