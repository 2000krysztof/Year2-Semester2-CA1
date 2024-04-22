package ca1.ca1;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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

   public static ArrayList<PillBorderRectangle> pillLocations(Pill pill) throws Exception{
       ArrayList<PillBorderRectangle> rectangles = new ArrayList<>(5);
       DisjointSet set = pill.set;
       for(int i = 0;i<set.roots.size();i++){
           rectangles.add(set.pillBoundariesFromRoot(set.findRoot(set.roots.get(i)),pill));
       }
       return rectangles;
   }

   public static Image rescaledImage(Image original, int newWidth, int newHeight){
       WritableImage newImage = new WritableImage(newWidth,newHeight){
           @Override
           public String toString() {return original.toString();}
       };
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

    public static Image allPillsMask(Image image, List<Pill> pills){
        WritableImage writableImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for(int x = 0; x<image.getWidth(); x++){
            for(int y = 0; y<image.getHeight();y++){
                int color= pixelReader.getArgb(x,y);
                for(Pill pill:pills){
                    if(pill.doesPixelBelongToPill(color)){
                        pixelWriter.setArgb(x,y,0xFFFFFFFF);
                    }
                    else{
                        pixelWriter.setArgb(x,y,0xFF000000);
                    }
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
        Color[] colors = Utils.randomColors(set.roots.size());
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

    public static Image coloredByPillType(Image image, List<Pill> pills){
        WritableImage output = new WritableImage((int)image.getWidth(),(int)image.getHeight());
        PixelWriter writer = output.getPixelWriter();
        Image[] masks = new Image[pills.size()];
        Color[] colors = Utils.randomColors(masks.length);
        for(int i = 0; i<pills.size();i++){
            masks[i] = selectedPillMask(image,pills.get(i));
        }

        for(int i=0;i<masks.length;i++) {
            PixelReader reader = masks[i].getPixelReader();

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if(reader.getColor(x,y).equals(Color.WHITE))
                        writer.setColor(x,y,colors[i]);
                }
            }
        }


        return output;
    }


    public static DisjointSet scanImage(Image image, int minSize) throws Exception{
        PixelReader reader = image.getPixelReader();
        int height = (int) image.getHeight();
        int width = (int) image.getWidth();
        DisjointSet set = new DisjointSet(width,height);
        set.setMinSize(minSize);
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

        return set;
    }

    private static void testSaveToFile(DisjointSet set){//TODO delete this later
        try {
            FileOutputStream fileOut = new FileOutputStream("pillsDisjointSet.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(set);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in pillsDisjointSet.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }



}
