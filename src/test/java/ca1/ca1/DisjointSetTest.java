package ca1.ca1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetTest {
    DisjointSet set ;
    @BeforeEach
    void setup() throws Exception{
        set = new DisjointSet(10,5);
        set.markAsRoot(1);
        set.markAsRoot(28);
        for(int i = 2; i < 25; i++){
            set.markAsRoot(i);
            set.join(i, i-1);
        }
        for(int i = 29; i < 45; i++){
            set.markAsRoot(i);
            set.join(i, i-1);
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
           if(set.get(i-1) == set.blankSpace){continue;}
           try {
               set.join(i, i-1);
           }catch (Exception e){
              assertTrue(false);
           }
       }
       try{
           assertEquals(1, set.findRoot(24));
       }catch (Exception e){
           assertTrue(false);
       }
       assertThrows(Exception.class, ()->{
           set.findRoot(0);
       } );
  }

  @Test
  void count(){
        assertEquals(2, set.roots.size());
  }

}