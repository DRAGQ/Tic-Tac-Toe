import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe !");
        prepareGame(null, null);
    }

    public enum gameResult {
        WIN,
        DRAW,
        CONTINUE
    }

    public static void prepareGame(String player1Name, String player2Name) {
        Scanner scanner = new Scanner(System.in);
        String player1 = player1Name != null ? player1Name : createPlayer(1, scanner);
        String player2 = player2Name != null ? player2Name : createPlayer(2, scanner);
        String[] players = new String[]{player1, player2};

        char[][] gameBoard = new char[3][3];
        createGameBoard(gameBoard);
        startGame(gameBoard, players);
    }

    public static String createPlayer(int playerOrder, Scanner scanner) {
        System.out.println("Player " + playerOrder + " choose name: ");
        return scanner.nextLine();
    }

    public static void startGame(char[][] gameBoard, String[] players) {
        System.out.println("New game!");
        final char[] symbols = new char[]{'X', 'O'};
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
                    int position = choosePositon();
                    if (position > 9 || position < 1) {
                        System.out.println("Only numbers between 1 and 9 are allowed");
                    } else if (isAvailable(gameBoard, position)) {
                        addSymbol(gameBoard, position, symbols[i]);
                        playerTurn = false;
                    } else {
                        System.out.println("Position is already filled");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, only numbers are allowed.");
                }
            }
            gameResult result = checkEndGame(gameBoard, symbols[i]);
            if (result.equals(gameResult.WIN)) {
                printGameBoard(gameBoard, false);
                System.out.println(players[i] + " wins!");
                gameIsRunning = false;
            } else if (result.equals(gameResult.DRAW)) {
                printGameBoard(gameBoard, false);
                System.out.println("Draw!");
                gameIsRunning = false;
            }
            i = (i == 0 ? 1 : 0);
        }
        restartGame(players);
    }

    public static int choosePositon() {
        Scanner scanner = new Scanner(System.in);
        return (scanner.nextInt());
    }

    public static boolean isAvailable(char[][] gameBoard, int position) {
        int i = (position - 1) / gameBoard.length;
        int j = (position - 1) % gameBoard.length;
        return gameBoard[i][j] == '_';
    }

    public static void addSymbol(char[][] gameBoard, int position, char symbol) {
        int i = (position - 1) / gameBoard.length;
        int j = (position - 1) % gameBoard.length;
        gameBoard[i][j] = symbol;
    }

    public static void createGameBoard(char[][] gameBoard) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = '_';
            }
        }
    }

    public static void printGameBoard(char[][] gameBoard, boolean isTemplate) {
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

    public static gameResult checkEndGame(char[][] gameBoard, char symbol) {
        int mainDiagonalCounter = 0;
        int secondaryDiagonalCounter = 0;
        int checkDraw = 0;

        for (int row = 0; row < gameBoard.length; row++) {
            int rowCounter = 0;
            int columnCounter = 0;
            for (int column = 0; column < gameBoard[0].length; column++) {
                if (gameBoard[row][column] == symbol) {
                    rowCounter++;
                } else {
                    rowCounter = 0;
                }
                if (gameBoard[column][row] == symbol) {
                    columnCounter++;
                } else {
                    columnCounter = 0;
                }
                if (gameBoard[row][column] != '_') {
                    checkDraw++;
                }

                if (rowCounter == 3 || columnCounter == 3) {
                    return gameResult.WIN;
                }
            }
            if (gameBoard[row][row] == symbol) {
                mainDiagonalCounter++;
            } else {
                mainDiagonalCounter = 0;
            }
            if (gameBoard[row][gameBoard.length - row - 1] == symbol) {
                secondaryDiagonalCounter++;
            } else {
                secondaryDiagonalCounter = 0;
            }

            if (mainDiagonalCounter == 3 || secondaryDiagonalCounter == 3) {
                return gameResult.WIN;
            }
        }
        if (checkDraw == 9) {
            return gameResult.DRAW;
        }
        return gameResult.CONTINUE;
    }

    public static void restartGame(String[] players) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
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