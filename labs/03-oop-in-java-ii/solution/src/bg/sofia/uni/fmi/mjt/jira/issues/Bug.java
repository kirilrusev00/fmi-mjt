package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

public class Bug extends Issue{

    public Bug(IssuePriority priority, Component component, String description){
        super(priority, component, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        if (resolution == null){
            throw new RuntimeException("Cannot resolve issue!");
        }
        boolean isFixIncluded = false;
        boolean isTestsIncluded = false;
        for (int i = 0; i < getNumberOfActions(); i++){
            if (getActions()[i] == WorkAction.FIX){
                isFixIncluded = true;
            }
            if (getActions()[i] == WorkAction.TESTS){
                isTestsIncluded = true;
            }
        }
        if(isFixIncluded == true && isTestsIncluded == true) {
            setLastModifiedOn();
            setResolution(resolution);
            setStatus(IssueStatus.RESOLVED);
        }
        else throw new RuntimeException("Cannot resolve issue!");
    }
}
