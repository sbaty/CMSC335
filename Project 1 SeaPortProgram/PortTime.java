
final class PortTime {

    // Rubric-required field
    private int time;

 
    protected PortTime(int time) {
        this.setTime(time);
    }

    // Setter

  
    private void setTime(int time) {
        this.time = time;
    }

    // Getter


    protected int getTime() {
        return this.time;
    }

    // Overriden method

    @Override
    public String toString() {
        return "Time: " + this.getTime();
    }
}