package com.chemcraft.game.math;

public class Misc 
{
	//Item Health Def
	protected final static int RED_DEF = 20;
	protected final static int ORANGE_DEF = 40;
	protected final static int GREEN_DEF = 80;
	//Item Health Color Def
	protected final static int RED = 0xFFff0000;
	protected final static int ORANGE = 0xFFff7c00;
	protected final static int GREEN = 0xFF00ff00;
	//get health value, return bar color
	public static int getHealthColor(int health)
	{
		if (health <= RED_DEF) 
		{
			return RED;
		}else if (health >RED_DEF && health <= ORANGE_DEF)
		{
			return ORANGE;
		}else if(health > GREEN_DEF)
		{
			return GREEN;
		}
		
		
		return -1;
	}
	
	
}
