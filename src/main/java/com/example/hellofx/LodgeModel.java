package com.example.hellofx;

import java.util.LinkedHashMap;
import java.util.Map;

public class LodgeModel {

    // 5 Authorized Staff/Workers credentials
    private final Map<String, String> staffCredentials = new LinkedHashMap<>();

    private String selectedRoom = "Standard";
    private int nights = 1;

    public LodgeModel() {
        // Register exactly 5 authorized staff accounts (username -> password)
        staffCredentials.put("ASHTEE", "1234");
        staffCredentials.put("DIEHARD", "0202");
        staffCredentials.put("PHD", "2018");
        staffCredentials.put("ATR", "2017");
        staffCredentials.put("YNBLD", "0000");
    }

    public static class RoomType {
        public String name;
        public String desc;
        public double price;
        public String interiorType;

        public RoomType(String name, String desc, double price, String interiorType) {
            this.name = name;
            this.desc = desc;
            this.price = price;
            this.interiorType = interiorType;
        }
    }

    public static class Bill {
        public String id;
        public String guest;
        public String room;
        public int nights;
        public double foodReservations;
        public double extras;
        public double discount;
        public double subtotal;
        public double total;
        public double paid;
        public double balance;
        public String status;

        public Bill(String id, String guest, String room, int nights, double foodReservations,
                    double extras, double discount, double subtotal, double total, double paid, double balance, String status) {
            this.id = id;
            this.guest = guest;
            this.room = room;
            this.nights = nights;
            this.foodReservations = foodReservations;
            this.extras = extras;
            this.discount = discount;
            this.subtotal = subtotal;
            this.total = total;
            this.paid = paid;
            this.balance = balance;
            this.status = status;
        }
    }

    // Staff Authentication Validation
    public boolean validateStaffLogin(String username, String password) {
        if (username == null || password == null) return false;
        String storedPassword = staffCredentials.get(username.trim().toUpperCase());
        return storedPassword != null && storedPassword.equals(password.trim());
    }

    public boolean isStaffUsername(String username) {
        if (username == null) return false;
        return staffCredentials.containsKey(username.trim().toUpperCase());
    }

    public Map<String, String> getStaffList() {
        return staffCredentials;
    }

    // Room Configurations
    public Map<String, RoomType> getRoomTypes() {
        Map<String, RoomType> rooms = new LinkedHashMap<>();
        rooms.put("Standard", new RoomType("Standard", "Comfortable & Elegant", 350.00, "STANDARD"));
        rooms.put("Deluxe", new RoomType("Deluxe", "Spacious & Refined", 550.00, "DELUXE"));
        rooms.put("Suite", new RoomType("Suite", "Luxury & Privacy", 850.00, "SUITE"));
        rooms.put("Presidential", new RoomType("Presidential", "Ultimate Indulgence", 1500.00, "PRESIDENTIAL"));
        return rooms;
    }

    public String getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(String selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public Bill calculateBill(String guest, String bookingId, double paid,
                              boolean breakfast, boolean spa, boolean transfer,
                              boolean dinnerBuffet, boolean roomService, boolean luxuryChef) {

        RoomType rt = getRoomTypes().get(selectedRoom);
        double roomRate = (rt != null) ? rt.price : 350.00;
        double baseRoomTotal = roomRate * nights;

        // General Extras Calculation
        double extrasTotal = 0.0;
        if (breakfast) extrasTotal += (35.00 * nights);
        if (spa) extrasTotal += 120.00;
        if (transfer) extrasTotal += 80.00;

        // Food Reservations Calculation
        double foodTotal = 0.0;
        if (dinnerBuffet) foodTotal += (65.00 * nights);
        if (roomService) foodTotal += 50.00;
        if (luxuryChef) foodTotal += (150.00 * nights);

        double subtotal = baseRoomTotal + extrasTotal + foodTotal;

        // 5% discount if staying 5 nights or more
        double discount = (nights >= 5) ? (subtotal * 0.05) : 0.0;
        double total = subtotal - discount;

        double balance = Math.max(0.0, total - paid);
        String status = (balance == 0.0 && paid > 0) ? "PAID" : "UNPAID";

        String billId = (bookingId != null && !bookingId.isEmpty()) ? bookingId : "RES-" + (int)(Math.random() * 90000 + 10000);

        return new Bill(billId, guest, selectedRoom, nights, foodTotal, extrasTotal, discount, subtotal, total, paid, balance, status);
    }
}