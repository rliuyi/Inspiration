import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Usage: java WordSearcher ABCCEATB
 * 
 * @version 1.0
 * @author liuyi
 *         <p>
 *         Given a 2D board and a word, find if the word exists in the grid. The
 *         word can be constructed from letters of sequentially adjacent cell,
 *         where adjacent cells are those horizontally or vertically
 *         neighboring. The same letter cell may not be used more than once.
 *         <hr>
 *         <header>INPUT SAMPLE:</header> <section> <b>The board to be used may
 *         be hard coded as:<br>
 *         -----------------------<br>
 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| A | B | C | E |<br>
 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| S | F | C | S |<br>
 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| A | D | E | E |<br>
 *         -----------------------<br>
 *         </b>
 *         <li>If search <b>'ASADB'</b>, it <b>CAN NOT</b> be found out.
 *         <li>If search <b>'ABCCED'</b>, it <b>CAN</b> be found out.
 *         <li>If search <b>'ABCF'</b>, it <b>CAN NOT</b> be found out.
 *         </section>
 */
public class WordSearcher {

	private char[][] board;

	public WordSearcher(char[][] board) {
		this.board = board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String htLine = this.getHeadAndTailLine();
		result.append(htLine);
		int rows = this.board.length;
		for (int i = 0; i < rows; i++) {
			result.append(this.getRowLine(i));
		}
		result.append(htLine);
		return result.toString();
	}

	public boolean wordSearch(String word) {
		if (null == this.board || null == word)
			return false;
		System.out.println("Searching the word: " + word);
		int rows = this.board.length;
		if (0 == rows)
			return false;
		int cols = this.board[0].length;
		if (0 == cols || 0 == word.trim().length())
			return false;

		char first = word.charAt(0);
		String searchedWord = word.substring(1);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (this.board[i][j] == first) {
					if (this.search(new Position(i, j), searchedWord, 0, null))
						return true;
				}
			}
		}

		return false;
	}

	private String getHeadAndTailLine() {
		StringBuilder result = new StringBuilder();
		int cols = this.board[0].length;
		for (int i = 0; i < cols; i++) {
			result.append("---");
		}
		result.append("\n");
		return result.toString();
	}

	private String getRowLine(int row) {
		int cols = this.board[0].length;
		StringBuilder result = new StringBuilder();
		result.append(" |");
		for (int i = 0; i < cols; i++) {
			result.append(this.board[row][i]);
			result.append("|");
		}
		result.append("\n");
		return result.toString();
	}

	/**
	 * Search with a character for specified word and character position, around
	 * with the specified position(TOP, RIGHT, BOTTOM, LEFT).
	 * 
	 * @param position
	 *            The central position where we want to search.
	 * @param word
	 *            Word.
	 * @param wordPosition
	 *            For specified character.
	 * @param filteredPosition
	 *            Can not go back.
	 * @return true or false.
	 */
	private boolean search(Position position, String word, int wordPosition, Position filteredPosition) {
		if (word.length() <= wordPosition)
			return true;
		char targetCharacter = word.charAt(wordPosition);
		System.out.println("Search the word(" + word + " with character '" + targetCharacter + "' and position '"
				+ wordPosition + "') around with character(" + this.getCharacterWithPosition(position)
				+ ") and position" + position);
		List<Position> nextCharacterPositions = this.getAroundPosition(position, targetCharacter, filteredPosition);
		for (Position pos : nextCharacterPositions) {
			if (search(pos, word, ++wordPosition, position))
				return true;
		}

		return false;
	}

	private List<Position> getAroundPosition(Position startPosition, char targetChar, Position filteredPosition) {
		List<Position> matchedPosition = new ArrayList<Position>(4);
		Position top = startPosition.top();
		Position right = startPosition.right();
		Position bottom = startPosition.bottom();
		Position left = startPosition.left();
		if (this.isValidPosition(top) && this.getCharacterWithPosition(top) == targetChar
				&& (null == filteredPosition || !filteredPosition.equals(top)))
			matchedPosition.add(top);
		if (this.isValidPosition(right) && this.getCharacterWithPosition(right) == targetChar
				&& (null == filteredPosition || !filteredPosition.equals(right)))
			matchedPosition.add(right);
		if (this.isValidPosition(bottom) && this.getCharacterWithPosition(bottom) == targetChar
				&& (null == filteredPosition || !filteredPosition.equals(bottom)))
			matchedPosition.add(bottom);
		if (this.isValidPosition(left) && this.getCharacterWithPosition(left) == targetChar
				&& (null == filteredPosition || !filteredPosition.equals(left)))
			matchedPosition.add(left);

		return matchedPosition;
	}

	private boolean isValidPosition(Position position) {
		if (0 <= position.x && position.x <= this.board.length - 1 && 0 <= position.y
				&& position.y <= this.board[0].length - 1)
			return true;
		return false;
	}

	private char getCharacterWithPosition(Position position) {
		return this.board[position.x][position.y];
	}

	/**
	 * An abstract class, which represents an element position for the given 2D
	 * board.
	 * 
	 * @author liuyi
	 *
	 */
	private class Position {

		private int x;
		private int y;

		private Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private Position top() {
			return new Position(this.x - 1, this.y);
		}

		private Position right() {
			return new Position(this.x, this.y + 1);
		}

		private Position bottom() {
			return new Position(this.x + 1, this.y);
		}

		private Position left() {
			return new Position(this.x, this.y - 1);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Position))
				return false;
			Position other = (Position) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}

	}

	public static void main(String[] args) throws IOException {
		char[][] board = new char[4][5];
		char[] row1 = { 'A', 'B', 'C', 'E', 'R' };
		char[] row2 = { 'S', 'F', 'C', 'S', 'T' };
		char[] row3 = { 'A', 'D', 'E', 'E', 'Z' };
		char[] row4 = { 'B', 'T', 'A', 'V', 'N' };
		board[0] = row1;
		board[1] = row2;
		board[2] = row3;
		board[3] = row4;
		WordSearcher ws = new WordSearcher(board);
		System.out.print(ws);
		if (0 == args.length) {
			System.out.println("Please enter a search word!");
		} else {
			boolean re = ws.wordSearch(args[0]);
			if (re)
				System.out.println("Find out the word: " + args[0]);
			else
				System.out.println("CAN NOT find out the word: " + args[0]);
		}
	}

}
