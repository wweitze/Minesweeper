package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Dave Sweeper!");
        while(true){
            System.out.println("To begin, please select your game mode");
            System.out.println("1. Default");
            System.out.println("2. Custom");
            try{
                int choice = scan.nextInt();

                switch(choice){
                    case 1:
                        break;
                    case 2:
                        System.out.println("Please select the length of the board");
                        int length = scan.nextInt();
                        System.out.println("Please select the width of the board");
                        int width = scan.nextInt();
                        System.out.println("Please select the difficulty.");
                        System.out.println("Difficulty represents the percentage of board that is covered with mines with 1 being 10% mines and 9 being 90% mines");
                        int difficulty = scan.nextInt();
                        board = new Board(length,width,difficulty);
                        break;
                    default:
                        System.out.println("Please enter a valid menu option");
                }
            }
            catch(Exception e){
                System.out.println("Please enter a numeric value");
                scan.nextInt();
                continue;
            }
            break;
        }
        clearScreen();

        while(true){
            board.printBoard();

            System.out.println("What would you like to do?");

            try{
                System.out.println("Please enter the row with which youd like to interact");
                int row = scan.nextInt();

                System.out.println("Please enter the column with which youd like to interact");
                int column = scan.nextInt();

                System.out.println("What would you like to do?");
                System.out.println("1.Check a tile");
                System.out.println("2.Place a flag");
                System.out.println("3.Remove a flag");

                int choice = scan.nextInt();

                switch(choice){
                    case 1:
                        int tile = board.checkTile(row, column);
                        if((char)tile == Game.MINE_TOKEN){
                            System.out.println("minesweeper.Game Over!");
                            System.exit(0);
                        }
                        else{
                            board.updateActiveBoard(row,column,(char)tile);
                        }
                        break;
                    case 2:
                        board.placeFlag(row,column);
                        break;
                    case 3:
                        board.removeFlag(row,column);
                    default:
                        System.out.println("Please enter a valid menu option");
                }
            }
            catch(Exception e){
                System.out.println("Please enter a numeric value");
                // Consume the invalid input to prevent an infinite loop
                scan.nextLine();
                continue; // Restart the loop
            }

            if(board.isWin()){
                System.out.println("Congratulations! You won!");
                break;
            }
            else{
                clearScreen();
            }
        }
    }

    public static void clearScreen() {
        for(int i = 0; i < 50; i++)
            System.out.println("");
        System.out.flush();
    }
}
