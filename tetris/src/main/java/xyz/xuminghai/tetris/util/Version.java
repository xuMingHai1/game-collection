package xyz.xuminghai.tetris.util;

import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

/**
 * 2024/3/14 2:26 星期四<br/>
 * 游戏版本
 *
 * @author xuMingHai
 */
public final class Version {

    public static final String VERSION = "v0.0.4";

    public static final String RELEASE_URI = "https://gitee.com/xuMingHai1/game-collection/releases";

    private static final URI VERSION_URI = URI.create("https://gitee.com/xuMingHai1/game-collection/raw/master/tetris/src/version.txt");

    private Version() {
    }

    public static void checkUpdate(Consumer<String> consumer) {
        ForkJoinPool.commonPool().execute(new Task<String>() {
            @Override
            protected String call() throws Exception {
                // 暂时不保存HttpClient实例
                final HttpClient httpClient = HttpClient.newHttpClient();
                final HttpRequest httpRequest = HttpRequest.newBuilder(VERSION_URI)
                        .timeout(Duration.ofSeconds(1L))
                        .build();
                return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
                        .body();
            }

            @Override
            protected void succeeded() {
                final String value = super.getValue();
                if (!VERSION.equals(value)) {
                    consumer.accept(value);
                }
            }
        });
    }


}
