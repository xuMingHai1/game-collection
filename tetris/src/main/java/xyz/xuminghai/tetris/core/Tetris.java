package xyz.xuminghai.tetris.core;

/**
 * 2024/1/16 14:46 星期二<br/>
 *
 * @author xuMingHai
 */
public interface Tetris {

    /**
     * 返回单元格列表
     *
     * @return 单元格列表
     */
    Cell[] getCells();

    void setCells(Cell[] cells);

    /**
     * 复制一个全新的单元格列表
     *
     * @return 深拷贝
     */
    default Cell[] copy() {
        final Cell[] cells = getCells();
        Cell[] copyCells = new Cell[cells.length];
        for (int i = 0; i < cells.length; i++) {
            final Cell cell = cells[i];
            copyCells[i] = new Cell(cell.getRow(), cell.getCol(), cell.getPaint());
        }
        return copyCells;
    }

    /**
     * 向下移动
     *
     * @return 单元格列表
     */
    Cell[] downMove();

    /**
     * 向左移动
     *
     * @return 单元格列表
     */
    Cell[] leftMove();

    /**
     * 向右移动
     *
     * @return 单元格列表
     */
    Cell[] rightMove();

    /**
     * 顺时针旋转
     *
     * @return 单元格列表
     */
    Cell[] rotateClockwise();

    /**
     * 逆时针旋转
     *
     * @return 单元格列表
     */
    Cell[] rotateCounterClockwise();

}
