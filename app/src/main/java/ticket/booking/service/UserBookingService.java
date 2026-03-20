package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<User> userList = new ArrayList<>();

    private User user;

    // ✅ Store in project root
    private final String USER_FILE_PATH = "users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    // ✅ Load users safely
    private void loadUserListFromFile() throws IOException {
        File file = new File(USER_FILE_PATH);

        // If file doesn't exist → create empty list
        if (!file.exists()) {
            userList = new ArrayList<>();
            return;
        }

        userList = objectMapper.readValue(
                file,
                new TypeReference<List<User>>() {}
        );
    }

    // ✅ Save users
    private void saveUserListToFile() throws IOException {
        File file = new File(USER_FILE_PATH);
        objectMapper.writeValue(file, userList);
    }

    // ✅ Login
    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream()
                .filter(user1 ->
                        user1.getName().equals(user.getName()) &&
                                UserServiceUtil.checkPassword(
                                        user.getPassword(),
                                        user1.getHashedPassword()
                                )
                )
                .findFirst();

        return foundUser.isPresent();
    }

    // ✅ Signup
    public Boolean signUp(User newUser) {
        try {
            userList.add(newUser);
            saveUserListToFile();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // ✅ Fetch bookings
    public void fetchBookings() {
        Optional<User> userFetched = userList.stream()
                .filter(user1 ->
                        user1.getName().equals(user.getName()) &&
                                UserServiceUtil.checkPassword(
                                        user.getPassword(),
                                        user1.getHashedPassword()
                                )
                )
                .findFirst();

        userFetched.ifPresent(User::printTickets);
    }

    // ✅ Cancel booking (cleaned)
    public Boolean cancelBooking(String ticketId) {
        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Invalid ticket ID");
            return false;
        }

        boolean removed = user.getTicketsBooked()
                .removeIf(ticket -> ticket.getTicketId().equals(ticketId));

        if (removed) {
            System.out.println("Ticket cancelled successfully");
            return true;
        } else {
            System.out.println("Ticket not found");
            return false;
        }
    }

    // ✅ Get trains
    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ✅ Fetch seats
    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }

    // ✅ Book seat
    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();

            if (row >= 0 && row < seats.size() &&
                    seat >= 0 && seat < seats.get(row).size()) {

                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true;
                }
            }

            return false;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}