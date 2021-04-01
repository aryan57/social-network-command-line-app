import java.util.*;

class Node {

    private Integer id;
    private String name;
    private String creationDate;
    private Set<String> content;
    private Set<Integer> links;

    public Node(Integer id, String name, String creationDate, Set<String> content, Set<Integer> links) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.content = content;
        this.links = links;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Set<String> getContent() {
        return content;
    }

    public void setContent(Set<String> content) {
        this.content = content;
    }

    public Set<Integer> getLinks() {
        return links;
    }

    public void setLinks(Set<Integer> links) {
        this.links = links;
    }

}