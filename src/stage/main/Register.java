package stage.main;

import generation.*;
import generation.releaseNotes.ReleaseNotesXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stage.main.point.PointStage;
import stage.utensil.OverAnimation;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Register {

    //加载一个窗口
    private Stage stage = new Stage();

    //注册窗口
    void register() {

        //边框布局
        BorderPane border = new BorderPane();

        //获取版本号文件
        List<Map<String, String>> maps = new ReleaseNotesXML().releaseNotes();
        //获取最新的一个版本说明
        Map<String, String> map1 = maps.get(0);
        //拼接版本号
        String NX_caption = "NX_"+map1.get("updateTime")+"_build_"+map1.get("version");

        Label versionNumber = new Label(NX_caption);
        versionNumber.setPadding(new Insets(20, 0, 5, 10));
        versionNumber.setStyle("-fx-text-fill: #707070 ;-fx-font-size: 10px");
//        versionNumber.setAlignment(Pos.BOTTOM_CENTER);

        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(20, 50, 20, 50));


        vBox.setAlignment(Pos.TOP_CENTER);
        border.setCenter(vBox);
        border.setBottom(versionNumber);

        //注册成功
        Label registrationSuccess = new Label("");
        registrationSuccess.setId("registrationSuccess");


        Text libraryURLPoint = new Text("");
        Text libraryNamePoint = new Text("");
        Text userPoint = new Text("");
        Text passwordPoint = new Text("");

        TextField libraryURLField = new TextField("localhost:3306");
        libraryURLField.setPromptText("输入数据库地址及端口号");
        libraryURLField.setFocusTraversable(false);
        libraryURLField.setId("libraryURLField");

        TextField libraryNameField = new TextField();
        libraryNameField.setPromptText("输入数据库名称");
        libraryNameField.setFocusTraversable(false);
        libraryNameField.setId("libraryNameField");

        TextField userField = new TextField();
        userField.setPromptText("输入数据库账号");
        userField.setFocusTraversable(false);
        userField.setId("userField");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("输入数据库密码");
        passwordField.setFocusTraversable(false);
        passwordField.setId("passwordField");

        //按钮
        VBox buttonVbox = new VBox(16);
        //第一行
        HBox buttonHbox1 = new HBox(50);
        //第二行
        HBox buttonHbox2 = new HBox(50);

        //查询表
        Button select = new Button("查询");
        select.setPrefSize(80, 30);
        select.getStyleClass().addAll("button");
        //提交按钮
        Button submit = new Button("生成");
        submit.setPrefSize(80, 30);
        submit.getStyleClass().addAll("button");
        //打开文件夹
        Button openFolder = new Button("打开目录");
        openFolder.setPrefSize(80, 30);
        openFolder.getStyleClass().addAll("button");
        //清空信息
        Button clear_account = new Button("清空账号");
        clear_account.setPrefSize(80, 30);
        clear_account.getStyleClass().addAll("button");
        //清空缓存
        Button clear_cache = new Button("清空缓存");
        clear_cache.setPrefSize(80, 30);
        clear_cache.getStyleClass().addAll("button");

        buttonHbox1.getChildren().addAll(select, submit, openFolder);
        buttonHbox2.getChildren().addAll(clear_account, clear_cache);

        buttonVbox.getChildren().addAll(buttonHbox1, buttonHbox2);


        //表名列表
        ListView<String> listView = new ListView<>();
        listView.setId("listView");
        listView.setPlaceholder(new Label("没有数据，请点击查询按钮"));
        //设置开启多选模式
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        VBox.setMargin(listView, new Insets(20, 0, 0, 0));

        vBox.getChildren().add(registrationSuccess);
        vBox.getChildren().add(libraryURLField);
        vBox.getChildren().add(libraryURLPoint);
        vBox.getChildren().add(libraryNameField);
        vBox.getChildren().add(libraryNamePoint);
        vBox.getChildren().add(userField);
        vBox.getChildren().add(userPoint);
        vBox.getChildren().add(passwordField);
        vBox.getChildren().add(passwordPoint);
        vBox.getChildren().add(buttonVbox);
        vBox.getChildren().add(listView);

        Scene scene = new Scene(border,420,680);
        //为场景引入Regiser的css
        scene.getStylesheets().addAll("stage/css/Regiser.css");
        stage.setScene(scene);
        stage.setTitle("逆向工程生成工具");
        stage.getIcons().add(new Image("stage/image/icon.png"));
        stage.setMinWidth(410);
        stage.setMinHeight(640);
        //设置不可拉伸大小
//        stage.setResizable(false);
        stage.show();

        //去除通知
        scene.setOnMouseClicked(event -> {
            registrationSuccess.setText("");
            libraryNamePoint.setText("");
            userPoint.setText("");
            passwordPoint.setText("");

        });
        //MySQl数据库驱动
        String driverClass = "com.mysql.jdbc.Driver";

        //查询按钮
        select.setOnAction(event -> {

            //调用查询方法返回的集合
            List<String> tableList = null;
            //获取相应的值
            String libraryURL = libraryURLField.getText();
            String libraryName = libraryNameField.getText();
            String user = userField.getText();
            String password = passwordField.getText();

            if (!libraryURL.isEmpty() && !libraryName.isEmpty() && !user.isEmpty() && !password.isEmpty()) {
                //输入都不为空的时候查询调用查询方法(不和记录方法一起用)
                //数据库详情对象
                DatabaseInformation databaseInformation = new DatabaseInformation(driverClass, libraryURL, libraryName, user, password);
                //调用查询数据库方法
                tableList = new SelectTable().select(databaseInformation);

                //记录方法
                Properties prop = new Properties();
                try {

                    FileOutputStream oFile = new FileOutputStream("config/user.properties", false);
//                    prop.setProperty(user, password);
                    prop.setProperty("libraryURL", libraryURL);
                    prop.setProperty("libraryName", libraryName);
                    prop.setProperty("user", user);
                    prop.setProperty("password", password);

                    prop.store(oFile, null);
                    oFile.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (libraryURL.equals("")) {
                libraryURLPoint.setText("数据库地址不能为空");
                //错误提示动画
                OverAnimation.fadingCycle(libraryURLField, 0);
            } else {
                libraryURLPoint.setText("");
            }
            if (libraryName.equals("")) {
                libraryNamePoint.setText("数据库名不能为空");
                //错误提示动画
                OverAnimation.fadingCycle(libraryNameField, 0);
            } else {
                libraryNamePoint.setText("");
            }
            if (user.equals("")) {
                userPoint.setText("账号不能为空");
                //错误提示动画
                OverAnimation.fadingCycle(userField, 0);
            } else {
                userPoint.setText("");
            }
            if (password.equals("")) {
                passwordPoint.setText("密码不能为空");
                //错误提示动画
                OverAnimation.fadingCycle(passwordField, 0);
            } else {
                passwordPoint.setText("");
            }

            System.out.println("查询到的表名 -->" + tableList);

            //遍历所有用户表并添加到listView中
            // （有BUG,查找到的内容可重复添加到listView中）
            //  使用 FXCollections.observableArrayList()
            //  已解决
            if (tableList != null && tableList.size() > 0) {
                registrationSuccess.setText("查找成功");

                //创建ObservableList (方便后续操作)
                ObservableList<String> tableName = FXCollections.observableArrayList();
                //把查找到的表名 放入ObservableList 中
                tableName.addAll(tableList);
                //把tableName放入listView
                listView.setItems(tableName);

            } else {
                registrationSuccess.setText("查找失败");
            }
        });

        //自动写入 将记录的登录赋值给输入框功能
        try {
            Properties prop = new Properties();
            if (new File("config/user.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("config/user.properties"));
                prop.load(in);

                for (String key : prop.stringPropertyNames()) {

                    if (key.equals("libraryURL")) {
                        libraryURLField.setText(prop.getProperty(key));
                    }
                    if (key.equals("libraryName")) {
                        libraryNameField.setText(prop.getProperty(key));
                    }
                    if (key.equals("user")) {
                        userField.setText(prop.getProperty(key));
                    }
                    if (key.equals("password")) {
                        passwordField.setText(prop.getProperty(key));
                    }

                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //生成按钮
        submit.setOnAction(event -> {

            registrationSuccess.setText("生成中。。。");

            String libraryURL = libraryURLField.getText();
            String libraryName = libraryNameField.getText();
            String user = userField.getText();
            String password = passwordField.getText();
            //数据库详情对象
            DatabaseInformation databaseInformation = new DatabaseInformation(driverClass, libraryURL, libraryName, user, password);
            //获取选中的列表中的数据名称
            ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();

            if (listView.getItems().size() > 0 && selectedItems.size() == 0) {
                //如果listView中有数据但是没有选中就默认选第一个
                listView.getSelectionModel().select(0);
            }

            System.out.println("选中的表名 -->" + selectedItems);

            //调用修改XML方法
            new ReadWriteXML().generator(databaseInformation, selectedItems);
            //调用生成方法
            boolean generator = new Generator().generator();

            if (generator) {
                registrationSuccess.setText("已生成");
            } else {
                registrationSuccess.setText("生成失败");
            }

        });

        //打开生成目录
        openFolder.setOnAction(event -> {
            String path = "Resource";

            try {
                Runtime.getRuntime().exec("cmd /c start " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //清除功能
        clear_account.setOnAction(event -> {

            //清除通知信息
            libraryURLField.setText("");
            libraryNameField.setText("");
            userField.setText("");
            passwordField.setText("");

            //清除视图信息信息
            listView.getItems().forEach(items -> listView.getItems().remove(items));

            //清空用户信息
            Properties prop = new Properties();
            try {
                FileOutputStream oFile = new FileOutputStream("config/user.properties", false);
                prop.remove("libraryURL");
                prop.remove("libraryName");
                prop.remove("user");
                prop.remove("password");
                prop.store(oFile, null);
                oFile.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //清空缓存
        clear_cache.setOnAction(event -> {

            String path = "Resource";
            String path1 = "Resource\\entity";
            String path2 = "Resource\\mapper";

            File file = new File(path);
            File[] files = file.listFiles();

            if (files != null && files.length > 0) {
                try {

                    Runtime.getRuntime().exec("cmd /c rmdir/s/q " + path1);
                    Runtime.getRuntime().exec("cmd /c rmdir/s/q " + path2);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                registrationSuccess.setText("清除成功");
            } else {
                registrationSuccess.setText("文件夹内没有内容");
            }
        });

        //版本说明
        versionNumber.setOnMouseClicked(event -> {
            //调用说明窗口
            new PointStage().point();

        });
    }


}
