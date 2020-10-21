package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

public class Task extends Issue{

    public Task(IssuePriority priority, Component component, String description){
        super(priority, component, description);
    }

    @Override
    public void addAction(WorkAction action, String description) {
        if(action == null || description == null){
            throw new RuntimeException("The info for this action is not enough");
        }
        if(action == WorkAction.RESEARCH || action == WorkAction.DESIGN) {
            super.addAction(action, description);
        }
        else throw new RuntimeException("You cannot add actions Fix, Implementation and Tests to the action log!");
    }

    @Override
    public void resolve(IssueResolution resolution) {
        if (resolution == null){
            throw new RuntimeException("Cannot resolve issue!");
        }
        setLastModifiedOn();
        setResolution(resolution);
        setStatus(IssueStatus.RESOLVED);
    }
}
