import java.util.ArrayList;
import java.util.Scanner;

final class SeaPort extends Thing {
    // Declaring variables
    private ArrayList<Dock> docks;
    private ArrayList<Ship> Queue;
    private ArrayList<Ship> ships;
    private ArrayList<Person> persons;

    protected SeaPort(Scanner scanContent) {
        super(scanContent);
        this.setDocks(new ArrayList<>());
        this.setQueue(new ArrayList<>());
        this.setShips(new ArrayList<>());
        this.setPersons(new ArrayList<>());
    }
    private void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }
    private void setQueue(ArrayList<Ship> Queue) {
        this.Queue = Queue;
    }
    private void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
    private void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
    protected ArrayList<Dock> getDocks() {
        return this.docks;
    }
    protected ArrayList<Ship> getQueue() {
        return this.Queue;
    }
    protected ArrayList<Ship> getShips() {
        return this.ships;
    }
    protected ArrayList<Person> getPersons() {
        return this.persons;
    }
    protected synchronized ArrayList<Person> getResources(Job job) {
        int counter;
        ArrayList<Person> candidates;
        boolean areAllRequirementsMet;
        String workerLogLine;

        counter = this.checkForQualifiedWorkers(job.getRequirements());
        candidates = new ArrayList<>();
        areAllRequirementsMet = true;
        workerLogLine = "";
        
        if (counter < job.getRequirements().size()) {
            job.getWorkerLog().append("No qualified workers found for " + job.getName()
                + " (" + job.getParentShip().getName() + ")\n");

            return new ArrayList<>();
        }
        outerLoop:
            for (String requirement : job.getRequirements()) {
                for (Person person : this.getPersons()) {

                    if (person.getSkill().equals(requirement) && !person.getIsEmployed()) {
                        person.setIsEmployed(true); 
                        candidates.add(person);

                        continue outerLoop;
                    }
                }
                areAllRequirementsMet = false;
                break;
            }
            if (areAllRequirementsMet) {
                workerLogLine += job.getName() + " (" + job.getParentShip().getName() + ") reserving";
                for (int i = 0; i < candidates.size(); i++) {
                    if (i == 0) {
                        workerLogLine += " ";
                    } else if (i < candidates.size() - 1) {
                        workerLogLine += ", ";
                    } else {
                        workerLogLine += " & ";
                    }
                    workerLogLine += candidates.get(i).getName();
                }
                job.getWorkerLog().append(workerLogLine + "\n");

                return candidates;
            } else {
                this.returnResources(candidates);
                return null;
            }
        }
    protected synchronized void returnResources(ArrayList<Person> resources) {
        resources.forEach((worker) -> {
            worker.setIsEmployed(false);
        });
    }
    private synchronized int checkForQualifiedWorkers(ArrayList<String> requirements) {
        int counter = 0;
        outerLoop:
        for (String requirement : requirements) {
            for (Person worker : this.getPersons()) {
                if (requirement.equals(worker.getSkill())) {
                    counter++;
                    continue outerLoop;
                }
            }
        }
        return counter;
    }
        
    @Override
    public String toString() {
        String stringOutput;

        stringOutput = "\n\nSeaPort: " + super.toString();
        for (Dock dock: this.getDocks()) {
            stringOutput += "\n> " + dock.toString();
        }

        stringOutput += "\n\n --- List of all ships in Queue:";
        for (Ship shipQueue: this.getQueue()) {
            stringOutput += "\n> " + shipQueue.toString();
        }

        stringOutput += "\n\n --- List of all ships:";
        for (Ship shipAll: this.getShips()) {
            stringOutput += "\n> " + shipAll.getName() + " " + shipAll.getIndex() + " ("
                + shipAll.getClass().getSimpleName() + ")";
        }

        stringOutput += "\n\n --- List of all persons:";
        for (Person person: this.getPersons()) {
            stringOutput += "\n> " + person.toString();
        }

        return stringOutput;
    }
}