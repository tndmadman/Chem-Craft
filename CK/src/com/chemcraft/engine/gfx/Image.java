package com.chemcraft.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image 
{
	private int w, h;
	private int[] p;
	
	private int lightBlock = 0;
	public Image(String path){
		BufferedImage image = null;
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		w = image.getWidth();
		h = image.getHeight();
		p = image.getRGB(0, 0, w, h, null, 0, w);
		image.flush();
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int[] getP() {
		return p;
	}
	public void setP(int[] p) {
		this.p = p;
	}
	public int getLightBlock() {
		return lightBlock;
	}
	public void setLightBlock(int lightBlock) {
		this.lightBlock = lightBlock;
	}
}