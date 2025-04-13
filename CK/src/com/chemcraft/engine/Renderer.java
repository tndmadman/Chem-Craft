package com.chemcraft.engine;

import java.awt.image.DataBufferInt;

import com.chemcraft.engine.gfx.Font;
import com.chemcraft.engine.gfx.Image;
import com.chemcraft.engine.gfx.ImageTile;

public class Renderer {
	private int pW, pH;
	private int[] p;
	private int[] zb;

	private int[] lm;
	private int[] lb;

	public static boolean canRenderLight = true;
	public static boolean canUpdate = false;

	public static int ambColor = 0XFF859999;
	public static int desAmbColor = 0XFF859999;
	public static int miningAmbColor = 0XFF3d3d3d;
	public static int normalAmbColor = 0XFF859999;
	

	private int zDepth = 0;

	private Font font = Font.STANDARD;

	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zb = new int[p.length];
		lm = new int[p.length];
		lb = new int[p.length];
		// setting up the light map cus ima shity coder
		for (int i = 0; i < p.length; i++) {
			lm[i] = ambColor;
		}

	}

	public void clearLM() {
		for (int i = 0; i < p.length; i++) {
			lm[i] = ambColor;
		}
		canRenderLight = true;
	}

	public void clear() {

		for (int i = 0; i < p.length; i++) {

			p[i] = 0;
			zb[i] = 0;
			lb[i] = 0;
		}
	}

	public void procces() {
		for (int i = 0; i < p.length; i++) {
			float red = ((lm[i] >> 16) & 0XFF) / 255f;
			float green = ((lm[i] >> 8) & 0XFF) / 255f;
			float blue = (lm[i] & 0XFF) / 255f;
			p[i] = ((int) (((p[i] >> 16) & 0XFF) * red) << 16 | (int) (((p[i] >> 8) & 0XFF) * green) << 8 | (int) ((p[i] & 0XFF) * blue));
		}
	}

	public void setPixel(int x, int y, int value) {
		int alpha = ((value >> 24) & 0xff);
		if ((x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff) {
			return;
		}

		if (zb[x + y * pW] > zDepth) {
			return;
		}

		if (alpha == 255) {
			p[x + y * pW] = value;
		} else {
			int color = 0;

			int pixelColor = p[x + y * pW];
			int newRed = ((pixelColor >> 16) & 0xff) - (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
			int newBlue = (pixelColor & 0xff) - (int) (((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));

			p[x + y * pW] = (newRed << 16 | newGreen << 8 | newBlue);

		}

	}

	public void setLightMap(int x, int y, int value) {
		if ((x < 0 || x >= pW || y < 0 || y >= pH)) {
			return;
		}

		int basecolor = lm[x + y * pW];

		int maxRed = Math.max((basecolor >> 16) & 0XFF, (value >> 16) & 0XFF);
		int maxGreen = Math.max((basecolor >> 8) & 0XFF, (value >> 8) & 0XFF);
		int maxBlue = Math.max(basecolor & 0XFF, value & 0XFF);
		lm[x + y * pW] = (maxRed << 16 | maxGreen << 8 | maxBlue);

	}

	public void setLighBlock(int x, int y, int value) {
		if ((x < 0 || x >= pW || y < 0 || y >= pH)) {
			return;
		}
		lb[x + y * pW] = value;
	}

	public void drawImage(Image image, int offX, int offY) {
		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();
		// render distance
		if (offX < -newWidth)
			return;
		if (offY < -newHeight)
			return;
		if (offX >= pW)
			return;
		if (offY >= pH)
			return;

		if (offX < 0) {
			newX -= offX;
		}
		if (offY < 0) {
			newY -= offY;
		}
		// render clipping
		if (newWidth + offX > pW) {
			newWidth -= (newWidth + offX - pW);
		}
		if (newHeight + offY > pH) {
			newHeight -= (newHeight + offY - pH);
		}
		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
				setLighBlock(x + offX, y + offY, image.getLightBlock());
			}
		}
	}

	
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {

		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();
		// render distance
		if (offX < -newWidth)
			return;
		if (offY < -newHeight)
			return;
		if (offX >= pW)
			return;
		if (offY >= pH)
			return;

		if (offX < 0) {
			newX -= offX;
		}
		if (offY < 0) {
			newY -= offY;
		}
		// render clipping
		if (newWidth + offX > pW) {
			newWidth -= (newWidth + offX - pW);
		}
		if (newHeight + offY > pH) {
			newHeight -= (newHeight + offY - pH);
		}
		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setLighBlock(x + offX, y + offY, image.getLightBlock());
				setPixel(x + offX, y + offY,
						image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
			}
		}

	}

	public void drawText(String text, int offX, int offY, int color) {
		text = text.toUpperCase();
		int offset = 0;
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			for (int y = 0; y < font.getFontImage().getH(); y++) {
				for (int x = 0; x < font.getWidths()[unicode]; x++) {
					if (font.getFontImage().getP()[(x + font.getOffsets()[unicode])
							+ y * font.getFontImage().getW()] == 0xffffffff) {
						setPixel(x + offX + offset, y + offY, color);
					}
				}
			}
			offset += font.getWidths()[unicode];

		}

	}

	public void drawRec(int offX, int offY, int width, int height, int color) {
		for (int y = 0; y <= height; y++) {
			setPixel(offX, y + offY, color);
			setPixel(offX + width, y + offY, color);

		}
		for (int x = 0; x <= height; x++) {
			setPixel(x + offX, offY, color);
			setPixel(x + offX, offY + height, color);

		}

	}

	public void drawFillRect(int offX, int offY, int width, int height, int color) {
		for (int y = 0; y <= width; y++) {
			for (int x = 0; x <= height; x++) {
				setPixel(x + offX, y + offY, color);
			}

		}
	}

	public void drawLight(int offx, int offy, Light l) {

		if (canRenderLight) {
			for (int i = 0; i <= l.getDiameter(); i++) {
				drawLightLine(offx, offy, l.getRadius(), l.getRadius(), i, 0, l);
				drawLightLine(offx, offy, l.getRadius(), l.getRadius(), i, l.getDiameter(), l);
				drawLightLine(offx, offy, l.getRadius(), l.getRadius(), 0, i, l);
				drawLightLine(offx, offy, l.getRadius(), l.getRadius(), l.getDiameter(), i, l);
			}
		}

	}

	private void drawLightLine(int offx, int offy, int x0, int y0, int x1, int y1, Light l) {

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		while (true) {
			int screenX = x0 - l.getRadius() + offx;
			int screenY = y0 - l.getRadius() + offy;

			if (screenX < 0 || screenX >= pW | screenY < 0 || screenY >= pW) {
				return;
			}
			int lightColor = l.getLightValue(x0, y0);

			if (lightColor == 0) {

				return;
			}
			if (lb[screenX + screenY * pW] == Light.FULL) {
				return;
			}
			setLightMap(screenX, screenY, lightColor);
			if (x0 == x1 && y0 == y1) {

				break;

			}
			e2 = 2 * err;
			if (e2 > -1 * dy) {
				err -= dy;
				x0 += sx;

			}
			if (e2 < dx) {
				err += dx;
				y0 += sy;
			}
		}
	}

	public void drawTextBuble(int x, int y, String text, int color) {
		drawFillRect(x, y, 10, text.length() * 4 + 6, 0XFF000000);
		drawText(text, x + 2, y + 2, color);
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
}
