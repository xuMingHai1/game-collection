package xyz.xuminghai.tetris.core;

import javafx.scene.paint.Paint;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 2024/1/16 13:37 星期二<br/>
 * 单元格
 *
 * @author xuMingHai
 */
public final class Cell {

    private final Paint paint;
    private int row, col;

    public Cell(int row, int col, Paint paint) {
        this.row = row;
        this.col = col;
        this.paint = paint;
    }

    public int getRow() {
        return row;
    }

    void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    void setCol(int col) {
        this.col = col;
    }

    public Paint getPaint() {
        return paint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell cell)) return false;

        if (row != cell.row) return false;
        if (col != cell.col) return false;
        return Objects.equals(paint, cell.paint);
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + (paint != null ? paint.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cell.class.getSimpleName() + "[", "]")
                .add("row=" + row)
                .add("col=" + col)
                .add("paint=" + paint)
                .toString();
    }
}