package com.chemcraft.game.world;

import java.util.Random;


public class BuldableTrees
{
	public static int xoff = 0;
	public static int yoff = 0;
	public static int rand;
	public static int TREE_BASE[][] = new int[][] { 
		{ 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0 }, 
		{ 0, 0, 1, 0, 0 }, 
		{ 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0 }

	};
	public static int TREE_BASE_1[][] = new int[][] { 
		{ 0, 0, 0, 0, 0 }, 
		{ 0, 0, 1, 0, 0 }, 
		{ 0, 0, 1, 0, 0 }, 
		{ 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0 }

	};
	public static int TREE_BASE_2[][] = new int[][] { 
		{ 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0 }, 
		{ 0, 0, 1, 1, 0 }, 
		{ 0, 0, 1, 0, 0 }, 
		{ 0, 0, 0, 0, 0 }

	};
	public static int TREE[][] = new int[][] { 
		{ 0, 0, 1, 0, 0 }, 
		{ 0, 1, 1, 1, 0 }, 
		{ 1, 1, 1, 1, 1 }, 
		{ 0, 1, 1, 1, 0 }, 
		{ 0, 0, 1, 0, 0 }

	};
	public static int TREE_1[][] = new int[][] { 
		{ 0, 0, 1, 0, 0 }, 
		{ 0, 1, 1, 1, 1 }, 
		{ 0, 1, 1, 1, 1 }, 
		{ 0, 1, 1, 1, 0 }, 
		{ 0, 1, 1, 0, 0 }

	};

	public static int TREE_2[][] = new int[][] { 
		{ 0, 0, 1, 1, 0 }, 
		{ 0, 1, 1, 1, 0 }, 
		{ 1, 1, 1, 1, 0 }, 
		{ 0, 1, 1, 1, 0 }, 
		{ 0, 1, 1, 0, 0 }

	};

	

	public static int[][] getBase() {
		Random random = new Random();
		rand = random.nextInt(3);

		if (rand == 0)
		{

			return TREE_BASE;

		} else if (rand == 1)
		{
			return TREE_BASE_1;
		} else if (rand == 2)
		{
			return TREE_BASE_2;
		}

		return TREE_BASE_2;
	}

	public static int[][] getTop() {
		Random random = new Random();
		int rand = random.nextInt(3);

		if (rand == 0)
		{
			return TREE;
		}
		if (rand == 1)
		{
			return TREE_1;
		}
		if (rand == 2)
		{
			return TREE_2;
		}
		return TREE;
	}
}
