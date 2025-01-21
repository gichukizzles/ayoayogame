package game;

public class Board {
    private static final int PITS_PER_PLAYER = 6;
    private static final int INITIAL_SEEDS_PER_PIT = 4;
    
    private int[] playerOnePits;
    private int[] playerTwoPits;
    private int playerOneScore;
    private int playerTwoScore;

    public Board() {
        playerOnePits = new int[PITS_PER_PLAYER];
        playerTwoPits = new int[PITS_PER_PLAYER];
        
        // Initialize board with 4 seeds in each pit
        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            playerOnePits[i] = INITIAL_SEEDS_PER_PIT;
            playerTwoPits[i] = INITIAL_SEEDS_PER_PIT;
        }
        
        playerOneScore = 0;
        playerTwoScore = 0;
    }

    public void display() {
        // Display Player 2's pits (upside down)
        System.out.print("Player 2: ");
        for (int i = PITS_PER_PLAYER - 1; i >= 0; i--) {
            System.out.print(playerTwoPits[i] + " ");
        }
        System.out.println();

        // Display Player 1's pits
        System.out.print("Player 1: ");
        for (int pit : playerOnePits) {
            System.out.print(pit + " ");
        }
        System.out.println();
    }

    public boolean isValidMove(int pit, Player player) {
        int[] playerPits = (player.getName().equals("Player 1")) ? playerOnePits : playerTwoPits;
        
        // Check if pit is within range and has seeds
        return pit >= 0 && pit < PITS_PER_PLAYER && playerPits[pit] > 0;
    }

    public void sow(int startPit, Player player) {
        int[] currentPlayerPits = (player.getName().equals("Player 1")) ? playerOnePits : playerTwoPits;
        int[] opponentPits = (player.getName().equals("Player 1")) ? playerTwoPits : playerOnePits;
        
        int seeds = currentPlayerPits[startPit];
        currentPlayerPits[startPit] = 0;
        
        int currentPit = startPit;
        boolean isPlayerOneTurn = player.getName().equals("Player 1");
        
        while (seeds > 0) {
            currentPit++;
            
            // Wrap around the board
            if (isPlayerOneTurn) {
                if (currentPit < PITS_PER_PLAYER) {
                    currentPlayerPits[currentPit]++;
                    seeds--;
                } else if (currentPit < PITS_PER_PLAYER * 2) {
                    opponentPits[currentPit - PITS_PER_PLAYER]++;
                    seeds--;
                } else {
                    currentPit = 0;
                    isPlayerOneTurn = false;
                }
            } else {
                if (currentPit < PITS_PER_PLAYER) {
                    opponentPits[currentPit]++;
                    seeds--;
                } else if (currentPit < PITS_PER_PLAYER * 2) {
                    currentPlayerPits[currentPit - PITS_PER_PLAYER]++;
                    seeds--;
                } else {
                    currentPit = 0;
                    isPlayerOneTurn = true;
                }
            }
        }
        
        // Capture logic (simplified)
        captureSeeds(player);
    }

    private void captureSeeds(Player player) {
        // Simplified capture logic
        // In a real Ayoayo game, this would be more complex
        int[] currentPlayerPits = (player.getName().equals("Player 1")) ? playerOnePits : playerTwoPits;
        int[] opponentPits = (player.getName().equals("Player 1")) ? playerTwoPits : playerOnePits;
        
        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            if (currentPlayerPits[i] == 2 || currentPlayerPits[i] == 3) {
                if (player.getName().equals("Player 1")) {
                    playerOneScore += currentPlayerPits[i];
                } else {
                    playerTwoScore += currentPlayerPits[i];
                }
                currentPlayerPits[i] = 0;
            }
        }
    }

    public boolean isGameFinished() {
        // Game ends when one player has no seeds to play
        int playerOneSeeds = sumSeeds(playerOnePits);
        int playerTwoSeeds = sumSeeds(playerTwoPits);
        
        return playerOneSeeds == 0 || playerTwoSeeds == 0;
    }

    private int sumSeeds(int[] pits) {
        int total = 0;
        for (int pit : pits) {
            total += pit;
        }
        return total;
    }

    public int getPlayerScore(Player player) {
        return player.getName().equals("Player 1") ? playerOneScore : playerTwoScore;
    }
}
