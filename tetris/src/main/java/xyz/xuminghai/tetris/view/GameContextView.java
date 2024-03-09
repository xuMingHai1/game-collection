package xyz.xuminghai.tetris.view;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import xyz.xuminghai.tetris.core.Cell;
import xyz.xuminghai.tetris.game.GameWorld;

/**
 * 2024/1/29 12:48 星期一<br/>
 * 游戏内容视图
 *
 * @author xuMingHai
 */
public final class GameContextView extends AbstractBlockView {

    public GameContextView(GameWorld gameWorld) {
        super(gameWorld.getRows(), gameWorld.getCols());
        gameWorld.currentCellsProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue != null) {
                for (Cell cell : oldValue) {
                    super.fillCell(cell.getRow(), cell.getCol(), Color.BLACK);
                }
            }
            if (newValue != null) {
                for (Cell cell : newValue) {
                    super.fillCell(cell.getRow(), cell.getCol(), cell.getPaint());
                }
            }
        });
        gameWorld.clearCellProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (Cell cell : newValue) {
                    super.fillCell(cell.getRow(), cell.getCol(), Color.BLACK);
                }
            }
        });
        gameWorld.renderCellProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (Cell cell : newValue) {
                    super.fillCell(cell.getRow(), cell.getCol(), cell.getPaint());
                }
            }
        });
        // 设置光标
        super.setCursor(Cursor.NONE);
    }


}
