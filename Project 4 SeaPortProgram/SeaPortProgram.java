import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class SeaPortProgram extends JFrame {
	
	//Declaring variables   
    private World world;
    private String title;
    private int width;
    private int height;
    private JFrame guiFrame;
    private JTree tree;
    private JTextArea searchResultsjta, jobsStatusjta, jobsPooljta;
    private JScrollPane jobScrollPane, treeScrollPane, resultsScrollPane, jobsStatusScrollPane, jobsPoolScrollPane;
    private JPanel mainPanel, topPanel, worldPanel, treePanel, treeButtonsPanel, jobsPanel, jobsLogsPanel, jobsScrollPanePanel;
    private JButton readButton, searchButton, sortButton, treeDetailsButton, treeExpandButton, treeMinButton;
    private JLabel searchLabel, sortLabel;
    private JTextField searchTextField;
    private String[] searchComboBoxValues, sortPortComboBoxValues, sortTypeComboBoxValues, sortTargetComboBoxValues;
    private JComboBox<String> searchComboBox, sortPortComboBox, sortTypeComboBox,sortTargetComboBox; 
    private JFileChooser fileChooser;
    private Scanner scanContent;

    protected SeaPortProgram() {
        super("SeaPortProgram");
        this.setWindowTitle("SeaPortProgram");
        this.setWindowWidth(1100);
        this.setWindowHeight(600);
    }

    private void setWindowTitle(String title) {
        this.title = title;
    }
    private void setWindowHeight(int height) {
        if (height < 600) {
            this.height = 600;
        } else {
            this.height = height;
        }
    }
    private void setWindowWidth(int width) {
        if (width < 1100) {
            this.width = 1100;
        } else {
            this.width = width;
        }
    }

    protected String getWindowTitle() {
        return this.title;
    }
    protected int getWindowWidth() {
        return this.width;
    }
    protected int getWindowHeight() {
        return this.height;
    }
    private void constructGUI() {

    this.mainPanel = new JPanel(new BorderLayout());
    this.topPanel = new JPanel(new GridLayout(1, 10, 5, 5));
    this.worldPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    this.treeButtonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
    this.treePanel = new JPanel(new BorderLayout());                  
    this.jobsPanel = new JPanel(new GridLayout(2, 1, 5, 5));          
    this.jobsLogsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
    this.jobsScrollPanePanel = new JPanel(new GridLayout(0, 1));                       

    this.tree = new JTree();
    this.tree.setModel(null);
    this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    
    this.treeScrollPane = new JScrollPane(this.tree);
    this.treeExpandButton = new JButton("Expand all");
    this.treeMinButton = new JButton("Minimize all");
    this.treeDetailsButton = new JButton("More information");
    this.treeButtonsPanel.add(this.treeExpandButton); 
    this.treeButtonsPanel.add(this.treeMinButton);  
    this.treeButtonsPanel.add(this.treeDetailsButton);
    
    this.searchResultsjta = new JTextArea();
    this.searchResultsjta.setEditable(false);
    this.searchResultsjta.setFont(new Font("Monospaced", 0, 12));
    this.searchResultsjta.setLineWrap(true);
    
    this.treePanel.add(this.treeScrollPane, BorderLayout.CENTER);
    this.treePanel.add(this.treeButtonsPanel, BorderLayout.SOUTH);
    
    this.resultsScrollPane = new JScrollPane(this.searchResultsjta);
    
    this.readButton = new JButton("Read");
    this.searchButton = new JButton("Search");
    this.sortButton = new JButton("Sort");
    
    this.searchLabel = new JLabel("Search entries:", JLabel.RIGHT);
    this.searchTextField = new JTextField("", 12);
    this.sortLabel = new JLabel("Sort:", JLabel.RIGHT);
    
    this.sortPortComboBoxValues = new String[] {"All ports" };
    
    this.sortPortComboBox = new JComboBox<>(this.sortPortComboBoxValues);
    this.searchComboBoxValues = new String[] {"By name", "By index", "By skill"};
    this.searchComboBox = new JComboBox<>(this.searchComboBoxValues);
    
    this.sortTargetComboBoxValues = new String[] { "Queue","Ships","Docks","Persons","Jobs" };
    this.sortTargetComboBox = new JComboBox<>(this.sortTargetComboBoxValues);

    this.sortTypeComboBoxValues = new String[] {
                "By name","By weight", "By length","By width","By draft"     };
    this.sortTypeComboBox = new JComboBox<>(this.sortTypeComboBoxValues);
   
    this.topPanel.add(this.readButton);     
    this.topPanel.add(this.searchLabel);    
    this.topPanel.add(this.searchTextField);  
    this.topPanel.add(this.searchComboBox);     
    this.topPanel.add(this.searchButton);      
    this.topPanel.add(this.sortLabel);
    this.topPanel.add(this.sortPortComboBox);
    this.topPanel.add(this.sortTargetComboBox);
    this.topPanel.add(this.sortTypeComboBox);
    this.topPanel.add(this.sortButton);
    
    this.jobsStatusjta = new JTextArea();
    this.jobsStatusjta.setEditable(false);
    this.jobsStatusjta.setFont(new Font("Monospaced", 0, 11));
    this.jobsStatusjta.setLineWrap(true);

    this.jobsPooljta = new JTextArea();
    this.jobsPooljta.setEditable(false);
    this.jobsPooljta.setFont(new Font("Monospaced", 0, 11));
    this.jobsPooljta.setLineWrap(true);

    this.jobsPoolScrollPane = new JScrollPane(this.jobsPooljta);
    this.jobsStatusScrollPane = new JScrollPane(this.jobsStatusjta);
    this.jobScrollPane = new JScrollPane(this.jobsScrollPanePanel);
    
    this.jobsLogsPanel.add(this.jobsStatusScrollPane);
    this.jobsLogsPanel.add(this.jobsStatusScrollPane);
    this.jobsLogsPanel.add(this.jobsPoolScrollPane);
    this.jobsPanel.add(this.jobScrollPane);
    this.jobsPanel.add(this.jobsLogsPanel);
    
    this.worldPanel.add(this.resultsScrollPane);
    this.worldPanel.add(this.treePanel);
    this.mainPanel.add(this.topPanel, BorderLayout.NORTH);
    this.mainPanel.add(this.worldPanel, BorderLayout.WEST);
    this.mainPanel.add(this.jobsPanel, BorderLayout.CENTER);
    
    this.topPanel.setBorder(BorderFactory.createTitledBorder("Options"));
    this.treePanel.setBorder(BorderFactory.createTitledBorder("World Tree"));
    this.jobsStatusScrollPane.setBorder(BorderFactory.createTitledBorder("Job Status Log"));
    this.jobScrollPane.setBorder(BorderFactory.createTitledBorder("Jobs Listing"));
    this.jobsPoolScrollPane.setBorder(BorderFactory.createTitledBorder("Job Resource Pool"));
    this.resultsScrollPane.setBorder(BorderFactory.createTitledBorder("Search/Sort Log"));
    this.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    this.jobsScrollPanePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    this.jobsScrollPanePanel.setBackground(Color.WHITE);
    this.jobsStatusjta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    this.jobsPooljta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    this.searchResultsjta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
 
    this.sortTargetComboBox.addActionListener((ActionEvent e) -> {
        this.sortOptions();
    });

    this.readButton.addActionListener((ActionEvent e) -> {
        this.readFileContents();
    });

    this.searchButton.addActionListener((ActionEvent e) -> {
        this.searchWorldContents();
    });

    this.sortButton.addActionListener((ActionEvent e) -> {
        this.sortWorldContents();
    });
    this.treeExpandButton.addActionListener((ActionEvent e) -> {
        this.toggleNodes("expandRow");
    });
    
    this.treeMinButton.addActionListener((ActionEvent e) -> {
        this.toggleNodes("collapseRow");
    });
    this.treeDetailsButton.addActionListener((ActionEvent e) -> {
        this.selectionDetails();
    });
    
    this.guiFrame = new JFrame(this.getWindowTitle());
    this.guiFrame.setContentPane(this.mainPanel);
    this.guiFrame.setSize(this.getWindowWidth(), this.getWindowHeight());
    this.guiFrame.setVisible(true);
    this.guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
    private void sortOptions() {
        this.sortTypeComboBox.removeAllItems();
        this.sortTypeComboBox.addItem("By name");
        if (this.sortTargetComboBox.getSelectedIndex() == 0) {
            this.sortTypeComboBox.addItem("By weight");
            this.sortTypeComboBox.addItem("By width");
            this.sortTypeComboBox.addItem("By length");
            this.sortTypeComboBox.addItem("By draft");
        }
    }
    private void seaPortSortOptions() {
	    this.sortPortComboBox.removeAllItems();
	    this.sortPortComboBox.addItem("All ports");
	    Collections.sort(this.world.getPorts(), new ThingComparator("By name"));
	    	if (this.world.getPorts().size() > 1) {
	    		for (SeaPort newPort : this.world.getPorts()) {
	    			this.sortPortComboBox.addItem(newPort.getName());
        }
    }
}
private void readFileContents() {
    int chooseFile;
    FileReader fileReader;
    FileNameExtensionFilter filter;

    this.fileChooser = new JFileChooser(".");
    filter = new FileNameExtensionFilter("Text Files", "txt", "text");
    this.fileChooser.setFileFilter(filter);

    chooseFile = this.fileChooser.showOpenDialog(new JFrame());

    if (chooseFile == JFileChooser.APPROVE_OPTION) {
        try {
            fileReader = new FileReader(this.fileChooser.getSelectedFile());
            this.scanContent = new Scanner(fileReader);
        } catch (FileNotFoundException ex) {
            this.errorPopup("File not found. Please try again.");
        }
    }
    if (this.world != null) {
        this.clearAllJobs();
    }
    this.world = new World(this.scanContent);

    if (this.world.getAllThings().isEmpty()) {
        this.jobsStatusjta.setText("");
        this.jobsPooljta.setText("");
        this.searchResultsjta.setText("");
        this.tree.setModel(null);
        this.clearAllJobs();
        this.world = null;
        this.errorPopup("File data may be empty or corrupted. Please make another selection.");
    } else {
        this.jobsStatusjta.setText("");
        this.jobsPooljta.setText("");
        this.searchResultsjta.setText("");
        this.tree.setModel(new DefaultTreeModel(this.world.toTree()));
        this.seaPortSortOptions();
        this.startAllJobs();
    }
}
private void searchWorldContents() {

    if (this.world == null || this.scanContent == null) {
        this.errorPopup("World was not initialized. Please try again.");
        return;
    }

    String resultsString, searchText;
    int dropdown;

    resultsString = "";
    searchText = this.searchTextField.getText().trim();
    dropdown = this.searchComboBox.getSelectedIndex();

    if (searchText.equals("")) {
        this.errorPopup("No search terms included. Please try again.");
        return;
    }

    switch(dropdown) {
        case 0: 
        case 1: 
            resultsString = this.assembleResults(dropdown, searchText);
            this.displayStatus(resultsString, searchText);
            break;
        case 2: 
            for (SeaPort port : this.world.getPorts()) {
                for (Person person : port.getPersons()) {
                    if (person.getSkill().equals(searchText)) {
                        resultsString += person.getName() + " (id #" + person.getIndex()
                            + ")\n";
                    }
                }
            }
            this.displayStatus(resultsString, searchText);
            break;
        default:
            break;
    }
}
private String assembleResults(int index, String target) {

    Method getParam;
    String param, methodName;
    String resultsString = "";

    methodName = (index == 0) ? "getName" : "getIndex";

    try {
        getParam = Thing.class.getMethod(methodName);

        for (Thing item : this.world.getAllThings()) {

            param = "" + getParam.invoke(item);

            if (param.equals(target)) {
                resultsString += item.getName() + " " + item.getIndex() + " ("
                    + item.getClass().getSimpleName() + ")\n";
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
    return resultsString;
}
@SuppressWarnings({ })
private void displayStatus(String resultsString, String target) {
    if (resultsString.equals("")) {
        if (resultsString.equals("")) {
            this.searchResultsjta.append("Warning: '" + target + "' not found.\n\n");
        } else {
            this.searchResultsjta.append("Search results for '" + target + "'\n"
                + resultsString + "\n");
        }
    }
   }
    @SuppressWarnings("unchecked")
	private void sortWorldContents() {
        if (this.world == null || this.scanContent == null) {
            this.errorPopup("Error: No world initialized. Please try again.");
            return;
        }
//Declaring HashMap Variables
String sortPort, sortType, sortTarget, result,listMethodName, fieldMethodName;
Method getField, getList;
ArrayList<Thing> thingsList, retrievedList;
HashMap<String, String> typeMethodMap, targetMethodMap;

typeMethodMap = new HashMap<String, String>() {{
    put("By name", "getIndex");
    put("By weight", "getWeight");
    put("By length", "getLength");
    put("By width", "getWidth");
    put("By draft", "getDraft");
}};

targetMethodMap = new HashMap<String, String>() {{
    put("Queue", "getQueue");
    put("Ships", "getShips");
    put("Docks", "getDocks");
    put("Persons", "getPersons");
    put("Jobs", "getShips"); 
    }};
    
    sortPort = this.sortPortComboBox.getSelectedItem().toString();
    sortTarget = this.sortTargetComboBox.getSelectedItem().toString();
    sortType = this.sortTypeComboBox.getSelectedItem().toString();
    fieldMethodName = typeMethodMap.get(sortType);
    listMethodName = targetMethodMap.get(sortTarget);
    result = "";
    thingsList = new ArrayList<>();
    
    try {
        getList = SeaPort.class.getMethod(listMethodName);

        if (!sortType.equals("By name") && sortTarget.equals("Queue")) {
            getField = Ship.class.getMethod(fieldMethodName);
        } else {
            getField = Thing.class.getMethod(fieldMethodName);
        }

        if (sortPort.equals("All ports")) {
            sortPort = sortPort.toLowerCase();
            for (SeaPort newPort : world.getPorts()) {
                retrievedList = (ArrayList<Thing>) getList.invoke(newPort);
                thingsList.addAll(retrievedList);
            }
        } else {
            for (SeaPort newPort : this.world.getPorts()) {
                if (newPort.getName().equals(sortPort)) {
                    retrievedList = (ArrayList<Thing>) getList.invoke(newPort);
                    thingsList.addAll(retrievedList);
                }
            }
        }
        
        if (sortTarget.equals("Jobs")) {
            ArrayList<Job> jobsList = new ArrayList<>();

            for (Iterator<Thing> iterator = thingsList.iterator(); iterator.hasNext();) {
                Ship newShip = (Ship) iterator.next();
                for (Job newJob : newShip.getJobs()) {
                    jobsList.add(newJob);
                }
            }
            thingsList.clear(); 
            thingsList.addAll(jobsList);
        }
        
        if (thingsList.isEmpty()) {
            result += "> No results found.\n";
        } else {
            Collections.sort(thingsList, new ThingComparator(sortType));

            for (Thing newThing : thingsList) {
                result += "> " + newThing.getName() + " (" + getField.invoke(newThing) + ")\n";
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
    
    this.searchResultsjta.append("Sort results for '" + sortTarget + " "
            + sortType.toLowerCase() + " in " + sortPort + "'\n" + result + "\n");
        }
    private void toggleNodes(String methodName) {

        if (this.world == null || this.scanContent == null) {
            this.errorPopup("Error: No world initialized. Please try again.");
            return;
        }
        Method toggleRow;

        try {
            toggleRow = JTree.class.getMethod(methodName, Integer.TYPE);

            for (int i = 0; i < this.tree.getRowCount(); i++) {
                toggleRow.invoke(this.tree, i);
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
    private void selectionDetails() {
    	if (this.world == null || this.scanContent == null) {
            this.errorPopup("No world initialized. Please try again.");
            return;
        } else if (this.tree.getLastSelectedPathComponent() == null) {
            this.errorPopup("Please select an element from this tree.");
            return;
        } else if (Arrays.asList(new String[] {"Queue", "Docks", "Ships", "Persons", "World"})
                .contains(this.tree.getLastSelectedPathComponent().toString())) {
            this.errorPopup("Invalid selection. Please select a different node.");
            return;
        }

        String userSelection;
        JTable resultsTable;
        HashMap<String, String> defaultValues, shipValues, passengerShipValues,
            cargoShipValues, personValues;
        LinkedHashMap<String, String> applicableFields;
        Object[][] results;
        String[] columnNames, nameIndexArray;
        int counter;

        userSelection = this.tree.getLastSelectedPathComponent().toString();
        nameIndexArray = userSelection.split(" ");
        userSelection = nameIndexArray[0].trim();
        applicableFields = new LinkedHashMap<>();
        columnNames = new String[] {"Field", "Value"};
        counter = 0;

        defaultValues = new HashMap<String, String>() {{
            put("ID", "getIndex");
            put("Name", "getName");
        }};

        shipValues = new HashMap<String, String>() {{
            put("Weight", "getWeight");
            put("Length", "getLength");
            put("Width", "getWidth");
            put("Draft", "getDraft");
        }};

        passengerShipValues = new HashMap<String, String>() {{
            put("Total rooms", "getNumberOfRooms");
            put("Occupied rooms", "getNumberOfOccupiedRooms");
            put("Passengers", "getNumberOfPassengers");
        }};

        cargoShipValues = new HashMap<String, String>() {{
            put("Cargo volume", "getCargoVolume");
            put("Cargo value", "getCargoValue");
            put("Cargo weight", "getCargoWeight");
        }};

        personValues = new HashMap<String, String>() {{
            put("Occupation", "getSkill");
        }};

    for (Thing newThing : this.world.getAllThings()) {
        if (newThing.getName().equals(userSelection)) {
            applicableFields.putAll(this.constructResults(newThing, Thing.class,
                defaultValues));

            if (newThing instanceof Ship) {
                applicableFields.putAll(this.constructResults(newThing, Ship.class,
                    shipValues));

                if (newThing instanceof PassengerShip) {
                    applicableFields.putAll(this.constructResults(newThing, PassengerShip.class,
                        passengerShipValues));
                } else if (newThing instanceof CargoShip) {
                    applicableFields.putAll(this.constructResults(newThing, CargoShip.class,
                        cargoShipValues));
                }
            } else if (newThing instanceof Person) {
                applicableFields.putAll(this.constructResults(newThing, Person.class,
                    personValues));
            }
        }
    }
    results = new Object[applicableFields.size()][2];
    for (HashMap.Entry<String,String> entry : applicableFields.entrySet()) {
        results[counter][0] = entry.getKey();
        results[counter][1] = entry.getValue();
        counter++;
    }

    resultsTable = new JTable(results, columnNames);
    resultsTable.setPreferredScrollableViewportSize(resultsTable.getPreferredSize());
    resultsTable.setFillsViewportHeight(true);

    JOptionPane.showMessageDialog(this.guiFrame, new JScrollPane(resultsTable), userSelection,
        JOptionPane.PLAIN_MESSAGE);
    }
    @SuppressWarnings("unchecked")
    private <T extends Thing> HashMap<String, String> constructResults(T newThing,
            @SuppressWarnings("rawtypes") Class className, HashMap<String, String> values) {

        HashMap<String, String> resultsMap;
        Method getAttribute;
        resultsMap = new HashMap<>();

        try {
            for (HashMap.Entry<String, String> row : values.entrySet()) {

                String displayText, methodName, methodResult;
                Object retrieveAttribute;

                displayText = row.getKey();
                methodName = row.getValue();

                getAttribute = className.getMethod(methodName);
                retrieveAttribute = getAttribute.invoke(newThing);

                if (retrieveAttribute instanceof String) {
                    methodResult = (String) retrieveAttribute;
                } else {
                    methodResult = String.valueOf(retrieveAttribute);
                }

                resultsMap.put(displayText, methodResult);
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

        return resultsMap;
    }

    private void startAllJobs() {
        for (SeaPort port : this.world.getPorts()) {
            for (Dock dock : port.getDocks()) { 
                if (dock.getShip().getJobs().isEmpty()) { 
                    this.jobsStatusjta.append("Unmooring " + dock.getShip().getName()
                        + " from " + dock.getName() + " in " + port.getName() + "\n");
                    dock.setShip(null);

                    while (!port.getQueue().isEmpty()) { 
                        Ship newShip = port.getQueue().remove(0); 
                        if (!newShip.getJobs().isEmpty()) { 
                            dock.setShip(newShip); 
                            this.jobsStatusjta.append("Mooring " + dock.getShip().getName()
                                + " at " + dock.getName() + " in " + port.getName() + "\n");
                            break;
                        }
                    }
                }
                dock.getShip().setDock(dock);
            }
            for (Ship ship : port.getShips()) {
                if (!ship.getJobs().isEmpty()) {
                    for (Job job : ship.getJobs()) {
                        this.jobsScrollPanePanel.add(job.getJobAsPanel());
                        this.jobsScrollPanePanel.revalidate();
                        this.jobsScrollPanePanel.repaint();

                        job.setStatusLog(this.jobsStatusjta);
                        job.setWorkerLog(this.jobsPooljta);
                        job.startJob();
                    }
                }
            }
        }
    }
            private void clearAllJobs() {
                this.jobsScrollPanePanel.removeAll();           
                this.world.getAllThings().forEach((thing) -> { 
                    if (thing instanceof Job) {                
                        ((Job) thing).endJob();                 
                    }
                });
            }
 
private void errorPopup(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
}


public static void main(String[] args) {
    SeaPortProgram newCollection = new SeaPortProgram();
    newCollection.constructGUI();
}
}