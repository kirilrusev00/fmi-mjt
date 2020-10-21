package bg.sofia.uni.fmi.mjt.jira.enums;

public enum WorkAction {
    RESEARCH("research"),
    DESIGN("design"),
    IMPLEMENTATION("implementation"),
    TESTS("tests"),
    FIX("fix");

    private String abbreviation;
    WorkAction(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
