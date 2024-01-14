package xyz.xuminghai.snake.view;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import xyz.xuminghai.snake.ControlPopup;
import xyz.xuminghai.snake.core.DirectionEnum;
import xyz.xuminghai.snake.core.GameContext;
import xyz.xuminghai.snake.core.Snake;

import java.util.concurrent.TimeUnit;

/**
 * 2023/10/26 15:45 星期四<br/>
 * 游戏界面
 *
 * @author xuMingHai
 */
public class GameView extends Group {

    /**
     * 可以修改参数，改变大小
     */
    private final Snake snake = new Snake(new GameContext(10, 10, 20, 4));

    private final ControlPopup controlPopup;

    /**
     * 定时器
     */
    private final AnimationTimer animationTimer = new AnimationTimer() {

        /**
         * 上次移动时的时间戳
         */
        private long lastMoveTime;

        @Override
        public void handle(long now) {
            long duration = TimeUnit.NANOSECONDS.toMillis(now - lastMoveTime);
            // 距离上次运行所过去的时间
            if (duration >= (1000L / controlPopup.speedProperty().get())) {
                switch (snake.move()) {
                    case STOP -> GameView.this.stop();
                    case GAME_OVER -> {
                        GameView.this.gameOver();
                        final Parent gameOverParent = createGameOverParent();
                        gameOverParent.setOpacity(0.0);
                        GameView.this.getScene().setRoot(gameOverParent);
                        final FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500.0), gameOverParent);
                        fadeTransition.setToValue(1.0);
                        fadeTransition.play();
                    }
                    case null -> {
                    }
                }
                lastMoveTime = now;
            }
        }
    };

    public GameView(ControlPopup controlPopup) {
        this.controlPopup = controlPopup;
        super.getChildren().add(snake.getGameContext());
    }

    private boolean active;

    private boolean gameOver;

    public void startOrStop() {
        if (!gameOver) {
            if (active) {
                animationTimer.stop();
                active = false;
            }
            else {
                animationTimer.start();
                active = true;
            }
        }
    }

    public void start() {
        if (!gameOver && !active) {
            animationTimer.start();
            active = true;
        }
    }

    public void stop() {
        if (active) {
            animationTimer.stop();
            active = false;
        }
    }

    private void gameOver() {
        gameOver = true;
        stop();
    }

    private Parent createGameOverParent() {
        // 创建线性渐变
        LinearGradient linearGradient = new LinearGradient(
                0, 0,
                1, 0,
                true,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#4facfe")),
                new Stop(1, Color.web("#00f2fe"))
        );
        final Text gameOverText = new Text("Game Over");
        final GameContext gameContext = snake.getGameContext();
        final double fontSize = Math.cbrt(gameContext.getWidth() * gameContext.getHeight());
        gameOverText.setFont(Font.font(fontSize));
        gameOverText.setFill(linearGradient);
        gameOverText.setEffect(new Glow());
        return new StackPane(gameOverText);
    }

    public int getSnakeLength() {
        return snake.getSnakeLength();
    }

    public void setMoveDirection(DirectionEnum directionEnum) {
        snake.setDirectionEnum(directionEnum);
    }

}
