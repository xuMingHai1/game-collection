package xyz.xuminghai.tetris.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * 2024/1/29 12:45 星期一<br/>
 *
 * @author xuMingHai
 */
public sealed abstract class AbstractBlockView extends Canvas
        permits NextBlockView, GameContextView {

    protected final GraphicsContext graphicsContext;
    /**
     * 行数和列数
     */
    private final int rows, cols;
    /**
     * 正方形边长和分割线宽度
     */
    private final double side, border;

    public AbstractBlockView(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.side = 30.0;
        this.border = 3.0;
        this.graphicsContext = super.getGraphicsContext2D();
        // 列是x轴，行是y轴
        super.setWidth(cols * side + cols * border + border);
        super.setHeight(rows * side + rows * border + border);
        graphicsContext.fillRect(0.0, 0.0,
                super.getHeight(), super.getHeight());
    }

    /**
     * 获取x坐标位置
     *
     * @param col 第几列，从0开始
     * @return x坐标位置
     */
    private double computeX(int col) {
        return side * col + col * border + border;
    }

    /**
     * 获取x坐标位置
     *
     * @param row 第几行，从0开始
     * @return y坐标位置
     */
    private double computeY(int row) {
        return side * row + row * border + border;
    }

    /**
     * 填充指定的单元格
     *
     * @param row   行
     * @param col   列
     * @param paint 填充
     */
    public final void fillCell(int row, int col, Paint paint) {
        if ((row < 0 || row >= getRows()) || (col < 0 || col >= getCols())) {
            throw new IllegalArgumentException(String.format("""
                    单元格位置不正确
                    （行列坐标，从0开始）
                    row = %d, col = %d
                    （行列数量）
                    rows = %d, cols = %d
                    """, row, col, getRows(), getCols()));
        }
        double x = computeX(col), y = computeY(row);
        graphicsContext.setFill(paint);
        graphicsContext.fillRect(x, y, side, side);
    }


    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

}
