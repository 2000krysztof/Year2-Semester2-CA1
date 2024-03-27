package ca1.ca1;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class ImageAnalyzer {
    public static int errorCount = 0;

    static Random random = new Random();
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
       return Utils.fromPixelArgb(colorInt);
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

   public static ArrayList<Rectangle> pillLocations( Pill pill) throws Exception{
       ArrayList<Rectangle> rectangles = new ArrayList<>(5);
       DisjointSet set = pill.set;
       for(int i = 0;i<set.roots.size();i++){
           rectangles.add(set.pillBoundariesFromRoot(set.findRoot(set.roots.get(i))));
       }
       return rectangles;
   }

   public static Image rescaledImage(Image original, int newWidth, int newHeight){
       WritableImage newImage = new WritableImage(newWidth,newHeight);
       PixelWriter writer = newImage.getPixelWriter();
       PixelReader reader = original.getPixelReader();
       double scaleFactorX = original.getWidth()/newWidth;
       double scaleFactorY = original.getHeight()/newHeight;
       for (int y = 0; y < newHeight; y++) {
           for (int x = 0; x < newWidth; x++) {
                Color color = reader.getColor((int)(x*scaleFactorX),(int)(y*scaleFactorY));
                writer.setColor(x,y,color);
           }
       }
       return newImage;
   }
   public static Image selectedPillMask(Image image, Pill pill){
       WritableImage writableImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
       PixelReader pixelReader = image.getPixelReader();
       PixelWriter pixelWriter = writableImage.getPixelWriter();
        for(int x = 0; x<image.getWidth(); x++){
           for(int y = 0; y<image.getHeight();y++){
               int color= pixelReader.getArgb(x,y);
               if(pill.doesPixelBelongToPill(color)){
                  pixelWriter.setArgb(x,y,0xFFFFFFFF);
               }
               else{
                   pixelWriter.setArgb(x,y,0xFF000000);
               }
           }
        }
        return writableImage;
   }


    public static Image coloredImageBasedOnPill(Image image, Pill pill) throws Exception{
        pill.scanImage(image);
        DisjointSet set = pill.set;
        WritableImage writeableImage = new WritableImage(set.width,set.height);
        PixelWriter writer = writeableImage.getPixelWriter();
        Color[] colors = randomColors(set.roots.size());
        for(int y = 0; y< set.height; y++){
            for(int x = 0; x< set.width;x++){
                int i = x + y* set.width;
                if(set.get(i) == set.blankSpace){
                    writer.setColor(x,y, Color.BLACK);

                } else {
                    try {
                        int index = set.roots.indexOf(set.findRoot(i));
                        if(index == -1){
                            writer.setColor(x,y,Color.BLACK);
                            continue;
                        }
                        Color color = colors[index];
                        writer.setColor(x,y,color);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }
        return writeableImage;
    }

    private static Color[] randomColors(int count){
        Color[] colors = new Color[count];
        for(int i =0; i<count;i++){
            colors[i] =Color.rgb(random.nextInt(0,255)
                    ,random.nextInt(0,255)
                    ,random.nextInt(0,255));
        }
        return colors;
    }

}
