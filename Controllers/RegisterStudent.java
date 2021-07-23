package placement.Controllers;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import placement.App;
import placement.Database;

import javax.swing.plaf.basic.BasicBorders;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RegisterStudent implements Initializable {
    public JFXButton registerButton;
    public JFXButton loginButton;
    public BorderPane registerPage;
    public ToggleGroup btngroup = new ToggleGroup();
    public JFXRadioButton companyToggle;
    public JFXRadioButton studentToggle;
    public JFXTextField nameLabel;
    public JFXTextField emailLabel;
    public JFXTextField ageLabel;
    public JFXTextField cgpaLabel;
    public JFXTextField cgpa3Label;
    public JFXTextField cgpa2Label;
    public JFXTextField programLabel;
    public JFXTextField passwordLabel;
    public JFXTextField phoneLabel;
    public JFXChipView skillChips;
    public Label notificationLabel;

    public void registerStudent(ActionEvent actionEvent) throws SQLException {
        int student_account_id = 0,student_id = 0;

        String name = nameLabel.getText();
        String email = emailLabel.getText();
        String age = ageLabel.getText();
        int cgpa = Integer.parseInt(cgpaLabel.getText());
        int cgpa10 = Integer.parseInt(cgpa2Label.getText());
        int cgpa12 = Integer.parseInt(cgpa3Label.getText());
        String program = programLabel.getText();
        String pwd = passwordLabel.getText();
        int phone = Integer.parseInt(phoneLabel.getText());

        Connection connection = Database.connectToDB();
        assert connection != null;
        var callableStatement = connection.prepareCall(String.format("call jobportal.register_student('%s', '%s', %d, %d, '%s', %d, %d, %d, '%s', '%s');",name,"",Integer.parseInt(age),phone,program,cgpa,cgpa10,cgpa12,email,pwd));
        App.sqlCommands.add(String.format("call jobportal.register_student('%s', '%s', %d, %d, '%s', %d, %d, %d, '%s', '%s');",name,"",Integer.parseInt(age),phone,program,cgpa,cgpa10,cgpa12,email,pwd));
        ResultSet rs = callableStatement.executeQuery();
        if(rs.next())
            student_account_id = rs.getInt(1);


        Object[] s =  skillChips.getChips().toArray();
        StringBuilder SQL = new StringBuilder();
        Statement statement = connection.createStatement();
        int[] a = new int[s.length];
        for(int i=0;i<s.length;i++){
            rs = statement.executeQuery(String.format("SELECT SKILL_ID FROM SKILLS WHERE NAME='%s' ",s[i]));
            rs.next();
            a[i] = rs.getInt(1);

        }
        int i =0;

        Statement preparedStatement = connection.createStatement();
        rs = preparedStatement.executeQuery("SELECT STUDENT_ID FROM STUDENT WHERE ACCOUNT_ID = "+student_account_id);
        rs.next();
        student_id = rs.getInt(1);

        for(i=0;i<a.length;i++) {
            preparedStatement.executeUpdate(String.format("INSERT INTO HAS_SKILLS VALUES(%d,%d)",student_id,a[i]));
        }
        connection.close();
        notificationLabel.setStyle(notificationLabel.getStyle().concat("-fx-opacity:1;"));
        notificationLabel.setText("Registered user id: "+student_account_id);

    }

    public void redirectLogin(ActionEvent actionEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../ui/login.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyToggle.setToggleGroup(btngroup);
        studentToggle.setToggleGroup(btngroup);
        btngroup.selectToggle(studentToggle);

    }

    public void redirectCompany(MouseEvent mouseEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../ui/registercompany.fxml")));
    }
}
