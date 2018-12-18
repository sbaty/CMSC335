import java.util.Scanner;

final class Person extends Thing {

    // Declaring variables
    private String skill;

    protected Person(Scanner scanContent) {
        super(scanContent);

        if (scanContent.hasNext()) {
            this.setSkill(scanContent.next());
        } else {
            this.setSkill("Error");
        }
    }

    // Getter and Setter

    private void setSkill(String skill) {
        this.skill = skill;
    }

    protected String getSkill() {
        return this.skill;
    }

    @Override
    public String toString() {
        return "Person: " + super.toString() + " " + this.getSkill();
    }
}