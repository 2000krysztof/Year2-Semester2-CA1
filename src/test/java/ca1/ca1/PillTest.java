package ca1.ca1;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PillTest {

    Pill pillWhite,  pillMultiColored;

    @BeforeEach
    void setup(){
        pillWhite = new Pill("panadol");
        pillMultiColored = new Pill("strepsil");
        pillWhite.addColor(Color.WHITE);
        pillMultiColored.addColor(Color.RED);
        pillMultiColored.addColor(Color.rgb(0,255,0));
    }

    @AfterEach
    void tearDown(){
       pillWhite = null;
    }

    @Test
    void getColorsAsString() {
        assertEquals("r: 255, g: 255, b: 255", pillWhite.getColorsAsString());
    }


    @Test
    void doesPixelBelongToPill() {
        pillWhite.setThreshold(0.1);
        assertTrue(pillWhite.doesPixelBelongToPill(0xFFFFFFFF));
        assertFalse(pillWhite.doesPixelBelongToPill(0xFF22AA11));
        assertFalse(pillWhite.doesPixelBelongToPill(0xFF000000));
        pillMultiColored.setThreshold(0.2);
        assertTrue(pillMultiColored.doesPixelBelongToPill(0xFFFF0000));
        assertTrue(pillMultiColored.doesPixelBelongToPill(0xFF00FF00));
        assertFalse(pillMultiColored.doesPixelBelongToPill(0xFF000000));
    }
}