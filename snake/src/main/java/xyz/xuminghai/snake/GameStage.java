package xyz.xuminghai.snake;

import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xyz.xuminghai.snake.core.DirectionEnum;
import xyz.xuminghai.snake.view.GameView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * 2023/10/27 15:47 星期五<br/>
 * 游戏窗体
 *
 * @author xuMingHai
 */
public class GameStage extends Stage {

    public static final Path PATH = Paths.get("data/snake.data");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withZone(ZoneId.systemDefault());


    private final ControlPopup controlPopup = new ControlPopup();

    private final GameView gameView = new GameView(controlPopup);

    private GameStage() {
        super(StageStyle.UNDECORATED);
        super.setScene(new Scene(gameView));
        setAccelerators();
    }

    public static void start() {
        Main.getPrimaryStage().close();
        new GameStage().show();
    }


    @Override
    public void hide() {
        String datetime = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String data = """
                %s\t%d
                """.formatted(datetime, gameView.getSnakeLength());
        try {
            Files.writeString(PATH, data, StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.hide();
    }

    private void setAccelerators() {
        ObservableMap<KeyCombination, Runnable> accelerators = super.getScene().getAccelerators();
        // 设置控制面板快捷键
        accelerators.put(new KeyCodeCombination(KeyCode.ESCAPE), () -> {
            System.out.println("ESC");
            gameView.stop();
            controlPopup.show(this);
        });
        // 游戏启动和暂停
        accelerators.put(new KeyCodeCombination(KeyCode.SPACE), gameView::startOrStop);
        // 方向控制
        // 上
        accelerators.put(new KeyCodeCombination(KeyCode.UP), () -> {
            gameView.setMoveDirection(DirectionEnum.UP);
            gameView.start();
        });
        // 下
        accelerators.put(new KeyCodeCombination(KeyCode.DOWN), () -> {
            gameView.setMoveDirection(DirectionEnum.DOWN);
            gameView.start();
        });
        // 左
        accelerators.put(new KeyCodeCombination(KeyCode.LEFT), () -> {
            gameView.setMoveDirection(DirectionEnum.LEFT);
            gameView.start();
        });
        // 右
        accelerators.put(new KeyCodeCombination(KeyCode.RIGHT), () -> {
            gameView.setMoveDirection(DirectionEnum.RIGHT);
            gameView.start();
        });
    }


}
