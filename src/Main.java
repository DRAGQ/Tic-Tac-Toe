import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe !");
        prepareGame(null, null);
    }

    public static void prepareGame(String player1Name, String player2Name) {
        Scanner scanner = new Scanner(System.in);
        String player1 = player1Name != null ? player1Name : createPlayer(1, scanner);
        String player2 = player2Name != null ? player2Name : createPlayer(2, scanner);
        String[] players = new String[]{player1, player2};

        String[][] gameBoard = new String[3][3];
        createGameBoard(gameBoard);
        startGame(gameBoard, players, scanner);
    }

    public static String createPlayer(int playerOrder, Scanner scanner) {
        System.out.println("Player " + playerOrder + " choose name: ");
        return scanner.nextLine();
    }

    public static void startGame(String[][] gameBoard, String[] players, Scanner scanner) {
        System.out.println("New game!");
        final String[] symbols = new String[]{"X", "O"};
        boolean gameIsRunning = true;
        int i = 0;
        printGameBoard(gameBoard, true);

        while (gameIsRunning) {
            boolean playerTurn = true;
            System.out.println(players[i] + " turn");
            printGameBoard(gameBoard, false);
            System.out.println("Choose position between 1 and 9");

            while (playerTurn) {
                try {
                    int position = scanner.nextInt();
                    if (position > 9 || position < 1) {
                        System.out.println("Only numbers between 1 and 9 are allowed");
                    } else if (isAvailableAndFill(gameBoard, position, symbols[i])) {
                        playerTurn = false;
                    } else {
                        System.out.println("Position is already filled");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, only numbers are allowed.");
                    scanner.nextLine();
                }
            }
            String result = checkEndGame(gameBoard, symbols[i]);
            if (result.equals("winner")) {
                printGameBoard(gameBoard, false);
                System.out.println(players[i] + " wins!");
                gameIsRunning = false;
            } else if (result.equals("draw")) {
                printGameBoard(gameBoard, false);
                System.out.println("Draw!");
                gameIsRunning = false;
            }
            i = (i == 0 ? 1 : 0);
        }
        restartGame(players, scanner);
    }

    public static boolean isAvailableAndFill(String[][] gameBoard, int position, String symbol) {
        int counter = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                counter++;
                if (counter == position) {
                    if (gameBoard[i][j].equals("_")) {
                        gameBoard[i][j] = symbol;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void createGameBoard(String[][] gameBoard) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = "_";
            }
        }
    }

    public static void printGameBoard(String[][] gameBoard, boolean isTemplate) {
        if (isTemplate) {
            System.out.println("Game board positions: ");
        } else {
            System.out.println("Game board: ");
        }
        for (int row = 0; row < gameBoard.length; row++) {
            for (int column = 0; column < gameBoard[0].length; column++) {
                if (isTemplate) {
                    System.out.print(3 * row + column + 1 + " ");
                } else {
                    System.out.print(gameBoard[row][column] + " ");
                }
            }
            System.out.println();
        }
    }

    public static String checkEndGame(String[][] gameBoard, String symbol) {
        int mainDiagonalCounter = 0;
        int secondaryDiagonalCounter = 0;
        int checkDraw = 0;

        for (int row = 0; row < gameBoard.length; row++) {
            int rowCounter = 0;
            int columnCounter = 0;
            for (int column = 0; column < gameBoard[0].length; column++) {
                if (gameBoard[row][column].equals(symbol)) {
                    rowCounter++;
                } else {
                    rowCounter = 0;
                }
                if (gameBoard[column][row].equals(symbol)) {
                    columnCounter++;
                } else {
                    columnCounter = 0;
                }
                if (!gameBoard[row][column].equals("_")) {
                    checkDraw++;
                }

                if (rowCounter == 3 || columnCounter == 3) {
                    return "winner";
                }
            }
            if (gameBoard[row][row].equals(symbol)) {
                mainDiagonalCounter++;
            } else {
                mainDiagonalCounter = 0;
            }
            if (gameBoard[row][gameBoard.length - row - 1].equals(symbol)) {
                secondaryDiagonalCounter++;
            } else {
                secondaryDiagonalCounter = 0;
            }

            if (mainDiagonalCounter == 3 || secondaryDiagonalCounter == 3) {
                return "winner";
            }
        }
        if (checkDraw == 9) {
            return "draw";
        }
        return "";
    }

    public static void restartGame(String[] players, Scanner scanner) {
        while (true) {
            scanner.nextLine();
            System.out.println("Do you want to restart the game? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equals("y")) {
                System.out.println("Do you want change names? (y/n)");
                String answerToChange = scanner.nextLine();
                if (answerToChange.equals("y")) {
                    prepareGame(null, null);
                    break;
                } else if (answerToChange.equals("n")) {
                    prepareGame(players[0], players[1]);
                    break;
                }
            } else if (answer.equals("n")) {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }
}