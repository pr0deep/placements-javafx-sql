package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import placement.Database;
import placement.Job;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserFilterComponentController implements Initializable {
    public JFXTextField salaryText;
    public JFXButton applyfilterButton;
    public JFXTextField filterPreference;
    public ObservableList<Job> jobList = FXCollections.observableArrayList();
    public Label sqlQueryLabel;
    public JFXTreeTableView viewTable;
    public JFXRadioButton button3;
    public JFXRadioButton button2;
    public JFXRadioButton button1;

    public void applyFilter(MouseEvent mouseEvent) throws SQLException {
        jobList.clear();
        sqlQueryLabel.setText("SQL>"+ Database.makeQuery(filterPreference.getText(),Integer.parseInt(salaryText.getText()),getGroupval()));

        jobList = Database.filteredResults(filterPreference.getText(),Integer.parseInt(salaryText.getText()),getGroupval());

        TreeItem<Job> root = new TreeItem<>(new Job("","",0,"","","","",""));

        for(int i=0;i<jobList.size();i++)
            root.getChildren().add(i,new TreeItem<Job>(jobList.get(i)));

        viewTable.setRoot(root);
        viewTable.setShowRoot(false);
    }

    public String getButtonval(JFXRadioButton jb){
        if(jb.isSelected()) return jb.getText();
        return "-";
    }

    public String[] getGroupval(){
        String[] skills = new String[3];
        skills[0] = getButtonval(button1);
        skills[1] = getButtonval(button2);
        skills[2] = getButtonval(button3);
        return skills;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        salaryText.setText("0");
    }
}
