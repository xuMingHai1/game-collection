package xyz.xuminghai.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.xuminghai.snake.view.StartView;

/**
 * 2023/10/25 14:14 星期三<br/>
 * 主启动类
 *
 * @author xuMingHai
 */
public class Main extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static Stage getPrimaryStage() {
        if (primaryStage == null) {
            throw new IllegalStateException("主窗口为null");
        }
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("贪吃蛇");
        // 禁止用户调整大小
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(new StartView(), 800.0, 600.0));
        primaryStage.show();
    }

}
