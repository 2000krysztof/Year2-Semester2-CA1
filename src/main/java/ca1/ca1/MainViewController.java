package ca1.ca1;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainViewController {

    FileChooser fileChooser;

    @FXML
    ImageView imageView;

    @FXML
    ComboBox<Image> imageSelectComboBox;
    Image selectedImage;
    @FXML
    protected void initialize(){
        fileChooser = new FileChooser();
        imageSelectComboBox.setOnAction((event)->{
            selectedImage = imageSelectComboBox.getSelectionModel().getSelectedItem();
            imageView.setImage(selectedImage);
        });
    }
    @FXML
    protected void addImageFromFile(){
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        Image image = new Image(file.toURI().toString()){
            @Override
            public String toString() {
               return file.getName();
            }
        };
        imageSelectComboBox.getItems().add(image);
        imageSelectComboBox.getSelectionModel().select(
                imageSelectComboBox.getSelectionModel().getSelectedIndex()+1);
    }

    @FXML
    protected void revertImage(){
        imageView.setImage(selectedImage);
    }
    @FXML
    protected void toBlackAndWhite(){

    }

   @FXML
   protected void  addPill(){

   }

   @FXML
   protected void colorDisjoints(){

   }
}