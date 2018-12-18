import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


final class World extends Thing {

    // Rubric-required fields
    private ArrayList<Thing> allThings;
    private ArrayList<SeaPort> ports;
    private PortTime time;

    protected World(Scanner scannerContents) {
        super(scannerContents);
        this.setAllThings(new ArrayList<>());
        this.setPorts(new ArrayList<>());
        this.process(scannerContents);
    }

    // Setters

    private void setAllThings(ArrayList<Thing> allThings) {
        this.allThings = allThings;
    }

    private void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }

    private void setTime(PortTime time) {
        this.time = time;
    }

    // Getters

    protected ArrayList<Thing> getAllThings() {
        return this.allThings;
    }

    protected ArrayList<SeaPort> getPorts() {
        return this.ports;
    }

    protected PortTime getTime() {
        return this.time;
    }

    // Handlers


    private void process(Scanner scannerContents) {

        // Assorted method fields
        String lineString;
        Scanner lineContents;

        while (scannerContents.hasNextLine()) {
            lineString = scannerContents.nextLine().trim(); // Remove spaces

            // Avoid evaluating any blank lines if exist
            if (lineString.length() == 0) {
                continue;
            }

            // Scanner for each line's contents
            lineContents = new Scanner(lineString);

            if (lineContents.hasNext()) {

                switch(lineContents.next().trim()) {
                    case "port":
                        SeaPort newSeaPort = new SeaPort(lineContents);
                        this.getAllThings().add(newSeaPort);
                        this.getPorts().add(newSeaPort);
                        break;
                    case "dock":
                        Dock newDock = new Dock(lineContents);
                        this.getAllThings().add(newDock);
                        this.addThingToList(newDock, "getDocks");
                        break;
                    case "pship":
                        PassengerShip newPassengerShip = new PassengerShip(lineContents);
                        this.getAllThings().add(newPassengerShip);
                        this.addShipToParent(newPassengerShip);
                        break;
                    case "cship":
                        CargoShip newCargoShip = new CargoShip(lineContents);
                        this.getAllThings().add(newCargoShip);
                        this.addShipToParent(newCargoShip);
                        break;
                    case "person":
                        Person newPerson = new Person(lineContents);
                        this.getAllThings().add(newPerson);
                        this.addThingToList(newPerson, "getPersons");
                        break;
                    case "job":
                        Job newJob = new Job(lineContents);
                        this.getAllThings().add(newJob);
                        this.addJobToShip(newJob);
                        break;
                    default: // Added because required by Google styleguide
                        break;
                }
            }
        }
    }


    private <T extends Thing> T getImmediateParentByIndex(ArrayList<T> thingsList, int index) {
        for (T thing : thingsList) {
            if (thing.getIndex() == index) {
                return thing;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T extends Thing> T getThingByIndex(int index, String methodName) {

        // Declarations
        Method getList;
        T newThing;
        ArrayList<T> thingsList;

        try {
            // Either SeaPort.class.getShips or SeaPort.class.getDocks
            getList = SeaPort.class.getDeclaredMethod(methodName);
            for (SeaPort port : this.getPorts()) {

 
                thingsList = (ArrayList<T>) getList.invoke(port);
                newThing = this.getImmediateParentByIndex(thingsList, index);

                if (newThing != null) {
                    return newThing;
                }
            }
        } catch (
            NoSuchMethodException |
            SecurityException |
            IllegalAccessException |
            IllegalArgumentException |
            InvocationTargetException ex
        ) {
            System.out.println("Error: " + ex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T extends Thing> void addThingToList(T newThing, String methodName) {

        // Declarations
        SeaPort newPort;
        ArrayList<T> thingsList;
        Method getList;

        // Definition
        newPort = this.getImmediateParentByIndex(this.getPorts(), newThing.getParent());

        try {
            // Either SeaPort.class.getPersons() or SeaPort.class.getDocks();
            getList = SeaPort.class.getDeclaredMethod(methodName);

            // See casting comment in above method
            thingsList = (ArrayList<T>) getList.invoke(newPort); // newPort.getList()

            if (newPort != null) {
                thingsList.add(newThing);
            }
        } catch (
            NoSuchMethodException |
            SecurityException |
            IllegalAccessException |
            IllegalArgumentException |
            InvocationTargetException ex
        ) {
            System.out.println("Error: " + ex);
        }
    }

    private void addJobToShip(Job newJob) {
        Dock newDock;
        Ship newShip = this.getThingByIndex(newJob.getParent(), "getShips");

        if (newShip != null) {
            newShip.getJobs().add(newJob);
        } else {
            newDock = this.getThingByIndex(newJob.getParent(), "getDocks");
            newDock.getShip().getJobs().add(newJob);
        }
    }


    private void addShipToParent(Ship newShip) {
        SeaPort myPort;
        Dock myDock = this.getThingByIndex(newShip.getParent(), "getDocks");

        if (myDock == null) {
            myPort = this.getImmediateParentByIndex(this.getPorts(), newShip.getParent());
            myPort.getShips().add(newShip);
            myPort.getQue().add(newShip);
        } else {
            myPort = this.getImmediateParentByIndex(this.getPorts(), myDock.getParent());
            myDock.setShip(newShip);
            myPort.getShips().add(newShip);
        }
    }

    // Overridden methods

    @Override
    public String toString() {
        String stringOutput = ">>>>> The world:\n";

        for (SeaPort seaPort : this.getPorts()) {
            stringOutput += seaPort.toString() + "\n";
        }
        return stringOutput;
    }
}