package minesweeper;

import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class Board implements Game{
    private final int length;
    private final int width;

    private final int difficulty;

    private char[][] trueBoard;
    private char[][] activeBoard;
    public Board(){
        length = 9;
        width = 9;
        difficulty = 1 ;

        trueBoard = new char[length][width];
        activeBoard = new char[length][width];
        generateTrueBoard();
        generateActiveBoard();
    }

    public Board(int l, int w, int d){
        length = l;
        width = w;
        difficulty = d;

        trueBoard = new char[length][width];
        activeBoard = new char[length][width];

        generateTrueBoard();
        generateActiveBoard();
    }

    @Override
    public int checkTile(int row, int column) {
        char tile = trueBoard[row][column];

        if(tile == MINE_TOKEN){
            System.out.println("You hit a mine!");
            return MINE_TOKEN;
        }
        else{
            return tile;
        }
    }

    public void clearBlankTiles(int startRow, int startCol) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});

        while (!queue.isEmpty()) {
            int[] currentCell = queue.poll();
            int row = currentCell[0];
            int col = currentCell[1];

            // Check if the current cell is out of bounds or has already been revealed
            if (row < 0 || row >= trueBoard.length || col < 0 || col >= trueBoard[0].length
                    || activeBoard[row][col] != Game.HIDDEN_TOKEN) {
                continue;
            }

            // Reveal the current cell if it's a blank space
            if (trueBoard[row][col] == Game.BLANK_TOKEN){
                activeBoard[row][col] = trueBoard[row][col];

                // Enqueue adjacent cells to be processed, including diagonals
                queue.offer(new int[]{row - 1, col}); // Top
                queue.offer(new int[]{row + 1, col}); // Bottom
                queue.offer(new int[]{row, col - 1}); // Left
                queue.offer(new int[]{row, col + 1}); // Right
                queue.offer(new int[]{row - 1, col - 1}); // Top Left
                queue.offer(new int[]{row - 1, col + 1}); // Top Right
                queue.offer(new int[]{row + 1, col - 1}); // Bottom Left
                queue.offer(new int[]{row + 1, col + 1}); // Bottom Right
            }
            else if (Character.isDigit(trueBoard[row][col])) {
                // Only enqueue adjacent cells with numbers
                if (hasAdjacentBlank(row, col)) {
                    activeBoard[row][col] = trueBoard[row][col];
                }
            }
        }
    }

    // Helper method to check if a cell has an adjacent blank space
    private boolean hasAdjacentBlank(int row, int col) {
        // Check all adjacent cells
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {1,1}, {-1,-1}, {-1,1}, {1,-1} }; // Top, Bottom, Left, Right
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < trueBoard.length && newCol >= 0 && newCol < trueBoard[0].length
                    && trueBoard[newRow][newCol] == Game.BLANK_TOKEN) {
                return true;
            }
        }
        return false;
    }

    public void updateActiveBoard(int row, int column, char tile){
        clearBlankTiles(row,column);
        activeBoard[row][column] = tile;
    }

    public void generateTrueBoard(){
        //Populate board with mines

        this.placeMines();

        //Populate board with blank tiles
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                char tile = (char) ('0' + countAdjacentMines(i,j));

                if(trueBoard[i][j] == Game.MINE_TOKEN){

                }
                else if(tile == '0'){
                    trueBoard[i][j] = Game.BLANK_TOKEN;
                }
                else {
                    trueBoard[i][j] = tile;
                }
            }
        }
    }

    public void generateActiveBoard(){
        for (int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                activeBoard[i][j] = HIDDEN_TOKEN;
            }
        }
    }

    public void generateBlankBoard(){
        //Populate board with blank tiles
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                activeBoard[i][j] = Game.HIDDEN_TOKEN; // Use any character to represent an empty cell
            }
        }
    }

    public void placeMines(){
        int numberOfMines = (int)((0.1 * difficulty) * (length*width)); // Define the number of mines you want to place

        // Loop to place mines randomly
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numberOfMines) {
            int randomRow = random.nextInt(length);
            int randomColumn = random.nextInt(width);

            if (trueBoard[randomRow][randomColumn] != Game.MINE_TOKEN) {
                trueBoard[randomRow][randomColumn] = Game.MINE_TOKEN; // Use any character to represent a mine
                minesPlaced++;
            }
        }
    }

    public void printBoard(){
        if(length >= 10){
            System.out.print("  |");
        }
        else{
            System.out.print(" |");
        }

        for(int i = 0; i < width; i++){
            if(i >= 10){
                System.out.print("" + i + " ");
            }
            else {
                System.out.print(" " + i + " ");
            }
        }
        System.out.println("|");

        if(length >= 10){
            System.out.print(" ");
        }
        System.out.print(" |");

        for(int i = 0; i < width; i++){
            System.out.print(" - ");
        }
        System.out.println("|");

        for(int i = 0; i < length; i++){
            if(length >= 10 && i < 10){
                System.out.print(" " + i + "|");
            }
            else{
                System.out.print("" + i + "|");
            }
            for(int j = 0; j < width; j++){
                if(j != width-1){
                    System.out.print(" " + activeBoard[i][j] + " ");
                }
                else{
                    System.out.println(" " + activeBoard[i][j] + " |");
                }
            }
        }

        if(length >= 10){
            System.out.print(" ");
        }
        System.out.print(" |");
        for(int i = 0; i < length; i++){
            System.out.print(" _ ");
        }
        System.out.println("|");
    }

    private int countAdjacentMines(int row, int column) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < length && j >= 0 && j < width && trueBoard[i][j] == Game.MINE_TOKEN) {
                    count++;
                }
            }
        }
        return count;
    }

    public void placeFlag(int row, int column){
        if(activeBoard[row][column] == Game.HIDDEN_TOKEN) {
            activeBoard[row][column] = Game.FLAG_TOKEN;
        }
    }

    public void removeFlag(int row, int column){
        if(activeBoard[row][column] == Game.FLAG_TOKEN){
            activeBoard[row][column] = Game.HIDDEN_TOKEN;
        }
    }

    public boolean isWin(){
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                if((trueBoard[i][j] == MINE_TOKEN && activeBoard[i][j] != FLAG_TOKEN) || activeBoard[i][j] == Game.HIDDEN_TOKEN){
                    return false;
                }
            }
        }
        return true;
    }
}