import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Room class represents a room in the hotel
class Room {
    private String roomNumber;
    private String roomType; // eg., Single, Double, Suite
    private double price;
    private boolean isAvailable;

    public Room(String roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", Type: " + roomType + ", Price: $" + price + ", Available: " + isAvailable;
    }
}

// Reservation class represents a reservation made by a user
class Reservation {
    private String guestName;
    private Room room;
    private int nights;
    private double totalAmount;

    public Reservation(String guestName, Room room, int nights) {
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.totalAmount = room.getPrice() * nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Reservation for " + guestName + ": " + room.getRoomType() + " Room " + room.getRoomNumber() + " for " + nights + " night(s). Total: $" + totalAmount;
    }
}

// Payment class to handle payment processing
class Payment {
    public static boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + "...");
        System.out.println("Payment successful!");
        return true;
    }
}

// HotelReservationSystem class contains the main logic
public class HotelReservationSystem {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to the Hotel Reservation System");
            System.out.println("1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View booking details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchAvailableRooms();
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewBookingDetails();
                    break;
                case 4:
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room("101", "Single", 100));
        rooms.add(new Room("102", "Double", 150));
        rooms.add(new Room("103", "Suite", 300));
        rooms.add(new Room("104", "Single", 100));
        rooms.add(new Room("105", "Double", 150));
    }

    private static void searchAvailableRooms() {
        System.out.println("Available rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.print("Enter your name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        scanner.nextLine();

        Room room = findRoomByNumber(roomNumber);
        if (room == null) {
            System.out.println("Room not found.");
        } else if (!room.isAvailable()) {
            System.out.println("Room is not available.");
        } else {
            double totalAmount = room.getPrice() * nights;
            if (Payment.processPayment(totalAmount)) {
                room.setAvailable(false);
                Reservation reservation = new Reservation(guestName, room, nights);
                reservations.add(reservation);
                System.out.println("Reservation successful!");
            } else {
                System.out.println("Payment failed. Reservation not completed.");
            }
        }
    }

    private static Room findRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    private static void viewBookingDetails() {
        System.out.println("Booking details:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
