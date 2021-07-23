package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import placement.App;
import placement.Database;
import placement.Controllers.UserController;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserInfoComponentController implements Initializable {
    public AnchorPane parentmiddle;
    public JFXTextField emailLabel;
    public JFXTextField phoneLabel;
    public JFXTextField passwordLabel;
    public JFXTextField skillsLabel;
    public JFXTextField nameLabel;
    public JFXButton saveChangesButton;
    public Label sqlQueryLabel = new Label();

    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameLabel.setText(App.user.getName());
        emailLabel.setText(App.user.getEmail());
        passwordLabel.setText(App.user.getPassword());
        skillsLabel.setText(App.user.getSkills());
        phoneLabel.setText(String.valueOf(App.user.getPhone()));

    }
    public void saveChanges(MouseEvent mouseEvent) throws SQLException {
        //initialize with getters
        String insertName = nameLabel.getText();
        String insertPwd = passwordLabel.getText();
        int insertPhone = Integer.parseInt(phoneLabel.getText());
        String insertSkills = skillsLabel.getText();
        String insertEmail = emailLabel.getText();
        //call to querybuild
        String query = Database.makeUpdateQuery(insertName,insertPwd,insertEmail,insertPhone,insertSkills);

        //updating query label;
        sqlQueryLabel.setText(query);

        Connection connection = Database.connectToDB();
        assert connection != null;
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

        connection.close();
    }
}
