package xyz.xuminghai.tetris.core;

/**
 * 2024/1/28 5:02 星期日<br/>
 *
 * @author xuMingHai
 */
sealed abstract class Abstract4StateBlock extends AbstractBlock
        permits LBlock, JBlock, TBlock {

    private byte state = 1;

    protected Abstract4StateBlock(Cell[] cells) {
        super(cells);
    }

    protected abstract void rotate1();

    protected abstract void rotate2();

    protected abstract void rotate3();

    protected abstract void rotate4();

    @Override
    public Cell[] rotateClockwise() {
        switch (state) {
            case 1: {
                rotate1();
                state++;
                break;
            }
            case 2: {
                rotate2();
                state++;
                break;
            }
            case 3: {
                rotate3();
                state++;
                break;
            }
            case 4: {
                rotate4();
                state = 1;
                break;
            }
            default:
                throw new IllegalStateException("J块未知状态，state = " + state);
        }

        return super.cells;
    }

    @Override
    public Cell[] rotateCounterClockwise() {
        switch (state) {
            case 1: {
                rotate3();
                state = 4;
                break;
            }
            case 2: {
                rotate4();
                state--;
                break;
            }
            case 3: {
                rotate1();
                state--;
                break;
            }
            case 4: {
                rotate2();
                state--;
                break;
            }
            default:
                throw new IllegalStateException("J块未知状态，state = " + state);
        }

        return super.cells;
    }


}
