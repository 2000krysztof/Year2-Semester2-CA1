package ca1.ca1;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pill {
    ArrayList<Color> colors = new ArrayList<>();
    String name;
    double threshold = 0.98;

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
       byte r = (byte) (color.getRed() *255);
        byte g = (byte) (color.getGreen() *255);
        byte b = (byte) (color.getBlue() *255);
        return String.format("r: %d, g: %d, b: %d",r,g,b);
    }
    public boolean doesPixelBelongToPill(int pixel){
        Vector3 vPixel = new Vector3(pixel);
        vPixel.normalize();
        for(Color color: colors){
            Vector3 vColor = new Vector3(color);
            vColor.normalize();
            System.out.println(vPixel.dot(vColor));
            if(vPixel.dot(vColor) > threshold){
                return true;
            }
        }
        return false;
    }
}
