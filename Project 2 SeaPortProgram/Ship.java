import java.util.ArrayList;
import java.util.Scanner;

public class Ship extends Thing{
	
	//Declaring variables
	private PortTime arrivalTime, dockTime;
	private double draft, length, weight, width;
	private ArrayList<Job> jobs;
	
	   protected Ship(Scanner scanContent) {
	        super(scanContent);

	        if (scanContent.hasNextDouble()) {
	            this.setWeight(scanContent.nextDouble());
	        }
	        if (scanContent.hasNextDouble()) {
	            this.setLength(scanContent.nextDouble());
	        }
	        if (scanContent.hasNextDouble()) {
	            this.setWidth(scanContent.nextDouble());
	        }
	        if (scanContent.hasNextDouble()) {
	            this.setDraft(scanContent.nextDouble());
	        }

	        this.setJobs(new ArrayList<>());
	        
	    }

		private void setLength(double length) {
	        this.length = length;
	    }
	  
	    private void setWidth(double width) {
	        this.width = width;
	    }
	    @SuppressWarnings("unused")
		private void setDockTime(PortTime dockTime) {
	        this.dockTime = dockTime;
	    }

	    private void setWeight(double weight) {
	        this.weight = weight;
	    }
	    @SuppressWarnings("unused")
		private void setArrivalTime(PortTime arrivalTime) {
	        this.arrivalTime = arrivalTime;
	    }
	    private void setDraft(double draft) {
	        this.draft = draft;
	    }

	    private void setJobs(ArrayList<Job> jobs) {
	        this.jobs = jobs;
	    }

	     protected double getLength() {
	         return this.length;
	     }

	     protected double getWidth() {
	         return this.width;
	     }

	     protected PortTime getDockTime() {
	         return this.dockTime;
	     }
	     protected double getWeight() {
	         return this.weight;
	     }
	     protected PortTime getArrivalTime() {
	         return this.arrivalTime;
	     }
	     protected double getDraft() {
	         return this.draft;
	     }

	     protected ArrayList<Job> getJobs() {
	         return this.jobs;
	     }
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

