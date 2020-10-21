package bg.sofia.uni.fmi.mjt.jira;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Filter;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Repository;
import bg.sofia.uni.fmi.mjt.jira.issues.Issue;

public class Jira implements Filter, Repository{

    private static final int ISSUES_CAPACITY = 100;

    private Issue[] issues;
    private int numberOfIssues;

    public Jira() {
        issues = new Issue[ISSUES_CAPACITY];
        numberOfIssues = 0;
    }

    public Issue find(String issueID) {
        if(issueID == null){
            throw new RuntimeException("The issueID is null!");
        }
        for(int i = 0; i < numberOfIssues; i++) {
            if (issueID.equals(issues[i].getIssueID())) {
                return issues[i];
            }
        }
        return null;
    }

    public void addIssue(Issue issue) {
        if(issue == null){
            throw new RuntimeException("The issue is null!");
        }
        if(numberOfIssues < ISSUES_CAPACITY) {
            for(int i = 0; i < numberOfIssues; i++) {
                if (issue.equals(issues[i])) {
                    throw new RuntimeException("This issue already exists!");
                }
            }
            issues[numberOfIssues++] = issue;
        }
        else throw new RuntimeException("No capacity for new issue!");
    }

    public void addActionToIssue(Issue issue, WorkAction action, String actionDescription) {
        if(issue == null){
            throw new RuntimeException("The issue is null!");
        }
        for(int i = 0; i < numberOfIssues; i++) {
            if (issue.equals(issues[i])) {
                issue.addAction(action, actionDescription);
            }
        }
    }
    public void resolveIssue(Issue issue, IssueResolution resolution) {
        if(issue == null){
            throw new RuntimeException("The issue is null!");
        }
        for(int i = 0; i < numberOfIssues; i++) {
            if (issue.equals(issues[i])) {
                issue.resolve(resolution);
            }
        }
    }

}
