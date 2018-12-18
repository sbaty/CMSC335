import java.util.ArrayList;
import java.util.Scanner;

class Ship extends Thing {

    // Rubric-required fields
    private PortTime arrivalTime, dockTime;
    private double draft, length, weight, width;
    private ArrayList<Job> jobs;

    protected Ship(Scanner scannerContents) {
        super(scannerContents);

        if (scannerContents.hasNextDouble()) {
            this.setWeight(scannerContents.nextDouble());
        }

        if (scannerContents.hasNextDouble()) {
            this.setLength(scannerContents.nextDouble());
        }

        if (scannerContents.hasNextDouble()) {
            this.setWidth(scannerContents.nextDouble());
        }

        if (scannerContents.hasNextDouble()) {
            this.setDraft(scannerContents.nextDouble());
        }

        this.setJobs(new ArrayList<>());
    }

    // Setters

    private void setArrivalTime(PortTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    private void setDockTime(PortTime dockTime) {
        this.dockTime = dockTime;
    }

    private void setWeight(double weight) {
        this.weight = weight;
    }

    private void setLength(double length) {
        this.length = length;
    }

  
    private void setWidth(double width) {
        this.width = width;
    }

  
    private void setDraft(double draft) {
        this.draft = draft;
    }

    private void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    // Getters

    protected PortTime getArrivalTime() {
        return this.arrivalTime;
    }

 
    protected PortTime getDockTime() {
        return this.dockTime;
    }

 
    protected double getWeight() {
        return this.weight;
    }

 
    protected double getLength() {
        return this.length;
    }


    protected double getWidth() {
        return this.width;
    }

    protected double getDraft() {
        return this.draft;
    }

    protected ArrayList<Job> getJobs() {
        return this.jobs;
    }

    // Overriden methods

    @Override
    public String toString() {
        String stringOutput;

        stringOutput = super.toString() + "\n\tWeight: " + this.getWeight() + "\n\tLength: "
            + this.getLength() + "\n\tWidth: " + this.getWidth() + "\n\tDraft: " + this.getDraft()
            + "\n\tJobs:";

        if (this.getJobs().isEmpty()){
            stringOutput += " None";
        } else {
            for (Job newJob : this.getJobs()) {
                stringOutput += "\n" + newJob.toString();
            }
        }

        return stringOutput;
    }
}