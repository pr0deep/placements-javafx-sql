package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AdminController {
    public JFXButton logoutButton;
    public AnchorPane adminPage;
    public AnchorPane renderPage;
    public JFXButton executeButton;

    public void logoutAdmin(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        adminPage.getChildren().setAll(pane);
    }

    public void showSQLcli(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("../ui/admin_sql_component.fxml"));
        renderPage.getChildren().setAll(pane);
    }
}
