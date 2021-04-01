import java.util.*;

class Organisation extends Node {

    private Pair location;
    private Set<Integer> individualMembers; // linked only

    public Organisation(Integer id, String name, String creationDate, Set<String> content, Set<Integer> links,
            Pair location, Set<Integer> individualMembers) {
        super(id, name, creationDate, content, links);
        this.location = location;
        this.individualMembers = individualMembers;
    }

    public Pair getLocation() {
        return location;
    }

    public void setLocation(Pair location) {
        this.location = location;
    }

    public Set<Integer> getIndividualMembers() {
        return individualMembers;
    }

    public void setIndividualMembers(Set<Integer> individualMembers) {
        this.individualMembers = individualMembers;
    }

}
