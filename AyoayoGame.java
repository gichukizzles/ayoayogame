package game;

import java.util.Scanner;

public class AyoayoGame {
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Scanner scanner;

    public AyoayoGame() {
        board = new Board();
        playerOne = new Player("Player 1");
        playerTwo = new Player("Player 2");
        currentPlayer = playerOne;
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (!isGameOver()) {
            displayBoard();
            int selectedPit = promptPlayerMove();
            makeMove(selectedPit);
            switchPlayers();
        }
        determineWinner();
    }

    private void displayBoard() {
        System.out.println("\n" + currentPlayer.getName() + "'s turn");
        board.display();
    }

    private int promptPlayerMove() {
        while (true) {
            System.out.print("Choose a pit (0-5): ");
            try {
                int pit = scanner.nextInt();
                if (board.isValidMove(pit, currentPlayer)) {
                    return pit;
                }
                System.out.println("Invalid move. Try again.");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private void makeMove(int startPit) {
        board.sow(startPit, currentPlayer);
    }

    private void switchPlayers() {
        currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne;
    }

    private boolean isGameOver() {
        return board.isGameFinished();
    }

    private void determineWinner() {
        int playerOneScore = board.getPlayerScore(playerOne);
        int playerTwoScore = board.getPlayerScore(playerTwo);

        System.out.println("\nGame Over!");
        System.out.println(playerOne.getName() + " score: " + playerOneScore);
        System.out.println(playerTwo.getName() + " score: " + playerTwoScore);

        if (playerOneScore > playerTwoScore) {
            System.out.println(playerOne.getName() + " wins!");
        } else if (playerTwoScore > playerOneScore) {
            System.out.println(playerTwo.getName() + " wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public static void main(String[] args) {
        AyoayoGame game = new AyoayoGame();
        game.start();
    }
}
