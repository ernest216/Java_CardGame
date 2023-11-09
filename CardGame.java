import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileWriter;


public class CardGame {

    private ArrayList<Player> players;
    private ArrayList<Deck> decks;
        /**
     * Function to create a player in the game
     * 
     * @param n  the number of players
     */
    public void createPlayer(int n) {
        players.add(new Player(n));
    }

    /**
     * Function to create a card deck in the game
     * 
     * @param n the card deck number
     */
    public void createDeck(int n) {
        decks.add(new Deck(n));
    }

    private static int getNumberOfPlayers() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int numPlayers = 0;

        try {
            System.out.print("Please enter the number of players: ");
            numPlayers = scanner.nextInt();

            if (numPlayers <= 0) {
                throw new IOException("Number of players should be a positive integer.");
            }
        } catch (Exception e) {
            throw new IOException("Invalid input. Please enter a valid integer.");
        }

        return numPlayers;
    }

    private static String getPackLocation(int numPlayers) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String packLocation = null;
    
        do {
            System.out.print("Please enter the location of the pack to load/create: ");
            packLocation = scanner.nextLine().trim();
    
            File packFile = new File(packLocation);
    
            if (!packFile.exists()) {
                // If the pack file doesn't exist, create it
                try (FileWriter fileWriter = new FileWriter(packFile)) {
                    List<Integer> cardValues = generateRandomCardValues(numPlayers);
                    for (int i = 0; i < cardValues.size(); i++) {
                        fileWriter.write(i + "\n");
                    }
                } catch (IOException e) {
                    throw new IOException("Error creating the pack file: " + e.getMessage());
                }
            }
    
            if (!packFile.exists()) {
                System.out.println("Failed to create the pack file. Please enter a valid file location.");
                packLocation = null;
            }
        } while (packLocation == null);
    
        return packLocation;
    }
    
    private static List<Integer> generateRandomCardValues(int numPlayers) {
        List<Integer> cardValues = new ArrayList<>();


        // Find the laregest value of the pack of cards
        int max = 8 * numPlayers/4;

        // Generate values from 1 to the largest value of the pack of cards
        for (int i = 1; i <= max; i++) {
            for (i = 1; i <= 4; i++){
                cardValues.add(i);
            }
        }

        // Shuffle the values to randomize the order
        Collections.shuffle(cardValues);

        return cardValues;
    }


    public static void main(String[] args) {
        // Get the number of players from the command line
        int numPlayers = getNumberOfPlayers();

        // Get the location of the pack from the command line
        String packLocation = getPackLocation();

        // Create an instance of CardGame
        CardGame cardGame = new CardGame();

        // Initialize players, decks, and distribute cards
        cardGame.initializeGame(numPlayers, packLocation);

        // Start the game
        cardGame.startGame();
    }
}

