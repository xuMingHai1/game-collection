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

    private final GameWorld gameWorld = new GameWorld();

    private final GameView gameView = new GameView(gameWorld);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        hostServices = super.getHostServices();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tetris");
        // 加载图标
        primaryStage.getIcons().add(new Image("img/icon.png"));
        // 禁止改变大小
        primaryStage.setResizable(false);
        primaryStage.setScene(keyMonitor(new Scene(gameView)));
        primaryStage.show();
        checkUpdate(primaryStage);
        System.out.printf("启动完成耗时 = %dms%n", System.currentTimeMillis() - BOOT_TIME);
    }

    private Scene keyMonitor(Scene scene) {
        // 键盘监听
        final ObservableMap<KeyCombination, Runnable> accelerators = scene.getAccelerators();
        accelerators.put(new KeyCodeCombination(KeyCode.A), gameWorld::leftMove);
        accelerators.put(new KeyCodeCombination(KeyCode.S), gameWorld::downMove);
        accelerators.put(new KeyCodeCombination(KeyCode.D), gameWorld::rightMove);
        accelerators.put(new KeyCodeCombination(KeyCode.LEFT), gameWorld::rotateCounterClockwise);
        accelerators.put(new KeyCodeCombination(KeyCode.RIGHT), gameWorld::rotateClockwise);
        accelerators.put(new KeyCodeCombination(KeyCode.EQUALS), gameWorld::levelPlus);
        accelerators.put(new KeyCodeCombination(KeyCode.MINUS), gameWorld::levelMinus);
        accelerators.put(new KeyCodeCombination(KeyCode.SPACE), gameWorld::startOrPauseGame);
        accelerators.put(new KeyCodeCombination(KeyCode.TAB, KeyCombination.CONTROL_DOWN), gameWorld::switchLanguage);
        return scene;
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
