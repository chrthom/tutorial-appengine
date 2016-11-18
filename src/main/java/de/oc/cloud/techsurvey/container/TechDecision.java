package de.oc.cloud.techsurvey.container;

public class TechDecision extends Container<TechDecision> {

    private String leftTechId;

    private String rightTechId;

    private Preference preference;

    public TechDecision() {
    }

    public String getLeftTechId() {
        return leftTechId;
    }

    public void setLeftTechId(String leftTechId) {
        this.leftTechId = leftTechId;
    }

    public String getRightTechId() {
        return rightTechId;
    }

    public void setRightTechId(String rightTechId) {
        this.rightTechId = rightTechId;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }
}
