import java.util.Scanner;

final class Person extends Thing {

    // Rubric-required field
    private String skill;


    protected Person(Scanner scannerContents) {
        super(scannerContents);

        if (scannerContents.hasNext()) {
            this.setSkill(scannerContents.next());
        } else {
            this.setSkill("Error");
        }
    }

    // Setter

 
    private void setSkill(String skill) {
        this.skill = skill;
    }

    // Getter

    protected String getSkill() {
        return this.skill;
    }

    // Overridden method


    @Override
    public String toString() {
        return "Person: " + super.toString() + " " + this.getSkill();
    }
}