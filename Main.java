/*
    author: Aryan Agarwal
    group: 4
    roll: 19CS30005
*/

/*
    commands to run this program
    javac Main.java
    java Main
*/

import java.util.*;
import java.text.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // mapping of each node's id to its type
    static Map<Integer, Individual> mapIndividual = new HashMap<Integer, Individual>();

    // mapping of id to different types of nodes
    static Map<Integer, Group> mapGroup = new HashMap<Integer, Group>();
    static Map<Integer, Business> mapBusiness = new HashMap<Integer, Business>();
    static Map<Integer, Organisation> mapOrganisation = new HashMap<Integer, Organisation>();
    static Map<Integer, String> mapType = new HashMap<Integer, String>();

    static String datePattern = "dd/MM/yyyy";
    static String TODAY = new SimpleDateFormat(datePattern).format(new Date());
    static String date = TODAY;

    static Pair location = new Pair(0, 0);

    static Integer maxInvalidAttempts = 3;

    static Set<Integer> linkedIndividualMembers = new HashSet<Integer>();
    static Set<Integer> businessMembers = new HashSet<Integer>();

    static boolean validNode;
    static Node node = new Node(-1, "", TODAY, new HashSet<String>(), new HashSet<Integer>());

    public static void main(String[] args) {

        Integer cnt = 0;
        String choice;
        while (true) {
            System.out.print("\n\n+++++ Social Network +++++\n");
            System.out.print("+++++ What do you want to do? +++++\n\n");
            System.out.print("\t[1] Create and delete nodes of each type.\n");
            System.out.print("\t[2] Search for nodes using the name or type or birthday.\n");
            System.out.print("\t[3] Print all linked nodes to a given input node.\n");
            System.out.print("\t[4] Create and post content by a user.\n");
            System.out.print("\t[5] Search for content posted by any node.\n");
            System.out.print("\t[6] Display all content posted by nodes linked to a given node.\n");
            System.out.print(
                    "\t[7] A master text-based interface to print all nodes in the system and utilize all the above functionalities.\n");
            System.out.print("\t[8] Quit the program.\n");

            System.out.print("\nChoice : ");

            choice = (sc.nextLine());
            switch (choice) {
                case "1":
                    createAndDeleteNode();
                    cnt = 0;
                    break;
                case "2":
                    searchNodes();
                    cnt = 0;
                    break;
                case "3":
                    printLinkedNodes();
                    cnt = 0;
                    break;
                case "4":
                    createAndPostContent();
                    cnt = 0;
                    break;
                case "5":
                    printContent();
                    cnt = 0;
                    break;
                case "6":
                    printLinkedContent();
                    cnt = 0;
                    break;
                case "7":
                    printMaster();
                    cnt = 0;
                    break;
                case "8":
                    System.out.println("\nThanks for using the program\n\tbye :)\n");
                    return;
                default:
                    cnt++;
                    System.out.println("Invalid Choice");
                    if (cnt == maxInvalidAttempts) {
                        System.out.println(
                                "Pressed " + maxInvalidAttempts + " wrong choices in a row, exiting the program....\n");
                        return;
                    }

            }
        }
    }

    private static void basicContentPrinting(Integer id) {

        switch (mapType.get(id)) {
            case "Individual":
                System.out.print(
                        "\n   \tContent posted by Individual node [name : " + mapIndividual.get(id).getName() + "] - ");
                for (String post : mapIndividual.get(id).getContent()) {
                    System.out.print("\n   \t\t[" + post + "]");
                }
                break;
            case "Group":
                System.out.print("\n   \tContent posted by Group node [name : " + mapGroup.get(id).getName() + "] - ");
                for (String post : mapGroup.get(id).getContent()) {
                    System.out.print("\n   \t\t[" + post + "]");
                }
                break;
            case "Business":
                System.out.print(
                        "\n   \tContent posted by Business node [name : " + mapBusiness.get(id).getName() + "] - ");
                for (String post : mapBusiness.get(id).getContent()) {
                    System.out.print("\n   \t\t[" + post + "]");
                }
                break;
            case "Organisation":
                System.out.print("\n   \tContent posted by Organisation node [name : "
                        + mapOrganisation.get(id).getName() + "] - ");
                for (String post : mapOrganisation.get(id).getContent()) {
                    System.out.print("\n   \t\t[" + post + "]");
                }
                break;
        }

    }

    private static void printContent() {

        System.out.print("\n+++++Content Printing of a node+++++");
        Integer cnt = 0;

        while (true) {
            System.out.print("\nEnter the id of the node you want to print content : ");
            try {
                Integer id = Integer.parseInt(sc.nextLine());
                if (!mapType.containsKey(id)) {
                    cnt++;
                    System.out.print("\nThere is no node with [id : " + id + "]. Please enter correct id.");
                } else {
                    basicContentPrinting(id);
                    return;
                }
            } catch (Exception e) {
                cnt++;
                System.out.print("\nPlease enter numeric id only");
            }

            if (cnt == maxInvalidAttempts) {
                System.out.println(
                        "Pressed " + maxInvalidAttempts + " wrong choices in a row, exiting the function...\n");
                return;
            }
        }
    }

    private static void printLinkedContent() {
        System.out.print("\n+++++Content Printing of a nodes linked to given node+++++");
        Integer cnt = 0;

        while (true) {
            System.out.print("\nEnter the id of the node whose linked nodes content's you want to print : ");
            try {
                Integer id = Integer.parseInt(sc.nextLine());
                if (!mapType.containsKey(id)) {
                    cnt++;
                    System.out.print("\nThere is no node with [id : " + id + "]. Please enter correct id.");
                } else {
                    switch (mapType.get(id)) {
                        case "Individual":
                            System.out.print("\nPrinting content of nodes linked to Individual node [id : " + id
                                    + "] [name : " + mapIndividual.get(id).getName() + "]");
                            for (Integer ID : mapIndividual.get(id).getLinks()) {
                                basicContentPrinting(ID);
                            }
                            break;
                        case "Group":
                            System.out.print("\nPrinting content of nodes linked to Group node [id : " + id
                                    + "] [name : " + mapGroup.get(id).getName() + "]");
                            for (Integer ID : mapGroup.get(id).getLinks()) {
                                basicContentPrinting(ID);
                            }
                            break;
                        case "Business":
                            System.out.print("\nPrinting content of nodes linked to Business node [id : " + id
                                    + "] [name : " + mapBusiness.get(id).getName() + "]");
                            for (Integer ID : mapBusiness.get(id).getLinks()) {
                                basicContentPrinting(ID);
                            }
                            break;
                        case "Organisation":
                            System.out.print("\nPrinting content of nodes linked to Organisation node [id : " + id
                                    + "] [name : " + mapOrganisation.get(id).getName() + "]");
                            for (Integer ID : mapOrganisation.get(id).getLinks()) {
                                basicContentPrinting(ID);
                            }
                            break;
                    }
                    return;
                }
            } catch (Exception e) {
                cnt++;
                System.out.print("\nPlease enter numeric id only");
            }

            if (cnt == maxInvalidAttempts) {
                System.out.println(
                        "Pressed " + maxInvalidAttempts + " wrong choices in a row, exiting the function...\n");
                return;
            }
        }
    }

    private static void createAndPostContent() {
        System.out.print("\n+++++Content creation and posting+++++");
        Integer cnt = 0;

        while (true) {
            System.out.print("\nEnter the id of the node you want to create a content : ");
            try {
                Integer id = Integer.parseInt(sc.nextLine());

                if (!mapType.containsKey(id)) {
                    cnt++;
                    System.out.print("\nThere is no node with [id : " + id + "]. Please enter correct id.");
                } else {

                    System.out.print("\nEnter the content you want to create for ");
                    printNameId(id);
                    System.out.print("\n");
                    String content = sc.nextLine();
                    boolean alreadyPosted = false;

                    switch (mapType.get(id)) {

                        case "Individual":

                            if (mapIndividual.get(id).getContent().contains(content))
                                alreadyPosted = true;
                            mapIndividual.get(id).getContent().add(content);
                            break;

                        case "Group":

                            if (mapGroup.get(id).getContent().contains(content))
                                alreadyPosted = true;
                            mapGroup.get(id).getContent().add(content);
                            break;

                        case "Business":

                            if (mapBusiness.get(id).getContent().contains(content))
                                alreadyPosted = true;
                            mapBusiness.get(id).getContent().add(content);
                            break;

                        case "Organisation":

                            if (mapOrganisation.get(id).getContent().contains(content))
                                alreadyPosted = true;
                            mapOrganisation.get(id).getContent().add(content);
                            break;
                    }

                    if (alreadyPosted)
                        System.out.print("\nEntered [content : " + content + "] was already posted, reposting it.");
                    else
                        System.out.print("\nPosting the entered [content : " + content + "] for the first time.");

                    return;
                }
            } catch (Exception e) {
                cnt++;
                System.out.print("\nPlease enter numeric id only");
            }

            if (cnt == maxInvalidAttempts) {
                System.out
                        .print("\nPressed " + maxInvalidAttempts + "wrong choices in a row, exiting the function...\n");
                return;
            }
        }

    }

    private static void createAndDeleteNode() {

        Integer cnt = 0;

        while (true) {
            System.out.print("\n\n+++++ Node creation/deletion +++++");
            System.out.print("\n\nWhat do you want to do?\n\t[1] Create Node\n\t[2] Delete Node\n");
            System.out.print("\nChoice : ");
            String choice = (sc.nextLine());

            if (choice.equals("1")) {
                System.out.print("\nWhat type of node you want to create ?\n");
                System.out.println("\n\t[1] Individual\n\t[2] Group\n\t[3] Business\n\t[4] Organisation");
                System.out.print("\nChoice : ");

                String type = (sc.nextLine());

                switch (type) {
                    case "1":
                        createIndividual();
                        break;
                    case "2":
                        createGroup();
                        break;
                    case "3":
                        createBusiness();
                        break;
                    case "4":
                        createOrganisation();
                        break;
                    default:
                        System.out.println("Wrong choice entered. Going to main menu.");
                }
                return;

            } else if (choice.equals("2")) {

                System.out.print("\nEnter the id of the node you want to delete : ");
                String input = (sc.nextLine());

                try {
                    Integer id = Integer.parseInt(input);

                    if (mapType.containsKey(id)) {

                        printNameId(id);
                        System.out.print("was successfully deleted.");

                        switch (mapType.get(id)) {
                            case "Individual":
                                mapIndividual.remove(id);
                                break;
                            case "Group":
                                mapGroup.remove(id);
                                break;
                            case "Business":
                                mapBusiness.remove(id);
                                break;
                            case "Organisation":
                                mapOrganisation.remove(id);
                                break;
                        }

                        mapType.remove(id);

                    } else {
                        System.out.print("\nThere is no Node with [id : " + id + "]");
                    }

                } catch (Exception e) {
                    System.out.print("\nInvalid input.\nid's can be numbers only.");
                }

                return;

            } else {
                cnt++;
                System.out.print("\tInvalid Choice\n");
                if (cnt == maxInvalidAttempts) {
                    System.out.println(
                            "Pressed " + maxInvalidAttempts + " wrong choices in a row, exiting the function....");
                    return;
                }
            }

        }

    }

    private static void searchNodes() {

        Integer cnt = 0;

        while (true) {
            System.out.print("\n+++++ Node searching +++++");
            System.out.print("\nWhat do you want to use for searching?\n\t[1] Name\n\t[2] Type\n\t[3] Birthday");
            System.out.print("\n\nChoice : ");
            String choice = (sc.nextLine());

            switch (choice) {

                case "1":
                    searchName();
                    return;
                case "2":
                    searchType();
                    return;
                case "3":
                    searchBirthday();
                    return;
                default:
                    cnt++;
                    System.out.print("Invalid Choice\n");
                    if (cnt == maxInvalidAttempts) {
                        System.out.println(
                                "Pressed " + maxInvalidAttempts + " wrong choices in a row, exiting the function....");
                        return;
                    }
            }
        }

    }

    private static void searchName() {

        System.out.print("\nEnter the name of the node you want to search : ");
        String inputName = sc.nextLine();

        System.out.print("\nId's of Individual nodes with [name : " + inputName + "]\n\t[ ");
        for (Map.Entry<Integer, Individual> node : mapIndividual.entrySet()) {
            if (node.getValue().getName().equals(inputName))
                System.out.print(" " + node.getValue().getId() + " ");
        }
        System.out.print("]");

        System.out.print("\nId's of Group nodes with [name : " + inputName + "]\n\t[ ");
        for (Map.Entry<Integer, Group> node : mapGroup.entrySet()) {
            if (node.getValue().getName().equals(inputName))
                System.out.print(" " + node.getValue().getId() + " ");
        }
        System.out.print("]");

        System.out.print("\nId's of Business nodes with [name : " + inputName + "]\n\t[ ");
        for (Map.Entry<Integer, Business> node : mapBusiness.entrySet()) {
            if (node.getValue().getName().equals(inputName))
                System.out.print(" " + node.getValue().getId() + " ");
        }
        System.out.print("]");

        System.out.print("\nId's of Organisation nodes with [name : " + inputName + "]\n\t[ ");
        for (Map.Entry<Integer, Organisation> node : mapOrganisation.entrySet()) {
            if (node.getValue().getName().equals(inputName))
                System.out.print(" " + node.getValue().getId() + " ");
        }
        System.out.print("]");

    }

    private static void searchType() {

        Integer cnt = 0;

        while (true) {
            System.out.print("\nEnter the type of node you want to search");
            System.out.print("\n\n\t[1] Individual\n\t[2] Group\n\t[3] Business\n\t[4] Organisation");
            System.out.print("\n\nChoice : ");
            String choice = (sc.nextLine());

            switch (choice) {

                case "1":
                    System.out.print("\nPrinting all Individual nodes : ");
                    for (Map.Entry<Integer, Individual> node : mapIndividual.entrySet()) {
                        printNameId(node.getValue().getId());
                    }
                    return;
                case "2":
                    System.out.print("\nPrinting all Group nodes : ");
                    for (Map.Entry<Integer, Group> node : mapGroup.entrySet()) {
                        printNameId(node.getValue().getId());
                    }
                    return;
                case "3":
                    System.out.print("\nPrinting all Business nodes : ");
                    for (Map.Entry<Integer, Business> node : mapBusiness.entrySet()) {
                        printNameId(node.getValue().getId());
                    }
                    return;
                case "4":
                    System.out.print("\nPrinting all Organisation nodes : ");
                    for (Map.Entry<Integer, Organisation> node : mapOrganisation.entrySet()) {
                        printNameId(node.getValue().getId());
                    }
                    return;
                default:
                    cnt++;
                    System.out.print("Invalid Choice\n");
                    if (cnt == maxInvalidAttempts) {
                        System.out.println("\nPressed " + maxInvalidAttempts
                                + " wrong choices in a row, exiting the function....");
                        return;
                    }
            }
        }

    }

    private static void searchBirthday() {

        validNode = true;
        getDate("Birthday");
        if (!validNode)
            return;

        System.out.print("\nPrinting Individual nodes with birthday on (" + date + ") : ");
        for (Map.Entry<Integer, Individual> me : mapIndividual.entrySet()) {
            if (me.getValue().getBirDate().equals(date))
                System.out
                        .print("\n\t[name : " + me.getValue().getName() + "]" + "[id : " + me.getValue().getId() + "]");
        }

    }

    private static void printLinkedNodes() {

        Integer cnt = 0;
        while (true) {
            System.out.print("\n+++++ Linked Nodes Printing +++++");
            System.out.print("\nEnter the id of the node : ");

            try {
                Integer Id = Integer.parseInt(sc.nextLine());
                if (mapType.containsKey(Id)) {

                    switch (mapType.get(Id)) {

                        case "Individual":

                            System.out.print("\nPrinting nodes linked to the Individual node [id : " + Id + "] [name : "
                                    + mapIndividual.get(Id).getName() + "]: ");
                            for (Integer id : mapIndividual.get(Id).getLinks())
                                printNameId(id);
                            return;

                        case "Group":

                            System.out.print("\nPrinting nodes linked to the Group node [id : " + Id + "] [name : "
                                    + mapGroup.get(Id).getName() + "]: ");
                            for (Integer id : mapGroup.get(Id).getLinks())
                                printNameId(id);
                            return;

                        case "Business":

                            System.out.print("\nPrinting nodes linked to the Business node [id : " + Id + "] [name : "
                                    + mapBusiness.get(Id).getName() + "]: ");
                            for (Integer id : mapBusiness.get(Id).getLinks())
                                printNameId(id);
                            return;

                        case "Organisation":
                            System.out.print("\nPrinting nodes linked to the Organisation node [id : " + Id
                                    + "] [name : " + mapOrganisation.get(Id).getName() + "]: ");

                            for (Integer id : mapOrganisation.get(Id).getLinks())
                                printNameId(id);
                            return;

                    }
                } else {
                    System.out.print("\nThere is no node with [id : " + Id + "].");
                    cnt++;
                }
            } catch (Exception e) {
                System.out.print("\nEnter only numeric Id.");
                cnt++;
            }

            if (cnt == maxInvalidAttempts) {
                System.out.println(
                        "\nPressed " + maxInvalidAttempts + " wrong choices in a row, exiting the function....\n");
                return;
            }
        }

    }

    private static void printNameId(Integer id) {

        if (!mapType.containsKey(id)) {
            System.out.print("\n   \tThere is no node present with [id : " + id + "]");
        } else {
            switch (mapType.get(id)) {

                case "Individual":
                    System.out.print("\n\t[type : Individual] [id : " + id + "] [name : "
                            + mapIndividual.get(id).getName() + "]");
                    break;
                case "Group":
                    System.out
                            .print("\n\t[type : Group] [id : " + id + "] [name : " + mapGroup.get(id).getName() + "]");
                    break;
                case "Business":
                    System.out.print(
                            "\n\t[type : Business] [id : " + id + "] [name : " + mapBusiness.get(id).getName() + "]");
                    break;
                case "Organisation":
                    System.out.print("\n\t[type : Organisation] [id : " + id + "] [name : "
                            + mapOrganisation.get(id).getName() + "]");
                    break;
            }
        }
    }

    private static void printMaster() {
        Integer cnt;
        System.out.print("\n+++++ Database of all nodes +++++\n");

        cnt = 0;
        System.out.print("\n +++++ Individual nodes +++++ \n");
        for (Map.Entry<Integer, Individual> me : mapIndividual.entrySet()) {
            Individual node = me.getValue();
            cnt++;
            System.out.print("\n[" + cnt + "]\tName : " + node.getName() + "\n");
            System.out.print("\n   \tId : " + node.getId() + "\n");
            System.out.print("\n   \tBirthday : " + node.getBirDate().toString() + "\n");
            System.out.print("\n   \tCreation Date : " + node.getCreationDate().toString() + "\n");

            System.out.print("\n   \tId's of nodes linked to this node : \n");
            for (Integer link : node.getLinks()) {
                printNameId(link);
            }
            System.out.print("\n");

            System.out.print("\n   \tContent posted by this node : ");
            for (String post : node.getContent()) {
                System.out.print("\n   \t\t[" + post + "]");
            }
        }

        cnt = 0;
        System.out.print("\n +++++ Group nodes +++++ \n");
        for (Map.Entry<Integer, Group> me : mapGroup.entrySet()) {
            Group node = me.getValue();
            cnt++;
            System.out.print("\n[" + cnt + "]\tName : " + node.getName() + "\n");
            System.out.print("\n   \tId : " + node.getId() + "\n");
            System.out.print("\n   \tCreation Date : " + node.getCreationDate().toString() + "\n");

            System.out.print("\n   \tId's of nodes linked to this node : \n");
            for (Integer link : node.getLinks()) {
                printNameId(link);
            }
            System.out.print("\n");

            System.out.print("\n   \tMembers of this group who are individuals : \n");
            for (Integer Id : node.getIndividualMembers()) {
                if (mapIndividual.containsKey(Id))
                    System.out.print("\n   \t\t[id : " + Id + "] [name : " + mapIndividual.get(Id).getName() + "]");
                else
                    System.out.print("\n   \t\tNo individual node with [id : " + Id
                            + "] who is a member of this group was created.");
            }
            System.out.print("\n");

            System.out.print("\n   \tMembers of this group who are business : \n");
            for (Integer Id : node.getBusinessMembers()) {
                if (mapBusiness.containsKey(Id))
                    System.out.print("\n   \t\t[id : " + Id + "] [name : " + mapBusiness.get(Id).getName() + "]");
                else
                    System.out.print("\n   \t\tNo Business node with [id : " + Id
                            + "] who is a member of this group was created.");
            }
            System.out.print("\n");

            System.out.print("\n   \tContent posted by this node : ");
            for (String post : node.getContent()) {
                System.out.print("\n   \t\t[" + post + "]");
            }
        }

        cnt = 0;
        System.out.print("\n +++++ Business nodes +++++ \n");
        for (Map.Entry<Integer, Business> me : mapBusiness.entrySet()) {
            Business node = me.getValue();
            cnt++;
            System.out.print("\n[" + cnt + "]\tName : " + node.getName() + "\n");
            System.out.print("\n   \tId : " + node.getId() + "\n");
            System.out.print("\n   \tCreation Date : " + node.getCreationDate().toString() + "\n");
            System.out.print("\n   \tLocation Co-ordinates : [" + node.getLocation().first + ", "
                    + node.getLocation().second + "]\n");

            System.out.print("\n   \tId's of nodes linked to this node : \n");
            for (Integer link : node.getLinks()) {
                printNameId(link);
            }
            System.out.print("\n");

            System.out.print("\n   \tMembers of this Business who are individuals : \n");
            for (Pair individualMembers : node.getIndividualMembers()) {
                if (mapIndividual.containsKey(individualMembers.first)) {
                    System.out.print("\n   \t\t[id : " + individualMembers.first + "] [type : ");
                    if (individualMembers.second == 1)
                        System.out.print("Owner");
                    else
                        System.out.print("Customer");
                    System.out.print("] [name : " + mapIndividual.get(individualMembers.first).getName() + "]");
                }

                else
                    System.out.print("\n   \t\tNo individual node with [id : " + individualMembers.first
                            + "] who is a member of this group was created.");
            }
            System.out.print("\n");

            System.out.print("\n   \tContent posted by this node : ");
            for (String post : node.getContent()) {
                System.out.print("\n   \t\t[" + post + "]");
            }
        }

        cnt = 0;
        System.out.print("\n +++++ Organisation nodes +++++ \n");
        for (Map.Entry<Integer, Organisation> me : mapOrganisation.entrySet()) {
            Organisation node = me.getValue();
            cnt++;
            System.out.print("\n[" + cnt + "]\tName : " + node.getName() + "\n");
            System.out.print("\n   \tId : " + node.getId() + "\n");
            System.out.print("\n   \tCreation Date : " + node.getCreationDate().toString() + "\n");
            System.out.print("\n   \tLocation Co-ordinates : [" + node.getLocation().first + ", "
                    + node.getLocation().second + "]\n");

            System.out.print("\n   \tId's of nodes linked to this node : \n");
            for (Integer link : node.getLinks()) {
                printNameId(link);
            }
            System.out.print("\n");

            System.out.print("\n   \tMembers of this Organisation who are individuals : \n");
            for (Integer Id : node.getIndividualMembers()) {
                if (mapIndividual.containsKey(Id))
                    System.out.print("\n   \t\t[id : " + Id + "] [name : " + mapIndividual.get(Id).getName() + "]");
                else
                    System.out.print("\n   \t\tNo individual node with [id : " + Id
                            + "] who is a member of this group was created.");
            }
            System.out.print("\n");

            System.out.print("\n   \tContent posted by this node : ");
            for (String post : node.getContent()) {
                System.out.print("\n   \t\t[" + post + "]");
            }
        }

    }

    private static void getBasicData() {

        Integer id;
        String name;
        date = TODAY;
        Set<Integer> links = new HashSet<Integer>();
        Set<String> content = new HashSet<String>();

        Integer cnt = 0;
        while (true) {

            System.out.print("\nEnter the unique (numeric) id of the new node : ");

            try {
                id = Integer.parseInt(sc.nextLine());

                if (mapType.containsKey(id)) {
                    cnt++;
                    System.out.print("\nThere is already a node with [id : " + id + "].");

                } else {
                    break;
                }

            } catch (Exception e) {
                System.out.print("\nId must be an integer!");
                cnt++;
            }

            if (cnt == maxInvalidAttempts) {
                validNode &= false;
                System.out.print("\nMade " + maxInvalidAttempts + " invalid attempts in a row, leaving for now...!");
                return;

            }
        }

        System.out.print("\nEnter the name of the new node : ");
        name = sc.nextLine();
        node.setName(name);

        System.out.print("\nCreation date is set to today (" + date
                + ").\n\nDo you want to change it?\n\t[1] Yes\n\t[Any other key] No");
        System.out.print("\n\nChoice : ");

        String choice = sc.nextLine();
        if (choice.equals("1"))
            getDate("Creation");

        if (!validNode)
            return;

        cnt = 0;
        while (true) {

            System.out.print(
                    "\nEnter the numeric id of node linked to this node or press [Any other Key] if there is no more : ");

            try {
                Integer linkId = Integer.parseInt(sc.nextLine());
                if (linkId == id) {
                    cnt++;
                    System.out.print("\nNode cannot be linked to itself. Please use another id");
                } else if (links.contains(linkId)) {
                    cnt++;
                    System.out.print("\n[id : " + linkId + "] is already linked to this node. Please use another id");
                } else {
                    cnt = 0;
                    links.add(linkId);
                }

            } catch (Exception e) {
                break;
            }

            if (cnt == maxInvalidAttempts) {

                validNode &= false;
                System.out.print("\nMade " + maxInvalidAttempts + " invalid attempts in a row, leaving for now...!");
                return;

            }
        }

        node.setCreationDate(date);
        node.setId(id);
        node.setLinks(links);
        node.setName(name);
        node.setContent(content);

    }

    private static void createIndividual() {

        validNode = true;

        getBasicData();
        if (!validNode)
            return;

        getDate("Birthday");
        if (!validNode)
            return;

        mapIndividual.put(node.getId(), new Individual(node.getId(), node.getName(), node.getCreationDate(),
                node.getContent(), node.getLinks(), date));
        mapType.put(node.getId(), "Individual");
        System.out.print("\nSucessfully created new individual with [id : " + node.getId() + "]\n");

    }

    private static void createGroup() {
        validNode = true;

        getBasicData();
        if (!validNode)
            return;

        linkedIndividualMembers.clear();
        getLinkedIndividualMembers(node, "Group");

        businessMembers.clear();
        getBusinessMembers(node, "Group");

        mapGroup.put(node.getId(), new Group(node.getId(), node.getName(), node.getCreationDate(), node.getContent(),
                node.getLinks(), linkedIndividualMembers, businessMembers));
        mapType.put(node.getId(), "Group");
        System.out.print("\nSucessfully created new Group with [id : " + node.getId() + "]\n");

    }

    private static void createBusiness() {

        validNode = true;

        getBasicData();
        if (!validNode)
            return;

        getLocation("Business");
        if (!validNode)
            return;

        Integer cnt = 0;
        Set<Pair> individualMembers = new HashSet<Pair>();
        while (true) {

            System.out.print(
                    "\nEnter the numeric id of the individual who is a member this business or press [Any other key] if there is no more : ");

            try {
                Integer Id = Integer.parseInt(sc.nextLine());

                if (Id == node.getId()) {
                    cnt++;
                    System.out.print("\nBusiness cannot be member of itself.");
                } else if (individualMembers.contains(new Pair(Id, 0)) || individualMembers.contains(new Pair(Id, 1))) {
                    cnt++;
                    System.out.print("\nIndividual with this Id is already a member.");
                } else {

                    cnt = 0;
                    System.out.print(
                            "\nPress [1] if this individual is a owner of this business or press [Any other key] if this individual is a customer : ");

                    try {
                        Integer isOwner = Integer.parseInt(sc.nextLine());
                        if (isOwner == 1)
                            individualMembers.add(new Pair(Id, 1));
                        else
                            individualMembers.add(new Pair(Id, 0));

                    } catch (Exception e) {
                        individualMembers.add(new Pair(Id, 0));
                    }
                }

            } catch (Exception e) {
                break;
            }

            if (cnt == maxInvalidAttempts) {
                System.out.print("\nMade " + maxInvalidAttempts
                        + " invalid attempts in a row. More Individual members cannot be added.");
                break;

            }
        }

        mapBusiness.put(node.getId(), new Business(node.getId(), node.getName(), node.getCreationDate(),
                node.getContent(), node.getLinks(), location, individualMembers));
        mapType.put(node.getId(), "Business");
        System.out.print("Sucessfully created new business with [id : " + node.getId() + "]\n");

    }

    private static void createOrganisation() {

        validNode = true;

        getBasicData();
        if (!validNode)
            return;

        getLocation("Organisation");
        if (!validNode)
            return;

        linkedIndividualMembers.clear();
        getLinkedIndividualMembers(node, "Organisation");

        mapOrganisation.put(node.getId(), new Organisation(node.getId(), node.getName(), node.getCreationDate(),
                node.getContent(), node.getLinks(), location, linkedIndividualMembers));
        mapType.put(node.getId(), "Organisation");
        System.out.print("Sucessfully created new Organisation with [id : " + node.getId() + "]\n");

    }

    private static void getBusinessMembers(Node node, String type) {

        Integer cnt = 0;
        while (true) {

            System.out.print("\nEnter the numeric id of the business who is a member this " + type
                    + " or press [Any other key] if there is no more : ");

            try {
                Integer Id = Integer.parseInt(sc.nextLine());

                if (Id == node.getId()) {
                    cnt++;
                    System.out.print("\n" + type + " cannot be member of itself.");
                } else if (businessMembers.contains(Id)) {
                    cnt++;
                    System.out.print("\nBusiness with this Id is already a member of this " + type + ".");
                } else {
                    cnt = 0;
                    businessMembers.add(Id);
                }

                if (cnt == maxInvalidAttempts) {
                    System.out.print("\nMade " + maxInvalidAttempts
                            + " invalid attempts in a row. More business members cannot be added.");
                    return;

                }

            } catch (Exception e) {
                return;
            }
        }
    }

    private static void getLinkedIndividualMembers(Node node, String type) {

        Integer cnt = 0;

        while (true) {

            System.out.print("\nEnter the numeric id of the LINKED individual who is a member this " + type
                    + " or press [Any other key] if there is no more : ");

            try {
                Integer Id = Integer.parseInt(sc.nextLine());

                if (Id == node.getId()) {
                    cnt++;
                    System.out.print("\n" + type + " cannot be member of itself.");
                } else if (linkedIndividualMembers.contains(Id)) {
                    cnt++;
                    System.out.print(
                            "\nIndividual with [id : " + Id + "] is already a linked member of this " + type + ".");
                } else if (!node.getLinks().contains(Id)) {
                    cnt++;
                    System.out.print(
                            "\nOnly individuals who are linked to this " + type + " are allowed to be member of this "
                                    + type + ".\nPlease enter Id of individual who is linked to this " + type + "\n");
                } else {
                    cnt = 0;
                    linkedIndividualMembers.add(Id);
                }

                if (cnt == maxInvalidAttempts) {
                    System.out.print("\n\nMade " + maxInvalidAttempts
                            + " invalid attempts in a row. More linked individual members cannot be added.\n");
                    return;
                }

            } catch (Exception e) {
                return;
            }
        }
    }

    private static void getLocation(String type) {

        Integer cnt;
        char coordinate = 'X';
        for (Integer i = 0; i < 2; i++) {

            cnt = 0;
            while (true) {

                System.out.print("\nEnter the " + coordinate + " coordinate of location of this " + type + " node : ");

                try {
                    Integer pos = Integer.parseInt(sc.nextLine());
                    if (i == 0)
                        location.first = pos;
                    if (i == 1)
                        location.second = pos;
                    break;

                } catch (Exception e) {
                    System.out.print("\nPlease write numeric co-ordinates only.");
                    cnt++;
                }

                if (cnt == maxInvalidAttempts) {

                    validNode &= false;
                    System.out
                            .print("\nMade " + maxInvalidAttempts + " invalid attempts in a row, leaving for now...!");
                    return;

                }
            }
            coordinate = 'Y';
        }
    }

    private static void getDate(String purpose) {

        Integer cnt = 0;
        while (true) {

            System.out.print("\nEnter the " + purpose + " Date (dd/mm/yyyy) : ");
            date = sc.nextLine();

            if (isDateValid(date)) {
                break;
            } else {
                System.out.print("\nPlease write the date in desired format. For ex. 25/12/2021");
                cnt++;
            }

            if (cnt == maxInvalidAttempts) {
                validNode &= false;
                System.out.print("\nMade " + maxInvalidAttempts + " invalid attempts in a row, leaving for now...!");
                return;

            }
        }
    }

    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(datePattern);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}