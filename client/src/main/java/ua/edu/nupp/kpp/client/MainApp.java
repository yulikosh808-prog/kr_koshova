package ua.edu.nupp.kpp.client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainApp extends Application {
    private static final Logger LOG = Logger.getLogger(MainApp.class.getName());
    private final HttpSportInfoClient api = new HttpSportInfoClient();
    private final TableView<users> usersTableView = new TableView<>();
    private final TableView<sportObjects> sportObjectsTableView = new TableView<>();
    private final TableView<time> timeTableView = new TableView<>();
    private final TableView<booking> bookingTableView = new TableView<>();

    @Override
    public void start(Stage stage) throws Exception {
        TabPane tabs = new TabPane(
                new Tab("Користувачі", usersPane()),
                new Tab("Спортивні об'єкти", sportObjectsPane()),
                new Tab("Розклад (Час)", timePane()),
                new Tab("Бронювання", bookingPane())
        );
        tabs.getTabs().forEach(t -> t.setClosable(false));
        stage.setScene(new Scene(tabs, 1100, 680));
        stage.setTitle("Система управління спортивними об'єктами та бронюваннями");
        stage.show();
        // sportObjects testObj = readTestSportObjects();
        // api.addSportObjects(testObj);
        refreshAll();
    }
    private sportObjects readTestSportObjects() throws IOException {
        Path file = Path.of("sample-data", "sport_objects.csv");
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        String line = lines.get(1);
        String[] parts = line.split(";", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Некоректна кількість стовпців: " + parts.length);
        }
        String name = parts[1].trim();
        String description = parts[2].trim();
        Double price = Double.parseDouble(parts[3].trim());
        return new sportObjects(null, name, description, price);
    }
    private Pane usersPane() {
        usersTableView.getColumns().setAll(
                col("ID", "id"),
                col("Ім'я користувача", "username"),
                col("Email", "email"),
                col("Роль", "role")
        );
        TextField username = new TextField();
        TextField email = new TextField();
        TextField password = new TextField();
        TextField role = new TextField();

        Button add = new Button("Додати");
        Button update = new Button("Змінити");
        Button delete = new Button("Видалити");
        Button refresh = new Button("Оновити");

        add.setOnAction(e -> safe(() -> {
            users u = new users(null, username.getText(), email.getText(), password.getText(), role.getText());
            api.addUser(u);
            refreshUsers();
        }));
        update.setOnAction(e -> safe(() -> {
            users selectedUser = selected(usersTableView);
            selectedUser.setUsername(username.getText());
            selectedUser.setEmail(email.getText());
            selectedUser.setPassword(password.getText());
            selectedUser.setRole(role.getText());
            api.updateUser(selectedUser);
            refreshUsers();
        }));
        delete.setOnAction(e -> safe(() -> {
            api.deleteUser(selected(usersTableView).getId());
            refreshAll();
        }));
        refresh.setOnAction(e -> refreshUsers());

        usersTableView.getSelectionModel().selectedItemProperty().addListener((o, a, s) -> {
            if (s != null) {
                username.setText(s.getUsername());
                email.setText(s.getEmail());
                password.setText(s.getPassword() != null ? s.getPassword() : "");
                role.setText(s.getRole());
            }
        });
        VBox form = new VBox(6,
                new Label("Ім'я користувача"), username,
                new Label("Email"), email,
                new Label("Пароль"), password,
                new Label("Роль"), role,
                new HBox(8, add, update, delete, refresh)
        );
        return layout(usersTableView, form);
    }
    private Pane sportObjectsPane() {
        sportObjectsTableView.getColumns().setAll(
                col("ID", "id"),
                col("Назва", "name"),
                col("Опис", "description"),
                col("Ціна", "price")
        );
        TextField name = new TextField();
        TextField description = new TextField();
        TextField price = new TextField();

        Button add = new Button("Додати");
        Button update = new Button("Змінити");
        Button delete = new Button("Видалити");
        Button refresh = new Button("Оновити");

        add.setOnAction(e -> safe(() -> {
            sportObjects obj = new sportObjects(null, name.getText(), description.getText(), Double.parseDouble(price.getText()));
            api.addSportObjects(obj);
            refreshSportObjects();
        }));
        update.setOnAction(e -> safe(() -> {
            sportObjects obj = selected(sportObjectsTableView);
            obj.setName(name.getText());
            obj.setDescription(description.getText());
            obj.setPrice(Double.parseDouble(price.getText()));
            api.updateSportObjects(obj);
            refreshSportObjects();
        }));
        delete.setOnAction(e -> safe(() -> {
            api.deleteSportObjects(selected(sportObjectsTableView).getId());
            refreshAll();
        }));
        refresh.setOnAction(e -> refreshSportObjects());

        sportObjectsTableView.getSelectionModel().selectedItemProperty().addListener((o, a, s) -> {
            if (s != null) {
                name.setText(s.getName());
                description.setText(s.getDescription());
                price.setText(String.valueOf(s.getPrice()));
            }
        });
        VBox form = new VBox(6,
                new Label("Назва об'єкта"), name,
                new Label("Опис"), description,
                new Label("Ціна"), price,
                new HBox(8, add, update, delete, refresh)
        );
        return layout(sportObjectsTableView, form);
    }
    private Pane timePane()  {
        timeTableView.getColumns().setAll(
                col("ID", "id"),
                col("ID об'єкта", "sportObjectId"),
                col("Початок", "startTime"),
                col("Кінець", "endTime"),
                col("Статус", "statusAvailable")
        );
        TextField sportObjectId = new TextField();
        TextField startTime = new TextField();
        TextField endTime = new TextField();
        CheckBox statusAvailable = new CheckBox("Доступно для бронювання");

        Button add = new Button("Додати");
        Button update = new Button("Змінити");
        Button delete = new Button("Видалити");
        Button refresh = new Button("Оновити");

        add.setOnAction(e -> safe(() -> {
            time t = new time(null, Long.parseLong(sportObjectId.getText()), startTime.getText(), endTime.getText(), statusAvailable.isSelected());
            api.addTime(t);
            refreshTime();
        }));

        update.setOnAction(e -> safe(() -> {
            time t = selected(timeTableView);
            t.setSportObjectId(Long.parseLong(sportObjectId.getText()));
            t.setStartTime(startTime.getText());
            t.setEndTime(endTime.getText());
            t.setStatusAvailable(statusAvailable.isSelected());
            api.updateTime(t);
            refreshTime();
        }));
        delete.setOnAction(e -> safe(() -> {
            api.deleteTime(selected(timeTableView).getId());
            refreshAll();
        }));
        refresh.setOnAction(e -> refreshTime());

        timeTableView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, t) -> {
            if (t != null) {
                sportObjectId.setText(String.valueOf(t.getSportObjectId()));
                startTime.setText(t.getStartTime());
                endTime.setText(t.getEndTime());
                statusAvailable.setSelected(t.isStatusAvailable());
            }
        });
        VBox form = new VBox(6,
                new Label("ID спортивного об'єкта"), sportObjectId,
                new Label("Час початку"), startTime,
                new Label("Час кінця"), endTime,
                statusAvailable,
                new HBox(8, add, update, delete, refresh)
        );
        return layout(timeTableView, form);
    }
    private Pane bookingPane() {
        bookingTableView.getColumns().setAll(
                col("ID", "id"),
                col("ID користувача", "userId"),
                col("ID часу", "timeId"),
                col("Статус", "status"),
                col("F1", "countF1"),
                col("F2", "hoursF2"),
                col("F3", "extraEqF3"),
                col("Індекс", "index"),
                col("Дата", "date")
        );
        TextField userId = new TextField();
        TextField timeId = new TextField();
        ComboBox<bookingStatus> statusCombo = new ComboBox<>(FXCollections.observableArrayList(bookingStatus.values()));
        TextField countF1 = new TextField();
        TextField hoursF2 = new TextField();
        TextField extraEqF3 = new TextField();
        TextField date = new TextField();

        Button add = new Button("Додати");
        Button update = new Button("Змінити");
        Button changeStatusBtn = new Button("Змінити стан");
        Button delete = new Button("Видалити");
        Button refresh = new Button("Оновити");

        add.setOnAction(e -> safe(() -> {
            booking b = readBookingForm(null, userId, timeId, statusCombo, countF1, hoursF2, extraEqF3, date);
            api.addBooking(b);
            refreshBooking();
        }));
        update.setOnAction(e -> safe(() -> {
            booking b = selected(bookingTableView);
            booking updatedB = readBookingForm(b.getId(), userId, timeId, statusCombo, countF1, hoursF2, extraEqF3, date);
            api.updateBooking(updatedB);
            refreshAll();
        }));
        delete.setOnAction(e -> safe(() -> {
            booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
            if (selectedBooking == null) {
                throw new IllegalArgumentException("Виберіть рядок у таблиці для видалення");
            }
            if (selectedBooking.getId() == null) {
                throw new IllegalArgumentException("Помилка: вибраний запис не має ID");
            }
            api.deleteBooking(selectedBooking.getId());
            refreshAll();
        }));
        changeStatusBtn.setOnAction(e -> safe(() -> {
            booking b = selected(bookingTableView);
            api.changeBookingStatus(b.getId(), statusCombo.getValue());
            refreshAll();
        }));
        refresh.setOnAction(e -> refreshBooking());

        bookingTableView.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> {
            if (b != null) {
                userId.setText(String.valueOf(b.getUserId()));
                timeId.setText(String.valueOf(b.getTimeId()));
                if (b.getStatus() != null) statusCombo.setValue(b.getStatus());
                countF1.setText(String.valueOf(b.getCountF1()));
                hoursF2.setText(String.valueOf(b.getHoursF2()));
                extraEqF3.setText(String.valueOf(b.getExtraEqF3()));
                date.setText(b.getDate() != null ? b.getDate() : "");
            }
        });
        VBox form = new VBox(5,
                new Label("ID коритсувача"), userId,
                new Label("ID часу"), timeId,
                new Label("Статус бронювання"), statusCombo,
                new Label("F1"), countF1,
                new Label("F2"), hoursF2,
                new Label("F3"), extraEqF3,
                new Label("Дата"), date,
                new HBox(6, add, update, changeStatusBtn, delete, refresh)
        );
        return layout(bookingTableView, form);
    }
    private booking readBookingForm(Long id, TextField userId, TextField timeId,
                                    ComboBox<bookingStatus> status, TextField countF1,
                                    TextField hoursF2, TextField extraEqF3, TextField date) {
        booking b = new booking();
        if (id != null) b.setId(id);
        b.setUserId(userId.getText().isEmpty() ? 0 : Long.parseLong(userId.getText()));
        b.setTimeId(timeId.getText().isEmpty() ? 0 : Long.parseLong(timeId.getText()));
        b.setStatus(status.getValue());
        b.setCountF1(countF1.getText().isEmpty() ? 0 : Integer.parseInt(countF1.getText()));
        b.setHoursF2(hoursF2.getText().isEmpty() ? 0.0 : Double.parseDouble(hoursF2.getText()));
        b.setExtraEqF3(extraEqF3.getText().isEmpty() ? 0 : Integer.parseInt(extraEqF3.getText()));
        b.setDate(date.getText());
        return b;
    }
    private void refreshAll() {
        refreshUsers();
        refreshSportObjects();
        refreshTime();
        refreshBooking();
    }
    private void refreshUsers() {
        safe(() -> usersTableView.setItems(FXCollections.observableArrayList(api.getUsers())));
    }
    private void refreshSportObjects() {
        safe(() -> sportObjectsTableView.setItems(FXCollections.observableArrayList(api.getSportObjects())));
    }
    private void refreshTime() {
        safe(() -> timeTableView.setItems(FXCollections.observableArrayList(api.getTime())));
    }
    private void refreshBooking() {
        safe(() -> bookingTableView.setItems(FXCollections.observableArrayList(api.getBooking())));
    }
    private <T> T selected(TableView<T> tableView) {
        T v = tableView.getSelectionModel().getSelectedItem();
        if (v == null) {
            throw new IllegalArgumentException("Виберіть запис у таблиці");
        }
        return v;
    }
    private void safe(Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            showError(e);
        }
    }
    private void showError(Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e == null ? "Помилка" : e.getMessage(), ButtonType.OK);
        alert.setHeaderText("Помилка виконання операції");
        alert.showAndWait();
    }
    private Pane layout(TableView<?> tableView, Pane form) {
        SplitPane p = new SplitPane(tableView, form);
        p.setDividerPosition(0, 0.68);
        BorderPane root = new BorderPane(p);
        return root;
    }
    private <S, T>TableColumn<S,T> col(String title, String property) {
        TableColumn<S, T> c = new TableColumn<>(title);
        c.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(property));
        return c;
    }
    public static void main(String[] args) {
        launch(args);
    }
}