import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * TextMenu runs the Menu in the console the user interacts with
 * Has a Plane object which contains all the seats
 *
 */
public class TextMenu {
    private Scanner sc = new Scanner(System.in, "UTF-8");
    private Plane pl;

    public TextMenu(Plane plane){
        pl = plane;
    }

    /**
     * Shows the main menu
     */
    public void mainMenu(){
        String usrInput = "intital";
        String quit = "Q";
        while (!usrInput.equals(quit)) {
            printMainMenuOptions();
            usrInput = sc.nextLine();
            if (usrInput.equals("P")) {
                pMenu();
            }
            else if (usrInput.equals("G")) {
                gMenu();
            }
            else if (usrInput.equals("A")) {
                aMenu();
            }
            else if (usrInput.equals("C")) {
                cMenu();
            }
            else if (usrInput.equals("M")) {
                mMenu();
            }
            else{
                if(!usrInput.equals(quit))
                {
                    System.out.println("Invalid input returning to Main Menu");
                }
            }
        }
        pl.saveToText();
        System.out.println("Bye!");
    }

    private void mMenu() {
        System.out.println("Service Class:");
        String classString = sc.nextLine();
        SeatClass seatClass;
        if(classString.equals("First")){
            seatClass = SeatClass.First;
            mChartFirst(seatClass);
        }
        else if(classString.equals("Economy")){
            seatClass = SeatClass.Economy;
            mChartEconomy(seatClass);
        }
        else{
            System.out.println("Request failed");
            return;
        }
        return;

    }

    private void mChartEconomy(SeatClass seatClass) {
        for(Seat s : pl.seatsByClass(seatClass)){
            if(null != s.getName()){
                System.out.println(s.getRow() + String.valueOf(s.getColumn()) + ": " + s.getName());
            }
        }
    }

    private void mChartFirst(SeatClass seatClass) {
        for(Seat s : pl.seatsByClass(seatClass)){
            if(null != s.getName()){
                System.out.println(s.getRow() + String.valueOf(s.getColumn()) + ": " + s.getName());
            }
        }
    }

    private void cMenu() {
        System.out.println("Cancel [I]ndividual or [G]roup?");
        String option = sc.nextLine();
        if(option.equals("I")){
            cMenuIndividual();
        }
        else if(option.equals("G")){
            cMenuGroup();
        }
        else{
            System.out.println("Request failed");
            return;
        }
    }

    private void cMenuGroup() {
        System.out.println("Group Name:");
        String groupName = sc.nextLine();
        boolean deleted = pl.deleteByGroup(groupName);
        if(deleted){
            System.out.println("Group: " + groupName + " has been canceled");
        }
        else{
            System.out.println("No Changes were made");
        }
    }

    private void cMenuIndividual() {
        System.out.println("Name:");
        String name = sc.nextLine();
        boolean deleted = pl.deleteByIndividual(name);
        if(deleted){
            System.out.println(name + " has been canceled");
        }
        else{
            System.out.println("No Changes were made");
        }
    }

    private void aMenu() {
        System.out.println("Service Class:");
        String classString = sc.nextLine();
        SeatClass seatClass;
        if(classString.equals("First")){
            seatClass = SeatClass.First;
            aChartFirst(seatClass);
        }
        else if(classString.equals("Economy")){
            seatClass = SeatClass.Economy;
            aChartEconomy(seatClass);
        }
        else{
            System.out.println("Request failed");
            return;
        }
        return;
    }
    private void aChartFirst(SeatClass s){
        System.out.println("First:");
        int row = 0;
        String openSeats = "";
        for(Seat someFirstClassSeats : pl.seatsByClass(s)){
            if(someFirstClassSeats.getRow() != row){
                System.out.println(openSeats);
                System.out.print(someFirstClassSeats.getRow() + ":");
                row = someFirstClassSeats.getRow();
                openSeats = "";
            }
            if(someFirstClassSeats.getName() == null){
                if(openSeats.length() > 0){
                    openSeats = openSeats + ",";
                }
                openSeats = openSeats + someFirstClassSeats.getColumn();
            }
        }
        System.out.println(openSeats);
    }
    private void aChartEconomy(SeatClass s){
        System.out.println("Economy:");
        int row = 0;
        int col = 0;
        String openSeats = "";
        for(Seat economyClassSeats : pl.seatsByClass(s)){
            if(economyClassSeats.getRow() != row){
                System.out.println(openSeats);
                System.out.print(economyClassSeats.getRow() + ":");
                row = economyClassSeats.getRow();
                col = 0;
                openSeats = "";
            }
            if(economyClassSeats.getName() == null){
                if(openSeats.length() > 0){
                    openSeats = openSeats + ",";
                }
                openSeats = openSeats + economyClassSeats.getColumn();
            }
        }
        System.out.println(openSeats);
    }


    private void gMenu() {
        System.out.println("Enter Group Name");
        String groupName = sc.nextLine();
        System.out.println("Enter Group Members (Separate by Comma)");
        String groupMembers = sc.nextLine();
        System.out.println("Enter Service Class(First,Economy): ");
        String serviceClass = sc.nextLine();
        SeatClass seatClass;
        if(serviceClass.equals("First")){
            seatClass = SeatClass.First;
        }
        else if(serviceClass.equals("Economy")){
            seatClass = SeatClass.Economy;
        }
        else{
            System.out.println("Request failed");
            return;
        }
        String[] memberNameArray = groupMembers.split(",");
        ArrayList<String> memberList = new ArrayList<>(Arrays.asList(memberNameArray));
        boolean added = pl.addGroup(seatClass,memberList,groupName);
        if(added){
            for(Seat s: pl.getGroupSeats(groupName)){
                System.out.println(s.getName() + " " + "Seated in " + s.getRow()+s.getColumn());
            }
        }
        else{
            System.out.println("Request Failed");
        }

    }


    private void pMenu() {
        System.out.println("Enter Name");
        String name = sc.nextLine();
        System.out.println("Enter Service Class(First,Economy): ");
        String serviceClass = sc.nextLine();
        SeatClass seatClass;
        if(serviceClass.equals("First")){
            seatClass = SeatClass.First;
        }
        else if(serviceClass.equals("Economy")){
            seatClass = SeatClass.Economy;
        }
        else{
            System.out.println("Request failed");
            return;
        }
        System.out.println("Enter Seat Preference(W,C,A): ");
        String seatPreference = sc.nextLine();
        SeatType seatType;
        if(seatPreference.equals("W")){
            seatType = SeatType.Window;
        }
        else if(seatPreference.equals("A")){
            seatType = SeatType.Aisle;
        }
        else if(seatPreference.equals("C")){
            seatType = SeatType.Center;
        }
        else{
            System.out.println("Request failed");
            return;
        }
        Seat rtSeat = pl.add(seatType,seatClass,name);
        if(rtSeat != null){
            System.out.println("Seat added on:" + rtSeat.getRow() + rtSeat.getColumn());
        }
        else{
            System.out.println("Request failed");
        }
    }

    private void printMainMenuOptions(){
        System.out.println("Add [P]assenger, Add [G]roup, [C]ancel Reservations, Print Seating [A]vailability Chart, Print [M]anifest, [Q]uit");
    }
}
