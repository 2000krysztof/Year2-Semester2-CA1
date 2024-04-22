package ca1.ca1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Driver extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle("Pill Finder");
        stage.setScene(scene);
        loadDisjointSet();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    private static void loadDisjointSet() {
        DisjointSet set = null;
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
        System.out.println(set.roots);
        }

}