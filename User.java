package placement;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class User{
    private String name;
    private String userdata;
    private String password;
    private int phone;
    private String skills;
    private String email;
    private int id;

    private String applications;
    private JFXCheckBox jfxCheckBox;

    public User(int id,String name, String userdata, String password, int phone, String skills, String email, String applications) {
        this.id = id;
        this.name = name;
        this.userdata = userdata;
        this.password = password;
        this.phone = phone;
        this.skills = skills;
        this.email = email;
        this.applications = applications;
        this.jfxCheckBox = new JFXCheckBox();

    }


    public String getName() {
        return name;
    }

    public String getUserdata() {
        return userdata;
    }

    public void setUserdata(String userdata) {
        this.userdata = userdata;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApplications() {
        return applications;
    }

    public void setApplications(String applications) {
        this.applications = applications;
    }

    public JFXCheckBox getJfxCheckBox() {
        return jfxCheckBox;
    }

    public void setJfxCheckBox(JFXCheckBox jfxCheckBox) {
        this.jfxCheckBox = jfxCheckBox;
    }

    public int getId() {
        return this.id;
    }
}
