package ca1.ca1;

import javafx.scene.paint.Color;

public class Utils {
    public static double colorDifference(Color color1, Color color2 ){
        double deltaR = Math.abs(color1.getRed() - color2.getRed());
        double deltaG = Math.abs(color1.getGreen() - color2.getGreen());
        double deltaB = Math.abs(color1.getBlue() - color2.getBlue());
        return  (deltaR + deltaB + deltaG)/3;
    }

    public static Color fromPixelRgba(int pixel){
        return Color.rgb(
                (pixel >> 24)    & 0xFF,
                (pixel >> 16)   & 0x00FF,
                (pixel >> 8)    & 0x0000FF
        );
    }
     public static Color fromPixelArgb(int pixel){
        return Color.rgb(
                (pixel >> 16)    & 0xFF,
                (pixel >> 8)    & 0x00FF,
                (pixel >> 0)    & 0x0000FF
        );
    }
}
