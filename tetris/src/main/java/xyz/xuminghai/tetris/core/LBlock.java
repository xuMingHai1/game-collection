package xyz.xuminghai.tetris.core;

/**
 * 2024/1/28 4:05 星期日<br/>
 * L块
 *
 * @author xuMingHai
 */
public final class LBlock extends Abstract4StateBlock {

    LBlock() {
        super(new Cell[]{
                new Cell(-1, 4, TetrisFactory.randomColor()),
                new Cell(-2, 4, TetrisFactory.randomColor()),
                new Cell(-2, 5, TetrisFactory.randomColor()),
                new Cell(-2, 6, TetrisFactory.randomColor())
        });
    }

    @Override
    protected void rotate1() {
        cell3.setCol(cell2.getCol());
        cell1.setCol(cell2.getCol());
        cell0.setCol(cell1.getCol() - 1);

        cell3.setRow(cell2.getRow() + 1);
        cell1.setRow(cell2.getRow() - 1);
        cell0.setRow(cell1.getRow());
    }

    @Override
    protected void rotate2() {
        cell3.setRow(cell2.getRow());
        cell1.setRow(cell2.getRow());
        cell0.setRow(cell1.getRow() - 1);

        cell3.setCol(cell2.getCol() - 1);
        cell1.setCol(cell2.getCol() + 1);
        cell0.setCol(cell1.getCol());
    }

    @Override
    protected void rotate3() {
        cell3.setCol(cell2.getCol());
        cell1.setCol(cell2.getCol());
        cell0.setCol(cell1.getCol() + 1);

        cell3.setRow(cell2.getRow() - 1);
        cell1.setRow(cell2.getRow() + 1);
        cell0.setRow(cell1.getRow());
    }

    @Override
    protected void rotate4() {
        cell3.setRow(cell2.getRow());
        cell1.setRow(cell2.getRow());
        cell0.setRow(cell1.getRow() + 1);

        cell3.setCol(cell2.getCol() + 1);
        cell1.setCol(cell2.getCol() - 1);
        cell0.setCol(cell1.getCol());
    }
}
