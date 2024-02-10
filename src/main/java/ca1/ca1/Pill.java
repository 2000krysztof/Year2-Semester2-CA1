package ca1.ca1;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pill {
    ArrayList<Color> colors = new ArrayList<>();
    String name;

    public Pill(String pillName){
       name = pillName;
    }

    public void addColor(Color color){
       colors.add(color);
    }
}
