package com.chemcraft.engine.gfx;

public class ImageTile extends Image 
{
	private int tileW=16;
	private int tileH=16;
	public ImageTile(String path, int tileW, int tileH)
	{
		super(path);
		this.tileW = tileW;
		this.tileH = tileH;
		
		
	}
	public int getTileW() {
		return tileW;
	}
	public void setTileW(int tileW) {
		tileW = tileW;
	}
	public int getTileH() {
		return tileH;
	}
	public void setTileH(int tileH) {
		this.tileH = tileH;
	}

}
