import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class Plane holds all seats
 * Plane parses files on load and writes on quit
 * if file matching fileName exists plane will be loaded
 * If no matching file found new plane will be created
 * Can add passengers to seats by group or individually
 * When adding groups
 * Passengers can also be deleted from seats by group or indiviually
 *
 * Java 11.0 API.
 * @author Wesley Zhao
 * @version 9/29/2022
 */
public class Plane {
    private ArrayList<Seat> seats = new ArrayList<>();
    private String fileName;

    /**
     * Used when file with name fileName is not found
     * Generates 8 first class seats in 1 rows and 120 economy seats in 30 rows
     */
    private void generateSeats() {
        //first class
        for (int i = 1; i <= 2; i++) {
            Seat newSeatA = new Seat(i, 'A', SeatType.Window, SeatClass.First, null);
            Seat newSeatB = new Seat(i, 'B', SeatType.Aisle, SeatClass.First, null);
            Seat newSeatC = new Seat(i, 'C', SeatType.Aisle, SeatClass.First, null);
            Seat newSeatD = new Seat(i, 'D', SeatType.Window, SeatClass.First, null);
            seats.add(newSeatA);
            seats.add(newSeatB);
            seats.add(newSeatC);
            seats.add(newSeatD);
        }
        //Economy
        for (int i = 10; i <= 29; i++) {
            Seat newSeatA = new Seat(i, 'A', SeatType.Window, SeatClass.Economy, null);
            Seat newSeatB = new Seat(i, 'B', SeatType.Center, SeatClass.Economy, null);
            Seat newSeatC = new Seat(i, 'C', SeatType.Aisle, SeatClass.Economy, null);
            Seat newSeatD = new Seat(i, 'D', SeatType.Aisle, SeatClass.Economy, null);
            Seat newSeatE = new Seat(i, 'E', SeatType.Center, SeatClass.Economy, null);
            Seat newSeatF = new Seat(i, 'F', SeatType.Window, SeatClass.Economy, null);
            seats.add(newSeatA);
            seats.add(newSeatB);
            seats.add(newSeatC);
            seats.add(newSeatD);
            seats.add(newSeatE);
            seats.add(newSeatF);
        }
        System.out.println("Empty Plane Generated");
    }

    /**
     * Seats a single passenger in the first seat matching seat type and seat class
     * Sets the name of the seat to the input name
     * @param st SeatType of the seat to be assigned
     * @param sc SeatClass of the seat to be assigned
     * @param pn Name of the passenger to be assigned to the seat
     * @return Seat for which passenger has been assigned null if no seat could be found
     */
    public Seat add(SeatType st, SeatClass sc, String pn){
        for(Seat s : seats){
            if(s.getSeatClass().equals(sc) && s.getSeatType().equals(st) && s.getName() == null){
                s.setName(pn);
                return s;
            }
        }
        return null;
    }

    /**
     * Removes the assignment for all seats matching the group
     * Sets the name and group to null
     * @param s Name of the group to search for to remove assignment
     * @return True if seat assignments have been deleted. False if no seats matching the group were found
     */
    public boolean deleteByGroup(String s){
        boolean rtval = false;
        for(Seat seat : seats){
            if(s.equals(seat.getGroup())){
                seat.setEmpty();
                rtval = true;
            }
        }
        return rtval;
    }

    /**
     * Removes the assignment for all seats matching the name
     * Sets the name and group to null
     * @param s Passenger name to search for to remove assignment
     * @return True if seat assignments have been deleted. False if no seats matching the group were found
     */
    public boolean deleteByIndividual(String s) {
        boolean rtval = false;
        for(Seat seat : seats){
            if(s.equals(seat.getName())){
                seat.setEmpty();
                return true;
            }
        }
        return rtval;
    }

    /**
     * Returns Arraylist of Seats matching the SeatClass
     * @param seatClass SeatClass to be searched for
     * @return Arraylist with Seats in the specified SeatClass
     */
    public ArrayList<Seat> seatsByClass(SeatClass seatClass){
        ArrayList<Seat> returnList = new ArrayList<>();
        for(Seat s : seats){
            if(seatClass.equals(s.getSeatClass())){
                returnList.add(s);
            }
        }
        return returnList;
    }

    /**
     * Assigns seats in a group
     * Sets the group to the groupName
     * Sets names until groupMembers no longer has any names
     * Fills the group to the largest contigious row.
     * If names will not fit in one row it will find the next largest row untill all names have been seated
     * If there are more group members than open seats in the given seatclass will return false
     * @param sc Seat class to assign the group to
     * @param groupMembers Names of the group members to be seated
     * @param groupName Name of the group to be assigned
     * @return True if group is seated. False if enough seats could not be found
     */
    public boolean addGroup(SeatClass sc, ArrayList<String> groupMembers, String groupName){
        // ROW, OpenSeats
        if(groupMembers.size() == 0){
            return true;
        }
        //First calculate free seats in class
        int totalFreeSeats = 0;
        Map<Integer, Integer> openSeatCountPerRow = new LinkedHashMap<>();
        for(Seat s : seats){
            if(s.getSeatClass() == sc && s.getName() == null){
                totalFreeSeats++;
                if(openSeatCountPerRow.get(s.getRow()) == null){
                    openSeatCountPerRow.put(s.getRow(),1);
                }
                else{
                    int temp = openSeatCountPerRow.get(s.getRow());
                    openSeatCountPerRow.put(s.getRow(),temp + 1);
                }
            }
        }
        if(totalFreeSeats < groupMembers.size()){
            return false;
        }
        int longestLength = 0;
        int longestRow = 0;
        //Find the first longest row
        for (var entry : openSeatCountPerRow.entrySet()){
            if(entry.getValue() > longestLength){
                longestLength = entry.getValue();
                longestRow = entry.getKey();
            }
        }
        //fill seat into longest row and call recursivley
        for(Seat s: seats){
            if(s.getRow() == longestRow && s.getName() == null){
                s.setGroup(groupName);
                s.setName(groupMembers.get(0));
                groupMembers.remove(0);
                if(groupMembers.size() == 0){
                    break;
                }
            }
        }
        return addGroup(sc,groupMembers,groupName);
    }

    /**
     * Returns an ArrayList of seats matching the group name
     * @param groupName The group name to be searched for
     * @return Arraylist of all seats matching the group name
     */
    public ArrayList<Seat> getGroupSeats(String groupName){
        ArrayList<Seat> returnList = new ArrayList<>();
        if(groupName == null){
            return returnList;
        }
        for(Seat s : seats){
            if(groupName.equals(s.getGroup())){
                returnList.add(s);
            }
        }
        return returnList;
    }

    /**
     * Returns the entire seatList in this plane
     * @return The entire seat list in this plane
     */
    public ArrayList<Seat> returnSeatList(){
        return seats;
    }

    /**
     * If file name s exists will load from text
     * Expects format: "R,C,SeatType,SeatClass,GroupName,Passenger Name"
     * If file is not created plane will be created with all seats unassigned
     * File will be created/overwritten on quit.
     * @param s
     */
    public void loadFromText(String s){
        fileName = s;
        try{
            String filePath = "./" + s;
            File file = new File(filePath);
            if (!file.exists()) {
                this.generateSeats();
                return;
            }
            BufferedReader readFromFile = new BufferedReader(new FileReader(filePath));
            String seatString = readFromFile.readLine();
            while(seatString != null){
                String[] seatStringArray = seatString.split(",");
                int newRow = Integer.parseInt(seatStringArray[0]);
                char newColumn = seatStringArray[1].charAt(0);
                SeatType st = SeatType.valueOf(seatStringArray[2]);
                SeatClass sc = SeatClass.valueOf(seatStringArray[3]);
                String newName = null;
                String newGroup = null;
                if(seatStringArray.length >= 6){
                    newGroup = seatStringArray[4];
                    newName = seatStringArray[5];
                }
                Seat newSeat = new Seat(newRow, newColumn, st, sc, newName);
                newSeat.setGroup(newGroup);
                seats.add(newSeat);
                seatString = readFromFile.readLine();
            }
            System.out.println("File Loaded");
            readFromFile.close();
        } catch(IOException e){
            System.out.println("File read error.");
        }

    }

    /**
     * Plane is saved as text document
     * with every line representing a seat in format "R,C,SeatType,SeatClass,GroupName,Passenger Name"
     *
     */
    public void saveToText(){
        BufferedWriter bufferedWriter = null;
        try{
            String filePath = "./" + fileName;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,false);//overwrite old file
            bufferedWriter = new BufferedWriter(fw);
            for(Seat s: seats){
                bufferedWriter.write(s.getRow() + "," + s.getColumn()  + "," + s.getSeatType()  + "," + s.getSeatClass() + ",");
                if(s.getGroup() != null){
                    bufferedWriter.write(s.getGroup());
                }
                bufferedWriter.write(",");
                if(s.getName() != null){
                    bufferedWriter.write(s.getName());
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try{
                if(bufferedWriter!=null)
                    bufferedWriter.close();
            }catch(Exception ex){
                System.out.println("Error in closing the BufferedWriter"+ex);
            }

        }
    }


}
