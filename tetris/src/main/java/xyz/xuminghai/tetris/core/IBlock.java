package xyz.xuminghai.tetris.core;


/**
 * 2024/1/16 15:05 星期二<br/>
 * I 块
 *
 * @author xuMingHai
 */
public final class IBlock extends Abstract2StateBlock {

    IBlock() {
        super(new Cell[]{
                new Cell(-1, 3, TetrisFactory.randomColor()),
                new Cell(-1, 4, TetrisFactory.randomColor()),
                new Cell(-1, 5, TetrisFactory.randomColor()),
                new Cell(-1, 6, TetrisFactory.randomColor())
        });
    }

    @Override
    protected void rotate1() {
        cell0.setCol(cell2.getCol());
        cell1.setCol(cell2.getCol());
        cell3.setCol(cell2.getCol());

        cell0.setRow(cell2.getRow() - 2);
        cell1.setRow(cell2.getRow() - 1);
        cell3.setRow(cell2.getRow() + 1);
    }

    @Override
    protected void rotate2() {
        cell0.setRow(cell2.getRow());
        cell1.setRow(cell2.getRow());
        cell3.setRow(cell2.getRow());

        cell0.setCol(cell2.getCol() - 2);
        cell1.setCol(cell2.getCol() - 1);
        cell3.setCol(cell2.getCol() + 1);
    }

}
