package placement.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import placement.App;
import placement.Database;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminSQLController implements Initializable {
    public WebView webView;
    public AnchorPane renderPage;
    public JFXButton executeUpdate;
    public JFXTextField queryToExecute;
    public JFXScrollPane webPane;
    public JFXSpinner loadAnim;
    public Label queryCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webPane.setVisible(false);
        WebEngine webEngine = webView.getEngine();
        File f = new File("C:\\Users\\prade\\IdeaProjects\\placements\\src\\code.html");
        webEngine.load(f.toURI().toString());
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:
                loadAnim.setVisible(false);
                webPane.setVisible(true);
                for(String sql : App.sqlCommands){
                    webEngine.executeScript(String.format("editor.insert('%s \\n');editor.insert('\\n')",sql));

                }
                queryCount.setText(String.format("Number of queries: %d",App.sqlCommands.size()));

            }
        });
    }

    public void executeSQL(MouseEvent mouseEvent) throws SQLException {
        Connection conn = Database.connectToDB();
        assert conn != null;
        Statement st = conn.createStatement();
        st.executeUpdate(queryToExecute.getText());
        conn.close();
    }
}
