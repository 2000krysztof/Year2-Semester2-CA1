package ca1.ca1;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    VBox pillTable;

    @FXML
    ColorPicker colorPicker;
    @FXML
    PillTableController pillTableController;
    @FXML
    protected void initialize(){
        fileChooser = new FileChooser();
        imageSelectComboBox.setOnAction((event)->{
            selectedImage = imageSelectComboBox.getSelectionModel().getSelectedItem();
            imageView.setImage(selectedImage);
        });
        imageHoverColorPicker();
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
        imageView.setImage(ImageAnalyzer.selectedPillMask(selectedImage, pillTableController.getPill()));
    }

    @FXML
    protected void  addPill(){
        //TODO need to add some error handling here
        Pill pill = new Pill(pillName.getText());
        pillTableController.addPill(pill);
    }
    @FXML
    protected void addColor(){
        pillTableController.addColor(colorPicker.getValue());
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
        colorPicker.setValue(Color.color((double) r /255, (double) g /255, (double) b /255));
    }


    private void imageHoverColorPicker(){
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