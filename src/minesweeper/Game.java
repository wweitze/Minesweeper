package minesweeper;

public interface Game {

    public char MINE_TOKEN = 'X';
    public char HIDDEN_TOKEN = '#';
    public char BLANK_TOKEN = ' ';
    public char FLAG_TOKEN = '!';
    /*Pre-Conditions:
    minesweeper.Board != NULL;
    Post-Conditions;
    Returns the number of bombs in the adjacent area if there are no bombs. If it's a bomb, returns the constant number BOMB;*/
    int checkTile(int row, int column);
}
