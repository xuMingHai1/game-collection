package xyz.xuminghai.tetris.game;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;
import xyz.xuminghai.tetris.core.Cell;
import xyz.xuminghai.tetris.core.Tetris;
import xyz.xuminghai.tetris.core.TetrisFactory;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 2024/2/1 21:30 星期四<br/>
 * 游戏规则和数据内容
 *
 * @author xuMingHai
 */
public class GameWorld {

    /**
     * 清除单元格
     */
    final ObjectProperty<List<Cell>> clearCell = new SimpleObjectProperty<>(this, "clearCell");
    /**
     * 渲染单元格
     */
    final ObjectProperty<List<Cell>> renderCell = new SimpleObjectProperty<>(this, "renderCell");
    final GameTimeLine gameTimeLine = new GameTimeLine(new Runnable() {

        @Override
        public void run() {
            // 初始化当前方块
            if (currentTetris.get() == null) {
                currentTetris.set(nextTetris.get());
            }
            final Tetris tetris = currentTetris.get();
            final Cell[] cells = checkCellsRowConvert(tetris.downMove());
            final Cell[] lastCells = currentCells.get();
            // 清除上次保存的数据
            Optional.ofNullable(lastCells)
                    .ifPresent(GameWorld.this::clearLastCells);
            if (saveCellsData(cells)) {
                currentCells.set(checkCellsRowConvert(tetris.copy()));
            }
            // 添加失败
            else {
                // 是否到顶判断
                if (lastCells == null || lastCells.length < 4) {
                    currentTetris.set(null);
                    currentCells.set(null);
                    // 游戏时间记录关闭
                    gameTimeLine.setGameTimeRecord(false);
                    // 游戏结束动画
                    gameTimeLine.setGameAnimation(new GameOverAnimation(GameWorld.this));
                }
                else {
                    // 保存上次方块数据
                    saveCellsData(lastCells);
                    // 下落失败，清除当前方块，生成新的方块
                    currentTetris.set(null);
                    currentCells.set(null);
                    // 判断是否可以消行
                    final List<Cell[]> removeRowList = getRemoveRow(lastCells);
                    final int size = removeRowList.size();
                    if (size != 0) {
                        gameTimeLine.setGameAnimation(new RemoveRowAnimation(GameWorld.this, removeRowList));
                        lines.set(lines.get() + size);
                        calculateScore(size);
                    }
                }
            }

        }

        private void calculateScore(int size) {
            int initScore = switch (size) {
                case 1 -> 10;
                case 2 -> 30;
                case 3 -> 60;
                case 4 -> 100;
                default -> throw new IllegalStateException("Unexpected value: " + size);
            };
            score.set(score.get() + Math.max(1, level.get()) * initScore);
        }


        private List<Cell[]> getRemoveRow(Cell[] cells) {
            // 获取可消除的行
            final Set<Integer> collect = Arrays.stream(cells).map(Cell::getRow).collect(Collectors.toSet());
            List<Cell[]> list = new LinkedList<>();
            for (Integer index : collect) {
                final Cell[] dataRow = data[index];
                // 判断是否是可消除的行
                boolean clearable = true;
                for (Cell c : dataRow) {
                    if (c == null) {
                        clearable = false;
                        break;
                    }
                }
                if (clearable) {
                    list.add(dataRow);
                }
            }
            return list;
        }

    });
    private final Robot robot = new Robot();
    private final int rows = 20, cols = 10;
    /**
     * 游戏数据
     */
    final Cell[][] data = new Cell[rows][cols];
    /**
     * 下一个方块
     */
    private final ObjectProperty<Tetris> nextTetris = new SimpleObjectProperty<>(this, "nextTetris", TetrisFactory.randomCreateTetris());
    /**
     * 当前单元格列表
     */
    private final ObjectProperty<Cell[]> currentCells = new SimpleObjectProperty<>(this, "currentCells");
    /**
     * 当前方块
     */
    private final ObjectProperty<Tetris> currentTetris = new SimpleObjectProperty<>(this, "currentTetris") {
        @Override
        protected void invalidated() {
            if (super.get() != null) {
                nextTetris.set(TetrisFactory.randomCreateTetris());
            }
        }
    };
    private final IntegerProperty score = new SimpleIntegerProperty(this, "score");
    /**
     * 游戏运行状态
     */
    boolean gameActive;

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * 消掉的行数
     */
    private final IntegerProperty lines = new SimpleIntegerProperty(this, "lines") {
        @Override
        protected void invalidated() {
            level.set(super.get() / 5);
        }
    };

    public ReadOnlyObjectProperty<Tetris> nextTetrisProperty() {
        return nextTetris;
    }

    public ReadOnlyObjectProperty<Cell[]> currentCellsProperty() {
        return currentCells;
    }

    public ReadOnlyIntegerProperty linesProperty() {
        return lines;
    }

    public ReadOnlyIntegerProperty scoreProperty() {
        return score;
    }

    private final IntegerProperty level = new SimpleIntegerProperty(this, "level") {

        @Override
        protected void invalidated() {
            if (get() < 30) {
                gameTimeLine.pulse = gameTimeLine.defaultPulse - super.get() * 10;
            }
        }
    };

    public ReadOnlyIntegerProperty levelProperty() {
        return level;
    }

    public ReadOnlyObjectProperty<Duration> gameTimeProperty() {
        return gameTimeLine.gameTimeProperty();
    }

    public ReadOnlyObjectProperty<List<Cell>> clearCellProperty() {
        return clearCell;
    }

    public ReadOnlyObjectProperty<List<Cell>> renderCellProperty() {
        return renderCell;
    }

    /**
     * 清除上一次单元格列表数据
     *
     * @param cells 单元格数组
     */
    private void clearLastCells(Cell[] cells) {
        for (Cell cell : cells) {
            data[cell.getRow()][cell.getCol()] = null;
        }
    }

    /**
     * 游戏结束，清除所有数据
     */
    void gameOver() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                final Cell cell = data[i][j];
                if (cell != null) {
                    data[i][j] = null;
                }
            }
        }
        startOrStopGame();
        // 清除消除行数
        lines.set(0);
        // 重置分数
        score.set(0);
        // 恢复游戏时间记录
        gameTimeLine.setGameTimeRecord(true);
        // 重置游戏时间
        gameTimeLine.gameTimeProperty().set(Duration.ZERO);
    }

    /*
     **********************游戏行为和规则************************
     */

    /**
     * 检查单元格行是否超出索引并转换
     *
     * @param cells 单元格数组
     * @return 原始单元格或转换后的
     */
    private Cell[] checkCellsRowConvert(Cell[] cells) {
        // 是否需要转换
        boolean convertable = false;
        for (Cell cell : cells) {
            if (cell.getRow() < 0) {
                // 未显示的方块，如果列不符合
                if (cell.getCol() < 0 || cell.getCol() >= getCols()) {
                    return new Cell[0];
                }
                convertable = true;
            }
        }
        // 需要转换
        if (convertable) {
            List<Cell> list = new LinkedList<>();
            for (Cell cell : cells) {
                if (cell.getRow() >= 0) {
                    list.add(cell);
                }
            }
            return list.toArray(new Cell[0]);
        }
        // 返回原始数组
        return cells;
    }

    /**
     * 规则检测并设置数据
     *
     * @param cells 单元格列表
     * @return 是否保存数据成功
     */
    private boolean saveCellsData(Cell[] cells) {
        if (cells.length == 0) {
            return false;
        }
        for (Cell cell : cells) {
            final int row = cell.getRow();
            final int col = cell.getCol();
            // 是否超出范围或被占用
            if (row >= rows || col < 0 || col >= cols || data[row][col] != null) {
                return false;
            }
        }

        // 保存数据
        for (Cell cell : cells) {
            final int row = cell.getRow();
            final int col = cell.getCol();
            data[row][col] = cell;
        }
        return true;
    }

    /**
     * 方块行为
     *
     * @param function 行为函数
     */
    private void tetrisAction(Function<Tetris, Cell[]> function) {
        // 游戏活跃时执行
        if (gameActive) {
            final Tetris tetris = currentTetris.get();
            if (tetris != null) {
                final Cell[] copyCells = tetris.copy();
                final Cell[] cells = checkCellsRowConvert(function.apply(tetris));
                // 清除上次保存的数据
                clearLastCells(currentCells.get());
                if (saveCellsData(cells)) {
                    currentCells.set(checkCellsRowConvert(tetris.copy()));
                }
                else {
                    tetris.setCells(copyCells);
                }
            }
        }
    }

    public void startOrStopGame() {
        final Optional<Boolean> keyLocked = Platform.isKeyLocked(KeyCode.CAPS);
        if (gameActive) {
            keyLocked.ifPresent(b -> {
                if (b) {
                    robot.keyType(KeyCode.CAPS);
                }
            });
            gameTimeLine.stop();
        }
        else {
            // 大写锁定处理
            keyLocked.ifPresent(b -> {
                if (!b) {
                    robot.keyType(KeyCode.CAPS);
                }
            });
            gameTimeLine.start();
        }
        gameActive = !gameActive;
    }

    /**
     * 向下移动
     */
    public void downMove() {
        tetrisAction(Tetris::downMove);
    }

    /**
     * 向左移动
     */
    public void leftMove() {
        tetrisAction(Tetris::leftMove);
    }

    /**
     * 向右移动
     */
    public void rightMove() {
        tetrisAction(Tetris::rightMove);
    }

    /**
     * 顺时针旋转
     */
    public void rotateClockwise() {
        tetrisAction(Tetris::rotateClockwise);
    }

    /**
     * 逆时针旋转
     */
    public void rotateCounterClockwise() {
        tetrisAction(Tetris::rotateCounterClockwise);
    }


}
