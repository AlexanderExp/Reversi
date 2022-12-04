package org.example;

public class PlayGround {
    // SIZE x SIZE - is a current size of playing board (local copy)
    public int SIZE;
    // PlayGround with all the chips
    public char[][] playGround;


    /**
     * Public constructor of PlayGround
     * */
    public PlayGround(int size) {
        SIZE = size;
        playGround = new char[SIZE][];
        for (int col = 0; col < SIZE; col++) {
            playGround[col] = new char[SIZE];
        }
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                playGround[row][col] = ' ';
            }
        }

        playGround[SIZE / 2 - 1][SIZE / 2 - 1] = playGround[SIZE / 2][SIZE / 2] = '●';
        playGround[SIZE / 2 - 1][SIZE / 2] = playGround[SIZE / 2][SIZE / 2 - 1] = '○';
    }

    /**
     * Displays the PlayGround in the Console
     */
    public void displayPlayGround() {
        StringBuilder stringBuilder = new StringBuilder("\n ");
        for (int col = 0; col < SIZE; col++) {
            stringBuilder.append(String.format("   %d", col));
        }
        stringBuilder.append("\n");
        for (int row = 0; row < SIZE; row++) {
            stringBuilder.append("  +").append("---+".repeat(SIZE)).append(String.format("\n%2d| ", row));
            for (int col = 0; col < SIZE; col++) {
                stringBuilder.append(String.format(playGround[row][col] + " | "));
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("  +").append("---+".repeat(SIZE)).append("\n");
        System.out.print(stringBuilder);
    }

}
