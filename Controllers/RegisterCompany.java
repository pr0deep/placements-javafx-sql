package placement.Controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import placement.App;
import placement.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterCompany implements Initializable {
    public JFXButton registerButton;
    public JFXButton loginRedirectButton;
    public BorderPane registerPage;
    public JFXRadioButton companyToggle;
    public JFXRadioButton studentToggle;
    public JFXTextField nameLabel;
    public JFXTextField emailLabel;
    public JFXTextField useridLabel;
    public JFXTextField passwordLabel;
    public JFXTextArea addressLabel;
    public JFXButton loginButton;
    public ToggleGroup btngroup = new ToggleGroup();
    public JFXTextField phoneLabel;
    public JFXTextField marketLabel;
    public JFXDatePicker foundingLabel;
    public Label notificationLabel;


    public void registerStudent(ActionEvent actionEvent) {
    }

    public void redirectLogin(ActionEvent actionEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../ui/login.fxml")));

    }

    public void registerCompany(ActionEvent actionEvent) throws SQLException {
        String name = nameLabel.getText();
        String email = emailLabel.getText();
        String address = addressLabel.getText();
        String pwd = passwordLabel.getText();
        String phone = passwordLabel.getText();
        String market = marketLabel.getText();
        String founded = foundingLabel.getValue().toString();
        int company_id;
        System.out.println(founded);
        Connection connection = Database.connectToDB();
        assert connection != null;
        var callableStatement = connection.prepareCall(String.format("call jobportal.create_new_company('%s', '%s', %d, '%s', '%s', '%s', '%s');",name,founded,Integer.parseInt(market),email,phone,address,pwd));
        App.sqlCommands.add(String.format("call jobportal.create_new_company('%s', '%s', %d, '%s', '%s', '%s', '%s');",name,founded,Integer.parseInt(market),email,phone,address,pwd));
        ResultSet rs = callableStatement.executeQuery();
        rs.next();
        company_id = rs.getInt(1);
        connection.close();

        notificationLabel.setStyle(notificationLabel.getStyle().concat("-fx-opacity:1;"));
        notificationLabel.setText("Registered user id: "+company_id);
        connection.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyToggle.setToggleGroup(btngroup);
        studentToggle.setToggleGroup(btngroup);
        btngroup.selectToggle(companyToggle);
    }

    public void redirectStudent(ActionEvent actionEvent) throws IOException {
        registerPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../ui/register.fxml")));

    }
}
