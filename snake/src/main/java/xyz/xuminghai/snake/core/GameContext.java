package xyz.xuminghai.snake.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * 2023/10/31 14:21 星期二<br/>
 * 游戏构建
 *
 * @author xuMingHai
 */
public class GameContext extends Canvas {

    /**
     * 默认单元格填充
     */
    public static final Paint DEFAULT_CELL_PANEL = Color.WHITE;

    /**
     * 行数和列数
     */
    private final int rows, cols;

    /**
     * 正方形边长和分割线宽度
     */
    private final double side, border;

    private final GraphicsContext graphicsContext;

    public GameContext(int rows, int cols, double side, double border) {
        super();
        this.rows = rows;
        this.cols = cols;
        this.side = side;
        this.border = border;
        this.graphicsContext = super.getGraphicsContext2D();
        // 列是x轴，行是y轴
        super.setWidth(cols * side + cols * border + border);
        super.setHeight(rows * side + rows * border + border);
        initMap();
    }


    private void initMap() {
        double x = border, y = border;
        graphicsContext.fillRect(0.0, 0.0, super.getWidth(), super.getHeight());
        graphicsContext.setFill(DEFAULT_CELL_PANEL);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                graphicsContext.fillRect(x, y, side, side);
                x += side + border;
            }
            y += side + border;
            x = border;
        }
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
     * @param paint 填充画
     */
    public void fillCell(int row, int col, Paint paint) {
        if ((row < 0 || row >= getRows()) || (col < 0 || col >= getCols())) {
            throw new IllegalArgumentException(String.format("""
                    单元格位置不正确
                    row = %d, col = %d
                    rows = %d, cols = %d
                    """, row, col, getRows(), getCols()));
        }
        graphicsContext.setFill(paint);
        graphicsContext.fillRect(computeX(col), computeY(row), side, side);
    }

    /**
     * 填充指定的单元格
     *
     * @param row 行
     * @param col 列
     */
    public void fillCell(int row, int col) {
        fillCell(row, col, DEFAULT_CELL_PANEL);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
