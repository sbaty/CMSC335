import java.util.ArrayList;
import java.util.Scanner;

final class SeaPort extends Thing {
    // Declaring variables
    private ArrayList<Dock> docks;
    private ArrayList<Ship> Queue;
    private ArrayList<Ship> ships;
    private ArrayList<Person> persons;

    protected SeaPort(Scanner scanContent) {
        super(scanContent);
        this.setDocks(new ArrayList<>());
        this.setQueue(new ArrayList<>());
        this.setShips(new ArrayList<>());
        this.setPersons(new ArrayList<>());
    }

    private void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }


    private void setQueue(ArrayList<Ship> Queue) {
        this.Queue = Queue;
    }

    private void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    private void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
 
    protected ArrayList<Dock> getDocks() {
        return this.docks;
    }

    protected ArrayList<Ship> getQueue() {
        return this.Queue;
    }

    protected ArrayList<Ship> getShips() {
        return this.ships;
    }

    protected ArrayList<Person> getPersons() {
        return this.persons;
    }

    @Override
    public String toString() {
        String stringOutput;

        stringOutput = "\n\nSeaPort: " + super.toString();
        for (Dock dock: this.getDocks()) {
            stringOutput += "\n> " + dock.toString();
        }

        stringOutput += "\n\n --- List of all ships in Queue:";
        for (Ship shipQueue: this.getQueue()) {
            stringOutput += "\n> " + shipQueue.toString();
        }

        stringOutput += "\n\n --- List of all ships:";
        for (Ship shipAll: this.getShips()) {
            stringOutput += "\n> " + shipAll.getName() + " " + shipAll.getIndex() + " ("
                + shipAll.getClass().getSimpleName() + ")";
        }

        stringOutput += "\n\n --- List of all persons:";
        for (Person person: this.getPersons()) {
            stringOutput += "\n> " + person.toString();
        }

        return stringOutput;
    }
}