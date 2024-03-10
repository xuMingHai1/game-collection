package xyz.xuminghai.tetris.core;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Supplier;

/**
 * 2024/2/23 13:36 星期五<br/>
 * 俄罗斯方块工厂类
 *
 * @author xuMingHai
 */
public final class TetrisFactory {

    private static final List<Supplier<Tetris>> SUPPLIER_LIST = List.of(IBlock::new, JBlock::new,
            LBlock::new, OBlock::new, SBlock::new, TBlock::new, ZBlock::new);

    private TetrisFactory() {
    }

    public static Tetris randomCreateTetris() {
        int index = (int) (Math.random() * SUPPLIER_LIST.size());
        return SUPPLIER_LIST.get(index).get();
    }

    public static Color randomColor() {
        int red = (int) (Math.random() * (256 - 100)) + 100;
        int green = (int) (Math.random() * (256 - 100)) + 100;
        int blue = (int) (Math.random() * (256 - 100)) + 100;
        return Color.rgb(red, green, blue);
    }
}
