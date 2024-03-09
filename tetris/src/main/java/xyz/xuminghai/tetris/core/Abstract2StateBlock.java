package xyz.xuminghai.tetris.core;

/**
 * 2024/1/28 5:46 星期日<br/>
 *
 * @author xuMingHai
 */
sealed abstract class Abstract2StateBlock extends AbstractBlock
        permits IBlock, SBlock, ZBlock {

    private boolean state = true;

    protected Abstract2StateBlock(Cell[] cells) {
        super(cells);
    }

    protected abstract void rotate1();

    protected abstract void rotate2();

    private Cell[] rotate() {
        if (state) {
            rotate1();
        }
        else {
            rotate2();
        }

        state = !state;
        return super.cells;
    }

    @Override
    public Cell[] rotateClockwise() {
        return rotate();
    }

    @Override
    public Cell[] rotateCounterClockwise() {
        return rotate();
    }
}
