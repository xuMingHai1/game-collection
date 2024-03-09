package xyz.xuminghai.tetris.game;

import xyz.xuminghai.tetris.core.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 2024/3/1 15:27 星期五<br/>
 * 消行动画
 *
 * @author xuMingHai
 */
public class RemoveRowAnimation implements GameAnimation {

    private final GameWorld gameWorld;

    private final GameTimeLine gameTimeLine;

    private final List<Cell[]> removeRowList;

    private final Cell[][] data;

    private long lastHandleTime;

    private int headIndex = 4, tailIndex = 5;

    RemoveRowAnimation(GameWorld gameWorld, List<Cell[]> removeRowList) {
        this.gameWorld = gameWorld;
        this.gameTimeLine = gameWorld.gameTimeLine;
        this.removeRowList = removeRowList;
        this.data = gameWorld.data;
    }


    private List<Cell> downUpperCell(int maxRemoveRow) {
        List<Cell> list = new LinkedList<>();
        for (int i = maxRemoveRow - 1; i >= 0; i--) {
            final int length = data[i].length;
            for (int j = 0; j < length; j++) {
                final Cell cell = data[i][j];
                if (cell != null) {
                    list.add(cell);
                    // 清除数据
                    data[i][j] = null;
                }
            }
        }
        return list;
    }

    private void moveUpperRow(List<Integer> rowIndexList) {
        // 自然顺序排序
        rowIndexList.sort(null);
        // 根据最大的删除行获取上面的方块，并清除数据
        final List<Cell> list = downUpperCell(rowIndexList.getLast());
        // 消除上方方块
        gameWorld.clearCell.set(list);
        List<Cell> newList = new ArrayList<>(list.size());
        // 下落上方方块，并设置数据
        for (Cell cell : list) {
            // 计算出下落行数
            int row = rowIndexList.size();
            for (Integer rowIndex : rowIndexList) {
                if (rowIndex > cell.getRow()) {
                    break;
                }
                row--;
            }
            // 设置新行数
            final Cell newCell = new Cell(cell.getRow() + row, cell.getCol(), cell.getPaint());
            // 保存数据
            data[newCell.getRow()][newCell.getCol()] = newCell;
            newList.add(newCell);
        }
        // 渲染新的位置
        gameWorld.renderCell.set(newList);
    }

    @Override
    public void handle(long now) {
        if (TimeUnit.NANOSECONDS.toMillis(now - lastHandleTime) >= 100L) {
            lastHandleTime = now;
            if (headIndex < 0) {
                List<Integer> rowIndexList = new ArrayList<>(removeRowList.size());
                // 清除删除行的数据
                for (Cell[] cells : removeRowList) {
                    final int row = cells[0].getRow();
                    rowIndexList.add(row);
                    Arrays.fill(data[row], null);
                }
                // 移动上方行
                moveUpperRow(rowIndexList);
                // 消除引用
                gameWorld.clearCell.set(null);
                gameWorld.renderCell.set(null);
                // 切换游戏动画
                gameTimeLine.setGameAnimation(null);
            }
            else {
                List<Cell> list = new ArrayList<>(removeRowList.size() * 2);
                for (Cell[] cells : removeRowList) {
                    list.add(cells[headIndex]);
                    list.add(cells[tailIndex]);
                }
                headIndex--;
                tailIndex++;
                gameWorld.clearCell.set(list);
            }
        }
    }
}
