// CS 2210 Assignment 4
// Harshi Kamboj
// 251296379
// March 21st, 2024
public class Record {
    // Instance variable to store the key associated with this record
    private Key theKey;
    // Instance variable to store the data associated with this record
    private String data;

    // Constructor that initializes a new Record object with a key and associated data
    public Record(Key k, String theData) {
        theKey = k; // Assign the provided key to the instance variable
        data = theData; // Assign the provided data to the instance variable
    }

    // Method to retrieve the key of this record
    public Key getKey() {
        return theKey; // Return the key associated with this record
    }

    // Method to retrieve the data of this record
    public String getDataItem() {
        return data; // Return the data associated with this record
    }
}