package com.company;

import java.util.List;

public abstract class Graph {
    private int numVertices;
    private int numEdges;

    public Graph(int numVertices, int numEdges) {
        numVertices = 0;
        numEdges = 0;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void setNumEdges(int numEdges) {
        this.numEdges = numEdges;
    }

    public void addVertex(){
        implementAddVertex();
        numVertices++;
    }

    public abstract void implementAddVertex();

    public abstract List<Integer> getNeighbors(int v);
}
