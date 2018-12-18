import java.util.ArrayList;
import java.util.Scanner;

final class Job extends Thing {

    // Rubric-required fields
    private double duration;
    private ArrayList<String> requirements;


    protected Job(Scanner scannerContents) {
        super(scannerContents);
        if (scannerContents.hasNextDouble()) {
            this.setDuration(scannerContents.nextDouble());
        }

        this.setRequirements(new ArrayList<>());
        while (scannerContents.hasNext()) {
            this.getRequirements().add(scannerContents.next());
        }
    }

    // Setters


    private void setDuration(double duration) {
        this.duration = duration;
    }

    private void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    // Getters


    protected double getDuration() {
        return this.duration;
    }

    protected ArrayList<String> getRequirements() {
        return this.requirements;
    }

    // Overriden method


    @Override
    public String toString() {
        String stringOutput;

        stringOutput = "\t\t" + super.toString() + "\n\t\tDuration: " + this.getDuration()
            + "\n\t\tRequirements:";

        if (this.getRequirements().isEmpty()) {
            stringOutput += "\n\t\t\t - None";
        } else {
            for(String requiredSkill : this.getRequirements()){
                stringOutput += "\n\t\t\t - " + requiredSkill;
            }
        }

        return stringOutput;
    }
}