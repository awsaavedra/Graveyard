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

    public boolean remove(int key){
        Node focusNode = root;
        Node parent = root;

        boolean isItALeftChild = true;

        while(focusNode.key != key){
            parent = focusNode;

            if(key < focusNode.key){
                isItALeftChild = true;

                focusNode = focusNode.leftChild;
            }else{
                isItALeftChild = false;

                focusNode = focusNode.rightChild;
            }

            if(focusNode == null){
                return false;
            }
        }

        if( focusNode.leftChild == null && focusNode.rightChild == null){
            if(focusNode == root){
                root = null;
            }else if(isItALeftChild){
                parent.leftChild = null;
            }else{
                parent.rightChild = null;
            }
        }
        else if(focusNode.rightChild == null){
            if(focusNode == root){
                root = focusNode.leftChild;
            }else if (isItALeftChild){
                parent.leftChild = focusNode.leftChild;
            }else{
                parent.rightChild = focusNode.leftChild;
            }
        }

        else if(focusNode.leftChild == null){
            if(focusNode == root){
                root = focusNode.rightChild;
            }else if(isItALeftChild){
                parent.leftChild = focusNode.rightChild;
            }else{
                parent.leftChild = focusNode.rightChild;
            }
        }else {
            Node replacement = getPlacementNode(focusNode);

            if(focusNode == root){
                root = replacement;
            }else if(isItALeftChild){
                parent.leftChild = replacement;
            }else{
                parent.rightChild = replacement;
            }
            replacement.leftChild = focusNode.leftChild;
        }
        return true;
    }

    public Node getPlacementNode(Node replacedNode){
        Node replacementParent = replacedNode;
        Node replacement = replacedNode;

        Node focusNode = replacedNode.rightChild;

        while (focusNode != null){
            replacementParent = replacement;

            replacement = focusNode;

            focusNode = focusNode.leftChild;
        }

        if( replacement != replacedNode.rightChild){
            replacementParent.leftChild = replacement.rightChild;
            replacement.rightChild = replacedNode.rightChild;
        }
        return replacement;
    }

    public static void main(String[] args) {
        BST theTree = new BST();

        theTree.addNode(50, "Boss");
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

        System.out.println("REMOVE KEY 25");
        theTree.remove(25);
        System.out.println(theTree.findNode(25));
        theTree.preOrderTraverseTree(theTree.root);
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
