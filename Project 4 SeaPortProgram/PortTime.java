public class PortTime {
	
	// Declaring variable
	private int time;

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

@Override
public String toString() {
    return "Time: " + this.getTime();
}
}
