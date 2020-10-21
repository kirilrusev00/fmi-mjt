package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

import java.lang.reflect.AccessibleObject;
import java.time.LocalDateTime;

public abstract class Issue {

    private static final int ACTION_LOG_CAPACITY = 20;

    private static int id;
    private String issueID;
    private String description;
    private IssuePriority priority;
    private IssueResolution resolution;
    private IssueStatus status;
    private Component component;
    private WorkAction[] actions;
    private String[] actionLog;
    private int numberOfActions;
    private final LocalDateTime createdOn;
    private LocalDateTime lastModifiedOn;

    public Issue(IssuePriority priority, Component component, String description) {
        this.priority = priority;
        this.component = component;
        this.description = description;
        issueID = component.getShortName() + "-" + id;
        id++;
        resolution = IssueResolution.UNRESOLVED;
        status = IssueStatus.OPEN;
        actions = new WorkAction[ACTION_LOG_CAPACITY];
        actionLog = new String [ACTION_LOG_CAPACITY];
        numberOfActions = 0;
        createdOn = LocalDateTime.now();
        lastModifiedOn = LocalDateTime.now();
    }

    public String getIssueID() {
        return issueID;
    }
    public String getDescription() {
        return description;
    }
    public IssuePriority getPriority() {
        return priority;
    }
    public IssueResolution getResolution() {
        return resolution;
    }
    public IssueStatus getStatus() {
        return status;
    }
    public Component getComponent() {
        return component;
    }
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public LocalDateTime getLastModifiedOn() {
        return lastModifiedOn;
    }
    public String[] getActionLog() {
        return actionLog;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
        lastModifiedOn = LocalDateTime.now();
    }
    public void setLastModifiedOn() {
        lastModifiedOn = LocalDateTime.now();
    }
    public void addAction(WorkAction action, String description) {
        if(action == null || description == null){
            throw new RuntimeException("The info for this action is not enough");
        }
        if (numberOfActions < ACTION_LOG_CAPACITY) {
            actions[numberOfActions] = action;
            String newAction = action.getAbbreviation().toLowerCase() + ": " + description;
            actionLog[numberOfActions++] = newAction;
        }
        else throw new RuntimeException("The action log is full!");
    }

    public abstract void resolve(IssueResolution resolution);

    public WorkAction[] getActions() {
        return actions;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public void setResolution(IssueResolution resolution) {
        this.resolution = resolution;
    }
}
