// CS 2210 Assignment 4
// Harshi Kamboj
// 251296379
// March 21st, 2024
import java.util.ArrayList;
import java.util.List;

public class BSTDictionary implements BSTDictionaryADT {
    // Binary search tree to maintain the dictionary records
    private BinarySearchTree bst;

    // Constructor initializes a new binary search tree to store the dictionary records
    public BSTDictionary() {
        bst = new BinarySearchTree();
    }

    // Retrieves a record with the specified key from the dictionary
    public Record get(Key k) {
        // Get the BSTNode corresponding to the key from the BST
        BSTNode node = bst.get(bst.getRoot(), k);
        // Return the record if the node is found, otherwise return null
        return (node == null) ? null : node.getRecord();
    }

    // Inserts a record into the dictionary
    public void put(Record d) throws DictionaryException {
        // Insert the record into the BST. May throw DictionaryException if the key already exists.
        bst.insert(bst.getRoot(), d);
    }

    // Removes the record with the specified key from the dictionary
    public void remove(Key k) throws DictionaryException {
        // Remove the node with the specified key from the BST. May throw DictionaryException if the key does not exist.
        bst.remove(bst.getRoot(), k);
    }

    // Finds and returns the record that is the successor of the specified key
    public Record successor(Key k) {
        // Get the BSTNode that is the successor of the specified key
        BSTNode node = bst.successor(bst.getRoot(), k);
        // Return the record of the successor node if found, otherwise return null
        return (node == null) ? null : node.getRecord();
    }

    // Finds and returns the record that is the predecessor of the specified key
    public Record predecessor(Key k) {
        // Get the BSTNode that is the predecessor of the specified key
        BSTNode node = bst.predecessor(bst.getRoot(), k);
        // Return the record of the predecessor node if found, otherwise return null
        return (node == null) ? null : node.getRecord();
    }

    // Retrieves the record with the smallest key in the dictionary
    public Record smallest() {
        // Find the BSTNode with the smallest key
        BSTNode node = bst.smallest(bst.getRoot());
        // Return the record with the smallest key if found, otherwise return null
        return (node == null) ? null : node.getRecord();
    }

    // Retrieves the record with the largest key in the dictionary
    public Record largest() {
        // Find the BSTNode with the largest key
        BSTNode node = bst.largest(bst.getRoot());
        // Return the record with the largest key if found, otherwise return null
        return (node == null) ? null : node.getRecord();
    }

    // Method to retrieve all records in the dictionary in sorted order
    public List<Record> getAllRecords() {
        // Initialize a list to store the records
        List<Record> recordsList = new ArrayList<>();
        // Perform in-order traversal of the BST and collect the records
        inOrderTraversal(bst.getRoot(), recordsList);
        return recordsList;
    }

    // Private helper method to perform an in-order traversal of the BST
    private void inOrderTraversal(BSTNode node, List<Record> recordsList) {
        if (node == null) {
            // Base case: if node is null, return
            return;
        }
        // Traverse left subtree
        inOrderTraversal(node.getLeftChild(), recordsList);
        // Process the current node
        if (node.getRecord() != null) {
            recordsList.add(node.getRecord());
        }
        // Traverse right subtree
        inOrderTraversal(node.getRightChild(), recordsList);
    }
}