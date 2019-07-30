package stage.main.point;

import generation.releaseNotes.ReleaseNotesXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class PointStage {
    //窗口
    private Stage stage = new Stage();
    public void point() {

        //获取版本信息
        ReleaseNotesXML releaseNotesXML = new ReleaseNotesXML();
        //获取说明标签方法
        String instructions = releaseNotesXML.instructions();

        //分割符
        Separator separator = new Separator();
        separator.setId("separator");

        //边框布局
        BorderPane border = new BorderPane();
        border.setId("border");

        ListView<String> listView = new ListView<>();
        listView.setId("listView");
        //创建ObservableList (方便后续操作)
        ObservableList<String> tableName = FXCollections.observableArrayList();

        //把查找到的表名 放入ObservableList 中
        tableName.addAll(instructions);

        //获取版本号方法
        List<Map<String, String>> maps = new ReleaseNotesXML().releaseNotes();

        //遍历添加到列表中
        maps.forEach(map -> tableName.addAll("\n更新时间：" + map.get("updateTime") + "\n版本号：_build_" + map.get("version") + "\n更新内容：\n" + map.get("text")));

        //把tableName放入listView
        listView.setItems(tableName);
        border.setCenter(listView);
        Scene scene = new Scene(border, 640, 430);
        scene.getStylesheets().addAll("stage/css/PointStage.css");
        stage.setScene(scene);
        stage.setTitle("介绍说明");
        //设置不可拉伸大小
        stage.setResizable(false);

        stage.show();

    }
}
