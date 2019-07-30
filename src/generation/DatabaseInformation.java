package generation;

public class DatabaseInformation {

    //数据库驱动（为以后切换oracle）
    private String driverClass;
    //数据库地址
    private String libraryURL;
    //数据库名称
    private String libraryName;
    //数据库管理员id
    private String userId;
    //数据库管理员密码
    private String password;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getLibraryURL() {
        return libraryURL;
    }

    public void setLibraryURL(String libraryURL) {
        this.libraryURL = libraryURL;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DatabaseInformation(String driverClass, String libraryURL, String libraryName, String userId, String password) {
        this.driverClass = driverClass;
        this.libraryURL = libraryURL;
        this.libraryName = libraryName;
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "DatabaseInformation{" +
                "driverClass='" + driverClass + '\'' +
                ", libraryURL='" + libraryURL + '\'' +
                ", libraryName='" + libraryName + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
