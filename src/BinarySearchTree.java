// CS 2210 Assignment 4
// Harshi Kamboj
// 251296379
// March 21st, 2024
public class BinarySearchTree {
    // The root node of the Binary Search Tree (BST)
    private BSTNode root;

    // Constructor for the BinarySearchTree class. Initializes an empty BST.
    public BinarySearchTree() {
        this.root = null;
    }

    // Returns the root node of the BST.
    public BSTNode getRoot() {
        return root;
    }

    // Recursive method to retrieve the node with the specified key from the BST.
    public BSTNode get(BSTNode r, Key k) {
        // Base case: if node r is null, the key is not found.
        if (r == null) {
            return null;
        }
        // Compare the key with the key in the current node r.
        int compareResult = r.getRecord().getKey().compareTo(k);
        // Recur to the left if the key to find is smaller than the current node's key.
        if (compareResult > 0) {
            return get(r.getLeftChild(), k);
        }
        // Recur to the right if the key to find is greater than the current node's key.
        else if (compareResult < 0) {
            return get(r.getRightChild(), k);
        }
        // If key is equal to the current node's key, return the current node.
        else {
            return r; // Found the node
        }
    }

    // Recursive method to insert a record into the BST.
    public void insert(BSTNode r, Record d) throws DictionaryException {
        // If the tree is empty, create a new node with d as root.
        if (root == null) {
            root = new BSTNode(d);
            return;
        }
        // Compare the key in the record with the key in the current node r.
        int compareResult = r.getRecord().getKey().compareTo(d.getKey());
        // Insert in the left subtree if the record's key is smaller.
        if (compareResult > 0) {
            // Insert here if left child is null.
            if (r.getLeftChild() == null) {
                r.setLeftChild(new BSTNode(d));
                r.getLeftChild().setParent(r);
            } else {
                // Recur to the left child if it's not null.
                insert(r.getLeftChild(), d);
            }
        }
        // Insert in the right subtree if the record's key is greater.
        else if (compareResult < 0) {
            // Insert here if right child is null.
            if (r.getRightChild() == null) {
                r.setRightChild(new BSTNode(d));
                r.getRightChild().setParent(r);
            } else {
                // Recur to the right child if it's not null.
                insert(r.getRightChild(), d);
            }
        }
        // If the record's key is equal to the key in the current node, throw an exception.
        else {
            throw new DictionaryException("Record with the same key already exists");
        }
    }

    // Method to remove a node with the specified key from the BST.
    public void remove(BSTNode r, Key k) throws DictionaryException {
        root = deleteRec(root, k);
    }
    // Helper method to recursively delete a node with the specified key.
    private BSTNode deleteRec(BSTNode root, Key key) throws DictionaryException {
        if (root == null) {
            throw new DictionaryException("No node found with the given key to delete");
        }

        int compareResult = key.compareTo(root.getRecord().getKey());
        // Recur to the left if the key to delete is smaller than the current node's key.
        if (compareResult < 0) {
            root.setLeftChild(deleteRec(root.getLeftChild(), key));
        }
        // Recur to the right if the key to delete is greater than the current node's key.
        else if (compareResult > 0) {
            root.setRightChild(deleteRec(root.getRightChild(), key));
        }
        // Found the node with the key to delete.
        else {
            // If one of the children is null, return the other child.
            if (root.getLeftChild() == null)
                return root.getRightChild();
            else if (root.getRightChild() == null)
                return root.getLeftChild();

            // Node with two children: Get the smallest node in the right subtree,
            // copy its record to the current node, and delete the smallest node.
            root.setRecord(smallest(root.getRightChild()).getRecord());
            root.setRightChild(deleteRec(root.getRightChild(), root.getRecord().getKey()));
        }
        return root;
    }
    // Method to find the predecessor of a node with the specified key in the BST.
    public BSTNode predecessor(BSTNode r, Key k) {
        // Retrieve the node with the given key.
        BSTNode current = get(root, k);
        if (current == null) {
            return null; // If the node is not found, there is no predecessor.
        }

        // If there is a left child, the predecessor is the maximum node in the left subtree.
        if (current.getLeftChild() != null) {
            return findMax(current.getLeftChild());
        } else {
            // If there is no left child, traverse up the tree to find the predecessor.
            BSTNode p = current.getParent();
            while (p != null && current == p.getLeftChild()) {
                current = p;
                p = p.getParent();
            }
            return p; // The predecessor is the parent of the leftmost child.
        }
    }

    // Helper method to find the node with the largest key in the given subtree.
    private BSTNode findMax(BSTNode node) {
        // Traverse to the rightmost node.
        while (node.getRightChild() != null) {
            node = node.getRightChild();
        }
        return node; // The rightmost node is the largest.
    }

    // Method to find the successor of a node with the specified key in the BST.
    public BSTNode successor(BSTNode r, Key k) {
        // Retrieve the node with the given key.
        BSTNode current = get(root, k);
        if (current == null) {
            return null; // If the node is not found, there is no successor.
        }

        // If there is a right child, the successor is the minimum node in the right subtree.
        if (current.getRightChild() != null) {
            return findMin(current.getRightChild());
        } else {
            // If there is no right child, traverse up the tree to find the successor.
            BSTNode p = current.getParent();
            while (p != null && current == p.getRightChild()) {
                current = p;
                p = p.getParent();
            }
            return p; // The successor is the parent of the rightmost child.
        }
    }

    // Helper method to find the node with the smallest key in the given subtree.
    private BSTNode findMin(BSTNode node) {
        // Traverse to the leftmost node.
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node; // The leftmost node is the smallest.
    }

    // Method to find the node with the smallest key in the BST rooted at r.
    public BSTNode smallest(BSTNode r) {
        // Return null if subtree rooted at r is empty.
        if (r == null) {
            return null;
        }
        // Traverse to the leftmost node of the subtree rooted at r.
        while (r.getLeftChild() != null) {
            r = r.getLeftChild();
        }
        return r; // The leftmost node has the smallest key.
    }

    // Method to find the node with the largest key in the BST rooted at r.
    public BSTNode largest(BSTNode r) {
        // Return null if subtree rooted at r is empty.
        if (r == null) {
            return null;
        }
        // Traverse to the rightmost node of the subtree rooted at r.
        while (r.getRightChild() != null) {
            r = r.getRightChild();
        }
        return r; // The rightmost node has the largest key.
    }
}
