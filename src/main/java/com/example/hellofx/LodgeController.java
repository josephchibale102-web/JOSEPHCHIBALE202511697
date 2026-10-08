package com.example.hellofx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.util.Map;

public class LodgeController extends Application {

    private static LodgeModel model;
    private static LodgeView view;
    private final ObservableList<LodgeModel.Bill> billList = FXCollections.observableArrayList();
    private String currentStaffUser = "";

    // Default constructor for Application
    public LodgeController() {}

    public LodgeController(LodgeModel model, LodgeView view) {
        LodgeController.model = model;
        LodgeController.view = view;

        initLoginController();
    }

    @Override
    public void start(Stage stage) {
        LodgeModel m = new LodgeModel();
        LodgeView v = new LodgeView(stage);
        new LodgeController(m, v);

        v.renderLoginScene();
    }

    private void initLoginController() {
        if (view != null && view.loginBtn != null) {
            view.loginBtn.setOnAction(e -> handleStaffLogin());
        }
    }

    private void handleStaffLogin() {
        String username = view.loginUser.getText().trim();
        String password = view.loginPass.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            view.loginErr.setText("Enter worker username and password.");
            return;
        }

        // Validate against the 5 authorized workers
        if (!model.isStaffUsername(username)) {
            view.loginErr.setText("Unauthorized worker ID.");
            return;
        }

        if (!model.validateStaffLogin(username, password)) {
            view.loginErr.setText("Incorrect password.");
            return;
        }

        // Successful Staff Login
        currentStaffUser = username.toUpperCase();
        view.renderMainScene(model.getRoomTypes());
        view.table.setItems(billList);

        view.userInfo.setText("Worker: " + currentStaffUser + "\nSession Active");
        view.bookingIdField.setText("RES-" + (int) (Math.random() * 90000 + 10000));

        initMainController();
    }

    private void initMainController() {
        // Room Selection
        for (Map.Entry<String, javafx.scene.layout.VBox> entry : view.roomCards.entrySet()) {
            String roomName = entry.getKey();
            entry.getValue().setOnMouseClicked(e -> {
                model.setSelectedRoom(roomName);
                view.updateRoomSelection(roomName);
            });
        }

        if (model.getSelectedRoom() != null) {
            view.updateRoomSelection(model.getSelectedRoom());
        }

        // Stepper
        view.minusBtn.setOnAction(e -> {
            if (model.getNights() > 1) {
                model.setNights(model.getNights() - 1);
                view.nightsLabel.setText(model.getNights() + (model.getNights() == 1 ? " Night" : " Nights"));
            }
        });

        view.plusBtn.setOnAction(e -> {
            model.setNights(model.getNights() + 1);
            view.nightsLabel.setText(model.getNights() + " Nights");
        });

        // Calculation & Confirm
        view.calcBtn.setOnAction(e -> calculateCurrentBill());
        view.saveBtn.setOnAction(e -> saveCurrentBooking());

        // Sidebar Navigation
        view.newGuestBtn.setOnAction(e -> {
            view.clearForm();
            model.setNights(1);
            view.bookingIdField.setText("RES-" + (int) (Math.random() * 90000 + 10000));
            view.info("Form reset for new guest booking.");
        });

        view.deleteBtn.setOnAction(e -> {
            LodgeModel.Bill selected = view.table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                billList.remove(selected);
                view.info("Booking " + selected.id + " has been cancelled.");
            } else {
                view.info("Select a booking row from table to cancel.");
            }
        });

        view.receiptBtn.setOnAction(e -> {
            LodgeModel.Bill selected = view.table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                view.info("--- RECEIPT ---\nBooking ID: " + selected.id +
                        "\nGuest Name: " + selected.guest +
                        "\nRoom: " + selected.room +
                        "\nTotal: K " + String.format("%.2f", selected.total) +
                        "\nPaid: K " + String.format("%.2f", selected.paid) +
                        "\nBalance: K " + String.format("%.2f", selected.balance));
            } else {
                view.info("Select a booking row to view its receipt.");
            }
        });

        view.staffListBtn.setOnAction(e -> {
            StringBuilder sb = new StringBuilder("--- AUTHORIZED WORKERS (5) ---\n");
            for (String staff : model.getStaffList().keySet()) {
                sb.append("• ").append(staff).append("\n");
            }
            view.info(sb.toString());
        });

        view.logoutBtn.setOnAction(e -> performLogout());
    }

    private void performLogout() {
        currentStaffUser = "";
        view.clearForm();
        view.loginUser.clear();
        view.loginPass.clear();
        view.loginErr.setText("");

        view.renderLoginScene();
        initLoginController();
    }

    private LodgeModel.Bill calculateCurrentBill() {
        double paid = 0.0;
        try {
            if (!view.paidField.getText().trim().isEmpty()) {
                paid = Double.parseDouble(view.paidField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            view.info("Invalid payment amount entered.");
        }

        LodgeModel.Bill bill = model.calculateBill(
                view.guestField.getText().trim(),
                view.bookingIdField.getText().trim(),
                paid,
                view.breakfastCb.isSelected(),
                view.spaCb.isSelected(),
                view.transferCb.isSelected(),
                view.dinnerBuffetCb.isSelected(),
                view.roomServiceCb.isSelected(),
                view.luxuryChefCb.isSelected()
        );

        view.displayCalculatedBill(bill);
        return bill;
    }

    private void saveCurrentBooking() {
        if (view.guestField.getText().trim().isEmpty()) {
            view.info("Please enter a guest/customer name before confirming.");
            return;
        }

        LodgeModel.Bill bill = calculateCurrentBill();
        billList.add(bill);
        view.info("Booking " + bill.id + " for guest '" + bill.guest + "' successfully confirmed!");
    }

    // Direct entry point so you can run directly from LodgeController
    public static void main(String[] args) {
        launch(args);
    }
}