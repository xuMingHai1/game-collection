package xyz.xuminghai.tetris.game;

import xyz.xuminghai.tetris.core.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 2024/3/11 0:08 星期一<br/>
 * 方块锁定动画
 *
 * @author xuMingHai
 */
public class TetrisLockAnimation implements GameAnimation {

    private final GameWorld gameWorld;

    private final List<Cell> cellList;

    private long lastHandleTime;

    public TetrisLockAnimation(GameWorld gameWorld, Cell[] cells) {
        this.gameWorld = gameWorld;
        this.cellList = List.of(cells);
    }

    @Override
    public void handle(long now) {
        if (TimeUnit.NANOSECONDS.toMillis(now - lastHandleTime) >= 35L) {
            if (lastHandleTime == 0) {
                List<Cell> list = new ArrayList<>(cellList.size());
                for (Cell cell : cellList) {
                    list.add(new Cell(cell.getRow(), cell.getCol(),
                            // 这个颜色的较暗版
                            cell.getColor().deriveColor(0, 1.0, 0.22, 1.0)));
                }
                gameWorld.renderCell.set(list);
            }
            else {
                gameWorld.renderCell.set(cellList);
                gameWorld.gameTimeLine.setGameAnimation(null);
            }
            lastHandleTime = now;
            // 消除引用
            gameWorld.renderCell.set(null);
        }
    }
}
