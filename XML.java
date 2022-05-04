import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XML {
    // Procedural Cohesion - open a connection to the XML file, create a DOM, parse
    // the file, build the elements
    public XML() {

    }

    public Document getDocFromFile(String filename) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try {
            doc = db.parse(filename);
        } catch (Exception ex) {
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        return doc;
    } // exception handling

    public Room[] readBoardData(Document d) {
        // I counted 20 rooms in the board.xml file
        Room[] rooms = new Room[30];

        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        for (int i = 0; i < sets.getLength(); i++) {
            // reads data from the nodes
            Node set = sets.item(i);
            String name = set.getAttributes().getNamedItem("name").getNodeValue();            
            // create and add the room to the array
            rooms[i] = new Set(name);
            // reads data from children
            NodeList children = set.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);
                if ("neighbors".equals(sub.getNodeName())) {
                    // need to get the data
                    Node neighbs;
                    String[] neighbors = new String[neighbs.getLength()];
                    for (int k = 0; k < .getLength(); k++) {
                        Node neigh = children.item(k);
                        String neighName = neigh.getAttributes().getNamedItem("name").getNodeValue();
                        
                    }                    
                } else if ("area".equals(sub.getNodeName())) {
                    String authorName = sub.getTextContent();
                    System.out.println(" Author = " + authorName);
                } else if ("takes".equals(sub.getNodeName())) {
                    String yearVal = sub.getTextContent();
                    System.out.println(" Publication Year = " + yearVal);
                } else if ("parts".equals(sub.getNodeName())) {
                    String priceVal = sub.getTextContent();
                    System.out.println(" Price = " + priceVal);
                }
            } // for childnodes

        } // for book nodes
        return rooms;
    }// method

    public SceneCard[] readSceneData(Document d) {
        // The game has 40 cards
        SceneCard[] sceneCards = new SceneCard[40];
        Element root = d.getDocumentElement();
        NodeList books = root.getElementsByTagName("book");
        for (int i = 0; i < books.getLength(); i++) {
            // reads data from the nodes
            Node book = books.item(i);
            String bookCategory = book.getAttributes().getNamedItem("category").getNodeValue();
            System.out.println("Category = " + bookCategory);
            // reads data from children
            NodeList children = book.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);
                if ("title".equals(sub.getNodeName())) {
                    String bookLanguage = sub.getAttributes().getNamedItem("lang").getNodeValue();
                    System.out.println("Language = " + bookLanguage);
                    String title = sub.getTextContent();
                    System.out.println("Title = " + title);
                } else if ("author".equals(sub.getNodeName())) {
                    String authorName = sub.getTextContent();
                    System.out.println(" Author = " + authorName);
                } else if ("year".equals(sub.getNodeName())) {
                    String yearVal = sub.getTextContent();
                    System.out.println(" Publication Year = " + yearVal);
                } else if ("price".equals(sub.getNodeName())) {
                    String priceVal = sub.getTextContent();
                    System.out.println(" Price = " + priceVal);
                }
            } // for childnodes

        } // for book nodes
        return sceneCards;
    }// method

}
