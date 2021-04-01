import java.util.*;

class Group extends Node {

    private Set<Integer> individualMembers; // linked only
    private Set<Integer> businessMembers;

    public Group(Integer id, String name, String creationDate, Set<String> content, Set<Integer> links,
            Set<Integer> individualMembers, Set<Integer> businessMembers) {
        super(id, name, creationDate, content, links);
        this.individualMembers = individualMembers;
        this.businessMembers = businessMembers;
    }

    public Set<Integer> getIndividualMembers() {
        return individualMembers;
    }

    public void setIndividualMembers(Set<Integer> individualMembers) {
        this.individualMembers = individualMembers;
    }

    public Set<Integer> getBusinessMembers() {
        return businessMembers;
    }

    public void setBusinessMembers(Set<Integer> businessMembers) {
        this.businessMembers = businessMembers;
    }

}