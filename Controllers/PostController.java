package placement.Controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

public class PostController implements Initializable {
    public JFXTextField titleLabel;
    public JFXTextArea descLabel;
    public JFXTextField salaryLabel;
    public JFXDatePicker deadlineLabel;
    public JFXButton applicationTab;
    public BorderPane companyPage;
    public JFXButton postButton;
    public JFXButton logoutButton;
    public Label idLabel;
    public Label nameLabel;
    public JFXChipView skillsLabel;
    public JFXButton applicationsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(App.company.companyName);
        idLabel.setText("ID : "+App.company.companyId);
    }

    public void redirectToApplications(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("../ui/companyApplication.fxml"));
        companyPage.getChildren().setAll(pane);

    }

    public void postJob(MouseEvent mouseEvent) throws SQLException, IOException {
        String insertName = titleLabel.getText();
        String insertDesc = descLabel.getText();
        String insertSal = salaryLabel.getText();
        String insertDate = deadlineLabel.getValue().toString();

        Connection connection = Database.connectToDB();
        ResultSet rs;
        assert connection != null;
        connection.createStatement().executeUpdate(String.format("INSERT INTO `jobportal`.`jobs`" +
                "(`position`," +
                "`description`," +
                "`status`," +
                "`salary`," +
                "`apply_deadline`," +
                "`company_id`)" +
                "VALUES" +
                "('%s','%s','Open',%d,'%s',%d);",insertName,insertDesc,Integer.parseInt(insertSal),insertDate,Integer.parseInt(App.company.companyId)));

        Object[] s =  skillsLabel.getChips().toArray();
        Statement statement = connection.createStatement();
        int[] a = new int[s.length];
        for(int i=0;i<s.length;i++){
            rs = statement.executeQuery(String.format("SELECT SKILL_ID FROM SKILLS WHERE NAME='%s' ",s[i]));
            rs.next();
            a[i] = rs.getInt(1);
        }

        rs = statement.executeQuery("SELECT last_insert_id()");
        rs.next();
        int jobid = rs.getInt(1);

        for (int j : a) {
            statement.executeUpdate(String.format("INSERT INTO REQUIRED_SKILLS VALUES(%d,%d)", j, jobid));
        }

        connection.close();

        Pane pane = FXMLLoader.load(getClass().getResource("../companyPosts.fxml"));
        companyPage.getChildren().setAll(pane);

    }

    public void logoutCompany(ActionEvent actionEvent) throws IOException {
        App.company = null;
        App.setAuthCompany(false);
        companyPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../ui/login.fxml")));
    }

    public void postOffer(ActionEvent actionEvent) {
    }
}
