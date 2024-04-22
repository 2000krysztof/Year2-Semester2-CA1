package ca1.ca1;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pill {
    ArrayList<Color> colors = new ArrayList<>();
    String name;
    double threshold = 0.2;
    int minSize = 30;
    DisjointSet set;
    public Pill(String pillName){
       name = pillName;
    }

    public void addColor(Color color){
        if(colors.contains(color)){return;}
        colors.add(color);
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getColorsAsString(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<colors.size(); i++) {
            builder.append(colorToString(colors.get(i)));
            if(i != colors.size()-1)
                builder.append(" | ");
        }
       return builder.toString();
    }

    public double getThreshold() {
        return threshold;
    }

    public String getName(){
        return name;
    }


    private static String colorToString(Color color){
        int r = (int) (color.getRed() *255);
        int g = (int) (color.getGreen() *255);
        int b = (int) (color.getBlue() *255);
        return String.format("r: %d, g: %d, b: %d",r,g,b);
    }
    public boolean doesPixelBelongToPill(int pixel){
        for(Color color : colors) {
            if(Utils.colorDifference(Utils.fromPixelArgb(pixel), color) < threshold){
                return true;
            }
        }
        return false;

    }

    public void scanImage(Image image) throws Exception{
        set = ImageAnalyzer.scanImage(image,minSize);
    }

   public void setMinSize(int size){
        minSize = size;
        if(set!=null) set.setMinSize(size);
   }

   public int getMinSize(){
        return minSize;
   }

}
