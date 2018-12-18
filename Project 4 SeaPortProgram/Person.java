import java.util.Scanner;

final class Person extends Thing {

    // Declaring variables
    private String skill;
    private boolean isEmployed;

    protected Person(Scanner scanContent) {
        super(scanContent);

        if (scanContent.hasNext()) {
            this.setSkill(scanContent.next());
        } else {
            this.setSkill("Error");
        }
    }

    // Getters and Setters

    private void setSkill(String skill) {
        this.skill = skill;
    }
    protected void setIsEmployed(boolean isEmployed) {
        this.isEmployed = isEmployed;
    }
    protected String getSkill() {
        return this.skill;
    }
    protected boolean getIsEmployed() {
        return this.isEmployed;
    }
    @Override
    public String toString() {
        return "Person: " + super.toString() + " " + this.getSkill();
    }
}