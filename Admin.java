package placement;

public class Admin {
    private String name;
    private String password;
    private String somedata;

    public Admin(String name,String password){
        this.name = name;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public String getSomedata() {
        return somedata;
    }

    public void setSomedata(String somedata) {
        this.somedata = somedata;
    }
}
