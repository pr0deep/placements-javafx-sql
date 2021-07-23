package placement;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Application extends RecursiveTreeObject<Application> {
    public StringProperty jobID;
    public StringProperty name;
    public StringProperty desc;
    public StringProperty age;
    public StringProperty program;
    public StringProperty position;
    public StringProperty email;
    public StringProperty qualification;
    public StringProperty cgpa;

    public Application(int jobID, String name, String desc, int age, String program, String position, String email, String qualification,double cgpa) {
        this.jobID =  new SimpleStringProperty(String.valueOf(jobID));
        this.name = new SimpleStringProperty(name);
        this.desc =  new SimpleStringProperty(desc);
        this.age = new SimpleStringProperty(String.valueOf(age));
        this.program = new SimpleStringProperty(program);
        this.position =  new SimpleStringProperty(position);
        this.email = new SimpleStringProperty(email);
        this.qualification = new SimpleStringProperty(qualification);
        this.cgpa = new SimpleStringProperty(String.valueOf(cgpa));
    }


}
