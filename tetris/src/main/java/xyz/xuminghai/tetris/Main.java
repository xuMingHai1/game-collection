package xyz.xuminghai.tetris;

import javafx.application.Application;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import xyz.xuminghai.tetris.game.GameWorld;
import xyz.xuminghai.tetris.view.GameView;

import java.time.LocalTime;

/**
 * 2024/1/15 20:30 星期一<br/>
 *
 * @author xuMingHai
 */
public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("俄罗斯方块");
        // 禁止改变大小
        primaryStage.setResizable(false);
        final GameWorld gameWorld = new GameWorld();
        final Scene scene = new Scene(new GameView(gameWorld));
        // Control + Enter 重新加载
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN),
                () -> {
                    System.out.println("重新加载时间 = " + LocalTime.now());
                    scene.setRoot(new GameView(gameWorld));
                });
        // 键盘监听
        final ObservableMap<KeyCombination, Runnable> accelerators = scene.getAccelerators();
        accelerators.put(new KeyCodeCombination(KeyCode.SPACE), gameWorld::startOrPauseGame);
        accelerators.put(new KeyCodeCombination(KeyCode.A), gameWorld::leftMove);
        accelerators.put(new KeyCodeCombination(KeyCode.S), gameWorld::downMove);
        accelerators.put(new KeyCodeCombination(KeyCode.D), gameWorld::rightMove);
        accelerators.put(new KeyCodeCombination(KeyCode.LEFT), gameWorld::rotateCounterClockwise);
        accelerators.put(new KeyCodeCombination(KeyCode.RIGHT), gameWorld::rotateClockwise);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
