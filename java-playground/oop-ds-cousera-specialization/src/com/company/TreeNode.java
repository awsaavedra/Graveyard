package com.company;

public class TreeNode<E> {
    private E value;
    private TreeNode<E> parent;
    private TreeNode<E> left;
    private TreeNode<E> right;

    public TreeNode(E value, TreeNode<E> parent) {
        this.value = value;
        this.parent = parent;
        this.left = null;
        this.right = null;
    }

    public TreeNode<E> addLeftChild(E val){
        this.left = new TreeNode<>(val, this);
        return this.left;
    }
}
