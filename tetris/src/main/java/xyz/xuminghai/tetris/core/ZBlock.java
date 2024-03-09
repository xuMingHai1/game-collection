package xyz.xuminghai.tetris.core;

/**
 * 2024/1/28 5:21 星期日<br/>
 * Z块
 *
 * @author xuMingHai
 */
public final class ZBlock extends Abstract2StateBlock {

    ZBlock() {
        super(new Cell[]{
                new Cell(-2, 4, TetrisFactory.randomColor()),
                new Cell(-2, 5, TetrisFactory.randomColor()),
                new Cell(-1, 5, TetrisFactory.randomColor()),
                new Cell(-1, 6, TetrisFactory.randomColor())
        });
    }

    @Override
    protected void rotate1() {
        cell2.setRow(cell1.getRow());
        cell3.setCol(cell2.getCol());

        cell1.setCol(cell2.getCol() + 1);
        cell0.setRow(cell1.getRow() - 1);
        cell0.setCol(cell1.getCol());
    }

    @Override
    protected void rotate2() {
        cell2.setRow(cell3.getRow());
        cell3.setCol(cell2.getCol() + 1);

        cell1.setCol(cell2.getCol());
        cell0.setRow(cell1.getRow());
        cell0.setCol(cell1.getCol() - 1);
    }

}
