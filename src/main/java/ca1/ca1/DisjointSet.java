package ca1.ca1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class DisjointSet {
    int[] data;
    int blankSpace, width, height;
    final int threshold = 30;

    ArrayList<Integer> roots = new ArrayList<>();
    public DisjointSet(int width, int height){
        this.width = width;
        this.height = height;
        data = new int[width*height];
        blankSpace = data.length;
        for(int i = 0; i < data.length; i++){
            data[i] = blankSpace;
        }
    }

    public int findRoot(int node)throws Exception{
        if(node == blankSpace){
            throw new Exception("root chain reaches dead end");
        }
        if(data[node] == node){
            throw new Exception("node references back to itself");
        }
        if(data[node] < 0){
            return node;
        }
        data[node] = findRoot(data[node]);
        return data[node];
    }


    public void join(int i, int j) throws Exception {
        int rootI = findRoot(i);
        int rootJ = findRoot(j);
        if(rootI == rootJ){return;}
        if(rootI < rootJ){
            union(rootJ, rootI);
        }else {
            union(rootI, rootJ);
        }
    }
    private void union(int bigger, int smaller){
        roots.remove(Integer.valueOf(bigger));
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
        roots.add(index);
        return true;
    }
    public void denois() throws Exception{
        for(int i = roots.size()-1; i >=0; i--){
            if(isSmallerThanThreshold(roots.get(i))){
                deleteDisjoint(roots.get(i));
                roots.remove(i);
            }
        }
    }
    private boolean isSmallerThanThreshold(int root)throws Exception{
        return data[root] > -threshold;
    }

    void deleteDisjoint(int root){
        try{
            int leftMostPixel = getRootLeft(root);
            int setX = leftMostPixel % width;
            int setY = root / width;
            int setWidth = (getRootRight(root) % width) - setX;
            int setHeight = getRootBottom(root, leftMostPixel) / width - setY;
            ArrayList<Integer> markedForDeletion = new ArrayList<>(-data[root]);
            for (int y = setY; y < setY + setHeight; ++y) {
                for (int x = setX; x < setX + setWidth; ++x) {
                    int i = x + y *width;
                    if(validPixel(root,i)){
                        markedForDeletion.add(i);
                    }
                }
            }
            for(Integer i : markedForDeletion){
                data[i] = blankSpace;
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Rectangle pillBoundariesFromRoot(int root)throws Exception{
        int leftMostPixel = getRootLeft(root);
       int x = (leftMostPixel % width) -3;
       int y = (root / width)-3;
       int rectWidth = (getRootRight(root) % width) - x +3;
       int rectHeight = getRootBottom(root, leftMostPixel) / width - y+3;

       Rectangle rectangle = new Rectangle(x,y,rectWidth,rectHeight);
       rectangle.setFill(Color.TRANSPARENT);
       rectangle.setStroke(Color.BLUE);
       rectangle.setStrokeWidth(2);
       return rectangle;
    }


    private int getRootBottom(int root, int left) throws Exception{
         int offset = left;
         int horizontalOffset = 0;
         while (true){
             while (!validPixel(root,offset+horizontalOffset)){
                 horizontalOffset ++;
                 if(horizontalOffset ==width) return offset;
             }
             offset +=width;
             if(horizontalOffset + offset > data.length) return offset;

             horizontalOffset = 0;
         }

    }

    private int getRootLeft(int root) throws Exception{
        int offset = root;
        int verticalOffset;
        while (true){
            verticalOffset=0;
            while (!validPixel(root,offset+verticalOffset)){
               verticalOffset += width;
               if(offset+verticalOffset > data.length) return offset;
            }
            if(offset%width ==0) return offset;
            offset --;
        }
    }
    private int getRootRight(int root) throws Exception{
        int offset = root;
        int verticalOffset;
        while (true){
            verticalOffset = 0;
            while (!validPixel(root, offset + verticalOffset)){
                verticalOffset += width;
                if(offset + verticalOffset > data.length) return offset;
            }
            offset++;
        }
    }

    private boolean validPixel(int root, int pixel)throws Exception{
        if(pixel >= data.length){return false;}
        if(data[pixel] == blankSpace){return false;}
        return findRoot(pixel) == root;
    }
    public int get(int i){
        return data[i];
    }
    public int getSize(){
        return data.length;
    }

    public int getX(int root){
        return root/width;
    }
    public int getY(int root){
        return root%width;
    }

    public ArrayList<Integer> getRoots() {
        return roots;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
