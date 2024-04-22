package ca1.ca1;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

import java.util.List;

public class PillTableController {


    @FXML
    TableView<Pill> table;
    public void addPill(Pill pill){
        table.getItems().add(pill);
    }

    public void removePill(){
        int index = table.getSelectionModel().getSelectedIndex();
        table.getItems().remove(index);
    }

    public Pill getPill(){
        return table.getSelectionModel().getSelectedItem();
    }

    public List<Pill> getAllPills(){return table.getItems();}
    public void addColor(Color color){
        if(table.getSelectionModel().getSelectedItem() == null){return;}
        table.getSelectionModel().getSelectedItem().addColor(color);
        table.refresh();
    }

    public void setThreshold(double threshold){
        if(table.getSelectionModel().getSelectedItem() == null){return;}
        table.getSelectionModel().getSelectedItem().setThreshold(threshold);
        table.refresh();
    }


    public void setMinSize(int minSize){
        if(table.getSelectionModel().getSelectedItem() == null){return;}
        table.getSelectionModel().getSelectedItem().setMinSize(minSize);
        table.refresh();
    }

}
