package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    // Inner playGroundWithMoves object
    PlayGroundWithMoves playGround;
    // First player's chip
    private char player1;
    // Second player's chip
    private char player2;
    // Current player's chip
    private char currentPlayer;
    // SIZE x SIZE - is a current size of playing board
    private final int SIZE = 8;
    // Number of invalid moves
    private int invalidMoves = 0;
    // Number of moves done throughout the game, so it ends sooner or later
    private int numberOfMoves = 4;
    // Maximum score that player got while playing against computer during current session
    private static int maxScoreThroughoutGames = 0;

    /**
     * Game's constructor
     */
    public Game() {
        playGround = new PlayGroundWithMoves(SIZE);
    }

    /**
     * Starts the game against Computer
     */
    public void gameModeComputerVSPlayer() {
        System.out.println("*********** Your opponent is Computer ***********");

        chipChoice();
        System.out.println("\nGreat!\n" +
                "You are playing with computer\n" +
                "Your chips are '" + player1 + "'");
        System.out.println("'" +
                "Good luck ! Have fun !\n");

        int stepCounter = -1;
        List<char[][]> previousMovesList = new ArrayList<>();
        boolean flagToContinueSteppingBack = true;
        do {
            if (currentPlayer == player1) {
                System.out.println("Your turn:");
                previousMovesList.add(new char[SIZE][]);
                stepCounter++;
                for (int i = 0; i < SIZE; i++) {
                    previousMovesList.get(stepCounter)[i] = new char[SIZE];
                }
                for (int i = 0; i < SIZE; i++) {
                    System.arraycopy(playGround.playGround[i], 0, previousMovesList.get(stepCounter)[i], 0, SIZE);
                }
                flagToContinueSteppingBack = true;
                do {
                    if (playerTakeTurn(currentPlayer).equals("Make a step back")) {
                        if (previousMovesList.size() > 1) {

                            for (int i = 0; i < SIZE; i++) {
                                System.arraycopy(previousMovesList.get(stepCounter - 1)[i], 0, playGround.playGround[i], 0, SIZE);
                            }
                            previousMovesList.remove(stepCounter - 1);
                            stepCounter--;
                            System.out.println("You have returned to the previous step");
                            playGround.displayPlayGround();
                        } else {
                            System.out.println("You need to make one step at least to be able to return to it");
                            flagToContinueSteppingBack = false;
                        }
                    } else {
                        flagToContinueSteppingBack = false;
                        currentPlayer = currentPlayer == player1 ? player2 : player1;
                        playGround.displayPlayGround();
                    }
                } while (flagToContinueSteppingBack);
            } else {
                System.out.println("Computer's turn:");
                computerTakeTurn("Easy");
                playGround.displayPlayGround();
                currentPlayer = currentPlayer == player1 ? player2 : player1;
            }
        } while (numberOfMoves < SIZE * SIZE && invalidMoves < 2);

        endGame("PlayerVSComputer");

    }

    /**
     * Starts the game against another player
     */
    public void gameModePlayerVSPlayer() {
        System.out.println("*********** Your opponent is another person ***********");

        chipChoice();

        System.out.println("\nGreat!\n" +
                "You are playing with player2\n" +
                "Your chips are '" + player1 + "'\n" +
                "Player2's chips are '" + player2 + "'");
        System.out.println("'" +
                "Good luck ! Have fun !\n");

        do {
            if (currentPlayer == player1) {
                System.out.println("Now it is Player1's turn: ");
                playerTakeTurn(currentPlayer);
            } else {
                System.out.println("Now it is Player2's turn: ");
                playerTakeTurn(currentPlayer);
            }
            currentPlayer = currentPlayer == player1 ? player2 : player1;
        } while (numberOfMoves < SIZE * SIZE && invalidMoves < 2);

        endGame("PlayerVSPlayer");
    }

    /**
     * Starts the game against Hard Computer
     */
    public void gameModePlayerVSHardBot() {
        System.out.println("*********** Your opponent is a trained russian Computer, Ivan ***********");
        System.out.println("Ivan: Bip Boop");

        chipChoice();

        System.out.println("\nGreat!\n" +
                "You are playing with Ivan\n" +
                "Your chips are '" + player1 + "'\n");
        System.out.println("'" +
                "Good luck ! Have fun !\n");
        System.out.println("Ivan: Bip Boop");


        int stepCounter = -1;
        List<char[][]> previousMovesList = new ArrayList<>();
        boolean flagToContinueSteppingBack = true;
        do {
            if (currentPlayer == player1) {
                System.out.println("Your turn:");
                previousMovesList.add(new char[SIZE][]);
                stepCounter++;
                for (int i = 0; i < SIZE; i++) {
                    previousMovesList.get(stepCounter)[i] = new char[SIZE];
                }
                for (int i = 0; i < SIZE; i++) {
                    System.arraycopy(playGround.playGround[i], 0, previousMovesList.get(stepCounter)[i], 0, SIZE);
                }
                flagToContinueSteppingBack = true;
                do {
                    if (playerTakeTurn(currentPlayer).equals("Make a step back")) {
                        if (previousMovesList.size() > 1) {

                            for (int i = 0; i < SIZE; i++) {
                                System.arraycopy(previousMovesList.get(stepCounter - 1)[i], 0, playGround.playGround[i], 0, SIZE);
                            }
                            previousMovesList.remove(stepCounter - 1);
                            stepCounter--;
                            System.out.println("You have returned to the previous step");
                            playGround.displayPlayGround();
                        } else {
                            System.out.println("You need to make one step at least to be able to return to it");
                            flagToContinueSteppingBack = false;
                        }
                    } else {
                        flagToContinueSteppingBack = false;
                        currentPlayer = currentPlayer == player1 ? player2 : player1;
                        playGround.displayPlayGround();
                    }
                } while (flagToContinueSteppingBack);
            } else {
                System.out.println("Computer's turn:");
                computerTakeTurn("Hard");
                playGround.displayPlayGround();
                currentPlayer = currentPlayer == player1 ? player2 : player1;
            }
        } while (numberOfMoves < SIZE * SIZE && invalidMoves < 2);

        endGame("PlayerVSHardComputer");
    }


    /**
     * refreshes maximum score that player got while playing against computer during current session
     *
     * @param score - maximum score that player got while playing against computer during current session
     */
    private void refreshMaxScoreAgainstComputer(int score) {
        if (score > maxScoreThroughoutGames) {
            maxScoreThroughoutGames = score;
        }
    }

    /**
     * Prints maximum score that player got while playing against computer during current session
     */
    public void getMaxScoreAgainstComputer() {
        System.out.println("Your maximum score against Computer is " + maxScoreThroughoutGames + "\n" +
                "Good Job !");
    }

    /**
     * Shows rules in Console
     */
    public void rules() {
        System.out.println("""
                Here are some basic rules for this game:\s
                Basic Play:
                Each turn, the player places one piece on the board with their colour facing up.

                For the first four moves, the players must play to one of the four squares in the middle of the board and no pieces are captured or reversed.

                Each piece played must be laid adjacent to an opponent's piece so that\s
                the opponent's piece or a row of opponent's pieces is flanked by the new piece\s
                and another piece of the player's colour.\s
                All of the opponent's pieces between these two pieces are 'captured' and turned over\s
                to match the player's colour.

                It can happen that a piece is played so that pieces or rows of pieces\s
                in more than one direction are trapped between the new piece played and other pieces of the same colour.\s
                In this case, all the pieces in all viable directions are turned over.

                The game is over when neither player has a legal move (i.e. a move that captures at least one opposing piece) or when the board is full.""");
    }


    /**
     * Shows '?' on moves that are estimated as possible as valid
     *
     * @param possibleMoves - List of possible moves where i'th position is for row of a possible move
     *                      and i+1'th position is for col of a possible move
     */
    private void showPlayerHisPossibleMoves(List<Integer> possibleMoves) {
        for (int i = 0; i < possibleMoves.size(); i += 2) {
            playGround.playGround[possibleMoves.get(i)][possibleMoves.get(i + 1)] = '?';
        }
    }

    /**
     * Hides '?' on moves that are estimated as possible as valid
     */
    private void hidePlayersPossibleMoves() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (playGround.playGround[row][col] == '?') {
                    playGround.playGround[row][col] = ' ';
                }
            }
        }
    }

    /**
     * Activates Player's moves
     *
     * @param currentPlayer - current player; '○' or '●'
     * @return - String that identifies whether player chose to make a step back
     */
    private String playerTakeTurn(char currentPlayer) {
        char opponent = (currentPlayer == '●' ? '○' : '●');
        if (playGround.getValidMoves(currentPlayer, opponent) != 0) {
            List<Integer> viableMovesList = playGround.getValidMovesList();
            hidePlayersPossibleMoves();
            showPlayerHisPossibleMoves(viableMovesList);

            playGround.displayPlayGround();
            System.out.println("Here are your possible moves : ");
            int optionNumber = 1;
            System.out.println("0. Step back");
            for (int i = 0; i < viableMovesList.size(); i += 2) {
                System.out.println(optionNumber++ + ". (" + (viableMovesList.get(i)) + ", " + (viableMovesList.get(i + 1)) + ")");
            }

            System.out.println("Choose one by typing in its number: ");

            int maxPossibleOptionNumber = viableMovesList.size() / 2;
            int choiceOfMove = getChoiceOfMove(maxPossibleOptionNumber);
            if (choiceOfMove != 0) {
                int row = viableMovesList.get((choiceOfMove - 1) * 2);
                int col = viableMovesList.get((choiceOfMove - 1) * 2 + 1);
                playGround.makeMove(row, col, currentPlayer, opponent);

                hidePlayersPossibleMoves();

                ++numberOfMoves;
            } else {
                hidePlayersPossibleMoves();
                return "Make a step back";
            }
        } else {
            if (++invalidMoves < 2) {
                System.out.println("\nYou have to pass\n");
            } else {
                System.out.println("There are no possible moves\n" + "Game over ! ");
            }
        }
        return "";
    }

    /**
     * Activates Computer's moves
     *
     * @param mode - identifies whether Hard mode of Computer is activated
     */
    private void computerTakeTurn(String mode) {
        if (playGround.getValidMoves(player2, player1) != 0) {
            invalidMoves = 0;
            if (mode.equals("Easy")) {
                playGround.makeMoveComputerEasy(player2, player1);
            } else if (mode.equals("Hard")) {
                playGround.makeMoveComputerHard(player2, player1);
            }
            ++numberOfMoves;
        } else {
            if (++invalidMoves < 2) {
                System.out.println("\nComputer has to pass\n");
            } else {
                System.out.println("There are no possible moves\n" + "Game over ! ");
            }
        }
    }

    /**
     * Ends game showing final results and final board
     * Counts final results for both players
     *
     * @param flag - needed to make clear with what words to end the game and identify the necessity of refreshing the
     *             maximum score player got while playing against computer during current session
     *             Possible values : "PlayerVSComputer", "PlayerVSPlayer", "PlayerVSHardComputer"
     */
    private void endGame(String flag) {
        playGround.displayPlayGround();

        int numberOfPlayer1Chips = 0;
        int numberOfPlayer2Chips = 0;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (playGround.playGround[row][col] == player2) {
                    ++numberOfPlayer2Chips;
                } else if (playGround.playGround[row][col] == player1) {
                    ++numberOfPlayer1Chips;
                }
            }
        }

        System.out.println("The final score of this game is:\n");
        switch (flag) {
            case "PlayerVSComputer" -> playerVSComputerAfterGameEnded(numberOfPlayer1Chips, numberOfPlayer2Chips);
            case "PlayerVSPlayer" -> playerVSPlayerAfterGameEnded(numberOfPlayer1Chips, numberOfPlayer2Chips);
            case "PlayerVSHardComputer" -> playerVSHardComputerAfterGameEnded(numberOfPlayer1Chips, numberOfPlayer2Chips);
        }
    }

    /**
     * Outputs some final phrases in console after the game against computer has ended
     * Refreshes the maximum score player got while playing against computer during current session
     *
     * @param numberOfPlayer1Chips - number of player's chips
     * @param numberOfPlayer2Chips - number of computer's chips
     */
    private void playerVSHardComputerAfterGameEnded(int numberOfPlayer1Chips, int numberOfPlayer2Chips) {
        System.out.println("Player: " + numberOfPlayer1Chips + "\n" + "Ivan: " + numberOfPlayer2Chips);
        if (numberOfPlayer1Chips > numberOfPlayer2Chips) {
            System.out.println("You are the winner !");
            System.out.println("Ivan: bip boop");
        } else if (numberOfPlayer1Chips < numberOfPlayer2Chips) {
            System.out.println("Computer is the winner !");
            System.out.println("Ivan: BIP BOOP BIP BOOP");
        } else {
            System.out.println("Nobody wins !");
            System.out.println("Ivan: Bip Boop");
        }
        refreshMaxScoreAgainstComputer(numberOfPlayer1Chips);
    }


    /**
     * Outputs some final phrases in console after the game against another player has ended
     *
     * @param numberOfPlayer1Chips - number of player1's chips
     * @param numberOfPlayer2Chips - number of player2's chips
     */
    private void playerVSPlayerAfterGameEnded(int numberOfPlayer1Chips, int numberOfPlayer2Chips) {
        System.out.println("Player1: " + numberOfPlayer1Chips + "\n" + "Player2: " + numberOfPlayer2Chips);
        if (numberOfPlayer1Chips > numberOfPlayer2Chips) {
            System.out.println("Player1 is the winner !");
        } else if (numberOfPlayer1Chips < numberOfPlayer2Chips) {
            System.out.println("Player2 is the winner !");
        } else {
            System.out.println("Nobody wins !");
        }
    }

    /**
     * Outputs some final phrases in console after the game against computer has ended
     * Refreshes the maximum score player got while playing against computer during current session
     *
     * @param numberOfPlayer1Chips - number of player's chips
     * @param numberOfPlayer2Chips - number of computer's chips
     */
    private void playerVSComputerAfterGameEnded(int numberOfPlayer1Chips, int numberOfPlayer2Chips) {
        System.out.println("Player: " + numberOfPlayer1Chips + "\n" + "Computer: " + numberOfPlayer2Chips);
        if (numberOfPlayer1Chips > numberOfPlayer2Chips) {
            System.out.println("You are the winner !");
        } else if (numberOfPlayer1Chips < numberOfPlayer2Chips) {
            System.out.println("Computer is the winner !");
        } else {
            System.out.println("Nobody wins !");
        }
        refreshMaxScoreAgainstComputer(numberOfPlayer1Chips);
    }

    /**
     * Checks if string can be turned into int value
     *
     * @param str - string that needs to be checked
     * @return - true if string can be turned into number, else - false
     */
    private static boolean tryParse(String str) {
        try {
            Integer.parseInt(str);
            return (true);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Handles player's choice of next move from given list of options
     *
     * @param maxPossibleOptionNumber - the last number of option in list of possible moves
     * @return - number of chosen option from the list of possible moves
     */
    private int getChoiceOfMove(int maxPossibleOptionNumber) {
        Scanner input = new Scanner(System.in);
        String choiceOfMove = input.next();
        int choiceOfMoveResult = 0;
        if (!choiceOfMove.equals("0")) {
            boolean choiceOfMoveIsCorrect = false;
            for (int i = 1; i <= maxPossibleOptionNumber; i++) {
                if (choiceOfMove.equals(Integer.toString(i))) {
                    choiceOfMoveIsCorrect = true;
                    break;
                }
            }
            while (!choiceOfMoveIsCorrect) {
                System.out.println("""
                        There is no such move
                        Please enter one of the numbers of moves from list above
                        Your move :\s""");
                choiceOfMove = input.next();
                for (int i = 1; i <= maxPossibleOptionNumber; i++) {
                    if (choiceOfMove.equals(Integer.toString(i))) {
                        choiceOfMoveIsCorrect = true;
                        break;
                    }
                }
            }
            if (!tryParse(choiceOfMove)) {
                System.out.println("Something went wrong in parsing choiceOfMove");
            } else {
                choiceOfMoveResult = Integer.parseInt(choiceOfMove);
            }
        }

        return choiceOfMoveResult;
    }


    /**
     * Choice of the chip for player 1
     * Possible chips: '●' or '○'
     * Returns void
     */
    private void chipChoice() {
        System.out.println("""
                Now it's time to choose your chips
                1. '○' - goes first
                2. '●' - goes second
                Your choice (1/2):\s""");
        Scanner in = new Scanner(System.in);
        String choice = in.next();


        while (!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Please press '1' or '2' on your keyboard then 'Enter' to continue : ");
            choice = in.next();
        }

        switch (choice) {
            case "1" -> {
                player1 = '○';
                player2 = '●';
                currentPlayer = player1;
            }
            case "2" -> {
                player1 = '●';
                player2 = '○';
                currentPlayer = player2;
            }
        }
    }

    /**
     * Prints the menu
     * Returns the player's choice of menu option in String format
     * Possible return values : "1", "2", "3", "4", "5", '6'
     */
    public String startMenu() {
        System.out.println("\n***************************  Reversi The Game  ***************************\n");
        System.out.println("""
                1. Play against Computer
                2. Play against real life opponent
                3. Play against hardcore Computer
                4. Get your maximum score in game mode against Computer
                5. Rules
                6. Exit
                Your Choice (1/2/3/4/5/6) :""");
        Scanner in = new Scanner(System.in);
        String choice = in.next();
        while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5") && !choice.equals("6")) {
            System.out.println("Please press '1' or '2' or '3' or '4' or '5' or '6' on your keyboard then 'Enter' to continue : ");
            choice = in.next();
        }
        return choice;
    }
}
