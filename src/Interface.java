// CS 2210 Assignment 4
// Harshi Kamboj
// 251296379
// March 21st, 2024
import java.io.*;
import java.util.*;

// The Interface class serves as the main entry point for the dictionary application
public class Interface {
    // Static members to hold the dictionary and multimedia handlers
    private static BSTDictionary dictionary = new BSTDictionary();
    private static SoundPlayer soundPlayer = new SoundPlayer();
    private static PictureViewer pictureViewer = new PictureViewer();
    private static ShowHTML showHTML = new ShowHTML();

    // The main method that drives the program
    public static void main(String[] args) {
        // Check if an input file argument is provided
        if (args.length != 1) {
            System.out.println("Usage: java Interface inputFile");
            return; // Exit if not provided
        }
        String inputFile = args[0];

        // Try to read and process the input file
        try {
            Scanner scanner = new Scanner(new File(inputFile));
            // Read records from the file and add them to the dictionary
            while (scanner.hasNextLine()) {
                String label = scanner.nextLine().trim().toLowerCase(); // Label is always in lower case
                String typeAndData = scanner.nextLine().trim(); // The next line contains type and data
                int type = determineType(typeAndData); // Determine the type of the record
                String data = extractData(typeAndData); // Extract the data part

                // Create a record and add it to the dictionary
                Record record = new Record(new Key(label, type), data);
                dictionary.put(record);
            }
            scanner.close(); // Close the scanner
        } catch (FileNotFoundException e) {
            // Handle the case where the input file isn't found
            System.out.println("File not found: " + inputFile);
        } catch (DictionaryException e) {
            // Handle exceptions specific to dictionary operations
            System.out.println("DictionaryException: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other exceptions and print an error message
            System.out.println("An error occurred while processing the file.");
            e.printStackTrace();
        }

        // Set up a loop to process commands from the user
        StringReader keyboard = new StringReader();
        String command;
        do {
            // Read a command from the user
            command = keyboard.read("Enter next command: ").trim().toLowerCase();
            try {
                // Process the command
                processCommand(command);
            } catch (MultimediaException e) {
                // Handle multimedia exceptions (such as failing to play a sound)
                System.out.println(e.getMessage());
            }
            // Continue until 'exit' command is entered
        } while (!command.equalsIgnoreCase("exit"));
    }

    // Placeholder for the processCommand method which will handle the user's input commands
    private static void processCommand(String command) throws MultimediaException {
        // Process the command string using StringTokenizer
        StringTokenizer tokenizer = new StringTokenizer(command);
        // Check if the tokenizer has more tokens to process
        if (!tokenizer.hasMoreTokens()) {
            // If not, the command is invalid
            System.out.println("Invalid command.");
            return;
        }

        // Retrieve the next token from the tokenizer which represents the command type
        String commandType = tokenizer.nextToken();
        switch (commandType) {
            // Each case corresponds to a different command that can be processed
            case "define":
                // Define command: Look up a word and display its definition
                define(tokenizer.nextToken());
                break;
            case "translate":
                // Translate command: Translate a word and display its French equivalent
                translate(tokenizer.nextToken());
                break;
            case "sound":
                // Sound command: Play the sound file associated with the word
                playSound(tokenizer.nextToken());
                break;
            case "play":
                // Play command: Play the music file associated with the word
                playMusic(tokenizer.nextToken());
                break;
            case "say":
                // Say command: Play the voice file associated with the word
                sayVoice(tokenizer.nextToken());
                break;
            case "show":
                // Show command: Display the image associated with the word
                showImage(tokenizer.nextToken());
                break;
            case "animate":
                // Animate command: Display the animated image associated with the word
                animateImage(tokenizer.nextToken());
                break;
            case "browse":
                // Browse command: Open and display the webpage associated with the word
                browseWebpage(tokenizer.nextToken());
                break;
            case "delete":
                // Delete command: Remove the record associated with the word from the dictionary
                deleteRecord(tokenizer.nextToken());
                break;
            case "add":
                // Add command: Add a new record to the dictionary with the given word, type, and data
                addRecord(tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
                break;
            case "first":
                // First command: Print the record with the smallest key in the dictionary
                printFirst();
                break;
            case "last":
                // Last command: Print the record with the largest key in the dictionary
                printLast();
                break;
            case "exit":
                // Exit command: Terminate the program
                System.out.println("Exiting the program.");
                break;
            default:
                // Handle the case of an unrecognized command
                System.out.println("Invalid command.");
                break;
            case "list":
                // List command: List all records that start with the given prefix
                if (tokenizer.hasMoreTokens()) {
                    listRecords(tokenizer.nextToken());
                } else {
                    System.out.println("Invalid command.");
                }
                break;
        }
    }

    // Method to define a word
    private static void define(String word) {
        // Look up the word in the dictionary and print its definition if it exists
        Record record = dictionary.get(new Key(word, 1));
        if (record != null) {
            System.out.println(record.getDataItem());
        } else {
            System.out.println("The word " + word + " is not in the ordered dictionary");
        }
    }

    // Method to translate a word
    private static void translate(String word) {
        // Look up the word's translation in the dictionary and print it if it exists
        Record record = dictionary.get(new Key(word, 2));
        if (record != null) {
            System.out.println(record.getDataItem());
        } else {
            System.out.println("There is no definition for the word " + word);
        }
    }

    // Method to play a sound file associated with a word
    private static void playSound(String word) {
        // Attempt to play the sound file for the word, handle exceptions if the file doesn't exist or can't be played
        try {
            Record record = dictionary.get(new Key(word, 3));
            if (record != null) {
                soundPlayer.play(record.getDataItem());
            } else {
                System.out.println("There is no sound file for " + word);
            }
        } catch (MultimediaException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to play a music file associated with a word
    private static void playMusic(String word) {
        try {
            // Retrieve the record associated with the music file from the dictionary
            Record record = dictionary.get(new Key(word, 4));
            if (record != null) {
                // Use the SoundPlayer to play the music file if it's found
                soundPlayer.play(record.getDataItem());
            } else {
                // Inform the user if the music file does not exist
                System.out.println("There is no music file for " + word);
            }
        } catch (MultimediaException e) {
            // Catch and print any errors related to playing the music file
            System.out.println(e.getMessage());
        }
    }

    // Method to play a voice file associated with a word
    private static void sayVoice(String word) {
        try {
            // Retrieve the record associated with the voice file from the dictionary
            Record record = dictionary.get(new Key(word, 5));
            if (record != null) {
                // Use the SoundPlayer to play the voice file if it's found
                soundPlayer.play(record.getDataItem());
            } else {
                // Inform the user if the voice file does not exist
                System.out.println("There is no voice file for " + word);
            }
        } catch (MultimediaException e) {
            // Catch and print any errors related to playing the voice file
            System.out.println(e.getMessage());
        }
    }

    // Method to display an image file associated with a word
    private static void showImage(String word) {
        try {
            // Retrieve the record associated with the image file from the dictionary
            Record record = dictionary.get(new Key(word, 6));
            if (record != null) {
                // Use the PictureViewer to display the image file if it's found
                pictureViewer.show(record.getDataItem());
            } else {
                // Inform the user if the image file does not exist
                System.out.println("There is no image file for " + word);
            }
        } catch (MultimediaException e) {
            // Catch and print any errors related to displaying the image file
            System.out.println(e.getMessage());
        }
    }

    // Method to display an animated image file associated with a word
    private static void animateImage(String word) {
        try {
            // Retrieve the record associated with the animated image file from the dictionary
            Record record = dictionary.get(new Key(word, 7));
            if (record != null) {
                // Use the PictureViewer to display the animated image file if it's found
                pictureViewer.show(record.getDataItem());
            } else {
                // Inform the user if the animated image file does not exist
                System.out.println("There is no animated image file for " + word);
            }
        } catch (MultimediaException e) {
            // Catch and print any errors related to displaying the animated image file
            System.out.println(e.getMessage());
        }
    }

    // Method to browse a webpage associated with a word
    private static void browseWebpage(String word) {
        // Retrieve the record associated with the webpage URL from the dictionary
        Record record = dictionary.get(new Key(word, 8));
        if (record != null) {
            // Use the ShowHTML to display the webpage if the URL is found
            showHTML.show(record.getDataItem());
        } else {
            // Inform the user if the webpage URL does not exist
            System.out.println("There is no webpage file for " + word);
        }
    }

    // Method to delete a record associated with a word from the dictionary
    private static void deleteRecord(String word) {
        try {
            // Attempt to remove the record from the dictionary
            dictionary.remove(new Key(word, 1));
            // Inform the user that the record has been deleted
            System.out.println("Deleted record for " + word);
        } catch (DictionaryException e) {
            // Inform the user if no record exists with the given key
            System.out.println("No record in the ordered dictionary has key " + word);
        }
    }

    // Method to add a new record with specified word, type, and data to the dictionary
    private static void addRecord(String word, String type, String data) {
        try {
            // Convert type from String to integer
            int typeInt = Integer.parseInt(type);
            // Create a new Record with the given word, type, and data
            Record record = new Record(new Key(word, typeInt), data);
            // Add the record to the dictionary
            dictionary.put(record);
            // Confirm addition to the user
            System.out.println("Added record for " + word);
        } catch (NumberFormatException e) {
            // Handle incorrect format for record type
            System.out.println("Invalid type: " + type);
        } catch (DictionaryException e) {
            // Handle case where a record already exists with the given key
            System.out.println("A record with the given key is already in the ordered dictionary");
        }
    }

    // Method to print the first (smallest key) record in the dictionary
    private static void printFirst() {
        // Get the record with the smallest key from the dictionary
        Record record = dictionary.smallest();
        if (record != null) {
            // If record exists, print its details
            System.out.println(record.getKey().getLabel() + "," + record.getKey().getKind() + "," + record.getDataItem());
        } else {
            // If the dictionary is empty, inform the user
            System.out.println("The dictionary is empty.");
        }
    }

    // Method to print the last (largest key) record in the dictionary
    private static void printLast() {
        // Get the record with the largest key from the dictionary
        Record record = dictionary.largest();
        if (record != null) {
            // If record exists, print its details
            System.out.println(record.getKey().getLabel() + "," + record.getKey().getKind() + "," + record.getDataItem());
        } else {
            // If the dictionary is empty, inform the user
            System.out.println("The dictionary is empty.");
        }
    }

    // Method to determine the type of the data based on its format
    private static int determineType(String typeAndData) {
        // Check the beginning and end of the string to infer type
        if (typeAndData.startsWith(".")) {
            // Type is determined by the file extension
            if (typeAndData.endsWith(".wav") || typeAndData.endsWith(".mid")) {
                return typeAndData.startsWith("*") ? 4 : 3;
            } else if (typeAndData.endsWith(".gif")) {
                return 7;
            } else if (typeAndData.endsWith(".jpg")) {
                return 6;
            } else if (typeAndData.endsWith(".html")) {
                return 8;
            }
        }
        // Default to type 1 (definition) if no special character is found
        return typeAndData.startsWith("`") ? 5 : 1;
    }

    // Method to extract the data from a string containing both type and data
    private static String extractData(String typeAndData) {
        // Remove the first character if it is a special indicator
        if (typeAndData.startsWith(".") || typeAndData.startsWith("*") || typeAndData.startsWith("`")) {
            return typeAndData.substring(1);
        }
        // Return the original string if no special character is found
        return typeAndData;
    }

    // Method to list all records that start with a given prefix
    private static void listRecords(String prefix) {
        // Retrieve all records from the dictionary
        List<Record> allRecords = dictionary.getAllRecords();
        boolean found = false;

        // Loop through all records and print those starting with the prefix
        for (Record record : allRecords) {
            if (record.getKey().getLabel().startsWith(prefix.toLowerCase())) {
                System.out.println(record.getKey().getLabel());
                found = true;
            }
        }

        // Inform the user if no records start with the given prefix
        if (!found) {
            System.out.println("No label attributes in the ordered dictionary start with the prefix '" + prefix + "'");
        }
    }
}
