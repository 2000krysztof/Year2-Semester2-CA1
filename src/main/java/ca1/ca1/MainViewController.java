package ca1.ca1;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class MainViewController {

    FileChooser fileChooser;

    @FXML
    ImageView imageView;

    @FXML
    ComboBox<Image> imageSelectComboBox;

    @FXML
    TextField pillName;
    Image selectedImage;
    @FXML
    protected void initialize(){
        fileChooser = new FileChooser();
        imageSelectComboBox.setOnAction((event)->{
            selectedImage = imageSelectComboBox.getSelectionModel().getSelectedItem();
            imageView.setImage(selectedImage);
        });
        colorPicker();
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
   @FXML
   protected void chooseColor(MouseEvent event){
       int color = ImageAnalyzer.getColorIntAtCoordinates(imageView.getImage(), event.getX(),event.getY()
               ,imageView.getFitWidth(),imageView.getFitHeight());

       int r = ImageAnalyzer.redFromPixel(color),
               g = ImageAnalyzer.greenFromPixel(color),
               b = ImageAnalyzer.blueFromPixel(color);
   }


   private void colorPicker(){
       Stage circleStage = new Stage();
       circleStage.initOwner(Driver.mainStage);
       circleStage.initStyle(StageStyle.TRANSPARENT);

       Circle circle = new Circle(20.0f, Color.RED);
       circle.setCenterX(22.5);
       circle.setCenterY(22.5);
       circle.setStroke(Color.gray(0.5));
       circle.setStrokeWidth(2);
       circle.setManaged(false);

       StackPane stackPane = new StackPane(circle);
       stackPane.backgroundProperty().setValue(Background.EMPTY);
       circleStage.setScene(new Scene(stackPane, 45, 45, Color.TRANSPARENT));

       imageView.setOnMouseDragged(event -> {
           circleStage.setX(event.getScreenX() + 20);
           circleStage.setY(event.getScreenY() - 20);

           Color color = ImageAnalyzer.getColorAtCoordinates(imageView.getImage(), event.getX(),event.getY()
                   ,imageView.getFitWidth(),imageView.getFitHeight());

           circle.setFill(color);
       });
       imageView.setOnMousePressed(event -> {
           circleStage.show();
       });
       imageView.setOnMouseReleased(event ->{
           circleStage.hide();
       });
   }
}