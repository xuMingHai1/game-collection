package xyz.xuminghai.snake.core;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 2023/11/1 13:34 星期三<br/>
 * 贪吃蛇数据
 *
 * @author xuMingHai
 */
public class Snake {

    /**
     * 食物单元格填充
     */
    private static final Paint FOOD_CELL_PAINT = Color.RED, HEAD_CELL_PAINT = Color.BROWN, BODY_CELL_PAINT = Color.GREEN;

    private final GameContext gameContext;

    /**
     * 蛇单元格
     */
    private final LinkedList<Cell> cellLinkedList = new LinkedList<>();

    /**
     * 食物单元格
     */
    private Cell foodCell;

    /**
     * 移动方向
     */
    private DirectionEnum directionEnum = DirectionEnum.LEFT;


    public Snake(GameContext gameContext) {
        this.gameContext = gameContext;
        init();
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public int getSnakeLength() {
        return cellLinkedList.size();
    }

    private void init() {
        // 开始行
        int beginRow = gameContext.getRows() / 2;
        // 开始列
        int beginCol = gameContext.getCols() / 2 - 3;
        // 设置食物
        foodCell = createFillCell(beginRow, beginCol, FOOD_CELL_PAINT);
        // 设置蛇头
        beginCol += 2;
        cellLinkedList.addFirst(createFillCell(beginRow, beginCol, HEAD_CELL_PAINT));
        // 添加蛇身
        for (int i = 0; i < 3; i++) {
            cellLinkedList.addLast(createFillCell(beginRow, ++beginCol, BODY_CELL_PAINT));
        }
    }

    private Cell createFillCell(int row, int col, Paint paint) {
        Cell cell = new Cell(row, col);
        gameContext.fillCell(cell.row(), cell.col(), paint);
        return cell;
    }


    private DirectionEnum getDirectionEnum() {
        return directionEnum;
    }

    /**
     * 设置蛇移动方向，相反方向不会设置
     *
     * @param directionEnum 方向
     */
    public void setDirectionEnum(DirectionEnum directionEnum) {
        Objects.requireNonNull(directionEnum, "方向不能为null");
        // 相反方向处理
        if (!directionEnum.equals(this.directionEnum)) {
            switch (directionEnum) {
                case DirectionEnum.UP -> {
                    if (DirectionEnum.DOWN.equals(this.directionEnum)) {
                        return;
                    }
                }
                case DirectionEnum.DOWN -> {
                    if (DirectionEnum.UP.equals(this.directionEnum)) {
                        return;
                    }
                }
                case DirectionEnum.LEFT -> {
                    if (DirectionEnum.RIGHT.equals(this.directionEnum)) {
                        return;
                    }
                }
                case DirectionEnum.RIGHT -> {
                    if (DirectionEnum.LEFT.equals(this.directionEnum)) {
                        return;
                    }
                }
                default -> throw new IllegalStateException("未知的方向枚举");
            }
            // 赋值可接受的值
            this.directionEnum = directionEnum;
        }
    }

    /**
     * 生成食物（没有食物时才会生成）
     */
    private Cell createRandomFood() {
        List<Cell> list = new ArrayList<>(gameContext.getRows() * gameContext.getCols());
        // 根据行和列获取所有单元格
        for (int i = 0; i < gameContext.getRows(); i++) {
            for (int j = 0; j < gameContext.getCols(); j++) {
                list.add(new Cell(i, j));
            }
        }
        // 删除蛇占用的单元格
        list.removeAll(cellLinkedList);
        final int size = list.size();
        if (size > 0) {
            // 抽取随机空白单元格
            int randomIndex = (int) (Math.random() * size);
            Cell cell = list.get(randomIndex);
            gameContext.fillCell(cell.row(), cell.col(), FOOD_CELL_PAINT);
            return cell;
        }
        // 游戏结束
        return null;
    }

    /**
     * 根据方向移动一个单元格
     */
    public GameStateEnum move() {
        Cell first = cellLinkedList.getFirst();
        // 新的蛇头位置
        int row = first.row(), col = first.col();

        // 计算移动位置
        switch (getDirectionEnum()) {
            // 行减1
            case UP -> row--;
            // 行加1
            case DOWN -> row++;
            // 列减1
            case LEFT -> col--;
            // 列加1
            case RIGHT -> col++;
            default -> throw new IllegalStateException("未知的方向枚举");
        }

        // 碰撞检测，碰到边界或自身抛出异常
        if (collisionCheck(row, col)) {
            return GameStateEnum.STOP;
        }

        // 设置新的蛇头
        cellLinkedList.addFirst(createFillCell(row, col, HEAD_CELL_PAINT));
        // 旧的蛇头变为蛇身
        gameContext.fillCell(first.row(), first.col(), BODY_CELL_PAINT);
        // 是否吃到食物
        if (eatFood(row, col)) {
            foodCell = createRandomFood();
            if (foodCell == null) {
                return GameStateEnum.GAME_OVER;
            }
        }
        else {
            Cell last = cellLinkedList.getLast();
            // 旧的蛇尾变为空白
            gameContext.fillCell(last.row(), last.col());
            // 删除旧的蛇尾
            cellLinkedList.removeLast();
        }

        return null;
    }

    /**
     * 是否吃到食物
     *
     * @param row 行
     * @param col 列
     * @return 吃到true
     */
    private boolean eatFood(int row, int col) {
        return row == foodCell.row() && col == foodCell.col();
    }


    private boolean collisionCheck(int row, int col) {
        // 边界检查
        try {
            Objects.checkIndex(row, gameContext.getRows());
            Objects.checkIndex(col, gameContext.getCols());
        }
        catch (IndexOutOfBoundsException exception) {
            return true;
        }
        // 蛇身检查
        for (Cell cell : cellLinkedList) {
            if (row == cell.row() && col == cell.col()) {
                return true;
            }
        }

        return false;
    }


    record Cell(int row, int col) {

    }


}
