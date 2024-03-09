package xyz.xuminghai.tetris.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.paint.Color;
import xyz.xuminghai.tetris.core.Cell;
import xyz.xuminghai.tetris.core.Tetris;

/**
 * 2024/1/29 12:22 星期一<br/>
 * 预览块视图
 *
 * @author xuMingHai
 */
public final class NextBlockView extends AbstractBlockView {

    public NextBlockView(ReadOnlyObjectProperty<Tetris> nextTetris) {
        super(4, 4);
        final Tetris tetris = nextTetris.get();
        if (tetris != null) {
            fillTetris(tetris);
        }
        nextTetris.addListener((observable, oldValue, newValue) -> {
            clearView();
            fillTetris(newValue);
        });
    }

    private void clearView() {
        super.graphicsContext.setFill(Color.BLACK);
        super.graphicsContext.fillRect(0.0, 0.0, super.getWidth(), super.getHeight());
    }

    private void fillTetris(Tetris tetris) {
        final Cell[] nextCells = tetris.getCells();
        final Cell[] cells = new Cell[nextCells.length];
        for (int i = 0; i < nextCells.length; i++) {
            final Cell cell = nextCells[i];
            cells[i] = new Cell(cell.getRow() + 3, cell.getCol() - 3, cell.getPaint());
        }
        for (Cell cell : cells) {
            super.fillCell(cell.getRow(), cell.getCol(), cell.getPaint());
        }
    }

}
