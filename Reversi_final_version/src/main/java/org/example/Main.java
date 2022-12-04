package org.example;


import java.util.Scanner;

public class Main {

    /**
     * Handles the general logic of program
     * @param args - parameters that may be entered through terminal (not in use)
     */
    public static void main(String[] args) {
        try {
            boolean continueMarker = true;

            while (continueMarker) {
                Game game = new Game();

                String choiceOfActionInStartMenu = game.startMenu();

                switch (choiceOfActionInStartMenu) {
                    case "1" -> {
                        game.gameModeComputerVSPlayer();
                        System.out.println("\nBack to menu (y/n) ?");
                    }
                    case "2" -> {
                        game.gameModePlayerVSPlayer();
                        System.out.println("\nBack to menu (y/n) ?");
                    }
                    case "3" -> {
                        game.gameModePlayerVSHardBot();
                        System.out.println("\nBack to menu (y/n) ?");
                    }
                    case "4" -> {
                        game.getMaxScoreAgainstComputer();
                        System.out.println("\nBack to menu (y/n) ?");
                    }
                    case "5" -> {
                        game.rules();
                        System.out.println("\nBack to menu (y/n) ?");
                    }
                    case "6" -> {
                        System.out.println("Type in 'n' to exit the program and 'y' to go back to menu");
                    }
                }
                continueMarker = continueOrNot();
            }
            sayGoodBuy();
        } catch (Exception e) {
            // It MUST NOT give any Exceptions but what if...
            String[] asd = new String[3];
            main(asd);
        }
    }

    /**
     * Outputs some goodbye phrases
     */
    public static void sayGoodBuy() {
        System.out.println("It was a pleasure playing with you !\n" +
                "Good luck !");
    }

    /**
     *
     * @return - true means that player wants to continue playing, false - means that program needs to be killed
     */
    public static boolean continueOrNot() {
        Scanner input = new Scanner(System.in);
        String choiceToContinue = input.next();
        while (!choiceToContinue.equals("y") && !choiceToContinue.equals("n")) {
            System.out.println("Please enter 'y' to continue the session or 'n' to end the session\n" +
                    "Your choice: ");
            choiceToContinue = input.next();
        }
        return choiceToContinue.equals("y");
    }
}
