package xyz.xuminghai.tetris.core;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * 2024/2/23 13:36 星期五<br/>
 * 俄罗斯方块工厂类
 *
 * @author xuMingHai
 */
public final class TetrisFactory {

    private static final List<Supplier<Tetris>> SUPPLIER_LIST = List.of(IBlock::new, JBlock::new,
            LBlock::new, OBlock::new, SBlock::new, TBlock::new, ZBlock::new);

    private static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.of("Xoroshiro128PlusPlus");

    private static final double BOUND_UP = Math.nextUp(1.0);

    private TetrisFactory() {
    }

    public static Tetris randomCreateTetris() {
        int index = RANDOM_GENERATOR.nextInt(SUPPLIER_LIST.size());
        return SUPPLIER_LIST.get(index).get();
    }

    public static Color randomColor() {
        return Color.color(RANDOM_GENERATOR.nextDouble(0.5, BOUND_UP),
                RANDOM_GENERATOR.nextDouble(0.5, BOUND_UP),
                RANDOM_GENERATOR.nextDouble(0.5, BOUND_UP));
    }
}
