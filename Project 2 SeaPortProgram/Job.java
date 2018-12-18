import java.util.ArrayList;
import java.util.Scanner;

final class Job extends Thing {

    // Declaring variables
    private ArrayList<String> requirements;
    private double duration;


    protected Job(Scanner scanContent) {
        super(scanContent);
        if (scanContent.hasNextDouble()) {
            this.setDuration(scanContent.nextDouble());
        }

        this.setRequirements(new ArrayList<>());
        while (scanContent.hasNext()) {
            this.getRequirements().add(scanContent.next());
        }
    }

    //Getters and Setters

    private void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }  
	private void setDuration(double duration) {
        this.duration = duration;
    }

    protected ArrayList<String> getRequirements() {
        return this.requirements;
    }
    protected double getDuration() {
        return this.duration;
    }

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