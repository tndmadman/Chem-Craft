package com.chemcraft.game.Gui;

import java.util.Random;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Renderer;
import com.chemcraft.engine.gfx.Image;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.items.ItemRegistry;
import com.chemcraft.game.math.Crafting;
import com.chemcraft.game.player.Player;

public class PlayerGui extends GuiBase
{
	protected int invoffx = 16;
	protected int invoffy = 160;
	protected int craftoffx = 13;
	protected int craftoffy = 25;
	protected int hotbaroffx = 161;
	protected int hotbaroffy = 493;

	protected Image hotbar_img;
	protected Image hotbar_selected_img;

	protected Random rand = new Random();

	public PlayerGui(int x, int y, String imageName)
	{
		super(x, y, imageName);
		super.setName("test");
		super.setCanGuiRender(false);

	}

	public void renderGui(Renderer r, GameContainer gc, GameManager gm) {
		// 12,168
		Player.drawinv(r, gc, Player.invArray, invoffx, invoffy, super.getX(), super.getY());
		Player.drawinv(r, gc, Player.craftingGrid, craftoffx, craftoffy, super.getX(), super.getY());
		r.drawImage(Player.hotbar_img, hotbaroffx, hotbaroffy);
		Player.drawinv(r, gc, Player.hotbar, hotbaroffx - super.getX(), hotbaroffy - super.getY(), super.getX(), super.getY());

		// 33 97

		if (!(Player.output.getID() == -1))
		{
			r.drawImage(ItemRegistry.getImageFromID(Player.output.getID()), super.getX() + 33, super.getY() + 97);

			if (gc.getInput().getMouseX() >= +Player.output.getX() && gc.getInput().getMouseX() <= Player.output.getX() + 16 && gc.getInput().getMouseY() >= Player.output.getY() && gc.getInput().getMouseY() <= Player.output.getY() + 16)
			{
				if (!(Player.output.getID() == -1))
				{
					r.drawText((Float.toString(Player.output.getStack() * ItemRegistry.getWeightFromID(Player.output.getID()))) + "Kg", Player.output.getX(), Player.output.getY(), 0XFFFFFFFF);
				}

			}

		}

	}

	public void renderGuiWhenClosed(Renderer r, GameContainer gc, GameManager gm) {
		r.drawImage(Player.hotbar_img, hotbaroffx, hotbaroffy);
		Player.drawinv(r, gc, Player.hotbar, hotbaroffx - super.getX(), hotbaroffy - super.getY(), super.getX(), super.getY());
		r.drawImage(Player.hotbar_selected_img, Player.hotbar[Player.selected][0].getX() + (Player.hotbar[Player.selected][0].getX() / 4) + hotbaroffx, hotbaroffy);
	}

	public void handleLeftClick(GameManager gm, GameContainer gc) {
		Player.handleInvsLeftClick(gm, gc, Player.invArray, invoffx, invoffy, super.getX(), super.getY());
		Player.handleInvsLeftClick(gm, gc, Player.craftingGrid, craftoffx, craftoffy, super.getX(), super.getY());
		Player.handleInvsLeftClick(gm, gc, Player.hotbar, hotbaroffx - super.getX(), hotbaroffy - super.getY(), super.getX(), super.getY());
		Player.handleInvsOutLeftClick(gm, gc, Player.output);
		Player.output = (Crafting.testRecpipie(Player.craftingGrid, super.getX() + 33, super.getY() + 97));

	}

	public void handleRightClick(GameManager gm, GameContainer gc) {
		Player.handleRightClickInvs(Player.invArray, gm, gc, invoffx, invoffy, super.getX(), super.getY());
		Player.handleRightClickInvs(Player.craftingGrid, gm, gc, craftoffx, craftoffy, super.getX(), super.getY());
		Player.output = (Crafting.testRecpipie(Player.craftingGrid, super.getX() + 33, super.getY() + 97));

	}

}
