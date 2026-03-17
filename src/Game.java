import java.util.Scanner;

public class Game {

    private static Game theGame = null;
    public final int gridRows = 3;
    public final int gridCols = 3;
    public final int gridSize = 3;
    private char winningSide = '_';



    public char[][] grid;
    private boolean isFinished;

    public boolean getState() {
        return isFinished;
    }
    public String getWinner() {
        if (winningSide != '_')
            return "Winner is " + winningSide;
        else
            return "No winner yet";
    }

    public static Game getGame() {
        if (theGame == null) {
            theGame = new Game();
        }
        return theGame;
    }

    private Game() {
        isFinished = false;
        grid = new char[gridRows][gridCols];
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                grid[i][j] = '_';
            }
        }
    }

    public void printState() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print('-');
        }
        System.out.println();
        for (int i = 0; i < gridRows; i++) {
            System.out.print("| ");
            for (int j = 0; j < gridCols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("|");
        }

        for (int i = 0; i < 9; i++) {
            System.out.print('-');
        }
        System.out.println();
    }

    public void analyseStateGame() {
        int numberX = 0;
        int numberO = 0;
        int numberEmpty = 0;
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                if (grid[i][j] == 'X')
                    numberX++;
                else if (grid[i][j] == 'O') {
                    numberO++;
                }
                else {
                    numberEmpty++;
                }

            }
        }

        if (Math.abs(numberX - numberO) >= 2) {
            isFinished = true;
            System.out.println("Impossible1");
            return;
        }

        checkResult();

        if (winningSide != '_' && isFinished == true) {
            System.out.println(winningSide + " wins");
        }
        else if (numberEmpty == 0){
            System.out.println("Draw");
            isFinished = true;
        }
    }

    public void checkResult() {
        char checked;
        int counter = 0;
        for (int i = 0; i < gridSize; i++) {
            checked = grid[i][0];
            if (checked == '_')
                continue;
            for (int j = 1; j < gridSize; j++) {
                if (checked == grid[i][j])
                    counter++;

            }
            if (counter == 2) {
                winningSide = checked;
                isFinished = true;
                return;
            }
            counter = 0;
        }

        for (int i = 0; i < gridSize; i++) {
            checked = grid[0][i];
            if (checked == '_')
                continue;
            for (int j = 1; j < gridSize; j++) {
                if (checked == grid[j][i])
                    counter++;

            }
            if (counter == 2) {
                winningSide = checked;
                isFinished = true;
                return;
            }
            counter = 0;

        }
        if (grid[0][0] != '_') {
            checked = grid[0][0];
            for (int i = 1; i < gridSize; i++) {
                if (grid[i][i] == checked) {
                    counter++;
                }
            }
            if (counter == 2) {
                isFinished = true;
                winningSide = checked;
                return;
            }
            counter = 0;
        }
        if (grid[0][2] != '_') {
            checked = grid[0][2];
            for (int i = 1; i < gridSize; i++) {
                if (grid[i][2-i] == checked) {
                    counter++;
                }
            }
            if (counter == 2) {
                isFinished = true;
                winningSide = checked;
                return;
            }
        }

        winningSide = '_';
        isFinished = false;
        return;
    }

    public boolean testPosition(String input,char side) {
        try {
            if (input.contains(" ")) {
                int x = Integer.parseInt(input.substring(0, input.indexOf(' ')));
                int y = Integer.parseInt(input.substring(input.indexOf(' ') + 1));
                if (x > 0 && x < 4 && y > 0 && y < 4) {

                    if (grid[x - 1][y - 1] == '_') {
                        grid[x - 1][y - 1] = side;
                        printState();
                        return true;
                    } else {
                        System.out.println("The cell is occupied! Choose anoter one!");
                        return false;
                    }
                } else {
                    System.out.println("Cordinates should be from 1 to 3!");
                    return false;
                }
            }
            else {
                System.out.println("You should enter numbers!");
                return false;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("You should enter  numbers!");
        }
        return false;
    }

    public void playTwoPlayers() {
        String startingInfo = "You will be playing tic tac toe. \n"
                + "You will take turns in passing position as either X/O"
                + "to the game by writing writing coridantes like as ROW COLUMN for example 2 3.";
        System.out.println(startingInfo);
        Scanner sc = new Scanner(System.in);
        printState();
        String singlePosition;
        boolean testPosition = false;
        while (!isFinished) {
            while (!testPosition && !isFinished) {
                System.out.println("X side:");
                singlePosition = sc.nextLine();
                testPosition = testPosition(singlePosition,'X');
            }
            if (!isFinished)
                analyseStateGame();
            testPosition = false;
            while (!testPosition && !isFinished) {
                System.out.println("O side");
                singlePosition = sc.nextLine();
                testPosition = testPosition(singlePosition,'O');
            }
            testPosition = false;
            if (!isFinished)
                analyseStateGame();
        }

        sc.close();
    }

    public void readState(String input) {
        boolean correctInput = true;
        if (input.length() == 9) {
            for (int i = 0; i < 9;i++) {
                if (!(input.charAt(i) == 'O' || input.charAt(i) == 'X' || input.charAt(i) == '_')){
                    correctInput = false;
                    break;
                }
                else {

                }
            }
            if (correctInput) {
                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < gridSize; j++) {
                        grid[i][j] = input.charAt(i*gridSize + j);
                    }
                }
            }
            else {
                System.out.println("Incorrect character in input!");
            }
        }
        else {
            System.out.println("Incorrect length of input!");
        }

    }

}
