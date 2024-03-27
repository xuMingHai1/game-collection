package xyz.xuminghai.tetris.game;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 2024/2/1 21:29 星期四<br/>
 * 游戏时间线
 *
 * @author xuMingHai
 */
public class GameTimeLine extends AnimationTimer {

    static final int DEFAULT_PULSE = 500;
    private final Runnable runnable;
    private final ObjectProperty<Duration> gameTime = new SimpleObjectProperty<>(this, "gameTime", Duration.ZERO);
    private int pulse = DEFAULT_PULSE;
    private long lastHandleTime, lastHandleGameTime;

    /**
     * 游戏时间记录
     */
    private boolean gameTimeRecord = true;

    private GameTimer gameAnimation, keyCompensateTimer;


    GameTimeLine(Runnable runnable) {
        this.runnable = runnable;
    }

    void setPulse(int pulse) {
        this.pulse = pulse;
    }

    void setGameAnimation(GameTimer gameAnimation) {
        this.gameAnimation = gameAnimation;
    }

    void setKeyCompensateTimer(GameTimer keyCompensateTimer) {
        this.keyCompensateTimer = keyCompensateTimer;
    }

    void setGameTimeRecord(boolean gameTimeRecord) {
        this.gameTimeRecord = gameTimeRecord;
    }

    ObjectProperty<Duration> gameTimeProperty() {
        return gameTime;
    }

    @Override
    public void handle(long now) {
        // 游戏时间记录
        if (gameTimeRecord && TimeUnit.NANOSECONDS.toSeconds(now - lastHandleGameTime) >= 1L) {
            lastHandleGameTime = now;
            final Duration duration = gameTime.get();
            gameTime.set(duration.plusSeconds(1L));
        }
        if (keyCompensateTimer != null) {
            keyCompensateTimer.handle(now);
        }
        if (gameAnimation != null) {
            gameAnimation.handle(now);
        }
        else {
            final long handleMillis = TimeUnit.NANOSECONDS.toMillis(now - lastHandleTime);
            if (handleMillis >= pulse) {
                lastHandleTime = now;
                runnable.run();
            }
        }
    }


}
