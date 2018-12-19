package com.chemcraft.game.world;

import java.util.ArrayList;

import com.chemcraft.game.math.WorldNoise;

public class World {
	public static int currentZ = 64;
	public static int currentChunk = 0;
	protected static int chunkXOFF = 0;
	protected static int chunkYOFF = 0;
	protected static int chunkX = 0;
	protected static int chunkY = 0;
	protected static WorldNoise surficenoise = new WorldNoise();
	protected static WorldNoise biomenoise = new WorldNoise();
	public static ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	protected static Chunk chunk;
	
	
	public static void worldStartup()
	{
		chunks.add(chunk = new Chunk(0, 0, surficenoise, biomenoise, chunkXOFF, chunkXOFF));
	}
	
	
	
	
	public static void genN()
	{
		chunkY -= 1;
		chunkYOFF -= 32;

		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).getX() == chunkX && chunks.get(i).getY() == chunkY)
			{
				return;
			}
		}

		chunks.add(chunk = new Chunk(chunkX, chunkY, surficenoise, biomenoise, chunkXOFF, chunkYOFF));
	}
	public static void genE()
	{
		chunkX += 1;
		chunkXOFF += 32;
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).getX() == chunkX && chunks.get(i).getY() == chunkY)
			{
				return;
			}
		}
		chunks.add(chunk = new Chunk(chunkX, chunkY, surficenoise, biomenoise, chunkXOFF, chunkYOFF));
	}
	public static void genS()
	{
		chunkY += 1;
		chunkYOFF += 32;
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).getX() == chunkX && chunks.get(i).getY() == chunkY)
			{
				return;
			}
		}
		chunks.add(chunk = new Chunk(chunkX, chunkY, surficenoise, biomenoise, chunkXOFF, chunkYOFF));
	}
	public static void genW()
	{
		chunkX -= 1;
		chunkXOFF -= 32;
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).getX() == chunkX && chunks.get(i).getY() == chunkY)
			{
				return;
			}
		}
		chunks.add(chunk = new Chunk(chunkX, chunkY, surficenoise, biomenoise, chunkXOFF, chunkYOFF));
	}
	public static Chunk getLoadedChunk() {
		int id = 0;
		for (int i = 0; i < chunks.size(); i++)
		{
			if (chunks.get(i).getX() == chunkX && chunks.get(i).getY() == chunkY)
			{
				id = i;
			}
		}
		return chunks.get(id);
	}
}
