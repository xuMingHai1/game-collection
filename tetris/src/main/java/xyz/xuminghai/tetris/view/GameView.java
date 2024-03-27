package xyz.xuminghai.tetris.view;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import xyz.xuminghai.tetris.TetrisApplication;
import xyz.xuminghai.tetris.game.GameWorld;
import xyz.xuminghai.tetris.util.Version;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

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
        super.getStylesheets().add("css/game-view.css");
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

        // 版本号
        final Hyperlink hyperlink = new Hyperlink(Version.VERSION);
        hyperlink.setId("version-hyperlink");
        hyperlink.setFocusTraversable(false);
        hyperlink.setOnAction(event -> TetrisApplication.hostServices.showDocument(Version.RELEASE_URI));
        final AnchorPane anchorPane = new AnchorPane(hyperlink);
        AnchorPane.setBottomAnchor(hyperlink, 0.0);
        AnchorPane.setRightAnchor(hyperlink, 0.0);

        final Region gameDataPane = gameDataPane();
        final Region descriptionPane = descriptionPane();
        final VBox rightVBox = new VBox(new Pane(nextVBox), gameDataPane, new Separator(),
                descriptionPane, anchorPane);
        rightVBox.setId("right-v-box");
        VBox.setMargin(gameDataPane, new Insets(0, 10, 0, 10));
        VBox.setMargin(descriptionPane, new Insets(0, 10, 0, 10));
        // 设置水平增长
        VBox.setVgrow(anchorPane, Priority.SOMETIMES);
        BorderPane.setMargin(rightVBox, new Insets(10));

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

    private Region gameDataPane() {
        final GridPane gameDataPane = new GridPane();
        gameDataPane.setId("game-data-pane");

        // 游戏等级
        final Text levelText = new Text("Level：");
        final Text levelValueText = new Text();
        levelValueText.textProperty().bind(gameWorld.levelProperty().asString());
        gameDataPane.addRow(0, levelText, levelValueText);
        GridPane.setValignment(levelText, VPos.TOP);
        GridPane.setHalignment(levelValueText, HPos.RIGHT);

        // 游戏分数
        final Text scoreText = new Text("Score：");
        final Text scoreValueText = new Text();
        scoreValueText.textProperty().bind(gameWorld.scoreProperty()
                .map(number -> NumberFormat.getIntegerInstance().format(number)));
        gameDataPane.addRow(1, scoreText, scoreValueText);
        GridPane.setValignment(scoreText, VPos.TOP);
        GridPane.setHalignment(scoreValueText, HPos.RIGHT);

        // 游戏时间
        final Text gameTimeText = new Text("GameTime：");
        final Text gameTimeValueText = new Text();
        gameTimeValueText.textProperty().bind(gameTimeProperty());
        gameDataPane.addRow(2, gameTimeText, gameTimeValueText);
        GridPane.setValignment(gameTimeText, VPos.TOP);
        GridPane.setHalignment(gameTimeValueText, HPos.RIGHT);

        return gameDataPane;
    }

    private final Label
            instructionLabel = new Label(),
            aKeyLabel = new Label(), aKeyActionLabel = new Label(),
            sKeyLabel = new Label(), sKeyActionLabel = new Label(),
            dKeyLabel = new Label(), dKeyActionLabel = new Label(),
            leftArrowKeyLabel = new Label(), leftArrowKeyActionLabel = new Label(),
            rightArrowKeyLabel = new Label(), rightArrowKeyActionLabel = new Label(),
            plusKeyLabel = new Label(), plusKeyActionLabel = new Label(),
            minusKeyLabel = new Label(), minusKeyActionLabel = new Label(),
            spaceKeyLabel = new Label(), spaceKeyActionLabel = new Label();


    private void setLocalText(Locale locale) {
        // 设置本地化语言
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/text", locale);

        // 操作语言
        instructionLabel.setText(resourceBundle.getString("instruction"));

        // A键语言
        aKeyLabel.setText(resourceBundle.getString("aKey"));
        aKeyActionLabel.setText(resourceBundle.getString("aKeyAction"));

        // S键语言
        sKeyLabel.setText(resourceBundle.getString("sKey"));
        sKeyActionLabel.setText(resourceBundle.getString("sKeyAction"));

        // D键语言
        dKeyLabel.setText(resourceBundle.getString("dKey"));
        dKeyActionLabel.setText(resourceBundle.getString("dKeyAction"));

        // 左箭头键语言
        leftArrowKeyLabel.setText(resourceBundle.getString("leftArrowKey"));
        leftArrowKeyActionLabel.setText(resourceBundle.getString("leftArrowKeyAction"));

        // 右箭头键语言
        rightArrowKeyLabel.setText(resourceBundle.getString("rightArrowKey"));
        rightArrowKeyActionLabel.setText(resourceBundle.getString("rightArrowKeyAction"));

        // +键语言
        plusKeyLabel.setText(resourceBundle.getString("plusKey"));
        plusKeyActionLabel.setText(resourceBundle.getString("plusKeyAction"));

        // -键语言
        minusKeyLabel.setText(resourceBundle.getString("minusKey"));
        minusKeyActionLabel.setText(resourceBundle.getString("minusKeyAction"));

        // 空格键语言
        spaceKeyLabel.setText(resourceBundle.getString("spaceKey"));
        spaceKeyActionLabel.setText(resourceBundle.getString("spaceKeyAction"));
    }


    private Region descriptionPane() {
        final GridPane descriptionPane = new GridPane();
        descriptionPane.setId("description-pane");
        setLocalText(gameWorld.languageProperty().get());
        // 语言切换监听
        gameWorld.languageProperty().addListener((observable, oldValue, newValue) -> setLocalText(newValue));

        // 操作说明
        descriptionPane.addRow(0, instructionLabel);
        GridPane.setColumnSpan(instructionLabel, 2);
        GridPane.setHalignment(instructionLabel, HPos.CENTER);
        GridPane.setMargin(instructionLabel, new Insets(0, 0, 10, 0));

        // A键说明
        descriptionPane.addRow(1, aKeyLabel, aKeyActionLabel);

        // S键说明
        descriptionPane.addRow(2, sKeyLabel, sKeyActionLabel);

        // D键说明
        descriptionPane.addRow(3, dKeyLabel, dKeyActionLabel);

        // 左箭头键说明
        descriptionPane.addRow(4, leftArrowKeyLabel, leftArrowKeyActionLabel);

        // 右箭头键说明
        descriptionPane.addRow(5, rightArrowKeyLabel, rightArrowKeyActionLabel);

        // +键说明
        descriptionPane.addRow(6, plusKeyLabel, plusKeyActionLabel);

        // -键说明
        descriptionPane.addRow(7, minusKeyLabel, minusKeyActionLabel);

        // 空格键说明
        descriptionPane.addRow(8, spaceKeyLabel, spaceKeyActionLabel);

        // Control + Tab键说明
        descriptionPane.addRow(9, new Label("Ctrl + Tab"), new Label("中文/English"));

        // 添加提示
        descriptionPane.getChildren().forEach(node -> {
            final Label label = (Label) node;
            final Tooltip tooltip = new Tooltip();
            tooltip.textProperty().bind(label.textProperty());
            label.setTooltip(tooltip);
        });

        return descriptionPane;
    }


}
