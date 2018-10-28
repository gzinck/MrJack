package view.board;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import view.TileView;

public class BoardView extends AnchorPane implements CharTokenViewModifier {
	
	private char[][] board;
	private int numRows, numCols;
	private TileView[][] tiles;
	/** This is an array of 2-tuples (row, col) of the indices of highlighted tiles. */
	private int[][] highlightedTiles;
	private double tW, tH; // Tile positions and sizes.
	
	public BoardView(char[][] boardTemplate) {
		board = boardTemplate;
		highlightedTiles = null;
		drawBoard();
		this.widthProperty().addListener((obs, oldVal, newVal) -> {
			if(tiles != null)
				resizeTiles();
		});
		this.heightProperty().addListener((obs, oldVal, newVal) -> {
			if(tiles != null)
				resizeTiles();
		});
	}
	
	private void drawBoard() {
		numRows = board.length;
		numCols = board[0].length;
		tiles = new TileView[numRows][numCols];
		tW = getTileWidth();
		tH = getTileHeight();
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				addTile(row, col);
			} // for each col
		} // for each row
	}
	
	private void resizeTiles() {
		tW = getTileWidth();
		tH = getTileHeight();
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				tiles[row][col].relocate(getTileX(col), getTileY(row, col));
				tiles[row][col].tileResize(tW, tH);
			} // for each col
		} // for each row
	}
	
	private void addTile(int row, int col) {
		tiles[row][col] = new TileView(board[row][col], tW, tH);
		tiles[row][col].resizeRelocate(getTileX(col), getTileY(row, col), tW, tH);
		this.getChildren().add(tiles[row][col]);
	}
	
	private double getTileWidth() {
		return getWidth() / (numCols * 0.75 + 0.25);
	}
	
	private double getTileX(int col) {
		double xOffset = getWidth() / (numCols + 0.25);
		return col * xOffset;
	}
	
	private double getTileHeight() {
		return getHeight() / (numRows + 0.5);
	}
	
	private double getTileY(int row, int col) {
		double yOffset = getTileHeight();
		if(col % 2 == 0) return (row + 0.5) * yOffset;
		return row * yOffset;
	}
	
	/**
	 * Highlights all the tiles with the (row, col) coordinates
	 * indicated array of 2-tupes passed as input.
	 * 
	 * @param tileLocations Array of 2-tupes (row, col) which indicate
	 * which tile locations should be highlighted.
	 */
	public void highlightTiles(int[][] tileLocations) {
		if(highlightedTiles != null)
			unhighlightTiles();
		highlightedTiles = tileLocations;
		for(int i = 0; i < highlightedTiles.length; i++)
			tiles[highlightedTiles[i][0]][highlightedTiles[i][1]].highlight();
	}
	
	public void unhighlightTiles() {
		for(int i = 0; i < highlightedTiles.length; i++)
			tiles[highlightedTiles[i][0]][highlightedTiles[i][1]].unhighlight();
		highlightedTiles = null;
	}
	
	@Override
	public void moveCharacter(int charIndex, int[] prevLocation, int[] newLocation) {
		tiles[prevLocation[0]][prevLocation[1]].removeCharToken();
		tiles[newLocation[0]][newLocation[1]].addCharacterToken(charIndex);
	}
	
	@Override
	public void setInnocence(int[] location, boolean isInnocent) {
		tiles[location[0]][location[1]].setInnocence(isInnocent);
	}
}