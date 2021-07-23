package placement.Controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import placement.Database;
import placement.Job;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserTableComponentController implements Initializable {
    public JFXTreeTableView viewTable;
    public Label sqlQueryLabel = new Label();
    public ObservableList<Job> jobList = FXCollections.observableArrayList();


    public void viewTableInitialize(){
        String styling = "-fx-background-color:#27282c;-fx-text-fill:white;-fx-alignment:Center;";

        JFXTreeTableColumn<Job, JFXCheckBox> colID= new JFXTreeTableColumn<>("Apply");
        colID.setPrefWidth(75.0);
        colID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, JFXCheckBox>, ObservableValue<JFXCheckBox>>() {
            @Override
            public ObservableValue<JFXCheckBox> call(TreeTableColumn.CellDataFeatures<Job, JFXCheckBox> jobJFXCheckBoxCellDataFeatures) {
                return jobJFXCheckBoxCellDataFeatures.getValue().getValue().jfxCheckBox;
            }
        });
        colID.setStyle(styling);


        JFXTreeTableColumn<Job,String> colName= new JFXTreeTableColumn<>("Position");
        colName.setPrefWidth(116.0);
        colName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobTitle;
            }
        });
        colName.setStyle(styling);


        JFXTreeTableColumn<Job,String> colSkills= new JFXTreeTableColumn<>("Requirements");
        colSkills.setPrefWidth(136.0);
        colSkills.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobSkills;
            }
        });
        colSkills.setStyle(styling);

        JFXTreeTableColumn<Job,String> colPay= new JFXTreeTableColumn<>("Payscale");
        colPay.setPrefWidth(81.0);
        colPay.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobSalary;
            }
        });
        colPay.setStyle(styling);

        JFXTreeTableColumn<Job,String> colDesc= new JFXTreeTableColumn<>("Job desc");
        colDesc.setPrefWidth(142.0);
        //colID.setPrefWidth(10);
        colDesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobDesc;
            }
        });
        colDesc.setStyle(styling);

        JFXTreeTableColumn<Job,String> colDate= new JFXTreeTableColumn<>("Application Due");
        colDate.setPrefWidth(131.0);
        colDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Job, String> jobStringCellDataFeatures) {
                return jobStringCellDataFeatures.getValue().getValue().jobDate;
            }
        });
        colDate.setStyle(styling);


        viewTable.getColumns().setAll(colID, colName, colSkills, colPay, colDesc,colDate);
    }
    public void initializePosts() throws SQLException {
        jobList.clear();
        Connection connection = Database.connectToDB();
        String query = "SELECT * FROM JOBS";

        sqlQueryLabel.setText("SQL>"+query);

        assert connection != null;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);


        while(rs.next()){
            jobList.add(new Job(rs.getString(2),rs.getString("position"),rs.getInt("salary"),rs.getString(6),rs.getString("apply_deadline"),rs.getString("description"),rs.getString("company_id"),rs.getString("job_id")));
        }
        for (Job job : jobList) {
            rs = st.executeQuery(String.format("SELECT NAME FROM SKILLS JOIN REQUIRED_SKILLS ON SKILL_ID = SKILLS_ID WHERE JOB_ID = %d", Integer.parseInt(job.jobId.getValue())));
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString(1)).append(" ");
            }
            job.jobSkills = new SimpleStringProperty(sb.toString());
        }
        for (Job job:
                jobList) {
            rs = st.executeQuery("SELECT COMPANY_NAME FROM COMPANY WHERE COMPANY_ID = "+job.jobcompID.getValue());
            rs.next();
            job.jobName = new SimpleStringProperty(rs.getString(1));
            job.jobTitle = new SimpleStringProperty(job.jobTitle.getValue().concat(String.format(" , %s",job.jobName.getValue())));
        }

        TreeItem<Job> root = new TreeItem<>(new Job("","",0,"","","","",""));

        for(int i=0;i<jobList.size();i++)
            root.getChildren().add(i,new TreeItem<Job>(jobList.get(i)));



        viewTable.setRoot(root);
        viewTable.setShowRoot(false);

        connection.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewTableInitialize();
        try {
            initializePosts();
            sqlQueryLabel.setText("SELECT * FROM JOBS");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
