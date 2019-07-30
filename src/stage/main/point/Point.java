package stage.main.point;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Point {


    public void pointa(String text) {

        //提示弹窗
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
//        alert.initStyle(StageStyle.TRANSPARENT);

        alert.setHeaderText(text);


        Optional<ButtonType> buttonType = alert.showAndWait();

    }


}
