package stage.utensil;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class OverAnimation {

    //创建一个动画
    private static FadeTransition fadeTransition = new FadeTransition();

    //闪烁(没有想要的实现效果)
    public static void fadingCycle(Node node, double time) {

//        for (int i = 0; i < 5; i++) {
//            fadingNode(node,time);
//        }
                    fadingNode(node,time);
    }

    //渐隐
    private static void fadingNode(Node node ,double time) {
        //添加动画的对象(因为所有的组件都是继承Node类)
        fadeTransition.setNode(node);
        //持续时间
        if (time == 0 || time == 0.0) {

            time = 0.3;
        }

        fadeTransition.setDuration(Duration.seconds(time));
        //开始值
        fadeTransition.setFromValue(0);
        //结束值
        fadeTransition.setToValue(1);
        //开始动画
        fadeTransition.play();
    }


}
