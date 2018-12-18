import java.util.Scanner;


final class PassengerShip extends Ship {

    // Rubric-required fields
    private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;

    protected PassengerShip(Scanner scannerContents) {
        super(scannerContents);

        if (scannerContents.hasNextInt()) {
            this.setNumberOfPassengers(scannerContents.nextInt());
        }

        if (scannerContents.hasNextInt()) {
            this.setNumberOfRooms(scannerContents.nextInt());
        }

        if (scannerContents.hasNextInt()) {
            this.setNumberOfOccupiedRooms(scannerContents.nextInt());
        }
    }

    // Setters


    private void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }


    private void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }


    private void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
        this.numberOfOccupiedRooms = numberOfOccupiedRooms;
    }

    // Getters

    protected int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    protected int getNumberOfRooms() {
        return this.numberOfRooms;
    }

  
    protected int getNumberOfOccupiedRooms() {
        return this.numberOfOccupiedRooms;
    }

    // Overriden methods


    @Override
    public String toString() {
        String stringOutput;

        stringOutput = "Passenger Ship: " + super.toString() + "\n\tPassengers: "
            + this.getNumberOfPassengers() + "\n\tRooms: " + this.getNumberOfRooms()
            + "\n\tOccupied Rooms: " + this.getNumberOfOccupiedRooms();

        return stringOutput;
    }
}