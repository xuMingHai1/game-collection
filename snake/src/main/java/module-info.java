module xyz.xuminghai.snake {
    requires javafx.controls;

    exports xyz.xuminghai.snake to javafx.graphics;
    // css文件模块化访问
    opens xyz.xuminghai.snake.view;
}