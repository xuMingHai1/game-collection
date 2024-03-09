package xyz.xuminghai.tetris.core;

/**
 * 2024/1/17 2:37 星期三<br/>
 * O块
 *
 * @author xuMingHai
 */
public final class OBlock extends AbstractBlock {

    OBlock() {
        super(new Cell[]{
                new Cell(-2, 4, TetrisFactory.randomColor()),
                new Cell(-2, 5, TetrisFactory.randomColor()),
                new Cell(-1, 5, TetrisFactory.randomColor()),
                new Cell(-1, 4, TetrisFactory.randomColor())
        });
    }

    @Override
    public Cell[] rotateClockwise() {
        Cell c0 = super.cells[0];
        Cell c1 = super.cells[1];
        Cell c2 = super.cells[2];
        Cell c3 = super.cells[3];

        c0.setCol(c1.getCol());
        c1.setRow(c2.getRow());
        c2.setCol(c3.getCol());
        c3.setRow(c0.getRow());

        super.cells[0] = c3;
        super.cells[1] = c0;
        super.cells[2] = c1;
        super.cells[3] = c2;

        return super.cells;
    }

    @Override
    public Cell[] rotateCounterClockwise() {
        Cell c0 = super.cells[0];
        Cell c1 = super.cells[1];
        Cell c2 = super.cells[2];
        Cell c3 = super.cells[3];

        c0.setRow(c3.getRow());
        c1.setCol(c0.getCol());
        c2.setRow(c1.getRow());
        c3.setCol(c2.getCol());

        super.cells[0] = c1;
        super.cells[1] = c2;
        super.cells[2] = c3;
        super.cells[3] = c0;

        return super.cells;
    }
}
