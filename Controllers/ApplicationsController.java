package placement.Controllers;

import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import placement.App;
import placement.Application;
import placement.Database;
import placement.Job;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationsController implements Initializable {

    public JFXButton postTab;
    public BorderPane applicationPage;
    public JFXTreeTableView<Application> applicationTable;
    public ObservableList<Application> applications = FXCollections.observableArrayList();
    public JFXButton applyfilterbutton;
    public ChoiceBox genderLabel;
    public JFXChipView skillsLabel;
    public JFXTextField programLabel;
    public JFXTextField cgpaLabel;
    public JFXTextField qualificationLabel;
    public JFXButton logoutButton;
    public Label idLabel;
    public Label nameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        nameLabel.setText(App.company.companyName);
        genderLabel.getItems().add("All");
        idLabel.setText("ID : "+App.company.companyId);
            treeviewInitialize();
            List<Job> jobList = new ArrayList<>();
            List<Integer> studentID = new ArrayList<>();
            Connection connection = Database.connectToDB();

            String query = String.format("SELECT * FROM JOBS WHERE COMPANY_ID = %s",App.company.companyId);
            App.sqlCommands.add(query);
            try {

                assert connection != null;
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    //applications.add(new Application(rs.getInt("student_id"), rs.getString("name"), rs.getString("gender"), rs.getInt("age"), rs.getString("program"), rs.getString("title"), rs.getString("email"), rs.getString("qualification")));
                    genderLabel.getItems().add(rs.getString("position"));
                    jobList.add(new Job(App.company.companyName,rs.getString("position"),rs.getInt("salary"),"","",rs.getString("description"),App.company.companyId,rs.getString("job_id")));
                }
                for(Job job:jobList){
                    rs = statement.executeQuery(String.format("SELECT STUDENT_ID FROM APPLICATION WHERE JOB_ID = %s",job.jobId.getValue()));
                    App.sqlCommands.add(String.format("SELECT STUDENT_ID FROM APPLICATION WHERE JOB_ID = %s",job.jobId.getValue()));
                    while (rs.next()){
                        studentID.add(rs.getInt("student_id"));
                    }
                    for(Integer sid:studentID){
                        rs = statement.executeQuery(String.format("SELECT * FROM STUDENT WHERE STUDENT_ID = %d",sid));
                        App.sqlCommands.add(String.format("SELECT * FROM STUDENT WHERE STUDENT_ID = %d",sid));
                        while (rs.next()){
                                applications.add(new Application(Integer.parseInt(job.jobId.getValue()),rs.getString("student_first_name")+rs.getString("student_last_name"),job.jobDesc.getValue(),rs.getInt("age"),rs.getString("program"),job.jobTitle.getValue(),rs.getString("email"),rs.getString("program"),rs.getDouble("cgpa")));

                        }
                    }
                    studentID.clear();
                }

                TreeItem<Application> root = new TreeItem<>(new Application(0,"","",0,"","","","",0.0));

                for(int i=0;i<applications.size();i++)
                    root.getChildren().add(i,new TreeItem<Application>(applications.get(i)));

                applicationTable.setRoot(root);
                applicationTable.setShowRoot(false);

                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }


    public void redirectToPost(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("../ui/companyPosts.fxml"));
        applicationPage.getChildren().setAll(pane);
    }
    public void treeviewInitialize(){
        String styling = "-fx-background-color:#27282c;-fx-text-fill:white;-fx-alignment:Center;";


        JFXTreeTableColumn<Application,String> colName= new JFXTreeTableColumn<>("Position");
        colName.setPrefWidth(116.0);
        colName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().position;
            }
        });
        colName.setStyle(styling);

        JFXTreeTableColumn<Application,String> colGender= new JFXTreeTableColumn<>("Description");
        colGender.setPrefWidth(116.0);
        colGender.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().desc;
            }
        });
        colGender.setStyle(styling);

        JFXTreeTableColumn<Application,String> colAge= new JFXTreeTableColumn<>("Candidate Name");
        colAge.setPrefWidth(116.0);
        colAge.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().name;
            }
        });
        colAge.setStyle(styling);

        JFXTreeTableColumn<Application,String> colProgram= new JFXTreeTableColumn<>("Qualification");
        colProgram.setPrefWidth(116.0);
        colProgram.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().program;
            }
        });
        colProgram.setStyle(styling);

        JFXTreeTableColumn<Application,String> colTitle= new JFXTreeTableColumn<>("Age");
        colTitle.setPrefWidth(50.0);
        colTitle.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().age;
            }
        });
        colTitle.setStyle(styling);

        JFXTreeTableColumn<Application,String> colEmail= new JFXTreeTableColumn<>("Email");
        colEmail.setPrefWidth(116.0);
        colEmail.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().email;
            }
        });
        colEmail.setStyle(styling);

        JFXTreeTableColumn<Application,String> colcgpa= new JFXTreeTableColumn<>("Grade");
        colcgpa.setPrefWidth(70.0);
        colcgpa.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Application, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Application, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().cgpa;
            }
        });
        colcgpa.setStyle(styling);


        applicationTable.getColumns().setAll(colName,colGender,colAge,colProgram,colTitle,colEmail,colcgpa);
    }

    public void applyfilter(MouseEvent mouseEvent) {
        applications.clear();
        List<Job> jobList = new ArrayList<>();
        List<Integer> studentID = new ArrayList<>();
        Connection connection = Database.connectToDB();

        String query = String.format("SELECT * FROM JOBS WHERE COMPANY_ID = %s",App.company.companyId);
        App.sqlCommands.add(query);
        try {

            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                //applications.add(new Application(rs.getInt("student_id"), rs.getString("name"), rs.getString("gender"), rs.getInt("age"), rs.getString("program"), rs.getString("title"), rs.getString("email"), rs.getString("qualification")));
                if(rs.getString("position").equals(genderLabel.getValue().toString()) || genderLabel.getValue().toString().equals("All") )
                    jobList.add(new Job(App.company.companyName,rs.getString("position"),rs.getInt("salary"),"","",rs.getString("description"),App.company.companyId,rs.getString("job_id")));
            }
            for(Job job:jobList){
                rs = statement.executeQuery(String.format("SELECT STUDENT_ID FROM APPLICATION WHERE JOB_ID = %s",job.jobId.getValue()));
                App.sqlCommands.add(String.format("SELECT STUDENT_ID FROM APPLICATION WHERE JOB_ID = %s",job.jobId.getValue()));
                while (rs.next()){
                    studentID.add(rs.getInt("student_id"));
                }
                for(Integer sid:studentID){
                    rs = statement.executeQuery(String.format("SELECT * FROM STUDENT WHERE STUDENT_ID = %d",sid));
                    App.sqlCommands.add(String.format("SELECT * FROM STUDENT WHERE STUDENT_ID = %d",sid));
                    while (rs.next()){
                        if((programLabel.getText().equals("")||rs.getString("program").equals(programLabel.getText())) && (cgpaLabel.getText().equals("")||Integer.parseInt(cgpaLabel.getText())<=rs.getDouble("cgpa")))
                            applications.add(new Application(Integer.parseInt(job.jobId.getValue()),rs.getString("student_first_name")+rs.getString("student_last_name"),job.jobDesc.getValue(),rs.getInt("age"),rs.getString("program"),job.jobTitle.getValue(),rs.getString("email"),rs.getString("program"),rs.getDouble("cgpa")));

                    }
                }
                studentID.clear();
            }

            TreeItem<Application> root = new TreeItem<>(new Application(0,"","",0,"","","","",0.0));

            for(int i=0;i<applications.size();i++)
                root.getChildren().add(i,new TreeItem<Application>(applications.get(i)));

            applicationTable.setRoot(root);
            applicationTable.setShowRoot(false);

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void logoutCompany(ActionEvent actionEvent) throws IOException, InterruptedException {
        App.company = null;
        App.setAuthCompany(false);
        applicationPage.getChildren().setAll((Pane) FXMLLoader.load(getClass().getResource("../ui/login.fxml")));
    }
}
