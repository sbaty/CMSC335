import java.util.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class World extends Thing {
	
	//Declaring variables
	private ArrayList<Thing> allThings;
    private ArrayList<SeaPort> ports;
    private PortTime time;
	
	protected World(Scanner scanContent) {
		super(scanContent);
        this.setAllThings(new ArrayList<>());
        this.setPorts(new ArrayList<>());
        this.process(scanContent);
    }
	
    //Getters and Setters
    private void setAllThings(ArrayList<Thing> allThings) {
        this.allThings = allThings;
    }

    private void setPorts(ArrayList<SeaPort> ports) {
        this.ports = ports;
    }
    
    @SuppressWarnings("unused")
	private void setTime(PortTime time) {
        this.time = time;
    }
    
    protected ArrayList<Thing> getAllThings() {
        return this.allThings;
    }
    protected PortTime getTime() {
        return this.time;
    }
    protected ArrayList<SeaPort> getPorts() {
        return this.ports;
    }
    private void process(Scanner scanContent) {

        String stringLine;
        Scanner lineData;

        HashMap<Integer, Dock> docksMap = new HashMap<>();
        HashMap<Integer, SeaPort> portsMap = new HashMap<>();
        HashMap<Integer, Ship> shipsMap = new HashMap<>();
        
        while (scanContent.hasNextLine()) {
            stringLine = scanContent.nextLine().trim();

            if (stringLine.length() == 0) {
                continue;
            }

            lineData = new Scanner(stringLine);

            if (lineData.hasNext()) {

                switch(lineData.next().trim()) {

                    case "dock":
                        Dock newDock = new Dock(lineData);
                        this.getAllThings().add(newDock);
                        this.addThingToList(portsMap, newDock, "getDocks");
                        docksMap.put(newDock.getIndex(), newDock);
                        break;
                    case "port":
                        SeaPort newSeaPort = new SeaPort(lineData);
                        this.getAllThings().add(newSeaPort);
                        this.getPorts().add(newSeaPort);
                        portsMap.put(newSeaPort.getIndex(), newSeaPort);
                        break;
                    case "cship":
                        CargoShip newCargoShip = new CargoShip(lineData);
                        this.getAllThings().add(newCargoShip);
                        this.addShipToParent(newCargoShip,docksMap, portsMap);
                        shipsMap.put(newCargoShip.getIndex(), newCargoShip);
                        break;
                    case "pship":
                        PassengerShip newPassengerShip = new PassengerShip(lineData);
                        this.getAllThings().add(newPassengerShip);
                        this.addShipToParent(newPassengerShip,docksMap, portsMap);
                        shipsMap.put(newPassengerShip.getIndex(), newPassengerShip);
                        break;
                    case "person":
                        Person newPerson = new Person(lineData);
                        this.getAllThings().add(newPerson);
                        this.addThingToList(portsMap, newPerson, "getPersons");
                        break;   
                    case "job":
                        Job newJob = new Job(lineData);
                        this.getAllThings().add(newJob);
                        this.addJobToShip(newJob, shipsMap, docksMap);
                        break;
                           
                    default: 
                        break;
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked" })
    private <T extends Thing> void addThingToList(HashMap<Integer, SeaPort> portsMap, T newThing,
            String methodName) { 
    SeaPort newSeaPort;
    ArrayList<T> listThings;
    Method getList;

    newSeaPort = portsMap.get(newThing.getParent());

    try {
        getList = SeaPort.class.getDeclaredMethod(methodName);

        listThings = (ArrayList<T>) getList.invoke(newSeaPort); 

        if (newSeaPort != null) {
            listThings.add(newThing);
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
    private void addJobToShip(Job newJob, HashMap<Integer, Ship> shipsMap, HashMap<Integer, Dock> docksMap) {
    Dock newDock;
    Ship newShip = shipsMap.get(newJob.getParent());

    if (newShip != null) {
        newShip.getJobs().add(newJob);
    } else {
        newDock = docksMap.get(newJob.getParent());
        newDock.getShip().getJobs().add(newJob);
    }
}
private void addShipToParent(Ship newShip, HashMap<Integer, Dock> docksMap,HashMap<Integer, SeaPort> portsMap) {
	
    SeaPort mySeaPort;
    Dock myDock = docksMap.get(newShip.getParent());

    if (myDock == null) {
        mySeaPort = portsMap.get(newShip.getParent());
        mySeaPort.getShips().add(newShip);
        mySeaPort.getQueue().add(newShip);
    } else {
        mySeaPort = portsMap.get(myDock.getParent());
        myDock.setShip(newShip);
        mySeaPort.getShips().add(newShip);
    }
  }

@SuppressWarnings({ "unchecked", "serial" })
protected <T extends Thing> DefaultMutableTreeNode toTree() {
	
    DefaultMutableTreeNode mainNode, parentNode, childNode;
    Method getList;
    HashMap<String, String> classMethodMap;
    ArrayList<T> thingsList;	
	
    mainNode = new DefaultMutableTreeNode("World");
    classMethodMap = new HashMap<String, String>() {{
        put("Docks", "getDocks");
        put("Ships", "getShips");
        put("Queue", "getQueue");
        put("Persons", "getPersons");
    }};
    
    for (SeaPort newPort : this.getPorts()) {
        parentNode = new DefaultMutableTreeNode("SeaPort: " + newPort.getName());
        mainNode.add(parentNode);

        for (HashMap.Entry<String, String> pair : classMethodMap.entrySet()) {
            try {
                getList = SeaPort.class.getDeclaredMethod(pair.getValue());
                thingsList = (ArrayList<T>) getList.invoke(newPort);
                childNode = this.addBranch(pair.getKey(), thingsList);
                parentNode.add(childNode);
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
    }

    return mainNode;
}
	private <T extends Thing> DefaultMutableTreeNode addBranch(String title,
        ArrayList<T> thingsList) {
	
        String newThingName, childTitle;
        DefaultMutableTreeNode parentNode, childNode;
        Dock thisDock;
        Ship mooredShip, newShip;
		
        parentNode = new DefaultMutableTreeNode(title);
        for (T newThing : thingsList) {
            newThingName = newThing.getName() + " (" + newThing.getIndex() + ")";
            childNode = new DefaultMutableTreeNode(newThingName);
            
            if (newThing instanceof Dock) {
                thisDock = (Dock) newThing;
                mooredShip = thisDock.getShip();

                if (thisDock.getShip() != null) {
                    childTitle = mooredShip.getName() + " (" + mooredShip.getIndex() + ")";
                    childNode.add(new DefaultMutableTreeNode(childTitle));
                }
            } else if (newThing instanceof Ship) {
                newShip = (Ship) newThing;
                childTitle = "";

                if (!newShip.getJobs().isEmpty()) {
                    for (Job newJob : newShip.getJobs()) {
                        childTitle = newJob.getName();
                        childNode.add(new DefaultMutableTreeNode(childTitle));
                    }
                }
            }

            parentNode.add(childNode);
        }

        return parentNode;
    }

	@Override
    public String toString() {
        String stringOutput = "----> The world:\n";

        for (SeaPort seaPort : this.getPorts()) {
            stringOutput += seaPort.toString() + "\n";
        }
        return stringOutput;
    }

}  

   
   