import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;




public class CardGame {

    // ArrayLists to store players and decks
    private ArrayList<Player> players;
    private ArrayList<Deck> decks;

   /**
     * Constructor for the CardGame class
     * Initializes the players and decks ArrayLists
     */
    public CardGame() {
        this.players = new ArrayList<Player>();
        this.decks = new ArrayList<Deck>();
    }

    /**
     * Function that returns every player in the game
     * 
     * @return an ArrayList with every player in the game
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Function that returns every deck in the game
     * 
     * @return an ArrayList with every deck in the game
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }


    /**
     * Creates a new player in the game
     *
     * @param playerIndex Player number
     * @param drawDeck    Deck from which the player draws cards
     * @param discardDeck Deck to which the player discards cards
     */
    public void createPlayer(int playerIndex, Deck drawDeck, Deck discardDeck) {
        players.add(new Player(playerIndex, drawDeck, discardDeck, this));
    }

    /**
     * Creates a new deck in the game
     *
     * @param deckIndex Deck number
     */
    public void createDeck(int deckIndex) {
        decks.add(new Deck(deckIndex));
    }

    /**
     * Reads the number of players from the user input
     *
     * @return Number of players
     * @throws IOException if there's an issue with input
     */
    private static int getNumberOfPlayers() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
    
        while (true) {
            try {
                System.out.print("Please enter the number of players: ");
                number = scanner.nextInt();
    
                if (number <= 0) {
                    System.out.println("Number of players should be a positive integer.");
                    continue;  // Continue to the next iteration of the loop
                }
    
                break;  // Exit the loop if the input is valid
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();  // Consume the invalid input to avoid an infinite loop
            }
        }
    
        return number;
    }
    
    /**
    * Checks if the pack file is valid.
    *
    * @param packLocation Location of the pack file
    * @param numPlayers   Number of players in the game
    * @return True if the pack file is valid, false otherwise
    */
    private static boolean isValidPackFile(String packLocation, int numPlayers) {
        File packFile = new File(packLocation);
        // Check if the pack file exists, is a file, and has a valid extension
        if (packFile.exists() && packFile.isFile() && isValidFileExtension(packLocation, "txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(packLocation))) {
                String line;
                int Count = 0;
                // Read each line from the pack file
                while ((line = reader.readLine()) != null) {
                    try {
                        // Parse the line to an integer representing a card value
                        int num = Integer.parseInt(line);
                        if (num <= 0) {
                            System.out.println("ERROR: Pack file contains a non-positive integer");
                            return false;
                        }
                        Count++;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Pack file contains a non-integer");
                        return false;
                    }
                }
                // Check if the total number of cards matches the expected count
                if (Count == numPlayers * 8) {
                    return true; // The pack file is valid
                } else {
                    System.out.println("ERROR: There are not " + 8 * numPlayers + " cards in the pack file");
                    return false;
                }
            } catch (IOException e) {
                System.out.println("ERROR: Could not read pack file");
                return false;
            }
        } else {
            System.out.println("ERROR: Pack file does not exist");
            return false;
        }
        
    }

    /**
     * Gets the location of the pack file from user input
     *
     * @param numPlayers Number of players in the game
     * @return Location of the pack file
     * @throws IOException if there's an issue with input or file creation
     */


    private static String getPackLocation(int numPlayers) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String packLocation;
    
        while (true) {
            System.out.print("Please enter the location of the pack to load/create: ");
            packLocation = scanner.nextLine().trim();
    
            // Check if the file has a valid .txt extension
            if (!isValidFileExtension(packLocation, "txt")) {
                System.out.println("Invalid file format. Please enter a text file (with .txt extension).");
                continue;  // Continue to the next iteration of the loop
            }
    
            // Attempt to create the pack file
            try (FileWriter fileWriter = new FileWriter(packLocation)) {
                ArrayList<Integer> cardValues = generateRandomCardValues(numPlayers);
                for (int value : cardValues) {
                    fileWriter.write(value + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error creating the pack file: " + e.getMessage());
                // Continue to the next iteration of the loop if file creation fails
                continue;
            }
    
            // Check if the created pack file is valid
            if (isValidPackFile(packLocation, numPlayers)) {
                // Exit the loop when the file is successfully created and valid
                break;
            } else {
                // Delete the invalid file
                Files.deleteIfExists(Paths.get(packLocation));
                System.out.println("ERROR: Pack file is invalid. Creating a new file.");
                continue;
            }
        }
    
        return packLocation;
    }
    
    /**
     * Checks if a file has a valid extension
     *
     * @param fileName  Name of the file
     * @param extension Expected file extension
     * @return True if the file has a valid extension, false otherwise
     */
    
    private static boolean isValidFileExtension(String fileName, String extension) {
        // Check if the file has the specified extension
        return fileName.toLowerCase().endsWith("." + extension.toLowerCase());
    }

    /**
     * Generates random card values for the pack
     *
     * @param numPlayers Number of players in the game
     * @return ArrayList of random card values
     */
    private static ArrayList<Integer> generateRandomCardValues(int numPlayers) {
        ArrayList<Integer> cardValues = new ArrayList<>();


        // Find the laregest value of the pack of cards
        int max = 8 * numPlayers/4;

        // Generate values from 1 to the largest value of the pack of cards
        for (int i = 1; i <= max; i++) {
            for (int j = 1; j <= 4; j++){
                cardValues.add(i);
            }
        }

        // Shuffle the values to randomize the order
        Collections.shuffle(cardValues);

        return cardValues;
    }

    /**
     * Starts the game by creating players, decks, and distributing cards
     *
     * @throw
     * s IOException if there's an issue with file I/O
     */
    public void startGame() throws IOException{
        int n = getNumberOfPlayers();
        String packLocation = getPackLocation(n);

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
                        player.addCard(new Card(cardValue));
                        // Remove distributed card from the pack
                        // pack.deliverCard();
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
                        // pack.deliverCard();
                    } else {
                    // Handle invalid pack (not enough cards)
                        System.out.println("Invalid pack: Not enough cards for distribution.");
                        return;
                    }
                }
            }

            
        }
    }

    /**
     * Function to declare the winner of the game
     * 
     * @param winner player number of the winning player
     * @throws IOException cannot log to a file
     */
    public void declareWin(int winner) throws IOException {
        // For loop tells evryplayer who the winner is
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setWinner(winner);
        }
        // For loop that logs the cards in the deck at the end of game
        for (int i = 0; i < decks.size(); i++) {
            decks.get(i).logDeck();
        }
    }

    /**
     * Function that runs the game
     * 
     * @throws IOException cannot log to a file
     */
    public void runGame() throws IOException {
        // For loop to check if any player has a winning hand at the start
        for (int i = 0; i < players.size(); i++) {
            players.get(i).checkWin();
        }
        // For loop that starts the player threads to play
        for (int i = 0; i < players.size(); i++) {
            players.get(i).start();
        }
    }




    public static void main(String[] args) throws IOException {
        // Create an instance of CardGame
        CardGame cardGame = new CardGame();

        // Start the game
        cardGame.startGame();

        cardGame.runGame();
    }
}
