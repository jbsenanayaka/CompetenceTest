package com.eyepax.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class Main {

	static int x;
	static int y;
	static int count;
	static String visited[][];
	static List<String[][]> resultList;

	public static void main(String args[]) {

		x = 40;
		y = 50;

		String grid[][] = createGame(x, y);
		largestContinuesColourChain(grid);
	}

	private static String[][] createGame(int x, int y) {
		Random rand = new Random();
		String[][] grid = new String[x][y];

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				grid[i][j] = "(" + String.valueOf(rand.nextInt(255)) + "," + String.valueOf(rand.nextInt(255)) + ","
						+ String.valueOf(rand.nextInt(255)) + ")";
			}
		}
		return grid;
	}

	private static void largestContinuesColourChain(String grid[][]) {

		int maxLength = 0;
		resultList = new ArrayList<String[][]>();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				visited = new String[x][y];
				count = 0;
				if (j + 1 < y) {
					doTraverse(grid[i][j], grid[i][j + 1], i, j, grid);
				}
				if (count != 0 && count == maxLength) {
					resultList.add(visited);
				} else if (count > maxLength) {
					maxLength = count;
					resultList.clear();
					resultList.add(visited);
				}
				visited = new String[x][y];
				count = 0;
				if (i + 1 < x) {
					doTraverse(grid[i][j], grid[i + 1][j], i, j, grid);
				}
				if (count != 0 && count == maxLength) {
					resultList.add(visited);
				} else if (count > maxLength) {
					maxLength = count;
					resultList.clear();
					resultList.add(visited);
				}
				
			}
		}
		showResult(grid, maxLength);
	}

	private static void doTraverse(String currentNode, String neighbor, int i, int j, String grid[][]) {

		if (currentNode.equalsIgnoreCase(neighbor)) {
			visited[i][j] = grid[i][j];
			count++;

			int move[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

			for (int k = 0; k < move.length; k++) {
				if ((isValidTraversal(i + move[k][1], j + move[k][0], currentNode, grid)) == true) {
					doTraverse(currentNode, neighbor, i + move[k][1], j + move[k][0], grid);
				}
			}
		}
	}

	private static boolean isValidTraversal(int xIndex, int yIndex, String currentColour, String input[][]) {
		boolean isTrue = false;
		if (0 <= xIndex && xIndex < x && 0 <= yIndex && yIndex < y) {
			if (visited[xIndex][yIndex] == null && input[xIndex][yIndex].equalsIgnoreCase(currentColour)) {
				isTrue = true;
			}
		}
		return isTrue;
	}

	private static void showResult(String[][] grid, int maxLength) {
		System.out.println("Input Colour Grid. \n");
		System.out.println(Arrays.deepToString(grid));
		/*
		 *  Uncomment below commented loops for a better grid view of the input color grid.
		 */
		/*for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (grid[i][j] != null) {
					System.out.print(grid[i][j] + " ");
				} else {
					System.out.print("* ");
				}
			}
			System.out.println();
		}*/
		
		System.out.println("\n\nLength of the largest equal colour chain is :" + maxLength + ".");
		System.out.println("Found " + resultList.size() + " occurences of largest equal colour chains.");
		System.out.println("\n------------GRAPHICAL VIEW OF EACH CHAIN----------------\n");
		if (!resultList.isEmpty()) {
			for (String[][] res : resultList) {
				for (int i = 0; i < x; i++) {
					for (int j = 0; j < y; j++) {
						if (res[i][j] != null) {
							System.out.print(res[i][j] + " ");
						} else {
							System.out.print("* ");
						}
					}
					System.out.println();
				}
				System.out.println("\n================================================================\n");
			}
		}
	}
}