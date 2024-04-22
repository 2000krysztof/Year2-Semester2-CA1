package ca1.ca1;

import com.sun.source.tree.UsesTree;
import org.openjdk.jmh.annotations.*;

import javafx.scene.image.Image;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class DisjointSetBenchmark {
    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void findRoot(BenchmarkState state) throws Exception{
        state.set.findRoot(9119);
    }


    @State(Scope.Thread)
    public static class BenchmarkState {
        DisjointSet set, setCopy;
        @Setup
        public void setup(){
            loadDisjointSet();

        }

        private void loadDisjointSet() {
            try {
                FileInputStream fileIn = new FileInputStream("src/benchmark/resources/pillsDisjointSet.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                set = (DisjointSet) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

        }
    }
}
