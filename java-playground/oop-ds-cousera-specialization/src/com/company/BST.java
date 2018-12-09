package com.company;


//Pre-order traversal (depth first traversal)
//PostOrder traversal
//InOrder traversal
//Level order traversal (breadth first traversal)
//Balanced BST : | LeftHeight - RightHeight | <= 1

public class BST {

    Node root;

    public void addNode(int key, String name){
        Node newNode = new Node(key, name);

        if(root == null){
            root = newNode;
        }else{
            Node focusNode = root;
            Node parent;

            boolean notAddedNode = true;
            while(notAddedNode){
                parent = focusNode;

                if(key < focusNode.key){
                    focusNode = focusNode.leftChild;

                    if(focusNode == null){
                        parent.leftChild = newNode;
                        notAddedNode = false;
                    }
                }else{
                    focusNode = focusNode.rightChild;

                    if(focusNode == null){
                        parent.rightChild = newNode;
                        notAddedNode = false;
                    }
                }
            }
        }
    }

    public void inOrderTraverseTree(Node focusNode){
        if( focusNode != null){
            inOrderTraverseTree(focusNode.leftChild);
            System.out.println(focusNode);
            inOrderTraverseTree(focusNode.rightChild);
        }
    }

    public void preOrderTraverseTree(Node focusNode){
        if( focusNode != null){

            System.out.println(focusNode);
            preOrderTraverseTree(focusNode.leftChild);
            preOrderTraverseTree(focusNode.rightChild);
        }
    }

    public void postOrderTraverseTree(Node focusNode){
        if( focusNode != null){

            System.out.println(focusNode);
            postOrderTraverseTree(focusNode.leftChild);
            postOrderTraverseTree(focusNode.rightChild);
        }
    }

    public Node findNode( int key){
        Node focusNode = root;

        while(focusNode.key != key){

            if(key < focusNode.key){
                focusNode = focusNode.leftChild;
            }else{
                focusNode = focusNode.rightChild;
            }
            if(focusNode == null){
                return null;
            }

        }
        return focusNode;
    }

    public static void main(String[] args) {
        BST theTree = new BST();

        theTree.addNode(50, "Baws");
        theTree.addNode(25, "VP");
        theTree.addNode(15, "Office Manager");
        theTree.addNode(30, "Secretary");
        theTree.addNode(75, "Sales Manager");
        theTree.addNode(85, "Sales 1");

        System.out.println("\nIn order traversal: \n");
        theTree.inOrderTraverseTree(theTree.root);
        System.out.println("\nPre order traversal: \n");
        theTree.preOrderTraverseTree(theTree.root);
        System.out.println("\nPost order traversal: \n");
        theTree.postOrderTraverseTree(theTree.root);

        System.out.println();

        System.out.println(theTree.findNode(30));
    }
}

class Node{

    int key;
    String name;
    Node leftChild;
    Node rightChild;

    public Node(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public String toString(){
        return name + " has a key " + key;
    }
}
