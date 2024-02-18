package ca1.ca1;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

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

   public static ArrayList<Rectangle> pillLocations(Image image, Pill pill){
       ArrayList<Rectangle> rectangles = new ArrayList<>(5);

       return rectangles;
   }

   public static Image selectedPillMask(Image image, Pill pill){
       WritableImage writableImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
       PixelReader pixelReader = image.getPixelReader();
       PixelWriter pixelWriter = writableImage.getPixelWriter();
        for(int x = 0; x<image.getWidth(); x++){
           for(int y = 0; y<image.getHeight();y++){
               int color= pixelReader.getArgb(x,y);
               if(pill.doesPixelBelongToPill(color)){
                  pixelWriter.setArgb(x,y,color);
               }
               else{
                   pixelWriter.setArgb(x,y,0xFF000000);
               }
           }
        }
        return writableImage;
   }

}
