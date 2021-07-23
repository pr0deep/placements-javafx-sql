package placement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class App extends Application {
    public static boolean isAuthUser = false;
    private static boolean  isAuthAdmin = false;
    private static boolean isAuthCompany = false;
    public static User user=new User(0,"","","",0,"","","");
    public static Admin admin=new Admin("","");
    public static Company company;
    public static ArrayList<String> sqlCommands = new ArrayList<>();
    public static Stage pstage;
    @Override
    public void start(Stage primaryStage) throws Exception {                   //(1)

        //bootstrap config



        //bootstrap config ends..
        pstage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("ui/login.fxml"));
        Scene scene = new Scene(root,1280,720);
        //scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)
        primaryStage.setTitle("Placements");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void setAuthAdmin(boolean authAdmin) {
        isAuthAdmin = authAdmin;
    }

    public static void setAuthUser(boolean authUser) {
        isAuthUser = authUser;
    }
    public static void newUser(int id,String name, String userdata, String password, int phone, String skills, String email, String applications){
        user = new User(id,name,userdata,password,phone,skills,email,applications);
    }

    public static void setAuthCompany(boolean b){
        isAuthCompany = b;
    }

    public static void newCompany(String companyName, String companyId, String companyDesc, String jobs){
        company = new Company(companyName,companyId,companyDesc,jobs);
    }

}