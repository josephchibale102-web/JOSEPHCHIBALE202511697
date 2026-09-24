package com.example.hellofx;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Optional;
public class HelloJavaFX extends Application {
    public static class Customer {
        private String name;
        private String province;
        private Customer(String name, String province) {
            this.name = name;
            this.province = province;
        }
        public String getName() { return name; }
        public String getProvince() { return province; }
    }

    @Override
    public void start(Stage stage) {
        // POINT 1: Form
        TextField nameField = new TextField();
        nameField.setPromptText("Enter customer name");
        ComboBox<String> provinceCombo = new ComboBox<>();
        provinceCombo.getItems().addAll("Central", "Copperbelt", "Eastern", "Luapula", "Lusaka", "Muchinga", "Northern", "North-Western", "Southern", "Western");
        provinceCombo.setPromptText("Select Province");
        Button addBtn = new Button("Add Customer");
        Button deleteBtn = new Button("Delete Selected");

        // POINT 2: ObservableList
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        // POINT 3: TableView
        TableView<Customer> table = new TableView<>(customerList);
        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        TableColumn<Customer, String> provCol = new TableColumn<>("Province");
        provCol.setPrefWidth(200);
        table.getColumns().addAll(nameCol, provCol);

        // POINT 4: Validate and Add
        addBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String province = provinceCombo.getValue();
            if (name.isEmpty()) {
                alert("Name empty!");
                return;
            }
            if (!name.matches("[a-zA-Z]+")) {
                alert("Name must be letters only");
                return;
            }
            customerList.add(new Customer(name, province));
            nameField.clear();
            provinceCombo.setValue(null);
        });

        // POINT 5: Confirm Delete
        deleteBtn.setOnAction(e -> {
            Customer sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) {
                alert("Select a customer first");
                return;
            }
            Alert c = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + sel.getName() + "?", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> r = c.showAndWait();
            if (r.isPresent() && r.get() == ButtonType.OK)
                customerList.remove(sel);
        });

        // POINT 6:Keyboard
        nameField.setOnAction(ev -> addBtn.fire());
        addBtn.setDefaultButton(true);
        deleteBtn.setCancelButton(true);
        table.setOnKeyPressed(e ->
        {
            if (e.getCode() == KeyCode.ENTER) {}
        });

        HBox form = new HBox(10, new Label("Name:"), nameField, new Label("Province:"), provinceCombo, addBtn, deleteBtn);
        form.setPadding(new Insets(10));
        VBox root = new VBox(10, form, table);
        root.setPadding(new Insets(10));
        stage.setScene(new Scene(root, 750, 400));
        stage.setTitle("Customer Manager - Joseph Chibale(PhD)");
        stage.show();
    }
    private void alert(String msg) {
    new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
    public static void main(String[] args) { launch();}
}