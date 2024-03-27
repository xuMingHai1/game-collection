package xyz.xuminghai.tetris.game;

import javafx.scene.input.KeyCode;

import java.util.concurrent.TimeUnit;

/**
 * 2024/3/26 23:09 星期二<br/>
 * 游戏按键处理
 *
 * @author xuMingHai
 */
public class GameKeyCodeAction implements GameTimer {

    private boolean a, s, d;

    private final GameWorld gameWorld;

    private long lastHandleTime;

    private Runnable keyCompensateRunnable;

    public GameKeyCodeAction(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void keyCodePressed(KeyCode keyCode) {
        switch (keyCode) {
            // A 键 + 组合
            case A -> {
                if (a) {
                    // 进入Javafx 按键机制，取消按键补偿
                    closeKeyCompensateRunnable();
                    aKeyAction();
                }
                else {
                    a = true;
                    keyCoKeyCompensateRunning(gameWorld::leftMove);
                }
            }
            // S 键 + 组合
            case S -> {
                if (s) {
                    closeKeyCompensateRunnable();
                    sKeyAction();
                }
                else {
                    s = true;
                    keyCoKeyCompensateRunning(gameWorld::downMove);
                }
            }
            // D 键 + 组合
            case D -> {
                if (d) {
                    closeKeyCompensateRunnable();
                    dKeyAction();
                }
                else {
                    d = true;
                    keyCoKeyCompensateRunning(gameWorld::rightMove);
                }
            }
            // 其他按键处理（补偿机制）
            default -> {
                if (a) {
                    closeKeyCompensateRunnable();
                    aKeyAction();
                }
                else if (s) {
                    closeKeyCompensateRunnable();
                    sKeyAction();
                }
                else if (d) {
                    closeKeyCompensateRunnable();
                    dKeyAction();
                }
            }
        }
    }

    public void keyCodeReleased(KeyCode keyCode) {
        switch (keyCode) {
            case A -> a = false;
            case S -> s = false;
            case D -> d = false;
        }
        // 按键释放
        // 设置按键补偿机制（多种组合，释放一个就不会发生按键事件）
        if (a) {
            applyKeyCompensateRunnable(this::aKeyAction);
        }
        else if (s) {
            applyKeyCompensateRunnable(this::sKeyAction);
        }
        else if (d) {
            applyKeyCompensateRunnable(this::dKeyAction);
        }
        else {
            closeKeyCompensateRunnable();
        }
    }

    /**
     * 按键取消补偿没有运行
     *
     * @param runnable 动作
     */
    private void keyCoKeyCompensateRunning(Runnable runnable) {
        if (keyCompensateRunnable == null) {
            runnable.run();
        }
    }

    private void applyKeyCompensateRunnable(Runnable runnable) {
        lastHandleTime = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(17L);
        keyCompensateRunnable = runnable;
        gameWorld.gameTimeLine.setKeyCompensateTimer(this);
    }

    private void closeKeyCompensateRunnable() {
        keyCompensateRunnable = null;
        gameWorld.gameTimeLine.setKeyCompensateTimer(null);
    }

    @Override
    public void handle(long now) {
        if (TimeUnit.NANOSECONDS.toMillis(now - lastHandleTime) >= 33L) {
            keyCompensateRunnable.run();
            lastHandleTime = now;
        }
    }

    private void aKeyAction() {
        // A + S
        if (s) {
            gameWorld.leftMove();
            gameWorld.downMove();
        }
        // A
        else {
            gameWorld.leftMove();
        }
    }

    private void sKeyAction() {
        // S + A
        if (a) {
            gameWorld.downMove();
            gameWorld.leftMove();
        }
        // S + D
        else if (d) {
            gameWorld.downMove();
            gameWorld.rightMove();
        }
        // S
        else {
            gameWorld.downMove();
        }
    }

    private void dKeyAction() {
        // D + S
        if (s) {
            gameWorld.rightMove();
            gameWorld.downMove();
        }
        // D
        else {
            gameWorld.rightMove();
        }
    }

}
