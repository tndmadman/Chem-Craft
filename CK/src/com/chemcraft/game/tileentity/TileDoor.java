package com.chemcraft.game.tileentity;

import com.chemcraft.engine.GameContainer;
import com.chemcraft.engine.Light;
import com.chemcraft.engine.Renderer;
import com.chemcraft.engine.gfx.ImageTile;
import com.chemcraft.game.GameManager;
import com.chemcraft.game.player.Player;

public class TileDoor extends TileEntityBase
{
	protected int facing = 0;
	protected ImageTile tileimage = new ImageTile("/images/blocks/doortile.png", 16, 16);
	public TileDoor() {
		this.tileimage.setLightBlock(Light.FULL);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doOnCreate(GameManager gm, GameContainer gc) {
		//i totaly over complicated this in my head
		int px = Player.x;
		int py = Player.y;
		int objx = gm.cX;
		int objy = gm.cY;
		int xdist = px - objx;
		int ydist = py - objy;
		if (xdist < 0) {
			xdist = Math.abs(xdist);
		}
		if (ydist < 0) {
			ydist = Math.abs(ydist);
		}
		if (xdist > ydist) {
			this.facing = 1;
		}
	}
	
	@Override
	public void tileRightClick(int x, int y, int z) {
		if (super.isColidable()) {
			super.setIsColidable(false);
		}else{
			super.setIsColidable(true);
		}
	}
	@Override
	public void tileGraphics(GameManager gm, Renderer r) {
		if (super.isColidable()) {
			r.drawImageTile(this.tileimage, super.getX()*16, super.getY()*16, 0, this.facing);
		}else{
			r.drawImageTile(this.tileimage, super.getX()*16, super.getY()*16, 1, this.facing);
		}
	}
	
}
