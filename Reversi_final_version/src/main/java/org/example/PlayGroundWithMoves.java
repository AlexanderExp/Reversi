package org.example;

import java.util.ArrayList;
import java.util.List;

final public class PlayGroundWithMoves extends PlayGround {
    // Field with zeroes and ones, where 1 is a valid move and 0 - is not
    private final int[][] moves;

    /**
     * Public constructor of PlayGroundWithMoves
     * @param size - SIZE x SIZE - is a current size of playing board (local copy)
     */
    public PlayGroundWithMoves(int size) {
        super(size);
        moves = new int[SIZE][];
        for (int col = 0; col < SIZE; col++) {
            moves[col] = new int[SIZE];
        }
    }

    /**
     * Gets the amount of valid moves for current player
     * @param currentPlayer - current player
     * @param opponent - current opponent
     * @return - amount of valid moves
     */
    public int getValidMoves(char currentPlayer, char opponent) {
        int col;
        int row;
        int colDelta;
        int rowDelta;
        int temporaryRow;
        int temporaryCol;
        int numberOfViableMoves = 0;
        for (row = 0; row < SIZE; row++) {
            for (col = 0; col < SIZE; col++) {
                moves[row][col] = 0;
            }
        }

        for (row = 0; row < SIZE; row++) {
            for (col = 0; col < SIZE; col++) {
                if (playGround[row][col] != ' ') {
                    continue;
                }
                for (rowDelta = -1; rowDelta < 2; rowDelta++) {
                    for (colDelta = -1; colDelta < 2; colDelta++) {
                        if (row + rowDelta < 0 || row + rowDelta >= SIZE || col + colDelta < 0 || col + colDelta >= SIZE || (rowDelta == 0 && colDelta == 0)) {
                            continue;
                        }
                        if (playGround[row + rowDelta][col + colDelta] == opponent) {
                            temporaryRow = row + rowDelta;
                            temporaryCol = col + colDelta;

                            for (; ; ) {
                                temporaryRow += rowDelta;
                                temporaryCol += colDelta;
                                if (temporaryRow < 0 || temporaryRow >= SIZE || temporaryCol < 0 || temporaryCol >= SIZE) {
                                    break;
                                }
                                if (playGround[temporaryRow][temporaryCol] == ' ') {
                                    break;
                                }
                                if (playGround[temporaryRow][temporaryCol] == currentPlayer) {
                                    moves[row][col] = 1;
                                    ++numberOfViableMoves;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return numberOfViableMoves;
    }

    /**
     * Gets the list of valid moves
     * @return List of valid moves
     */
    public List<Integer> getValidMovesList() {
        List<Integer> result = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (moves[row][col] == 1) {
                    result.add(row);
                    result.add(col);
                }
            }
        }
        return result;
    }

    /**
     * Makes move for current player to the row and col
     * @param row - row of move
     * @param col - col of move
     * @param player - current player
     * @param opponent - current opponent
     */
    public void makeMove(int row, int col, char player, char opponent) {
        int rowDelta;
        int colDelta;
        int temporaryRow;
        int temporaryCol;

        playGround[row][col] = player;

        for (rowDelta = -1; rowDelta < 2; rowDelta++) {
            for (colDelta = -1; colDelta < 2; colDelta++) {
                if (row + rowDelta < 0 || row + rowDelta >= SIZE || col + colDelta < 0 || col + colDelta >= SIZE || (rowDelta == 0 && colDelta == 0)) {
                    continue;
                }
                if (playGround[row + rowDelta][col + colDelta] == opponent) {
                    temporaryRow = row + rowDelta;
                    temporaryCol = col + colDelta;

                    for (; ; ) {
                        temporaryRow += rowDelta;
                        temporaryCol += colDelta;
                        if (temporaryRow < 0 || temporaryRow >= SIZE || temporaryCol < 0 || temporaryCol >= SIZE) {
                            break;
                        }
                        if (playGround[temporaryRow][temporaryCol] == ' ') {
                            break;
                        }
                        if (playGround[temporaryRow][temporaryCol] == player) {
                            while (playGround[temporaryRow -= rowDelta][temporaryCol -= colDelta] == opponent) {
                                playGround[temporaryRow][temporaryCol] = player;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Makes move for hard mode Computer (with estimation of current move and possible next moves of player)
     * @param player - current player
     * @param opponent - current opponent
     */
    public void makeMoveComputerHard(char player, char opponent) {
        PlayGroundWithMoves temporaryPlayGroundInnerCopy = new PlayGroundWithMoves(SIZE);
        PlayGroundWithMoves temporaryPlayGroundInnerCopyForPossiblePlayersMove = new PlayGroundWithMoves(SIZE);
        int value = 0;
        int valueOfNextPlayersMove = 0;
        int maxValueOfNextPlayersMove = 0;
        int maxValue = 0;
        int bestCol = 0;
        int bestRow = 0;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (moves[row][col] == 0) {
                    continue;
                }
                for (int i = 0; i < SIZE; i++) {
                    System.arraycopy(playGround[i], 0, temporaryPlayGroundInnerCopy.playGround[i], 0, SIZE);
                }

                temporaryPlayGroundInnerCopy.makeMove(row, col, player, opponent);

                value = temporaryPlayGroundInnerCopy.getValue(playGround, player, opponent);

                temporaryPlayGroundInnerCopy.getValidMoves(opponent, player);

                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        if (temporaryPlayGroundInnerCopy.moves[row][col] == 0) {
                            continue;
                        }
                        for (int k = 0; k < SIZE; k++) {
                            System.arraycopy(temporaryPlayGroundInnerCopy.playGround[k], 0, temporaryPlayGroundInnerCopyForPossiblePlayersMove.playGround[k], 0, SIZE);
                        }
                        valueOfNextPlayersMove = temporaryPlayGroundInnerCopyForPossiblePlayersMove.getValue(temporaryPlayGroundInnerCopy.playGround, opponent, player);
                        if (maxValueOfNextPlayersMove < valueOfNextPlayersMove) {
                            maxValueOfNextPlayersMove = valueOfNextPlayersMove;
                        }
                    }
                }
                value -= maxValueOfNextPlayersMove;


                if (maxValue < value) {
                    maxValue = value;
                    bestRow = row;
                    bestCol = col;
                }
            }
        }
        makeMove(bestRow, bestCol, player, opponent);
    }

    /**
     * Makes move for easy mode Computer (with estimation of current move)
     * @param player - current player
     * @param opponent - current opponent
     */
    public void makeMoveComputerEasy(char player, char opponent) {
        PlayGroundWithMoves temporaryPlayGroundInnerCopy = new PlayGroundWithMoves(SIZE);
        int value;
        int maxValue = 0;
        int bestCol = 0;
        int bestRow = 0;


        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (moves[row][col] == 0) {
                    continue;
                }
                for (int i = 0; i < SIZE; i++) {
                    System.arraycopy(playGround[i], 0, temporaryPlayGroundInnerCopy.playGround[i], 0, SIZE);
                }

                temporaryPlayGroundInnerCopy.makeMove(row, col, player, opponent);

                value = temporaryPlayGroundInnerCopy.getValue(playGround, player, opponent);

                if (maxValue < value) {
                    maxValue = value;
                    bestRow = row;
                    bestCol = col;
                }
            }
        }
        makeMove(bestRow, bestCol, player, opponent);
    }

    /**
     * Gets the value of the step
     * @param previousBoard - previous board with its chips
     * @param player - current player
     * @param opponent - current opponent
     * @return - value of possible step
     */
    private int getValue(char[][] previousBoard, char player, char opponent) {
        int value = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (playGround[row][col] == player && previousBoard[row][col] == opponent) {
                    if (row == SIZE - 1 || col == SIZE - 1) {
                        value += 2;
                    } else {
                        value += 1;
                    }
                }
                if (playGround[row][col] == player && (previousBoard[row][col] == ' ' || previousBoard[row][col] == '?')) {
                    if (row == SIZE - 1 && col == SIZE - 1) {
                        value += 0.8;
                    } else if (row == SIZE - 1 || col == SIZE - 1) {
                        value += 0.4;
                    }
                }
            }
        }
        return value;
    }
}