import java.util.Scanner;
import java.util.HashMap;

final class CargoShip extends Ship {

    // Declaring variables
    private double cargoValue, cargoVolume, cargoWeight;
    protected CargoShip(Scanner scanContent, HashMap<Integer, Dock> docksMap,
            HashMap<Integer, SeaPort> portsMap) {
        super(scanContent, docksMap, portsMap);
        
        if (scanContent.hasNextDouble()) {
            this.setCargoWeight(scanContent.nextDouble());
        }

        if (scanContent.hasNextDouble()) {
            this.setCargoVolume(scanContent.nextDouble());
        }

        if (scanContent.hasNextDouble()) {
            this.setCargoValue(scanContent.nextDouble());
        }
    }

    // Getters and Setters

    private void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    private void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    private void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }
	
    protected double getCargoWeight() {
        return this.cargoWeight;
    }

    protected double getCargoVolume() {
        return this.cargoVolume;
    }

    protected double getCargoValue() {
        return this.cargoValue;
    }

    @Override
    public String toString() {
        String stringOutput;

        stringOutput =  "Cargo Ship: " + super.toString() + "\n\tCargo Weight: "
            + this.getCargoWeight() + "\n\tCargo Volume: " + this.getCargoVolume()
            + "\n\tCargo Value: " + this.getCargoValue();

        return stringOutput;
    }
}