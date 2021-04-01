import java.util.*;

class Individual extends Node {

    private String birDate;

    public Individual(Integer id, String name, String creationDate, Set<String> content, Set<Integer> links,
            String birDate) {
        super(id, name, creationDate, content, links);
        this.birDate = birDate;
    }

    public String getBirDate() {
        return birDate;
    }

    public void setBirDate(String birDate) {
        this.birDate = birDate;
    }

}