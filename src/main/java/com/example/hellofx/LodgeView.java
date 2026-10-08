package com.example.hellofx;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;

public class LodgeView {

    // 🔵 Chelsea Royal Blue & Extra Deep Pitch Dark Palette 🔵
    public static final String CHELSEA_BLUE = "#034694";       // Official Chelsea FC Royal Blue
    public static final String CHELSEA_BLUE_LIGHT = "#2862EC"; // Vibrant Blue Highlight
    public static final String BG_DARK = "#02050A";           // Pitch Black Dark Background
    public static final String CARD_BG = "#070E18";           // Ultra Dark Slate Surface
    public static final String CARD_BORDER = "#0F1C30";       // Subtle Dark Border
    public static final String FIELD_BG = "#040810";          // Deep Input Field Background
    public static final String TEXT_WHITE = "#F8FAFC";
    public static final String TEXT_MUTED = "#64748B";

    public Stage stage;

    // Login Controls
    public TextField loginUser = new TextField();
    public PasswordField loginPass = new PasswordField();
    public Button loginBtn = new Button("STAFF PORTAL LOGIN");
    public Label loginErr = lbl("", "-fx-text-fill: #EF4444; -fx-font-size: 12;");

    // Header Display
    public Label userInfo = lbl("Staff Operator\nLogged In", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11;");

    // Main Booking Input Controls
    public TextField guestField = new TextField();
    public TextField bookingIdField = new TextField();
    public TextField paidField = new TextField();
    public Label nightsLabel = lbl("1 Night", "-fx-text-fill: " + TEXT_WHITE + "; -fx-font-weight: bold;");
    public Button minusBtn = iconButton("<");
    public Button plusBtn = iconButton(">");

    // Checkboxes
    public CheckBox breakfastCb = darkCheckbox("Daily Breakfast", "K 35.00 / night");
    public CheckBox spaCb = darkCheckbox("Spa Access", "K 120.00 / stay");
    public CheckBox transferCb = darkCheckbox("Airport Transfer", "K 80.00 / trip");
    public CheckBox dinnerBuffetCb = darkCheckbox("Evening Buffet", "K 65.00 / night");
    public CheckBox roomServiceCb = darkCheckbox("24/7 Room Service", "K 50.00 / stay");
    public CheckBox luxuryChefCb = darkCheckbox("Private Dining Chef", "K 150.00 / night");

    // Summary Display Labels
    public Label discountVal = lbl("- K 0.00", "-fx-text-fill: " + TEXT_WHITE + ";");
    public Label extrasVal = lbl("K 0.00", "-fx-text-fill: " + TEXT_WHITE + ";");
    public Label foodVal = lbl("K 0.00", "-fx-text-fill: " + TEXT_WHITE + ";");
    public Label subtotalVal = lbl("K 0.00", "-fx-text-fill: " + TEXT_WHITE + ";");
    public Label totalVal = lbl("K 0.00", "-fx-text-fill: " + CHELSEA_BLUE_LIGHT + "; -fx-font-size: 22; -fx-font-weight: bold;");
    public Label amountPaidVal = lbl("K 0.00", "-fx-text-fill: " + TEXT_WHITE + ";");
    public Label balanceVal = lbl("K 0.00", "-fx-text-fill: " + CHELSEA_BLUE_LIGHT + "; -fx-font-size: 26; -fx-font-weight: bold;");
    public Label statusBadge = lbl("UNPAID", "-fx-background-color: #2D0F0F; -fx-text-fill: #EF4444; -fx-background-radius: 12; -fx-padding: 3 10; -fx-font-size: 10; -fx-font-weight: bold;");

    // Action Buttons
    public Button saveBtn = blueButton("CONFIRM BOOKING");
    public Button calcBtn = darkOutlineButton("CALCULATE");
    public Button newGuestBtn = darkNavButton("+ New Guest Booking");
    public Button receiptBtn = darkNavButton("≡ View Receipt");
    public Button deleteBtn = darkNavButton("✕ Cancel Booking");
    public Button staffListBtn = darkNavButton("👥 Authorized Workers");
    public Button logoutBtn = darkNavButton("➔ Log Out");

    public TableView<LodgeModel.Bill> table = new TableView<>();
    public final Map<String, VBox> roomCards = new LinkedHashMap<>();

    public LodgeView(Stage stage) {
        this.stage = stage;
    }

    public void renderLoginScene() {
        Label lodgeIcon = lbl("🏰", "-fx-font-size: 42;");
        Label lodgeTitle = lbl("SANSIRO LODGE", "-fx-text-fill: " + CHELSEA_BLUE_LIGHT + "; -fx-font-size: 30; -fx-font-weight: bold; -fx-letter-spacing: 2;");
        Label lodgeSubtitle = lbl("Staff & Worker Access Portal", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 12;");

        VBox brandBox = new VBox(6, lodgeIcon, lodgeTitle, lodgeSubtitle);
        brandBox.setAlignment(Pos.CENTER);

        loginUser.setPromptText("Worker ID / Username");
        styleField(loginUser);
        loginPass.setPromptText("Password");
        styleField(loginPass);

        loginBtn.setStyle("-fx-background-color: " + CHELSEA_BLUE + "; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 12 16; -fx-cursor: hand; -fx-font-size: 13;");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setDefaultButton(true);

        VBox cardBox = new VBox(18, brandBox, new Separator(), loginUser, loginPass, loginBtn, loginErr);
        cardBox.setPadding(new Insets(40));
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setMaxWidth(420);
        cardBox.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 12; -fx-border-color: " + CHELSEA_BLUE + "; -fx-border-width: 1.5; -fx-border-radius: 12;");

        StackPane root = new StackPane(cardBox);
        root.setStyle("-fx-background-color: " + BG_DARK + ";");

        stage.setTitle("Sansiro Lodge - Staff Login");
        stage.setScene(new Scene(root, 850, 550));
        stage.centerOnScreen();
        stage.show();
    }

    public void renderMainScene(Map<String, LodgeModel.RoomType> roomTypes) {
        roomCards.clear();

        // Left Sidebar Navigation
        VBox sidebar = new VBox(12, newGuestBtn, receiptBtn, deleteBtn, staffListBtn, new Separator(), logoutBtn);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(180);
        sidebar.setStyle("-fx-background-color: " + CARD_BG + "; -fx-border-color: " + CARD_BORDER + "; -fx-border-width: 0 1 0 0;");

        // Top Header
        Label title = lbl("Sansiro Lodge", "-fx-text-fill: " + CHELSEA_BLUE_LIGHT + "; -fx-font-size: 20; -fx-font-weight: bold;");
        Label subtitle = lbl("BOUTIQUE LUXURY HOSPITALITY MANAGEMENT", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 9; -fx-letter-spacing: 1.5;");
        VBox logoBox = new VBox(2, title, subtitle);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Label userAvatar = lbl("W", "-fx-background-color: " + CHELSEA_BLUE + "; -fx-text-fill: #FFFFFF; -fx-background-radius: 20; -fx-padding: 8 12; -fx-font-weight: bold;");
        HBox userBox = new HBox(10, userAvatar, userInfo);
        userBox.setAlignment(Pos.CENTER_LEFT);

        HBox header = new HBox(logoBox, headerSpacer, userBox);
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setStyle("-fx-background-color: " + CARD_BG + "; -fx-border-color: " + CARD_BORDER + "; -fx-border-width: 0 0 1 0;");

        // Guest Input Form
        guestField.setPromptText("Guest/Customer Full Name");
        styleField(guestField);
        bookingIdField.setPromptText("Booking / Reservation ID");
        styleField(bookingIdField);

        GridPane guestGrid = new GridPane();
        guestGrid.setHgap(15);
        guestGrid.add(guestField, 0, 0);
        guestGrid.add(bookingIdField, 1, 0);
        GridPane.setHgrow(guestField, Priority.ALWAYS);
        GridPane.setHgrow(bookingIdField, Priority.ALWAYS);

        // Room Selection Display
        Label roomTypeTitle = lbl("ROOM SELECTION & PREVIEW", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11; -fx-font-weight: bold;");
        HBox roomCardsBox = new HBox(12);
        for (LodgeModel.RoomType rt : roomTypes.values()) {
            VBox card = createRoomCard(rt);
            roomCards.put(rt.name, card);
            roomCardsBox.getChildren().add(card);
            HBox.setHgrow(card, Priority.ALWAYS);
        }

        // Stepper & Paid Options
        Label nightsTitle = lbl("NUMBER OF NIGHTS", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11; -fx-font-weight: bold;");
        HBox stepper = new HBox(10, minusBtn, nightsLabel, plusBtn);
        stepper.setAlignment(Pos.CENTER);
        stepper.setStyle("-fx-background-color: " + FIELD_BG + "; -fx-background-radius: 8; -fx-padding: 6 12; -fx-border-color: " + CARD_BORDER + "; -fx-border-radius: 8;");

        Label paidTitle = lbl("AMOUNT PAID IN K (OPTIONAL)", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11; -fx-font-weight: bold;");
        paidField.setPromptText("0.00");
        styleField(paidField);

        VBox leftControl = new VBox(8, nightsTitle, stepper, paidTitle, paidField);

        // Extras & Food Options
        VBox extrasBox = new VBox(8, lbl("GENERAL EXTRAS", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11; -fx-font-weight: bold;"), breakfastCb, spaCb, transferCb);
        extrasBox.setPadding(new Insets(12));
        extrasBox.setStyle("-fx-background-color: " + FIELD_BG + "; -fx-background-radius: 8; -fx-border-color: " + CARD_BORDER + "; -fx-border-radius: 8;");

        VBox foodBox = new VBox(8, lbl("FOOD RESERVATIONS", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11; -fx-font-weight: bold;"), dinnerBuffetCb, roomServiceCb, luxuryChefCb);
        foodBox.setPadding(new Insets(12));
        foodBox.setStyle("-fx-background-color: " + FIELD_BG + "; -fx-background-radius: 8; -fx-border-color: " + CARD_BORDER + "; -fx-border-radius: 8;");

        HBox middleControls = new HBox(15, leftControl, extrasBox, foodBox);
        HBox.setHgrow(leftControl, Priority.ALWAYS);
        HBox.setHgrow(extrasBox, Priority.ALWAYS);
        HBox.setHgrow(foodBox, Priority.ALWAYS);

        // Right Summary Panel
        VBox summaryCard = new VBox(8,
                summaryRow("Discount (5%, 5+ nights)", discountVal),
                summaryRow("Extras Total", extrasVal),
                summaryRow("Food Reservations", foodVal),
                summaryRow("Subtotal", subtotalVal),
                new Separator(),
                summaryRow("TOTAL BILL", totalVal),
                summaryRow("AMOUNT PAID", amountPaidVal),
                new Separator(),
                summaryRow("BALANCE DUE", balanceVal),
                statusBadge,
                new Label(" "),
                saveBtn, calcBtn
        );
        summaryCard.setPadding(new Insets(20));
        summaryCard.setPrefWidth(280);
        summaryCard.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 10; -fx-border-color: " + CHELSEA_BLUE + "; -fx-border-width: 1.5; -fx-border-radius: 10;");

        VBox mainForm = new VBox(16, guestGrid, roomTypeTitle, roomCardsBox, middleControls);
        HBox.setHgrow(mainForm, Priority.ALWAYS);
        HBox topArea = new HBox(20, mainForm, summaryCard);

        // Bookings Table
        table.getColumns().clear();
        table.getColumns().add(col("Bill ID", 70, b -> b.id));
        table.getColumns().add(col("Guest Name", 130, b -> b.guest));
        table.getColumns().add(col("Room Type", 90, b -> b.room));
        table.getColumns().add(col("Nights", 55, b -> String.valueOf(b.nights)));
        table.getColumns().add(col("Food Resv", 80, b -> String.format("K %.2f", b.foodReservations)));
        table.getColumns().add(col("Total Bill", 95, b -> String.format("K %.2f", b.total)));
        table.getColumns().add(col("Paid", 85, b -> String.format("K %.2f", b.paid)));
        table.getColumns().add(col("Balance", 85, b -> String.format("K %.2f", b.balance)));
        table.getColumns().add(col("Status", 70, b -> b.status));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        table.setPrefHeight(180);
        table.setStyle("-fx-background-color: " + CARD_BG + "; -fx-base: " + CARD_BG + "; -fx-control-inner-background: " + CARD_BG + "; -fx-table-cell-border-color: " + CARD_BORDER + ";");

        VBox content = new VBox(20, topArea, new VBox(10, lbl("≡ RECENT GUEST BOOKINGS", "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11; -fx-font-weight: bold;"), table));
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: " + BG_DARK + ";");

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + BG_DARK + "; -fx-background-color: " + BG_DARK + ";");

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setLeft(sidebar);
        root.setCenter(scrollPane);

        stage.setTitle("Sansiro Lodge - Guest Reservations");
        stage.setScene(new Scene(root, 1180, 820));
        stage.centerOnScreen();
    }

    private VBox createRoomCard(LodgeModel.RoomType rt) {
        Node bedIllustration = createBedInteriorIllustration(rt.name != null ? rt.name : rt.interiorType);

        Label name = lbl(rt.name, "-fx-text-fill: " + TEXT_WHITE + "; -fx-font-weight: bold; -fx-font-size: 13;");
        Label desc = lbl(rt.desc, "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 10;");
        Label price = lbl(String.format("K %.2f / night", rt.price), "-fx-text-fill: " + CHELSEA_BLUE_LIGHT + "; -fx-font-size: 11;");

        VBox card = new VBox(8, bedIllustration, name, desc, price);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: " + FIELD_BG + "; -fx-background-radius: 8; -fx-border-color: " + CARD_BORDER + "; -fx-border-radius: 8; -fx-cursor: hand;");
        return card;
    }

    private Node createBedInteriorIllustration(String roomType) {
        ImageView imgView = new ImageView();
        imgView.setFitWidth(140);
        imgView.setFitHeight(80);
        imgView.setPreserveRatio(false);

        Rectangle clip = new Rectangle(140, 80);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imgView.setClip(clip);

        String url = "";
        if (roomType != null && roomType.toUpperCase().contains("STANDARD")) {
            url = "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=300&q=80";
        } else if (roomType != null && roomType.toUpperCase().contains("DELUXE")) {
            url = "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=300&q=80";
        } else if (roomType != null && roomType.toUpperCase().contains("SUITE")) {
            url = "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=300&q=80";
        } else {
            url = "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=300&q=80";
        }

        try {
            Image img = new Image(url, true);
            imgView.setImage(img);
        } catch (Exception e) {
            System.err.println("Failed to load image for: " + roomType);
        }

        return imgView;
    }

    public void clearForm() {
        guestField.clear();
        bookingIdField.clear();
        paidField.clear();
        nightsLabel.setText("1 Night");

        breakfastCb.setSelected(false);
        spaCb.setSelected(false);
        transferCb.setSelected(false);
        dinnerBuffetCb.setSelected(false);
        roomServiceCb.setSelected(false);
        luxuryChefCb.setSelected(false);

        discountVal.setText("- K 0.00");
        extrasVal.setText("K 0.00");
        foodVal.setText("K 0.00");
        subtotalVal.setText("K 0.00");
        totalVal.setText("K 0.00");
        amountPaidVal.setText("K 0.00");
        balanceVal.setText("K 0.00");

        statusBadge.setText("UNPAID");
        statusBadge.setStyle("-fx-background-color: #2D0F0F; -fx-text-fill: #EF4444; -fx-background-radius: 12; -fx-padding: 3 10; -fx-font-size: 10; -fx-font-weight: bold;");
    }

    public void updateRoomSelection(String selectedRoom) {
        for (Map.Entry<String, VBox> entry : roomCards.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getKey().equals(selectedRoom)) {
                    entry.getValue().setStyle("-fx-background-color: " + FIELD_BG + "; -fx-background-radius: 8; -fx-border-color: " + CHELSEA_BLUE_LIGHT + "; -fx-border-width: 2; -fx-border-radius: 8;");
                } else {
                    entry.getValue().setStyle("-fx-background-color: " + FIELD_BG + "; -fx-background-radius: 8; -fx-border-color: " + CARD_BORDER + "; -fx-border-width: 1; -fx-border-radius: 8;");
                }
            }
        }
    }

    public void displayCalculatedBill(LodgeModel.Bill b) {
        discountVal.setText(String.format("- K %.2f", b.discount));
        extrasVal.setText(String.format("K %.2f", b.extras));
        foodVal.setText(String.format("K %.2f", b.foodReservations));
        subtotalVal.setText(String.format("K %.2f", b.subtotal));
        totalVal.setText(String.format("K %.2f", b.total));
        amountPaidVal.setText(String.format("K %.2f", b.paid));
        balanceVal.setText(String.format("K %.2f", b.balance));

        if (b.balance == 0 && b.paid > 0) {
            statusBadge.setText("PAID");
            statusBadge.setStyle("-fx-background-color: #0E2918; -fx-text-fill: #10B981; -fx-background-radius: 12; -fx-padding: 3 10; -fx-font-size: 10; -fx-font-weight: bold;");
        } else {
            statusBadge.setText("UNPAID");
            statusBadge.setStyle("-fx-background-color: #2D0F0F; -fx-text-fill: #EF4444; -fx-background-radius: 12; -fx-padding: 3 10; -fx-font-size: 10; -fx-font-weight: bold;");
        }
    }

    private CheckBox darkCheckbox(String title, String price) {
        CheckBox cb = new CheckBox(title + " (" + price + ")");
        cb.setStyle("-fx-text-fill: " + TEXT_WHITE + "; -fx-font-size: 11;");
        return cb;
    }

    private HBox summaryRow(String labelText, Label valueLbl) {
        Label l = lbl(labelText, "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        return new HBox(l, sp, valueLbl);
    }

    private void styleField(TextField tf) {
        tf.setStyle("-fx-background-color: " + FIELD_BG + "; -fx-text-fill: " + TEXT_WHITE + "; -fx-border-color: " + CARD_BORDER + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8 12;");
    }

    private Button blueButton(String text) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: " + CHELSEA_BLUE + "; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10 16; -fx-cursor: hand;");
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    private Button darkOutlineButton(String text) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-border-color: " + CARD_BORDER + "; -fx-border-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    private Button darkNavButton(String text) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: " + FIELD_BG + "; -fx-text-fill: " + TEXT_WHITE + "; -fx-border-color: " + CARD_BORDER + "; -fx-border-radius: 6; -fx-padding: 8 12; -fx-alignment: CENTER-LEFT; -fx-cursor: hand;");
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    private Button iconButton(String text) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: transparent; -fx-text-fill: " + CHELSEA_BLUE_LIGHT + "; -fx-font-weight: bold; -fx-cursor: hand;");
        return b;
    }

    private Label lbl(String text, String style) {
        Label l = new Label(text);
        l.setStyle(style);
        return l;
    }

    private TableColumn<LodgeModel.Bill, String> col(String title, double width, java.util.function.Function<LodgeModel.Bill, String> mapper) {
        TableColumn<LodgeModel.Bill, String> c = new TableColumn<>(title);
        c.setPrefWidth(width);
        c.setCellValueFactory(data -> new SimpleStringProperty(mapper.apply(data.getValue())));
        return c;
    }

    public void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }
}