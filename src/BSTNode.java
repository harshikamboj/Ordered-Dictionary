// CS 2210 Assignment 4
// Harshi Kamboj
// 251296379
// March 21st, 2024
public class BSTNode {
    // Record object to store the data for this node
    private Record record;
    // Reference to the left child in the binary search tree
    private BSTNode leftChild;
    // Reference to the right child in the binary search tree
    private BSTNode rightChild;
    // Reference to the parent of this node in the binary search tree
    private BSTNode parent;

    // Constructor for the BSTNode class. It initializes the node with the given Record item
    // and sets children and parent to null.
    public BSTNode(Record item) {
        this.record = item;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    // Returns the record stored in this node
    public Record getRecord() {
        return record;
    }

    // Sets the record of this node to the specified Record object
    public void setRecord(Record record) {
        this.record = record;
    }

    // Returns the left child of this node
    public BSTNode getLeftChild() {
        return leftChild;
    }

    // Sets the left child of this node to the specified BSTNode
    public void setLeftChild(BSTNode leftChild) {
        this.leftChild = leftChild;
        if (leftChild != null) {
            leftChild.setParent(this); // Sets this node as the parent of the left child
        }
    }

    // Returns the right child of this node
    public BSTNode getRightChild() {
        return rightChild;
    }

    // Sets the right child of this node to the specified BSTNode
    public void setRightChild(BSTNode rightChild) {
        this.rightChild = rightChild;
        if (rightChild != null) {
            rightChild.setParent(this); // Sets this node as the parent of the right child
        }
    }

    // Returns the parent of this node
    public BSTNode getParent() {
        return parent;
    }

    // Sets the parent of this node to the specified BSTNode
    public void setParent(BSTNode parent) {
        this.parent = parent;
    }

    // Determines if this node is a leaf node (i.e., has no children)
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }
}
