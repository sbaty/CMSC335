import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
public class Ship extends Thing{
	
	//Declaring variables
	private PortTime arrivalTime, dockTime;
	private double draft, length, weight, width;
	private ArrayList<Job> jobs;
    private SeaPort port;
    private Dock dock;
    private HashMap<Integer, Dock> docksMap;
    
	   protected Ship(Scanner scanContent, HashMap<Integer, Dock> docksMap,
	            HashMap<Integer, SeaPort> portsMap) {
		   
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
	        this.setPort(docksMap, portsMap);
	        this.setDocksMap(docksMap);
	        this.setDock();
	    }
	   
	   //Getters and Setters
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
	    private void setPort(HashMap<Integer, Dock> docksMap, HashMap<Integer, SeaPort> portsMap) {
	        this.port = portsMap.get(this.getParent());

	        if (this.port == null) {
	            Dock pier = docksMap.get(this.getParent());
	            this.port = portsMap.get(pier.getParent());
	        }
	    }
	    private void setDock() {
	        if (this.getDocksMap().containsKey(this.getParent())) {
	            this.dock = this.getDocksMap().get(this.getParent());
	        } else {
	            this.dock = null;
	        }
	    }
	    private void setDocksMap(HashMap<Integer, Dock> docksMap) {
	        this.docksMap = docksMap;
	    }
	    protected void setDock(Dock dock) {
	        this.dock = dock;
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
	     private HashMap<Integer, Dock> getDocksMap() {
	         return this.docksMap;
	     }
	     protected SeaPort getPort() {
	         return this.port;
	     }
	     protected Dock getDock() {
	         return this.dock;
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

