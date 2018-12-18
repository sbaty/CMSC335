import java.util.Scanner;

class Thing implements Comparable<Thing> {

    //Declaring variables
    private int index;
    private String name;
    private int parent;

    protected Thing(Scanner scanContent) {
        if (scanContent.hasNext()) {
            this.setName(scanContent.next());
        }

        if (scanContent.hasNextInt()) {
            this.setIndex(scanContent.nextInt());
        }

        if (scanContent.hasNextInt()) {
            this.setParent(scanContent.nextInt());
        }
    }

    // Getters and Setters
    private void setName(String name) {
        this.name = name;
    }
    private void setIndex(int index) {
        this.index = index;
    }
    private void setParent(int parent) {
        this.parent = parent;
    }
	
    protected String getName() {
        return this.name;
    }
    protected int getIndex() {
        return this.index;
    }
    protected int getParent() {
        return this.parent;
    }

    @Override
    public String toString(){
        return this.getName() + " " + this.getIndex();
    }

    @Override
    public int compareTo(Thing instanceOfThing) {
        if (
		    (instanceOfThing.getName().equals(this.getName())) &&
            (instanceOfThing.getIndex() == this.getIndex()) &&
            (instanceOfThing.getParent() == this.getParent())
        ) {
            return 1;
        } else {
            return 0;
        }
    }
}