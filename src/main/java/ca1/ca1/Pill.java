package ca1.ca1;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pill {
    ArrayList<Color> colors = new ArrayList<>();
    String name;
    double threshold = 0.2;

    DisjointSet set;
    public Pill(String pillName){
       name = pillName;
    }

    public boolean addColor(Color color){
        if(colors.contains(color)){return false;}
        colors.add(color);
        return true;
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
        PixelReader reader = image.getPixelReader();
        int height = (int) image.getHeight();
        int width = (int) image.getWidth();
        set = new DisjointSet(width,height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = x+y*width;
                if(reader.getArgb(x,y) != 0xFF000000){
                    set.markAsRoot(i);
                    if(x != 0 && set.get(i-1) != set.blankSpace){
                        set.join(i, i-1);
                    }
                    if(y!= 0 && set.get(i-width)!= set.blankSpace ){
                        set.join(i, i-width);
                    }
                }
            }
        }
        set.denois();
    }


}
