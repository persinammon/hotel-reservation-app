
import api.AdminResource;
import api.HotelResource;

import java.util.Scanner;
import java.util.Collection;
import java.util.Optional;
import java.util.Date;
import java.util.HashSet;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;


import model.*;

public class HotelApplication {

    private static Scanner scan;
    private HotelResource hotel;
    private AdminResource admin;

    private Calendar calendar;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("What would you like to do today? ");
        HotelApplication app = new HotelApplication(scan);
    }

    public HotelApplication(Scanner scan){
        hotel = HotelResource.getInstance();
        admin = AdminResource.getInstance();
        calendar = Calendar.getInstance();

        printMainMenu(scan);
    }

    private void stringMainMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("---------------------------------------------");
        System.out.println("Please select a number from the menu options");
    }

    public void printMainMenu(Scanner scan) {

        stringMainMenu();
        String response;
        Integer i = 0;
        boolean runProgram = true;
        while (runProgram) {
            response = scan.nextLine();
            try {
                i = Integer.valueOf(response);
            } catch (Exception e) {
                System.out.println("Sorry, that is not a valid response.");
            }
            switch (i) {
                case 1:
                    System.out.println("Do you have an account with us? (Y/N)");
                    response = scan.nextLine();
                    if (response.equals("Y")) {
                        System.out.println("What is the email associated with your account?");
                        response = scan.nextLine();
                        Optional<Customer> customer = hotel.getCustomer(response);
                        if (customer.isEmpty()) {
                            System.out.println("Sorry, we do not have an account with the email " +
                                    response);
                        } else {
                            System.out.println("What dates would you like to visit our hotel? Please" +
                                    " enter the dates in the format \"YYYY-MM-DD to YYYY-MM-DD\" .");
                            String dates = scan.nextLine();
                            String[] splitStr = dates.trim().split("\\s+");
                            String date1String = splitStr[0];
                            String date2String = splitStr[2];
                            String[] splitdate1 = date1String.split("-");
                            String[] splitdate2 = date2String.split("-");

                            try {
                                calendar.set(Integer.parseInt(splitdate1[0]), Integer.parseInt(splitdate1[1]),
                                        Integer.parseInt(splitdate1[2]));
                                Date checkIn = calendar.getTime();
                                calendar.set(Integer.parseInt(splitdate2[0]), Integer.parseInt(splitdate2[1]),
                                        Integer.parseInt(splitdate2[2]));
                                Date checkOut = calendar.getTime();
                                Collection<IRoom> availableRooms = hotel.findARoom(checkIn, checkOut);
                                if (availableRooms.size() == 0) {
                                    System.out.println("Sorry, there are no available rooms during that time period.");
                                } else {
                                    System.out.println("The following rooms are available:");
                                    Collection<String> roomNumbers = new HashSet<String>();
                                    for (IRoom room : availableRooms) roomNumbers.add(room.getRoomNumber());
                                    for (IRoom room : availableRooms) System.out.println("Room " + room.getRoomNumber() + " $" +
                                            room.getRoomPrice() + " Per Night Type: " + room.getRoomType().toString() + "" +
                                            " Occupancy");
                                    System.out.println("Please pick a room to reserve or hit enter to return to the main menu.");
                                    response = scan.nextLine();
                                    if (roomNumbers.contains(response)) {
                                        try {
                                            String email = customer.get().getEmail();
                                            /** Potential crash here. **/
                                            IRoom room = hotel.getRoom(response).get();
                                            hotel.bookARoom(email, room, checkIn, checkOut);
                                            System.out.println("Success! You're booked for " + date1String + " to " + date2String);
                                        } catch (Exception e) {
                                            System.out.println("Sorry, the reservation failed to be booked.");
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Please enter dates in the requested format.");
                            }
                        }
                    } else if (response.equals("N")) {
                        System.out.println("Please create an account with us using the menu option.");
                    } else {
                        System.out.println("Sorry, did not recognize the input. Please select from the options.");
                    }
                    break;
                case 2:
                    System.out.println("What is the email associated with your account?");
                    String email = scan.nextLine().trim();
                    Optional<Customer> customer = hotel.getCustomer(email);
                    if (customer.isPresent()) {
                        System.out.println("Here are your current reservations:");
                        Collection<Reservation> reservations = hotel.getCustomersReservations(email);
                        for (Reservation r : reservations) {
                            System.out.println("Room " + r.getRoom().getRoomNumber() + " with " +
                                    r.getRoom().getRoomType().toString() + " occupancy from " +
                                    r.getCheckInDate().toString() + " to " +
                                    r.getCheckOutDate().toString());
                        }
                        System.out.println(" ");
                    } else {
                        System.out.println("An account with that email does not exist.");
                    }
                    break;
                case 3:
                    System.out.println("What is your first name?");
                    String firstName = scan.nextLine();
                    System.out.println("What is your last name?");
                    String lastName = scan.nextLine();
                    System.out.println("What is the email you would like to register your account under?");
                    String emailCurr = scan.nextLine();
                    if (hotel.getCustomer(emailCurr).isPresent()) {
                        System.out.println("The email is already in use. Please try again with a different email.");
                    } else {
                        hotel.createACustomer(emailCurr, firstName, lastName);
                        System.out.println("A new account has been created! Otherwise, the email is an invalid format.");
                    }
                    break;
                case 4: printAdminMenu(scan);
                        break;
                case 5: runProgram = false;
                        break;
                default: ;
            }
            if (!runProgram) {
                break;
            }
            stringMainMenu();
        }
        System.out.println("---------------------------------------------");
        System.out.println("Thanks for visiting our hotel!");
        System.out.println("    |      ");
        System.out.println("  \\ | /     ");
        System.out.println("   \\*/     ");
        System.out.println("--**O**--   ");
        System.out.println("   /*\\      ");
        System.out.println("  / | \\     ");
        System.out.println("    |        ");
    }

    private void stringAdminMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to Main Menu");
        System.out.println("---------------------------------------------");
        System.out.println("Please select a number from the menu options");
    }

    public void printAdminMenu(Scanner scan) {
        stringAdminMenu();
        boolean runProgram = true;
        while (runProgram) {
            String response = scan.nextLine();
            Integer i = 0;
            try {
                i = Integer.valueOf(response);
            } catch (Exception e) {
                System.out.println("Sorry, that is not a valid response.");
            }
            switch (i) {
                case 1:
                    System.out.println("All customers:");
                    Collection<Customer> customers = admin.getAllCustomers();
                    for (Customer c : customers) {
                        System.out.println(c.toString());
                    }
                    break;
                case 2:
                    System.out.println("All rooms:");
                    Collection<IRoom> rooms = admin.getAllRooms();
                    for (IRoom r : rooms) {
                        System.out.println(r.toString());
                    }
                    break;
                case 3:
                    System.out.println("All reservations:");
                    admin.displayAllReservations();
                    break;
                case 4:
                    System.out.println("Please enter details of the new room.");
                    System.out.println("Room Number:");
                    response = scan.nextLine();
                    if (hotel.getRoom(response).isPresent()) {
                        System.out.println("The room is already entered.");
                    } else {
                        String roomNumber = response;
                        System.out.println("Cost per night (USD):");
                        response = scan.nextLine();
                        try {
                            Double cost = Double.valueOf(response);
                            System.out.println("Occupancy type (Single/Double):");
                            response = scan.nextLine();
                            RoomType type = RoomType.SINGLE;
                            if (response.equals("Single")) {
                                type = RoomType.SINGLE;
                            } else if (response.equals("Double")) {
                                type = RoomType.DOUBLE;
                            }
                            Room room = new Room(roomNumber, cost, type);
                            List<IRoom> roomList = new ArrayList<IRoom>();
                            roomList.add(room);
                            admin.addRoom(roomList);
                            System.out.println("Room added.");
                        } catch (Exception e) {
                            System.out.println("Please enter a valid price value.");
                        }
                    }
                    break;
                case 5:
                    runProgram = false;
                    printMainMenu(scan);
                    break;
                default: ;
            }
            stringAdminMenu();
        }
    }

}
