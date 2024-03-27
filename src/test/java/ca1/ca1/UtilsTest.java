package ca1.ca1;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    int red, green, blue, white;
    @BeforeEach
    void setup(){
        blue =  0x0000FFFF;
        green = 0x00FF00FF;
        red =   0xFF0000FF;
        white = 0xFFFFFFFF;
    }
    @AfterEach
    void tearDown(){
       red = green = blue = white = 0;
    }
    @Test
    void colorDifference() {
        assertEquals(0, Utils.colorDifference(Color.BLUE,Color.BLUE));
        assertEquals(1, Utils.colorDifference(Color.WHITE,Color.BLACK));
        double grayDiff = Utils.colorDifference(Color.WHITE, Color.rgb(128,128,128));
        assertTrue(0.49<grayDiff && grayDiff<0.5);
        assertEquals(0.6666666666666666, Utils.colorDifference(Color.BLUE,Color.RED));
        assertEquals(0.3333333333333333, Utils.colorDifference(Color.BLUE,Utils.fromPixelRgba(0x000000FF)));
    }

    @Test
    void fromPixel() {
        assertEquals(Color.rgb(255,0,0), Utils.fromPixelRgba(red) );
        assertEquals(Color.rgb(0,255,0), Utils.fromPixelRgba(green) );
        assertEquals(Color.rgb(0,0,255), Utils.fromPixelRgba(blue) );
        assertEquals(Color.WHITE, Utils.fromPixelRgba(white));

        assertEquals(Color.rgb(25,53,12),Utils.fromPixelRgba(0x19350CFF));
    }
    @Test
    void fromPixelArgb(){
       assertEquals(Color.rgb(255,0,0), Utils.fromPixelArgb(0xFFFF0000));
    }
}