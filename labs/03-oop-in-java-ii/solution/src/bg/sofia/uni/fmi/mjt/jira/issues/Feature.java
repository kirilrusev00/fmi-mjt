package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

public class Feature extends Issue{

    public Feature(IssuePriority priority, Component component, String description){
        super(priority, component, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        if (resolution == null){
            throw new RuntimeException("Cannot resolve issue!");
        }
        boolean isDesignIncluded = false;
        boolean isImplementationIncluded = false;
        boolean isTestsIncluded = false;
        for (int i = 0; i < getNumberOfActions(); i++){
            if (getActions()[i] == WorkAction.DESIGN){
                isDesignIncluded = true;
            }
            if (getActions()[i] == WorkAction.IMPLEMENTATION){
                isImplementationIncluded = true;
            }
            if (getActions()[i] == WorkAction.TESTS){
                isTestsIncluded = true;
            }
        }
        if(isDesignIncluded == true && isImplementationIncluded == true && isTestsIncluded == true){
            setLastModifiedOn();
            setResolution(resolution);
            setStatus(IssueStatus.RESOLVED);
        }
        else throw new RuntimeException("Cannot resolve issue!");
    }
}
