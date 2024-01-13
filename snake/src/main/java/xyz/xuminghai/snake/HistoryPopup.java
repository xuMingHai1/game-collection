package xyz.xuminghai.snake;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * 2024/1/13 1:59 星期六<br/>
 * 历史记录弹出层
 *
 * @author xuMingHai
 */
public class HistoryPopup extends Popup {

    private final TableView<Data> tableView = new TableView<>();

    public HistoryPopup() {
        // 失去焦点，自动隐藏
        super.setAutoHide(true);
        final TableColumn<Data, String> datetimeColumn = new TableColumn<>("时间");
        datetimeColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().datetime()));
        tableView.getColumns().add(datetimeColumn);

        final TableColumn<Data, Number> sourceColumn = new TableColumn<>("长度");
        sourceColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().source()));
        tableView.getColumns().add(sourceColumn);

        super.getContent().add(tableView);
    }

    @Override
    public void show(Window owner) {
        try {
            final List<Data> dataList = Files.readAllLines(GameStage.PATH)
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(s -> {
                        final String[] split = s.split("\t");
                        return new Data(split[0], Integer.valueOf(split[1]));
                    })
                    .toList();
            tableView.setItems(FXCollections.observableArrayList(dataList));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.show(owner);
    }

    record Data(String datetime, Number source) {
    }

}
