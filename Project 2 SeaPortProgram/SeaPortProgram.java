import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.swing.tree.DefaultTreeModel;

@SuppressWarnings("serial")
public class SeaPortProgram extends JFrame {
	
	//Declaring variables   
    private World world;
    private String title;
    private int width;
    private int height;
    private JFrame guiFrame;
    private JTree tree;
    private JTextArea dataArea, searchResultsjta;
    private JScrollPane mainScrollPane, treeScrollPane, resultsScrollPane;
    private JPanel mainPanel, topPanel, worldPanel;
    private JButton readButton, searchButton, sortButton;
    private JLabel searchLabel, sortLabel;
    private JTextField searchTextField;
    private String[] searchComboBoxValues, sortPortComboBoxValues, sortTypeComboBoxValues, sortTargetComboBoxValues;
    private JComboBox<String> searchComboBox, sortPortComboBox, sortTypeComboBox,sortTargetComboBox; 
    private JFileChooser fileChooser;
    private Scanner scanContent;

    protected SeaPortProgram() {
        super("SeaPortProgram");
        this.setWindowTitle("SeaPortProgram");
        this.setWindowWidth(1000);
        this.setWindowHeight(500);
    }

    private void setWindowTitle(String title) {
        this.title = title;
    }
    private void setWindowHeight(int height) {
        if (height < 500) {
            this.height = 500;
        } else {
            this.height = height;
        }
    }
    private void setWindowWidth(int width) {
        if (width < 1000) {
            this.width = 1000;
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
    this.worldPanel = new JPanel(new GridLayout(1, 3, 5, 5));

    this.dataArea = new JTextArea();
    this.dataArea.setEditable(false);                       
    this.dataArea.setFont(new Font("Monospaced", 0, 12));   
    this.dataArea.setLineWrap(true);                       

    this.tree = new JTree();
    this.tree.setModel(null);

    this.searchResultsjta = new JTextArea();
    this.searchResultsjta.setEditable(false);
    this.searchResultsjta.setFont(new Font("Monospaced", 0, 12));
    this.searchResultsjta.setLineWrap(true);
    
    this.mainScrollPane = new JScrollPane(this.dataArea);
    this.treeScrollPane = new JScrollPane(this.tree);
    this.resultsScrollPane = new JScrollPane(this.searchResultsjta);
    
    this.readButton = new JButton("Read");
    this.searchButton = new JButton("Search");
    this.sortButton = new JButton("Sort");
    
    this.searchLabel = new JLabel("Search entries:", JLabel.RIGHT);
    this.searchTextField = new JTextField("", 12);
    this.sortLabel = new JLabel("Sort:", JLabel.RIGHT);
    
    this.sortPortComboBoxValues = new String[] {
    		"All ports"
    };
    
    this.sortPortComboBox = new JComboBox<>(this.sortPortComboBoxValues);
    this.searchComboBoxValues = new String[] {"By name", "By index", "By skill"};
    this.searchComboBox = new JComboBox<>(this.searchComboBoxValues);
    
    this.sortTargetComboBoxValues = new String[] {
            "Queue","Ships","Docks","Persons","Jobs"
        };
    this.sortTargetComboBox = new JComboBox<>(this.sortTargetComboBoxValues);

    this.sortTypeComboBoxValues = new String[] {
                "By name","By weight", "By length","By width","By draft"    
        };
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
    this.mainPanel.add(this.mainScrollPane, BorderLayout.CENTER);
    this.mainPanel.add(this.topPanel, BorderLayout.NORTH);

    this.worldPanel.add(this.treeScrollPane);
    this.worldPanel.add(this.mainScrollPane);
    this.worldPanel.add(this.resultsScrollPane);
    
    this.topPanel.setBorder(BorderFactory.createTitledBorder("Options"));
    this.treeScrollPane.setBorder(BorderFactory.createTitledBorder("World tree"));
    this.mainScrollPane.setBorder(BorderFactory.createTitledBorder("World"));
    this.resultsScrollPane.setBorder(BorderFactory.createTitledBorder("Search/Sort Results"));
    this.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    this.mainPanel.add(this.worldPanel, BorderLayout.CENTER);
    this.mainPanel.add(this.topPanel, BorderLayout.NORTH);

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

    this.world = new World(this.scanContent);

    if (this.world.getAllThings().isEmpty()) {
        this.dataArea.setText("");
        this.searchResultsjta.setText("");
        this.tree.setModel(null);
        this.world = null;
        this.errorPopup("File empty or corrupted. Please try again.");
    } else {
        this.dataArea.setText("" + this.world.toString()); 
        this.searchResultsjta.setText("");
        this.tree.setModel(new DefaultTreeModel(this.world.toTree()));
        this.seaPortSortOptions();
    }
}

private void searchWorldContents() {

    if (this.world == null || this.scanContent == null) {
        this.errorPopup("No world initialized. Please try again.");
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
        getParam = Thing.class.getDeclaredMethod(methodName);

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
String sortPort, sortTarget, sortType, result, fieldMethodName, listMethodName;
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
        getList = SeaPort.class.getDeclaredMethod(listMethodName);

        if (sortTarget.equals("Queue") && !sortType.equals("By name")) {
            getField = Ship.class.getDeclaredMethod(fieldMethodName);
        } else {
            getField = Thing.class.getDeclaredMethod(fieldMethodName);
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

private void errorPopup(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
}


public static void main(String[] args) {
    SeaPortProgram newCollection = new SeaPortProgram();
    newCollection.constructGUI();
}
}