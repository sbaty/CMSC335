import java.util.Scanner;

final class Dock extends Thing {

    // Declaring variables
    private Ship ship;

    protected Dock(Scanner scanContent) {
        super(scanContent);
    }

    protected void setShip(Ship ship) {
        this.ship = ship;
    }
    
    protected Ship getShip() {
        return this.ship;
    }

    @Override
    public String toString() {
        String stringOutput = "Dock: " + super.toString() + "\n\t";

        if (this.getShip() == null) {
            stringOutput += "EMPTY";
        } else {
            stringOutput += this.getShip().toString();
        }

        return stringOutput;
    }
}