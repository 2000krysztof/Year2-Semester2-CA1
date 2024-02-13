package ca1.ca1;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pill {
    ArrayList<Color> colors = new ArrayList<>();
    String name;
    double threshold = 0.1;

    public Pill(String pillName){
       name = pillName;
    }

    public void addColor(Color color){
       colors.add(color);
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getColors(){
        StringBuilder builder = new StringBuilder();
        for(Color color : colors){
           builder.append(colorToString(color));
        }
       return builder.toString();
    }


    private static String colorToString(Color color){
       byte r = (byte) (color.getRed() *255);
        byte g = (byte) (color.getGreen() *255);
        byte b = (byte) (color.getBlue() *255);
        return String.format("r: %d, g: %d, b: %d",r,g,b);
    }
}
