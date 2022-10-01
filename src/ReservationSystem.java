/**
 * Reservation system takes in file name as parameter
 * If file does not exist it creates a new empty plane
 * Upon exit it saves the assigned seats in following format
 * R=Row, C=Column
 * R,C,SeatType,SeatClass,GroupName,Passenger Name
 * Java 11.0 API.
 * @author Wesley Zhao
 * @version 9/29/2022
 */

public class ReservationSystem {
    public static void main(String[] args) {
        String fileName = args[0];
        System.out.println(fileName);
        Plane aNewFlight = new Plane();
        aNewFlight.loadFromText(fileName);
        TextMenu tm = new TextMenu(aNewFlight);
        tm.mainMenu();
    }
}
