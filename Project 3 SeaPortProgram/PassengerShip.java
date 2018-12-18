import java.util.Scanner;
import java.util.HashMap;

public class PassengerShip extends Ship {
	
	//Declaring variables
	private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;
	
    protected PassengerShip(Scanner scanContent, HashMap<Integer, Dock> docksMap,
            HashMap<Integer, SeaPort> portsMap) {
        super(scanContent, docksMap, portsMap);

        if (scanContent.hasNextInt()) {
            this.setNumberOfPassengers(scanContent.nextInt());
        }

        if (scanContent.hasNextInt()) {
            this.setNumberOfOccupiedRooms(scanContent.nextInt());
        }
		        if (scanContent.hasNextInt()) {
            this.setNumberOfRooms(scanContent.nextInt());
        }
    }

    // Getters and Setters

    private void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }


    private void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
        this.numberOfOccupiedRooms = numberOfOccupiedRooms;
    }
	
	    private void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }


    protected int getNumberOfRooms() {
        return this.numberOfRooms;
    }

  
    protected int getNumberOfOccupiedRooms() {
        return this.numberOfOccupiedRooms;
    }
	
    protected int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    @Override
    public String toString() {
        String stringOutput;

        stringOutput = "Passenger Ship: " + super.toString() + "\n\tPassengers: "
            + this.getNumberOfPassengers() + "\n\tRooms: " + this.getNumberOfRooms()
            + "\n\tOccupied Rooms: " + this.getNumberOfOccupiedRooms();

        return stringOutput;
    }
}