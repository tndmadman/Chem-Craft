package com.chemcraft.game.Gui;

import java.util.ArrayList;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Light;
import com.chemcraft.engine.Renderer;
import com.chemcraft.engine.gfx.Image;
import com.chemcraft.game.GameManager;

public class GuiBase
{
	private int x = 0;
	private int y = 0;
	private Image img;
	private boolean canGuiRender = false;
	public static ArrayList<GuiBase> guis = new ArrayList<GuiBase>();
	private String guiName = "";
	public static boolean isGuiOpen = false;

	public GuiBase(int x, int y, String imageName)
	{
		this.x = x;
		this.y = y;
		this.img = new Image("/gui/" + imageName + ".png");
		img.setLightBlock(Light.FULL);
	}

	public static void initGui() {
		guis.add(new PlayerGui(128, 128, "base"));
		guis.add(new GuiFurnace(128, 128, "furnace"));
		// add guis here;

	}
	
	public static void renderGuis(Renderer r, GameContainer gc, GameManager gm) {
		for (int i = 0; i < guis.size(); i++)
		{
			if (guis.get(i).canGuiRender())
			{
				r.drawImage(guis.get(i).getImg(), guis.get(i).getX(), guis.get(i).getY());
				r.drawText(guis.get(i).getName(), guis.get(i).getX() + 10, guis.get(i).getY() + 11, 0XFF000000);
				guis.get(i).renderGui(r, gc, gm);
				;
			}
			if (!(guis.get(i).canGuiRender))
			{
				guis.get(i).renderGuiWhenClosed(r, gc, gm);
			}
		}
	}

	public void renderGuiWhenClosed(Renderer r, GameContainer gc, GameManager gm) {
		// mostly for hotbars, bufs and shit
	}

	public void renderGui(Renderer r, GameContainer gc, GameManager gm) {

	}

	public void handleLeftClick(GameManager gm, GameContainer gc) {

	}

	public void handleRightClick(GameManager gm, GameContainer gc) {

	}

	public String getName() {
		return this.guiName;
	}

	public void setName(String name) {
		this.guiName = name;
	}

	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Image getImg() {
		return this.img;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setCanGuiRender(Boolean canGuiRender) {
		GuiBase.isGuiOpen = canGuiRender;
		this.canGuiRender = canGuiRender;
		if (this.canGuiRender) {
			Renderer.desAmbColor = 0XFFFFFFFF;
		}else{
			Renderer.ambColor = Renderer.desAmbColor;
		}
	}

	public boolean canGuiRender() {
		return this.canGuiRender;
	}

}
