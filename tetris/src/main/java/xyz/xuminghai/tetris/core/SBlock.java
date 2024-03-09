package xyz.xuminghai.tetris.core;

/**
 * 2024/1/28 5:21 星期日<br/>
 * S块
 *
 * @author xuMingHai
 */
public final class SBlock extends Abstract2StateBlock {

    SBlock() {
        super(new Cell[]{
                new Cell(-1, 4, TetrisFactory.randomColor()),
                new Cell(-1, 5, TetrisFactory.randomColor()),
                new Cell(-2, 5, TetrisFactory.randomColor()),
                new Cell(-2, 6, TetrisFactory.randomColor())
        });
    }

    @Override
    protected void rotate1() {
        cell2.setCol(cell3.getCol());
        cell3.setRow(cell2.getRow() + 1);

        cell1.setRow(cell2.getRow());
        cell0.setRow(cell1.getRow() - 1);
        cell0.setCol(cell1.getCol());
    }

    @Override
    protected void rotate2() {
        cell2.setCol(cell1.getCol());
        cell3.setRow(cell2.getRow());

        cell1.setRow(cell2.getRow() + 1);
        cell0.setRow(cell1.getRow());
        cell0.setCol(cell1.getCol() - 1);
    }

}
