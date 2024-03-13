package xyz.xuminghai.tetris.game;

import javafx.scene.paint.Color;
import xyz.xuminghai.tetris.core.Cell;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 2024/3/2 17:28 星期六<br/>
 * 游戏结束动画
 *
 * @author xuMingHai
 */
public class GameOverAnimation implements GameAnimation {

    private final Color gameOverColor = Color.LIGHTGRAY;

    private final GameWorld gameWorld;

    private final GameTimeLine gameTimeLine;
    private final int colIndex;
    private final int avgRow;
    private long lastHandleTime;
    private Cell headCell, tailCell;

    public GameOverAnimation(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.gameTimeLine = gameWorld.gameTimeLine;
        int rowIndex = gameWorld.getRows() - 1;
        this.colIndex = gameWorld.getCols() - 1;
        this.avgRow = gameWorld.getRows() / 2;
        this.headCell = new Cell(0, -1, gameOverColor);
        this.tailCell = new Cell(rowIndex, gameWorld.getCols(), gameOverColor);
    }

    @Override
    public void handle(long now) {
        if (TimeUnit.NANOSECONDS.toMillis(now - lastHandleTime) >= 20L) {
            lastHandleTime = now;
            int headRowIndex = headCell.getRow(),
                    headColIndex = headCell.getCol(),
                    tailRowIndex = tailCell.getRow(),
                    tailColIndex = tailCell.getCol();
            // 到达最大列
            if (headColIndex == colIndex) {
                headRowIndex++;
                headColIndex = 0;
                tailRowIndex--;
                tailColIndex = colIndex;
            }
            else {
                headColIndex++;
                tailColIndex--;
            }
            // 到达平均行
            if (headRowIndex == avgRow) {
                List<Cell> list = new ArrayList<>(gameWorld.getRows() * gameWorld.getCols());
                for (int i = 0; i < gameWorld.getRows(); i++) {
                    for (int j = 0; j < gameWorld.getCols(); j++) {
                        list.add(new Cell(i, j, gameOverColor));
                    }
                }
                // 清除方块
                gameWorld.clearCell.set(list);
                // 消除引用
                gameWorld.renderCell.set(null);
                gameWorld.clearCell.set(null);
                // 切换动画
                gameTimeLine.setGameAnimation(null);
                gameOver();
                return;
            }
            // 渲染方块
            headCell = new Cell(headRowIndex, headColIndex, gameOverColor);
            tailCell = new Cell(tailRowIndex, tailColIndex, gameOverColor);
            gameWorld.renderCell.set(List.of(headCell, tailCell));
        }
    }

    /**
     * 游戏结束，重置所有数据
     */
    private void gameOver() {
        for (int i = 0; i < gameWorld.getRows(); i++) {
            for (int j = 0; j < gameWorld.getCols(); j++) {
                final Cell cell = gameWorld.data[i][j];
                if (cell != null) {
                    gameWorld.data[i][j] = null;
                }
            }
        }
        gameWorld.startOrPauseGame();
        // 清除消除行数
        gameWorld.lines.set(0);
        // 重置等级
        gameWorld.level.set(0);
        gameWorld.customizeLevel = 0;
        // 重置分数
        gameWorld.score.set(0);
        // 恢复游戏时间记录
        gameTimeLine.setGameTimeRecord(true);
        // 重置游戏时间
        gameTimeLine.gameTimeProperty().set(Duration.ZERO);
    }

}
