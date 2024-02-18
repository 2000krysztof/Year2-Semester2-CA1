package ca1.ca1;

import javafx.scene.paint.Color;


public class Vector3 {

    double[] values = new double[3];
    public static Vector3 ones = new Vector3(1,1,1);
    public static Vector3 zeros = new Vector3(0,0,0);
    public Vector3(Color color){
       values[0] = color.getRed() * 255.0;
       values[1] = color.getGreen() * 255.0;
       values[2] = color.getBlue() * 255.0;
    }

    public Vector3(int color){
        values[0] = ImageAnalyzer.redFromPixel(color);
        values[1] = ImageAnalyzer.greenFromPixel(color);
        values[2] = ImageAnalyzer.blueFromPixel(color);
    }
    public Vector3(float x, float y, float z){
        values[0] = x;
        values[1] = y;
        values[2] = z;
    }
    public void normalize(){
       double magnitude = magnitude();
       if(magnitude == 0){return;}
       values[0] /= magnitude;
       values[1] /= magnitude;
       values[2] /= magnitude;
    }

    public double magnitude(){
        return Math.sqrt(values[0]* values[0]+
                values[1]* values[1] +
                values[2]* values[2]);
    }
    public double dot(Vector3 other){
        return this.values[0]*other.values[0] +
                this.values[1]*other.values[1]+
                this.values[2]*other.values[2];
    }

    @Override
    public String toString() {
        return String.format("x: %f, y: %f, z: %f", values[0], values[1], values[2]);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Vector3))
            return false;
        Vector3 other = (Vector3) obj;
        return values[0] != other.values[0] ? false :
                values[1] != other.values[1] ? false :
                        values[2] == other.values[2] ;
    }
}
