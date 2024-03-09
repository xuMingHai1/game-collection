package xyz.xuminghai.tetris.core;

/**
 * 2024/1/16 16:21 星期二<br/>
 *
 * @author xuMingHai
 */
sealed abstract class AbstractBlock
        implements Tetris
        permits OBlock, Abstract2StateBlock, Abstract4StateBlock {

    protected Cell[] cells;

    protected Cell cell0, cell1, cell2, cell3;

    protected AbstractBlock(Cell[] cells) {
        this.cells = cells;
        this.cell0 = this.cells[0];
        this.cell1 = this.cells[1];
        this.cell2 = this.cells[2];
        this.cell3 = this.cells[3];
    }

    @Override
    public Cell[] getCells() {
        return cells;
    }

    @Override
    public void setCells(Cell[] cells) {
        this.cells = cells;
        this.cell0 = this.cells[0];
        this.cell1 = this.cells[1];
        this.cell2 = this.cells[2];
        this.cell3 = this.cells[3];
    }


    @Override
    public Cell[] downMove() {
        cell0.setRow(cell0.getRow() + 1);
        cell1.setRow(cell1.getRow() + 1);
        cell2.setRow(cell2.getRow() + 1);
        cell3.setRow(cell3.getRow() + 1);
        return cells;
    }

    @Override
    public Cell[] leftMove() {
        cell0.setCol(cell0.getCol() - 1);
        cell1.setCol(cell1.getCol() - 1);
        cell2.setCol(cell2.getCol() - 1);
        cell3.setCol(cell3.getCol() - 1);
        return cells;
    }

    @Override
    public Cell[] rightMove() {
        cell0.setCol(cell0.getCol() + 1);
        cell1.setCol(cell1.getCol() + 1);
        cell2.setCol(cell2.getCol() + 1);
        cell3.setCol(cell3.getCol() + 1);
        return cells;
    }
}
