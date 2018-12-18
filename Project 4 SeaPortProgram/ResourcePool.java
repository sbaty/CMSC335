import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.util.ArrayList;


final class ResourcePool {

	//Declaring variable
	private ArrayList<Person> personsInPool;
    private int availablePersons, allPersons;
    private String skill, parentPort;
    private JPanel rowPanel;
    private JLabel portLabel, skillLabel, countLabel, totalLabel;

    protected ResourcePool(ArrayList<Person> personsInPool, String skill, String parentPort) {
        this.setPersonsInPool(personsInPool);
        this.setAllPersons(this.getPersonsInPool().size());
        this.setAvailablePersons(this.getPersonsInPool().size());
        this.setSkill(skill);
        this.setParentPort(parentPort);
    }

    // Getters and Setters

    private void setPersonsInPool(ArrayList<Person> personsInPool) {
        this.personsInPool = personsInPool;
    }

    private void setAllPersons(int allPersons) {
        this.allPersons = allPersons;
    }

    private void setAvailablePersons(int availablePersons) {
        this.availablePersons = availablePersons;
    }

    private void setSkill(String skill) {
        this.skill = skill;
    }

    private void setParentPort(String parentPort) {
        this.parentPort = parentPort;
    }


    protected ArrayList<Person> getPersonsInPool() {
        return this.personsInPool;
    }


    protected int getAllPersons() {
        return this.allPersons;
    }


    protected int getAvailablePersons() {
        return this.availablePersons;
    }


    protected String getSkill() {
        return this.skill;
    }


    protected String getParentPort() {
        return this.parentPort;
    }


    protected JPanel getPoolAsPanel() {

        String job = this.getSkill().substring(0, 1).toUpperCase() + this.getSkill().substring(1);

        this.rowPanel = new JPanel(new GridLayout(1, 3));
        this.skillLabel = new JLabel(job, JLabel.CENTER);
        this.portLabel = new JLabel(this.getParentPort(), JLabel.CENTER);
        this.countLabel = new JLabel("Available: " + String.valueOf(this.getAvailablePersons()), JLabel.CENTER);
        this.totalLabel = new JLabel("Total: " + String.valueOf(this.getAllPersons()), JLabel.CENTER);

        this.countLabel.setOpaque(true);
        this.totalLabel.setOpaque(true);
        this.skillLabel.setOpaque(true);
        this.portLabel.setOpaque(true);

        this.countLabel.setBackground(Color.WHITE);
        this.totalLabel.setBackground(Color.WHITE);
        this.skillLabel.setBackground(Color.WHITE);
        this.portLabel.setBackground(Color.WHITE);

        this.countLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.totalLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.skillLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.portLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        this.rowPanel.add(this.countLabel);
        this.rowPanel.add(this.totalLabel);
        this.rowPanel.add(this.skillLabel);
        this.rowPanel.add(this.portLabel);
      
        return this.rowPanel;
    }

    protected void addPerson(Person person) {
        this.getPersonsInPool().add(person);
        this.setAvailablePersons(this.getPersonsInPool().size());
        this.setAllPersons(this.getPersonsInPool().size());
    }

    protected void reservePerson(Person person) {
        person.setIsEmployed(true);
        this.setAvailablePersons(this.getAvailablePersons() - 1);
        this.countLabel.setText("Available: " + String.valueOf(this.getAvailablePersons()));
    }

    protected void returnPerson(Person person) {
        person.setIsEmployed(false);
        this.setAvailablePersons(this.getAvailablePersons() + 1);
        this.countLabel.setText("Available: " + String.valueOf(this.getAvailablePersons()));
    }
}