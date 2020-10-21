package bg.sofia.uni.fmi.mjt.jira.issues;

public class Component {

    private String name;
    private String shortName;

    public Component(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
