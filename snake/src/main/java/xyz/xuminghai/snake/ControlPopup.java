package xyz.xuminghai.snake;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

/**
 * 2023/11/3 23:20 星期五<br/>
 * 游戏控制面板
 *
 * @author xuMingHai
 */
public class ControlPopup extends Popup {

    private final GridPane gridPane = new GridPane(10.0, 30.0);

    /**
     * 蛇移动速度滑块
     */
    private final Slider speedSlider = new Slider(1.0, 10.0, 5.0);

    private final Button mainButton = new Button("主界面");


    public ControlPopup() {
        // 失去焦点，自动隐藏
        super.setAutoHide(true);
        initLayout();
        initEvent();
        super.getContent().add(gridPane);
        // 弹窗不随父窗口光标
        super.getScene().setCursor(Cursor.DEFAULT);
    }

    private void initLayout() {
        gridPane.setPrefSize(200.0, 100.0);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        gridPane.addRow(0, new Label("速度"), speedSlider);
        gridPane.addRow(1, mainButton);
        GridPane.setColumnSpan(mainButton, 2);
        GridPane.setHalignment(mainButton, HPos.CENTER);
    }

    private void initEvent() {
        mainButton.setOnAction(event -> {
            super.getOwnerWindow().hide();
            Main.getPrimaryStage().show();
        });
    }

    public DoubleProperty speedProperty() {
        return speedSlider.valueProperty();
    }

}
