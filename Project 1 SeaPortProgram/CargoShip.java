import java.util.Scanner;

final class CargoShip extends Ship {

    // Rubric-required fields
    private double cargoValue, cargoVolume, cargoWeight;

    /**
     * Parameterized constructor
     * @param scannerContents Contents of <code>.txt</code> file
     */
    protected CargoShip(Scanner scannerContents) {
        super(scannerContents);

        if (scannerContents.hasNextDouble()) {
            this.setCargoWeight(scannerContents.nextDouble());
        }

        if (scannerContents.hasNextDouble()) {
            this.setCargoVolume(scannerContents.nextDouble());
        }

        if (scannerContents.hasNextDouble()) {
            this.setCargoValue(scannerContents.nextDouble());
        }
    }

    // Setters

    private void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    private void setCargoVolume(double cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    private void setCargoValue(double cargoValue) {
        this.cargoValue = cargoValue;
    }

    // Getters

    protected double getCargoWeight() {
        return this.cargoWeight;
    }

    protected double getCargoVolume() {
        return this.cargoVolume;
    }

    protected double getCargoValue() {
        return this.cargoValue;
    }

    // Overriden methods

    @Override
    public String toString() {
        String stringOutput;

        stringOutput =  "Cargo Ship: " + super.toString() + "\n\tCargo Weight: "
            + this.getCargoWeight() + "\n\tCargo Volume: " + this.getCargoVolume()
            + "\n\tCargo Value: " + this.getCargoValue();

        return stringOutput;
    }
}