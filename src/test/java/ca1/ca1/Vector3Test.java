package ca1.ca1;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {

    Vector3 fromPixelWhite, fromPixelBlack, fromColorWhite, fromColorBlack, ones, zeros;
    @BeforeEach
    void setup(){
       fromPixelWhite = new Vector3(0xFFFFFFFF);
       fromPixelBlack = new Vector3(0xFF000000);
       fromColorWhite = new Vector3(Color.WHITE);
       fromColorBlack = new Vector3(Color.BLACK);
       ones = Vector3.ones;
       zeros = Vector3.zeros;
    }


    @AfterEach
    void tearDown(){
        fromColorBlack = fromPixelBlack =
                fromPixelWhite = fromColorWhite =
                        zeros = ones = null;
    }

    @Test
    void isEqual(){
        assertEquals(fromColorWhite, fromColorWhite);
        assertEquals(fromColorWhite, fromPixelWhite);
        assertNotEquals(fromColorWhite, fromColorBlack);
        assertEquals(fromColorBlack, zeros);
        assertEquals(fromColorBlack, fromPixelBlack);
    }

    @Test
    void magnitude(){
        assertEquals(Math.sqrt(3), ones.magnitude());
        assertEquals(0, zeros.magnitude());
    }

    @Test
    void normalize(){
        ones.normalize();
        zeros.normalize();
        fromColorBlack.normalize();
        fromColorWhite.normalize();

        assertEquals(1, ones.magnitude());
        assertEquals(1, fromColorWhite.magnitude());
        assertEquals(0, fromColorBlack.magnitude());
        assertEquals(0, zeros.magnitude());
    }
    @Test
    void dotProduct(){
        fromColorWhite.normalize();
        fromColorBlack.normalize();
        assertEquals(1 , fromColorWhite.dot(fromColorWhite));
        assertEquals(0 , fromColorWhite.dot(fromColorBlack));
    }
}