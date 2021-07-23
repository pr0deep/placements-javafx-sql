package placement;


import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Job extends RecursiveTreeObject<Job> {
      public StringProperty jobId ;
      public StringProperty jobName;
      public StringProperty jobTitle;
      public StringProperty jobSalary;
      public StringProperty jobSkills;
      public StringProperty jobDate;
      public StringProperty jobDesc;
      public StringProperty jobcompID;
      public ObservableValue<JFXCheckBox> jfxCheckBox;
      public boolean jobApply;

    public Job(String Name,String title,int salary,String skills,String date,String desc,String compid,String jobid){
            this.jobId = new SimpleStringProperty(jobid);
            this.jobName = new SimpleStringProperty(Name);
            this.jobTitle = new SimpleStringProperty(title);
            this.jobSalary = new SimpleStringProperty("$"+String.valueOf(salary));
            this.jobSkills = new SimpleStringProperty(skills);
            this.jobDate = new SimpleStringProperty(date);
            this.jobDesc = new SimpleStringProperty(desc);
            this.jobcompID = new SimpleStringProperty(compid);
            JFXCheckBox tempBox = new JFXCheckBox();

            tempBox.setOnAction(e->this.invertStatus());

            this.jfxCheckBox = new ObservableValue<JFXCheckBox>() {
                @Override
                public void addListener(InvalidationListener invalidationListener) {

                }

                @Override
                public void removeListener(InvalidationListener invalidationListener) {

                }

                @Override
                public void addListener(ChangeListener<? super JFXCheckBox> changeListener) {

                }

                @Override
                public void removeListener(ChangeListener<? super JFXCheckBox> changeListener) {

                }

                @Override
                public JFXCheckBox getValue() {
                    return tempBox;
                }
            };
    }

    private void invertStatus() {
        this.jobApply = !this.jobApply;

    }


}
