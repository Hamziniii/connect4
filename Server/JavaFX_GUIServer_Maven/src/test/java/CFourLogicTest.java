import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CFourLogicTest {
	static boolean debug = false;

	@BeforeEach
	void reset() {
		CFourLogic.resetBoard();
	}

	@Test
	void NoWinTest() {
		assertEquals(-1, CFourLogic.checkWin());
	}

	@Test
	void P0WinHTest() {
		for(int i = 0; i < 6; i++) {
			reset();
			for(int j = 0; j < 5; j++)
				CFourLogic.c.board[i][j] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(0, CFourLogic.checkWin());
		}
	}

	@Test
	void P1WinHTest() {
		for(int i = 0; i < 6; i++) {
			reset();
			CFourLogic.c.turn = 1;

			for(int j = 0; j < 5; j++)
				CFourLogic.c.board[i][j] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(1, CFourLogic.checkWin());
		}
	}

	@Test
	void P0WinVTest() {
		for(int j = 0; j < 7; j++) {
			reset();
			for(int i = 0; i < 5; i++)
				CFourLogic.c.board[i][j] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(0, CFourLogic.checkWin());
		}
	}

	@Test
	void P1WinVTest() {
		for(int j = 0; j < 7; j++) {
			reset();
			CFourLogic.c.turn = 1;

			for(int i = 0; i < 5; i++)
				CFourLogic.c.board[i][j] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(1, CFourLogic.checkWin());
		}
	}

	@Test
	void P0WinDDTest() {
		for(int k = 0; k < 4; k++) {
			reset();
			for(int l = 0; l < 4; l++)
				CFourLogic.c.board[l][k+l] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(0, CFourLogic.checkWin());
		}
	}

	@Test
	void P1WinDDTest() {
		for(int k = 0; k < 4; k++) {
			reset();
			CFourLogic.c.turn = 1;
			for(int l = 0; l < 4; l++)
				CFourLogic.c.board[l][k+l] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(1, CFourLogic.checkWin());
		}
	}

	@Test
	void P0WinDATest() {
		for(int k = 0; k < 4; k++) {
			reset();
			for(int l = 0; l < 4; l++)
				CFourLogic.c.board[5-l][k+l] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(0, CFourLogic.checkWin());
		}
	}

	@Test
	void P1WinDATest() {
		for(int k = 0; k < 4; k++) {
			reset();
			CFourLogic.c.turn = 1;
			for(int l = 0; l < 4; l++)
				CFourLogic.c.board[5-l][k+l] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(1, CFourLogic.checkWin());
		}
	}

	@Test
	void P0NoWinHTest() {
		for(int i = 0; i < 6; i++) {
			reset();
			for(int j = 0; j < 5; j++)
				if(j > 2)
					CFourLogic.c.board[i][j] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	@Test
	void P1NoWinHTest() {
		for(int i = 0; i < 6; i++) {
			reset();
			CFourLogic.c.turn = 1;

			for(int j = 0; j < 5; j++)
				if(j > 3)
					CFourLogic.c.board[i][j] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	@Test
	void P0NoWinVTest() {
		for(int j = 0; j < 7; j++) {
			reset();
			for(int i = 0; i < 5; i++)
				if(i > 2)
					CFourLogic.c.board[i][j] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	@Test
	void P1NoWinVTest() {
		for(int j = 0; j < 7; j++) {
			reset();
			CFourLogic.c.turn = 1;

			for(int i = 0; i < 5; i++)
				if(i > 1)
					CFourLogic.c.board[i][j] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	@Test
	void P0NoWinDDTest() {
		for(int k = 0; k < 4; k++) {
			reset();
			for(int l = 0; l < 4; l++)
				if(l > 2)
					CFourLogic.c.board[l][k+l] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	@Test
	void P1NoWinDDTest() {
		for(int k = 0; k < 4; k++) {
			reset();
			CFourLogic.c.turn = 1;
			for(int l = 0; l < 4; l++)
				if(l > 2)
					CFourLogic.c.board[l][k+l] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	@Test
	void P0NoWinDATest() {
		for(int k = 0; k < 4; k++) {
			reset();
			for(int l = 0; l < 4; l++)
				if(l > 1)
					CFourLogic.c.board[5-l][k+l] = 0;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	@Test
	void P1NoWinDATest() {
		for(int k = 0; k < 4; k++) {
			reset();
			CFourLogic.c.turn = 1;
			for(int l = 0; l < 4; l++)
				if(l > 2)
					CFourLogic.c.board[5-l][k+l] = 1;

			if(debug)
				CFourLogic.debugDisplayBoard(null);

			assertEquals(-1, CFourLogic.checkWin());
		}
	}

	boolean equivalentBoards(int[][] a, int[][] b) {
		if(a == null || b == null)
			return false;

		for(int i = 0; i < a.length; i++)
			for(int j = 0; j < a[0].length; j++)
				if(a[i][j] != b[i][j])
					return false;
		return true;
	}

	@Test
	void ValidMovesTest1() {
		int[][] cBoard = {
			{-2, -2, -2, -2, -2, -2, -2},
			{-2, -2, -2, -2, -2, -2, -2},
			{-2, -2, -2, -2, -2, -2, -2},
			{-2, -2, -2, -2, -2, -2, -2},
			{-2, -2, -2, -2, -2, -2, -2},
			{-1, -1, -1, -1, -1, -1, -1}
		};

		assertTrue(equivalentBoards(cBoard, CFourLogic.validMoves()));
	}

	@Test
	void ValidMovesTest2() {
		CFourLogic.c.board = new int[][] {
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-1, 0, 1, -1, -1, -1, -1}
		};

		int[][] cBoard = {
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -1, -1, -2, -2, -2, -2},
				{-1, 0, 1, -1, -1, -1, -1}
		};

		if(debug)
			CFourLogic.debugDisplayBoard(CFourLogic.validMoves());

		assertTrue(equivalentBoards(cBoard, CFourLogic.validMoves()));
	}

	@Test
	void ValidMovesTest3() {
		CFourLogic.c.board = new int[][] {
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, 0, -2, -2, -2, -2, -2},
				{-2, 0, -2, -2, -2, -2, -2},
				{-2, 0, -2, -2, 0, -2, -2},
				{-1, 0, 1, 0, 0, 0, -1}
		};

		int[][] cBoard = {
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -1, -2, -2, -2, -2, -2},
				{-2, 0, -2, -2, -2, -2, -2},
				{-2, 0, -2, -2, -1, -2, -2},
				{-2, 0, -1, -1, 0, -1, -2},
				{-1, 0, 1, 0, 0, 0, -1}
		};

		if(debug)
			CFourLogic.debugDisplayBoard(CFourLogic.validMoves());

		assertTrue(equivalentBoards(cBoard, CFourLogic.validMoves()));
	}

	@Test
	void TiedGameTest() {
		CFourLogic.c.board = new int[][] {
				{1, 0, 1, 0, 1, 0, 1},
				{1, 0, 1, 0, 1, 0, 1},
				{1, 0, 1, 0, 1, 0, 1},
				{0, 1, 0, 1, 0, 1, 0},
				{0, 1, 0, 1, 0, 1, 0},
				{0, 1, 0, 1, 0, 1, 0}
		};

		assertFalse(CFourLogic.playerWin(0));
		assertFalse(CFourLogic.playerWin(1));
		assertTrue(CFourLogic.allTied());
	}
}

/*

		int[][] a = new int[][]{
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-2, -2, -2, -2, -2, -2, -2},
				{-1, -1, -1, -1, -1, -1, -1},
		};

*/