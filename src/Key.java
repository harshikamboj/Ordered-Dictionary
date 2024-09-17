// CS 2210 Assignment 4
// Harshi Kamboj
// 251296379
// March 21st, 2024
public class Key {
    // Instance variable to store the label of the key. It is a String.
    private String label;
    // Instance variable to store the type of the key. It is an integer.
    private int kind;

    // Constructor that initializes the Key object with a label and kind.
    // The label is converted to lowercase to ensure case-insensitive comparison.
    public Key(String label, int kind) {
        // Using the toLowerCase method to store the label in lowercase
        this.label = label.toLowerCase();
        this.kind = kind;
    }

    // Method to retrieve the label of the key.
    public String getLabel() {
        return label;
    }

    // Method to retrieve the kind (type) of the key.
    public int getKind() {
        return kind;
    }

    // Method to compare this key with another key.
    // It first compares labels lexicographically. If they are equal, it compares the kind.
    public int compareTo(Key other) {
        // Compare labels lexicographically
        int labelComparison = this.label.compareTo(other.label);
        if (labelComparison != 0) {
            return labelComparison;
        } else {
            // If labels are equal, compare kinds
            return Integer.compare(this.kind, other.kind);
        }
    }

    // Method to check equality of two Key objects.
    // It checks if both the label and kind are equal.
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Key key = (Key) obj;
        return kind == key.kind && label.equals(key.label);
    }

    // Method to generate a hash code for the Key object.
    // It uses the hash code of the label and combines it with the kind.
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + kind;
        return result;
    }
}