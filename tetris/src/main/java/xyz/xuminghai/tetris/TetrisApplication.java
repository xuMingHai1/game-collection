package xyz.xuminghai.tetris;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import xyz.xuminghai.tetris.game.GameWorld;
import xyz.xuminghai.tetris.util.Version;
import xyz.xuminghai.tetris.view.GameView;

import java.net.URL;
import java.time.LocalTime;

/**
 * 2024/1/15 20:30 星期一<br/>
 *
 * @author xuMingHai
 */
public class TetrisApplication extends Application {

    /**
     * 启动时间
     */
    private static final long BOOT_TIME = System.currentTimeMillis();

    public static HostServices hostServices;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        hostServices = super.getHostServices();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tetris");
        loadIcon(primaryStage);
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
        accelerators.put(new KeyCodeCombination(KeyCode.EQUALS), gameWorld::levelPlus);
        accelerators.put(new KeyCodeCombination(KeyCode.MINUS), gameWorld::levelMinus);
        primaryStage.setScene(scene);
        primaryStage.show();
        checkUpdate(primaryStage);
        System.out.printf("启动完成耗时 = %dms%n", System.currentTimeMillis() - BOOT_TIME);
    }

    private void loadIcon(Stage primaryStage) {
        final URL resource = TetrisApplication.class.getResource("/img/tetris.png");
        if (resource != null) {
            primaryStage.getIcons().add(new Image(resource.toExternalForm()));
        }
    }

    private void checkUpdate(Stage primaryStage) {
        Version.checkUpdate(version -> {
            final Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "当前版本：" + Version.VERSION + "，新版本：" + version,
                    ButtonType.APPLY);
            alert.setHeaderText("有新的版本更新");
            alert.initOwner(primaryStage);
            alert.showAndWait().ifPresent(buttonType -> super.getHostServices().showDocument(Version.RELEASE_URI));
        });
    }
}
