package xyz.xuminghai.tetris.view;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import xyz.xuminghai.tetris.game.GameWorld;

import java.text.NumberFormat;

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
        scoreText.textProperty().bind(gameWorld.scoreProperty()
                .map(number -> NumberFormat.getIntegerInstance().format(number)));
        final Text gameTimeText = new Text();
        gameTimeText.textProperty().bind(gameTimeProperty());
        gridPane.addRow(0, new Text("Level："), levelText);
        gridPane.addRow(1, new Text("Score："), scoreText);
        gridPane.addRow(2, new Text("GameTime："), gameTimeText);
        final Separator separator = new Separator();
        gridPane.addRow(3, separator);
        GridPane.setColumnSpan(separator, 2);
        final Text instructionText = new Text("操作说明");
        gridPane.addRow(4, instructionText);
        GridPane.setColumnSpan(instructionText, 2);
        GridPane.setHalignment(instructionText, HPos.CENTER);
        gridPane.addRow(5, new Text("A键"), new Text("左移"));
        gridPane.addRow(6, new Text("S键"), new Text("下移"));
        gridPane.addRow(7, new Text("D键"), new Text("右移"));
        gridPane.addRow(8, new Text("左方向键"), new Text("顺时针旋转"));
        gridPane.addRow(9, new Text("右方向键"), new Text("逆时针旋转"));
        gridPane.addRow(10, new Text("空格键"), new Text("开始或暂停"));
        gridPane.addRow(11, new Text("+键"), new Text("等级加1"));
        gridPane.addRow(12, new Text("-键"), new Text("等级减1"));

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
        return gameWorld.gameTimeProperty().map(duration -> {
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
        });
    }


}
