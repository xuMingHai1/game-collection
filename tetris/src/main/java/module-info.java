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
    // 模块资源访问
    opens img;
    opens css;
}