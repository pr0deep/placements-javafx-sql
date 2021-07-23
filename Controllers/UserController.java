package placement.Controllers;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import placement.App;
import placement.Database;
import placement.Job;


import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Collection;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML public Pane leftpane;
    @FXML public Pane rightpane;

    public Border focused = new Border(new BorderStroke(Color.web("#583bcd"),BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT));
    public BorderPane userpage;
    public Label profilenamelabel;
    public MenuItem exitlabel;
    public JFXButton viewposts;
    public AnchorPane parentmiddle;

    public JFXButton infoPage;
    public Label sqlQueryLabel;
    public JFXTextField filterPreference;
    public JFXButton applyfilterButton;
    public JFXTextField salaryText;
    public JFXButton applySelected;
    public ImageView profilePicture;
    public Circle clipCircle;
    public JFXButton logoutButton;
    public UserTableComponentController tableComponentController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting username
        profilenamelabel.setText(App.user.getName());


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../ui/user_info_component.fxml"));
        Pane temp = null;
        try {
            temp = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserInfoComponentController c = (UserInfoComponentController) fxmlLoader.getController();
        c.sqlQueryLabel = sqlQueryLabel;
        parentmiddle.getChildren().setAll(temp);
        rightpane.getChildren().setAll( );
        applySelected.setDisable(true);


        profilePicture.setClip(new Circle(100,50,50));

    }

    public void leftpaneMouseclick(MouseEvent mouseEvent) {
        toggleBorder(leftpane);
    }

    public void rightpaneMouseclick(MouseEvent mouseEvent) {
        toggleBorder(rightpane);
    }


    public void toggleBorder(Pane p){
        if(p.getBorder()==null)
            p.setBorder(focused);
        else
            p.setBorder(null);
    }


    public void redirectToInfo(MouseEvent mouseEvent) throws IOException {
        //Pane temp = FXMLLoader.load(getClass().getResource("../user_info.fxml"));
        //userpage.getChildren().setAll(temp);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../ui/user_info_component.fxml"));
        Pane temp = fxmlLoader.load();
        UserInfoComponentController c = (UserInfoComponentController) fxmlLoader.getController();
        c.sqlQueryLabel = sqlQueryLabel;
        parentmiddle.getChildren().setAll(temp);
        rightpane.getChildren().setAll( );
        applySelected.setDisable(true);
    }

    public void applySelectedJobs(MouseEvent mouseEvent) throws SQLException {

        Connection conn = Database.connectToDB();
        assert conn != null;
        Statement st = conn.createStatement();
        ObservableList<Job> jobList = tableComponentController.jobList;
        for(Job job:jobList){
            if(job.jobApply){
                CallableStatement callableStatement = conn.prepareCall(String.format("call jobportal.job_apply(%s, %s, '%s')",App.user.getId(),job.jobId.getValue(),(new Date(new java.util.Date().getTime())).toLocalDate()));
                callableStatement.execute();
            }
        }
        conn.close();
    }

    public void logoutUser(ActionEvent actionEvent) throws IOException {
        App.user = null;
        App.setAuthUser(false);
        userpage.getChildren().setAll((Pane)FXMLLoader.load(getClass().getResource("../ui/login.fxml")));

    }

    public void renderView(MouseEvent mouseEvent) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("../ui/student_table_component.fxml"));
        Pane temp = fxmlLoader.load();
        tableComponentController = (UserTableComponentController)fxmlLoader.getController();
        tableComponentController.sqlQueryLabel = sqlQueryLabel;
        parentmiddle.getChildren().setAll(temp);

        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../ui/filter_component.fxml"));
        Pane temp2 = fxmlLoader.load();
        UserFilterComponentController userFilterComponentController = (UserFilterComponentController)fxmlLoader.getController();
        userFilterComponentController.sqlQueryLabel = sqlQueryLabel;
        userFilterComponentController.viewTable = tableComponentController.viewTable;
        rightpane.getChildren().setAll(temp2);

        applySelected.setDisable(false);
    }
}




