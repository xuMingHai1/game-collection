package xyz.xuminghai.snake.view;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import xyz.xuminghai.snake.GameStage;
import xyz.xuminghai.snake.HistoryPopup;

/**
 * 2023/10/26 15:41 星期四<br/>
 * 开始页面
 *
 * @author xuMingHai
 */
public class StartView extends VBox {

    /**
     * 开始游戏按钮
     */
    private final Button startGameButton = new Button("开始游戏");
    /**
     * 历史记录按钮
     */
    private final Button historyButton = new Button("历史记录");

    private final HistoryPopup historyPopup = new HistoryPopup();

    /**
     * 开始游戏过渡动画
     */
    private FadeTransition fadeTransition;

    public StartView() {
        super.setId("start-view");
        super.getStylesheets().add("xyz/xuminghai/snake/view/start-view.css");
        super.getChildren().addAll(startGameButton, historyButton);
        bindEvent();
    }

    /**
     * 开始游戏的过渡动画，延迟初始化
     *
     * @return 淡入淡出动画
     */
    private FadeTransition getFadeTransition() {
        if (fadeTransition == null) {
            fadeTransition = new FadeTransition(Duration.millis(300.0), this);
            fadeTransition.setToValue(0.0);
            // 动画播放是异步调用的，播放结束时执行
            fadeTransition.setOnFinished(event -> {
                // 切换场景
                GameStage.start();
                // 恢复透明度
                super.setOpacity(1.0);
            });
        }

        return fadeTransition;
    }

    /**
     * 绑定事件
     */
    private void bindEvent() {
        // 开始游戏按钮事件
        startGameButton.setOnAction(event -> {
            System.out.println("开始游戏");
            getFadeTransition().play();
        });

        // 历史记录按钮事件
        historyButton.setOnAction(event -> {
            System.out.println("历史记录");
            historyPopup.show(super.getScene().getWindow());
        });
    }

}
