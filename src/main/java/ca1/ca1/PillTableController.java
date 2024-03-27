package ca1.ca1;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

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

    public void addColor(Color color){
        if(table.getSelectionModel().getSelectedItem() == null){return;}
        table.getSelectionModel().getSelectedItem().addColor(color);
        table.refresh();
    }


}
