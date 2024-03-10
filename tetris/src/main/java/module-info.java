/**
 * 2024/1/15 20:28 星期一<br/>
 * 俄罗斯方块模块
 *
 * @author xuMingHai
 */
module xyz.xuminghai.tetris {
    requires javafx.controls;
    requires javafx.media;

    exports xyz.xuminghai.tetris to javafx.graphics;
    // css文件模块化访问
    opens xyz.xuminghai.tetris.view;
}