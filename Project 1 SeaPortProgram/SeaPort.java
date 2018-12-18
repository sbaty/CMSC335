import java.util.ArrayList;
import java.util.Scanner;


final class SeaPort extends Thing {

    // Rubric-required <code>ArrayList</code>s
    private ArrayList<Dock> docks;
    private ArrayList<Ship> que;
    private ArrayList<Ship> ships;
    private ArrayList<Person> persons;

    protected SeaPort(Scanner scannerContents) {
        super(scannerContents);
        this.setDocks(new ArrayList<>());
        this.setQue(new ArrayList<>());
        this.setShips(new ArrayList<>());
        this.setPersons(new ArrayList<>());
    }

    // Setters


    private void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }


    private void setQue(ArrayList<Ship> que) {
        this.que = que;
    }

    private void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }


    private void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    // Getters

 
    protected ArrayList<Dock> getDocks() {
        return this.docks;
    }


    protected ArrayList<Ship> getQue() {
        return this.que;
    }

    protected ArrayList<Ship> getShips() {
        return this.ships;
    }

    protected ArrayList<Person> getPersons() {
        return this.persons;
    }

    // Overridden methods

    @Override
    public String toString() {
        String stringOutput;

        // A near-identical implementation of the method as denoted in the rubric
        stringOutput = "\n\nSeaPort: " + super.toString();
        for (Dock dock: this.getDocks()) {
            stringOutput += "\n> " + dock.toString();
        }

        stringOutput += "\n\n --- List of all ships in que:";
        for (Ship shipQue: this.getQue()) {
            stringOutput += "\n> " + shipQue.toString();
        }

        // Since the above output displays ship-related details, this one is just a quick summary
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