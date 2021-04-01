import java.util.*;

class Business extends Node {

    private Pair location;
    private Set<Pair> individualMembers; // true(1) if owner, false(0) if customer

    public Business(Integer id, String name, String creationDate, Set<String> content, Set<Integer> links,
            Pair location, Set<Pair> individualMembers) {
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

    public Set<Pair> getIndividualMembers() {
        return individualMembers;
    }

    public void setIndividualMembers(Set<Pair> individualMembers) {
        this.individualMembers = individualMembers;
    }

}