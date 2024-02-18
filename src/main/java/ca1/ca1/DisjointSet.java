package ca1.ca1;

import java.util.ArrayList;

public class DisjointSet {
    int[] data;
    int blankSpace;

    ArrayList<Integer> roots = new ArrayList<>();
    public DisjointSet(int size){
        data = new int[size];
        blankSpace = size;
        for(int i = 0; i < size; i++){
            data[i] = -1;
        }
    }

    public int getRoot(int node)throws Exception{
        if(node == blankSpace){
            throw new Exception("root chain reaches dead end");
        }
        if(data[node] == node){
            throw new Exception("node references back to itself");
        }
        if(data[node] < 0){
            return node;
        }
        data[node] = getRoot(data[node]);
        return data[node];
    }

    public void join(int i, int j) throws Exception {
        int rootI = getRoot(i);
        int rootJ = getRoot(j);
        if(rootI == rootJ){return;}
        if(data[rootI] < data[rootJ]){
            union(rootJ, rootI);
        }else {
            union(rootI, rootJ);
        }
    }
    private void union(int bigger, int smaller){
        data[smaller] += data[bigger];
        data[bigger] = smaller;
    }

    public boolean markAsBlank(int index){
        if(index >= data.length){return false;}
        data[index] = blankSpace;
        return true;
    }
    public boolean markAsRoot(int index){
        if(index >= data.length){return false;}
        data[index] = -1;
        return true;
    }
    public int get(int i){
        return data[i];
    }
    public int getSize(){
        return data.length;
    }
}
