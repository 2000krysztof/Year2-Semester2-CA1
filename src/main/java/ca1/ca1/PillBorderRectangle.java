package ca1.ca1;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PillBorderRectangle extends Rectangle implements Comparable<PillBorderRectangle>{
    Pill pill;
    Label activeLabel;
    int index = 0,
    indexInImage = 0;
    double offsetFromMouse =40;
    public PillBorderRectangle(double x, double y, double width, double height, int indexInImage){
        super(x,y,width,height);
        this.indexInImage = indexInImage;
        setOnMouseEntered(event -> {
            activeLabel = pillLabel();
            setLabelLocation(event);
            Pane parentPane = (Pane)getParent();
            parentPane.getChildren().add(activeLabel);
        });

        setOnMouseExited(event -> {
            Pane parentPane = (Pane)getParent();
            parentPane.getChildren().remove(activeLabel);
        });

        setOnMouseMoved(this::setLabelLocation);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPill(Pill pill){
        this.pill = pill;
    }

    private void setLabelLocation(MouseEvent event){
        double labelX = event.getX();
        double labelY = event.getY();
        labelX += offsetFromMouse;
        labelY += offsetFromMouse;
        if(labelX > 300-offsetFromMouse) labelX -= offsetFromMouse*2;
        if(labelY > 300-offsetFromMouse) labelY -= offsetFromMouse*2;
        activeLabel.setTranslateX(labelX);
        activeLabel.setTranslateY(labelY);

    }
    Label pillLabel(){
        if(pill == null)return new Label("No pill set");
        StringBuilder pillText = new StringBuilder();
        pillText.append("Name: ").append(pill.getName()).append("\n");
        pillText.append("Index: ").append(index).append("\n");
        Label label = new Label(pillText.toString());
        label.setScaleX(2);
        label.setScaleY(2);
        label.setTextFill(Color.WHITE);
        return label;
    }


    @Override
    public int compareTo(PillBorderRectangle o) {
        return this.indexInImage - o.indexInImage;
    }
}
