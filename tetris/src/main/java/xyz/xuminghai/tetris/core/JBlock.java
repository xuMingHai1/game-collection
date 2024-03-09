package xyz.xuminghai.tetris.core;

/**
 * 2024/1/28 4:05 星期日<br/>
 * J块
 *
 * @author xuMingHai
 */
public final class JBlock extends Abstract4StateBlock {

    JBlock() {
        super(new Cell[]{
                new Cell(-2, 4, TetrisFactory.randomColor()),
                new Cell(-2, 5, TetrisFactory.randomColor()),
                new Cell(-2, 6, TetrisFactory.randomColor()),
                new Cell(-1, 6, TetrisFactory.randomColor())
        });
    }

    @Override
    protected void rotate1() {
        cell0.setCol(cell1.getCol());
        cell2.setCol(cell1.getCol());
        cell3.setCol(cell2.getCol() - 1);

        cell0.setRow(cell1.getRow() - 1);
        cell2.setRow(cell1.getRow() + 1);
        cell3.setRow(cell2.getRow());
    }

    @Override
    protected void rotate2() {
        cell0.setRow(cell1.getRow());
        cell2.setRow(cell1.getRow());
        cell3.setRow(cell2.getRow() - 1);

        cell0.setCol(cell1.getCol() + 1);
        cell2.setCol(cell1.getCol() - 1);
        cell3.setCol(cell2.getCol());
    }

    @Override
    protected void rotate3() {
        cell0.setCol(cell1.getCol());
        cell2.setCol(cell1.getCol());
        cell3.setCol(cell2.getCol() + 1);

        cell0.setRow(cell1.getRow() + 1);
        cell2.setRow(cell1.getRow() - 1);
        cell3.setRow(cell2.getRow());
    }

    @Override
    protected void rotate4() {
        cell0.setRow(cell1.getRow());
        cell2.setRow(cell1.getRow());
        cell3.setRow(cell2.getRow() + 1);

        cell0.setCol(cell1.getCol() - 1);
        cell2.setCol(cell1.getCol() + 1);
        cell3.setCol(cell2.getCol());
    }

}
