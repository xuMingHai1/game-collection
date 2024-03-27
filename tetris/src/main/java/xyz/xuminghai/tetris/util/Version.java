package xyz.xuminghai.tetris.util;

import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2024/3/14 2:26 星期四<br/>
 * 游戏版本
 *
 * @author xuMingHai
 */
public final class Version {

    public static final String VERSION = "v0.0.5";
    private static final Pattern VERSION_PATTERN = Pattern.compile("^v(\\d+)\\.(\\d+)\\.(\\d)");

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
                final String body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
                        .body();
                final Matcher matcher = VERSION_PATTERN.matcher(VERSION);
                final Matcher bodyMatcher = VERSION_PATTERN.matcher(body);
                if (matcher.matches() && bodyMatcher.matches()) {
                    if (Integer.parseInt(bodyMatcher.group(1)) >= Integer.parseInt(matcher.group(1))) {
                        if (Integer.parseInt(bodyMatcher.group(2)) >= Integer.parseInt(matcher.group(2))) {
                            if (Integer.parseInt(bodyMatcher.group(3)) >= Integer.parseInt(matcher.group(3))) {
                                return body;
                            }
                        }
                    }
                }
                return null;
            }

            @Override
            protected void succeeded() {
                final String value = super.getValue();
                if (value != null) {
                    consumer.accept(value);
                }
            }
        });
    }


}
