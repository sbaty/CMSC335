import java.util.Scanner;

class Thing implements Comparable<Thing> {

    // Rubric-required variables in use in all objects
    private int index;
    private String name;
    private int parent;


    protected Thing(Scanner scannerContents) {
        if (scannerContents.hasNext()) {
            this.setName(scannerContents.next());
        }

        if (scannerContents.hasNextInt()) {
            this.setIndex(scannerContents.nextInt());
        }

        if (scannerContents.hasNextInt()) {
            this.setParent(scannerContents.nextInt());
        }
    }

    // Setters

    private void setIndex(int index) {
        this.index = index;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setParent(int parent) {
        this.parent = parent;
    }

    // Getters

    protected int getIndex() {
        return this.index;
    }

    protected String getName() {
        return this.name;
    }

    protected int getParent() {
        return this.parent;
    }

    // Overridden methods


    @Override
    public String toString(){
        // It didn't make sense to include parent, since things are organized by parent anyway
        return this.getName() + " " + this.getIndex();
    }

    @Override
    public int compareTo(Thing thingInstance) {
        if (
            (thingInstance.getIndex() == this.getIndex()) &&
            (thingInstance.getName().equals(this.getName())) &&
            (thingInstance.getParent() == this.getParent())
        ) {
            return 1;
        } else {
            return 0;
        }
    }
}