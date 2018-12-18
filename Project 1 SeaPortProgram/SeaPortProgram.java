import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class SeaPortProgram extends JFrame {

    // New world instance
    private World world;

    // Window-related fields
    private String title;
    private int width;
    private int height;

    // GUI related fields
    private JFrame mainFrame;
    private JTextArea mainTextArea;
    private JScrollPane mainScrollPane;
    private JPanel mainPanel, optionsPanel;
    private JButton readButton, searchButton;
    private JLabel searchTextLabel;
    private JTextField searchTextField;
    private String[] searchComboBoxValues;
    private JComboBox<String> searchComboBox;

    // User input-related fields
    private JFileChooser fileChooser;
    private Scanner scannerContents;

    protected SeaPortProgram() {
        super("SeaPortProgram");
        this.setWindowTitle("SeaPortProgram");
        this.setWindowWidth(800);
        this.setWindowHeight(500);
    }

    protected SeaPortProgram(String title, int width, int height) {
        super(title);
        this.setWindowTitle(title);
        this.setWindowWidth(width);
        this.setWindowHeight(height);
    }

    private void setWindowTitle(String title) {
        this.title = title;
    }

    private void setWindowWidth(int width) {
        if (width < 800) {
            this.width = 800;
        } else {
            this.width = width;
        }
    }

    private void setWindowHeight(int height) {
        if (height < 500) {
            this.height = 500;
        } else {
            this.height = height;
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

        // Layout manager definitions, eschewing FlowLayout as per rubric
        this.mainPanel = new JPanel(new BorderLayout());
        this.optionsPanel = new JPanel(new GridLayout(1, 5, 5, 5));

        // Central text area related definitions
        this.mainTextArea = new JTextArea();
        this.mainTextArea.setEditable(false);                       // Forbid editing JTextArea
        this.mainTextArea.setFont(new Font("Monospaced", 0, 12));   // Insert Font as per rubric
        this.mainTextArea.setLineWrap(true);                        // Precaution to encourage wrap

        // Add JTextArea to JScrollPane as per rubric
        this.mainScrollPane = new JScrollPane(this.mainTextArea);

        // Create buttons for options menu
        this.readButton = new JButton("Read");
        this.searchButton = new JButton("Search");

        // Search related definitions
        this.searchTextLabel = new JLabel("Search entries:", JLabel.RIGHT);
        this.searchTextField = new JTextField("", 10);


        this.searchComboBoxValues = new String[] {"By name", "By index", "By skill"};
        this.searchComboBox = new JComboBox<>(this.searchComboBoxValues);

        // Add UI options to top panel
        this.optionsPanel.add(this.readButton);         // Read button first
        this.optionsPanel.add(this.searchTextLabel);    // Search text label "Search entries:"
        this.optionsPanel.add(this.searchTextField);    // Search bar itself
        this.optionsPanel.add(this.searchComboBox);     // Sorting options combo box
        this.optionsPanel.add(this.searchButton);       // Search button itself

        // Per rubric, JTextArea within JScrollPane on JPanel in the BorderLayout.CENTER position
        this.mainPanel.add(this.mainScrollPane, BorderLayout.CENTER);

        // Add buttons options UI to the top of the main panel
        this.mainPanel.add(this.optionsPanel, BorderLayout.NORTH);

        // Add borders for a cleaner look
        this.optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        this.mainScrollPane.setBorder(BorderFactory.createTitledBorder("World"));
        this.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Read button handler
        this.readButton.addActionListener((ActionEvent e) -> {
            this.readFileContents();
        });

        // Search button handler
        this.searchButton.addActionListener((ActionEvent e) -> {
            this.searchWorldContents();
        });

        // Placement/sizing details for main JFrame element
        this.mainFrame = new JFrame(this.getWindowTitle());
        this.mainFrame.setContentPane(this.mainPanel);
        this.mainFrame.setSize(this.getWindowWidth(), this.getWindowHeight());
        this.mainFrame.setVisible(true);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void readFileContents() {

        // Declarations
        int selection;
        FileReader fileReader;
        FileNameExtensionFilter filter;

        // Main folder, as per rubric
        this.fileChooser = new JFileChooser(".");

        /**
         * Addition of <code>.txt</code> file-only filter, as per the answer on the following page:
         * <br />
         * <a href=
         * "//stackoverflow.com/questions/15771949/how-do-i-make-jfilechooser-only-accept-txt"
         * >See here</a>
         */
        filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        this.fileChooser.setFileFilter(filter);

        selection = this.fileChooser.showOpenDialog(new JFrame());

        if (selection == JFileChooser.APPROVE_OPTION) {
            try {
                fileReader = new FileReader(this.fileChooser.getSelectedFile());
                this.scannerContents = new Scanner(fileReader);
            } catch (FileNotFoundException ex) {
                this.displayErrorPopup("Error: No such file found. Please try again.");
            }
        }

        // Create new <code>World</code> instance, passing <code>Scanner</code> contents
        this.world = new World(this.scannerContents);

        // Forbid users from using a text file that is not in the proper format
        if (this.world.getAllThings().isEmpty()) {
            // Clear any previous results from proper input and set World object to null
            this.mainTextArea.setText("");
            this.world = null;
            this.displayErrorPopup("Error: File data may be empty or corrupted. Please try again.");
        } else {
            this.mainTextArea.setText("" + this.world.toString()); // Clear text area first
        }

    }


    private void searchWorldContents() {

        // Prevent users seeking to hit the search button before building da world
        if (this.world == null || this.scannerContents == null) {
            this.displayErrorPopup("Error: No world initialized. Please try again.");
            return;
        }

        // Declaration
        String resultsString, searchText;
        int dropdownSelection;

        // Definitions
        resultsString = "";
        searchText = this.searchTextField.getText().trim();
        dropdownSelection = this.searchComboBox.getSelectedIndex();

        // Catch users hitting the Search button without entering any content, sneaky buggers
        if (searchText.equals("")) {
            this.displayErrorPopup("Error: No search terms included. Please try again.");
            return;
        }

        // See discussion above for more details
        switch(dropdownSelection) {
            case 0: // By name
            case 1: // By index
                resultsString = this.assembleResults(dropdownSelection, searchText);
                this.displayStatus(resultsString, searchText);
                break;
            case 2: // By skill
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

        // Declarations/definitions
        Method getParam;
        String parameter, methodName;
        String resultsString = "";

        methodName = (index == 0) ? "getName" : "getIndex";

        try {
            // Either Thing.class.getName or Thing.class.getIndex
            getParam = Thing.class.getDeclaredMethod(methodName);

            for (Thing item : this.world.getAllThings()) {

                // Hacky? Yes. But it leaves Strings alone and converts Integers to String
                parameter = "" + getParam.invoke(item);

                if (parameter.equals(target)) {
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


    private void displayStatus(String resultsString, String target) {
        if (resultsString.equals("")) {
            JOptionPane.showMessageDialog(
                null,
                "Warning: '" + target + "' not found.",
                "Warning",
                JOptionPane.WARNING_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                null,
                resultsString,
                "Search results for '" + target + "'",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    private void displayErrorPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String[] args) {
        SeaPortProgram newCollection = new SeaPortProgram();
        newCollection.constructGUI();
    }
}