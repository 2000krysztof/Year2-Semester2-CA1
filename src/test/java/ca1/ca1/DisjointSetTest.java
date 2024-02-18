package ca1.ca1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetTest {
    DisjointSet set ;
    @BeforeEach
    void setup(){
        set = new DisjointSet(50);
        for(int i = 30; i < 35; i++){
            set.markAsBlank(i);
        }
    }

    @AfterEach
    void tearDown(){
        set = null;
    }

  @Test
  void join(){
       for(int i =0; i< set.getSize()-1; i++){
           if(set.get(i) == set.blankSpace){continue;}
           if(set.get(i+1) == set.blankSpace){continue;}
           try {
               set.join(i, i+1);
           }catch (Exception e){
              assertTrue(false);
           }
       }
       try{
           assertEquals(1, set.getRoot(20));
           assertEquals(1, set.getRoot(0));
           assertEquals(1, set.getRoot(29));
       }catch (Exception e){
           assertTrue(false);
       }
  }

}