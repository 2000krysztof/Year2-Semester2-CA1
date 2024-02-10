package ca1.ca1;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ImageAnalyzer {


    public static int getColorIntAtCoordinates(Image image, double mouseX, double mouseY, double imageScaleX, double imageScaleY){
        double scaleFactorX = image.getWidth()/imageScaleX,
                scaleFactorY = image.getHeight()/imageScaleY;
        int x = (int)(mouseX*scaleFactorX);
        int y = (int)(mouseY*scaleFactorY);
        x = Math.max(0, Math.min(x, (int) image.getWidth() - 1));
        y = Math.max(0, Math.min(y, (int) image.getHeight() - 1));
        return image.getPixelReader().getArgb(x,y) & 0x00FFFFFF;
    }

    public static Color getColorAtCoordinates(Image image, double mouseX, double mouseY, double imageScaleX, double imageScaleY){
       int colorInt = getColorIntAtCoordinates( image, mouseX,  mouseY,  imageScaleX,  imageScaleY);
       int r = redFromPixel(colorInt),
               g =(greenFromPixel(colorInt)),
               b = blueFromPixel(colorInt);
        return Color.rgb(r,g,b,1.0);
    }

    public static int redFromPixel(int pixel){
        return (int)((pixel >> 16) & 0x0000FF);
    }

    public static int greenFromPixel(int pixel){
        return (int)((pixel >> 8) & 0x00FF);
    }

    public static int blueFromPixel(int pixel){
        return (int)(pixel & 0xFF);
    }

}
