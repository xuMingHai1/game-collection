package xyz.xuminghai.tetris.view;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import xyz.xuminghai.tetris.game.GameWorld;

import java.time.Duration;

/**
 * 2024/1/15 20:55 星期一<br/>
 *
 * @author xuMingHai
 */
public class GameView extends BorderPane {

    private final GameWorld gameWorld;

    private final GameContextView gameContextView;

    private final NextBlockView nextBlockView;

    public GameView(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.gameContextView = new GameContextView(gameWorld);
        this.nextBlockView = new NextBlockView(gameWorld.nextTetrisProperty());
        super.setId("game-view");
        super.getStylesheets().add("xyz/xuminghai/tetris/view/game-view.css");
        initView();
    }

    private void initView() {
        // center
        final Text linesValueText = new Text();
        linesValueText.textProperty().bind(gameWorld.linesProperty().asString());
        final HBox linesHBox = new HBox(new Text("LINES-"), linesValueText);
        linesHBox.setId("lines-h-box");
        linesHBox.setMaxWidth(gameContextView.getWidth());

        final VBox centerVBox = new VBox(linesHBox, gameContextView);
        centerVBox.setId("center-v-box");
        super.setCenter(centerVBox);

        // right
        final VBox nextVBox = new VBox(new Text("NEXT"), nextBlockView);
        nextVBox.setId("next-v-box");
        nextVBox.setMaxWidth(nextBlockView.getWidth());

        final GridPane gridPane = new GridPane();
        gridPane.setId("text-grid-pane");
        final Text levelText = new Text();
        levelText.textProperty().bind(gameWorld.levelProperty().asString());
        final Text scoreText = new Text();
        scoreText.textProperty().bind(gameWorld.scoreProperty().asString());
        final Text gameTimeText = new Text();
        gameTimeText.textProperty().bind(gameTimeProperty());
        gridPane.addRow(0, new Text("Level："), levelText);
        gridPane.addRow(1, new Text("Score："), scoreText);
        gridPane.addRow(2, new Text("GameTime："), gameTimeText);
        gridPane.addRow(3, new Separator(), new Separator());
        gridPane.addRow(4, new Text("A"), new Text("左移"));
        gridPane.addRow(5, new Text("S"), new Text("下移"));
        gridPane.addRow(6, new Text("D"), new Text("右移"));
        gridPane.addRow(7, new Text("左方向键"), new Text("顺时针旋转"));
        gridPane.addRow(8, new Text("右方向键"), new Text("逆时针旋转"));

        final VBox rightVBox = new VBox(nextVBox, gridPane);
        BorderPane.setMargin(rightVBox, new Insets(10));
        rightVBox.setId("right-v-box");
        super.setRight(rightVBox);
    }

    /**
     * 游戏时间格式
     *
     * @return 游戏时间
     */
    private ObservableValue<String> gameTimeProperty() {
        return Bindings.createStringBinding(() -> {
            final Duration duration = gameWorld.gameTimeProperty().get();
            final StringBuilder stringBuilder = new StringBuilder("00:00:00".length());
            final long hours = duration.toHours();
            if (hours < 10) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hours).append(':');
            final int minutesPart = duration.toMinutesPart();
            if (minutesPart < 10) {
                stringBuilder.append(0);
            }
            stringBuilder.append(minutesPart).append(':');
            final int secondsPart = duration.toSecondsPart();
            if (secondsPart < 10) {
                stringBuilder.append(0);
            }
            stringBuilder.append(secondsPart);
            return stringBuilder.toString();
        }, gameWorld.gameTimeProperty());
    }


}