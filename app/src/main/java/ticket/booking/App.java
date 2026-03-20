package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.service.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {

    public static void main(String[] args) {

        System.out.println("Running Train Booking System");

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        UserBookingService userBookingService;
        Train trainSelectedForBooking = null; // ✅ moved outside loop

        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        while (option != 7) {

            System.out.println("\nChoose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");

            option = scanner.nextInt();

            switch (option) {

                case 1:
                    System.out.println("Enter username:");
                    String nameToSignUp = scanner.next();

                    System.out.println("Enter password:");
                    String passwordToSignUp = scanner.next();

                    User userToSignup = new User(
                            nameToSignUp,
                            passwordToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp),
                            new ArrayList<>(),
                            UUID.randomUUID().toString()
                    );

                    if (userBookingService.signUp(userToSignup)) {
                        System.out.println("Signup successful!");
                    } else {
                        System.out.println("Signup failed!");
                    }
                    break;

                case 2:
                    System.out.println("Enter username:");
                    String nameToLogin = scanner.next();

                    System.out.println("Enter password:");
                    String passwordToLogin = scanner.next();

                    User userToLogin = new User(
                            nameToLogin,
                            passwordToLogin,
                            UserServiceUtil.hashPassword(passwordToLogin),
                            new ArrayList<>(),
                            UUID.randomUUID().toString()
                    );

                    try {
                        userBookingService = new UserBookingService(userToLogin);

                        if (userBookingService.loginUser()) {
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Invalid credentials!");
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.println("Fetching your bookings...");
                    userBookingService.fetchBookings();
                    break;

                case 4:
                    System.out.println("Enter source station:");
                    String source = scanner.next();

                    System.out.println("Enter destination station:");
                    String dest = scanner.next();

                    List<Train> trains = userBookingService.getTrains(source, dest);

                    if (trains.isEmpty()) {
                        System.out.println("No trains found!");
                        break;
                    }

                    int index = 1;
                    for (Train t : trains) {
                        System.out.println(index + ". Train ID: " + t.getTrainId());
                        index++;

                        for (Map.Entry<String, String> entry : t.getStationTimes().entrySet()) {
                            System.out.println("Station: " + entry.getKey() + " Time: " + entry.getValue());
                        }
                        System.out.println();
                    }

                    System.out.println("Select a train (1,2,3...):");
                    int selectedIndex = scanner.nextInt();

                    if (selectedIndex <= 0 || selectedIndex > trains.size()) {
                        System.out.println("Invalid selection!");
                        break;
                    }

                    trainSelectedForBooking = trains.get(selectedIndex - 1); // ✅ FIXED
                    break;

                case 5:
                    if (trainSelectedForBooking == null) {
                        System.out.println("Please search and select a train first!");
                        break;
                    }

                    System.out.println("Available seats:");

                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);

                    for (List<Integer> row : seats) {
                        for (Integer val : row) {
                            System.out.print(val + " ");
                        }
                        System.out.println();
                    }

                    System.out.println("Enter row:");
                    int row = scanner.nextInt();

                    System.out.println("Enter column:");
                    int col = scanner.nextInt();

                    System.out.println("Booking seat...");

                    Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row, col);

                    if (booked) {
                        System.out.println("Booked successfully!");
                    } else {
                        System.out.println("Seat already booked or invalid!");
                    }
                    break;

                case 6:
                    System.out.println("Enter ticket ID to cancel:");
                    String ticketId = scanner.next();

                    if (userBookingService.cancelBooking(ticketId)) {
                        System.out.println("Cancelled successfully!");
                    } else {
                        System.out.println("Cancellation failed!");
                    }
                    break;

                case 7:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }

        scanner.close();
    }
}