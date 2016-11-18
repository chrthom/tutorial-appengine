package de.oc.cloud.techsurvey.container;

public class Tech extends Container<Tech> {
    String name;
    String type;
    String logo;
    Long score;

    public String getName() {
        return name;
    }

    public Tech setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Tech setType(String type) {
        this.type = type;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public Tech setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public Long getScore() {
        return score;
    }

    public Tech setScore(Long score) {
        this.score = score;
        return this;
    }
}
