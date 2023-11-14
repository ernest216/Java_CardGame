import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Collections;


public class CardGame {

    private ArrayList<Player> players;
    private ArrayList<Deck> decks;
        /**
     * Function to create a player in the game
     * 
     * @param n  the player number
     */
    public void createPlayer(int playerIndex, Deck drawDeck, Deck discardDeck) {
        players.add(new Player(playerIndex, drawDeck, discardDeck, this));
    }

    /**
     * Function to create a card deck in the game
     * 
     * @param n the deck number
     */
    public void createDeck(int deckIndex) {
        decks.add(new Deck(deckIndex));
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
                    ArrayList<Integer> cardValues = generateRandomCardValues(numPlayers);
                    for (int i = 0; i < cardValues.size(); i++) {
                        fileWriter.write(i + "\n");
                        fileWriter.close();
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

    private static ArrayList<Integer> generateRandomCardValues(int numPlayers) {
        ArrayList<Integer> cardValues = new ArrayList<>();


        // Find the laregest value of the pack of cards
        int max = 8 * numPlayers/4;

        // Generate values from 1 to the largest value of the pack of cards
        for (int i = 1; i <= max; i++) {
            for (j = 1; j <= 4; j++){
                cardValues.add(i);
            }
        }

        // Shuffle the values to randomize the order
        Collections.shuffle(cardValues);

        return cardValues;
    }

    public void startGame(){
        String packLocation = getPackLocation();
        int n = getNumberOfPlayers();

        try (Scanner packScanner = new Scanner(new File(packLocation))) {
        // Create players and decks
            for (int i = 0; i < n; i++) {
                createDeck(i + 1);
            }
            for (int i = 0; i < n; i++) {
                // Check if it is the last player
                if (i != n - 1) {
                    // Let player discard card to the deck on the right hand side and draw cards from the left
                    Player player = new Player(i + 1, decks.get(i), decks.get(i + 1), this);
                    players.add(player);
                } else {
                    Player player = new Player(i + 1, decks.get(i), decks.get(0), this);
                    players.add(player);
                }
            }
            // Distribute hands to players in a round-robin fashion
            for (int i = 0; i < 4; i++) {
                for (Player player : players) {
                    if (packScanner.hasNextInt()) {
                        int cardValue = packScanner.nextInt();
                        //shd call function in the class
                        player.fillHand(new Card(cardValue));
                        // Remove distributed card from the pack
                        pack.deliverCard();
                    } else {
                    // Handle invalid pack (not enough cards)
                        System.out.println("Invalid pack: Not enough cards for distribution.");
                        return;
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                for (Deck deck : decks) {
                    if (packScanner.hasNextInt()) {
                        int cardValue = packScanner.nextInt();
                        //shd call function in the class
                        deck.addCard(new Card(cardValue));
                        // Remove distributed card from the pack
                        pack.deliverCard();
                    } else {
                    // Handle invalid pack (not enough cards)
                        System.out.println("Invalid pack: Not enough cards for distribution.");
                        return;
                    }
                }
            }

            
        }
    }




    public static void main(String[] args) {
        // Get the number of players from the command line
        int numPlayers = getNumberOfPlayers();

        // Create an instance of CardGame
        CardGame cardGame = new CardGame();

        // Start the game
        cardGame.startGame();
    }
}
