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

    public String[] readBoardData(Document d) {
        // I counted 20 rooms in the board.xml file
        String[] rooms = new String[12];
        d.getDocumentElement().normalize();
        NodeList sets = d.getElementsByTagName("set");
        for (int i = 0; i < sets.getLength(); i++) {
            // reads data from the nodes
            Node set = sets.item(i);
            if (set.getNodeType() == Node.ELEMENT_NODE) {
               Element setElement = (Element) set;
               String name = set.getAttributes().getNamedItem("name").getNodeValue();   
               room(setElement, name);
               NodeList takes = setElement.getElementsByTagName("take");
               System.out.println("Number of takes: " + takes.getLength());
               for (int j = 0; j < takes.getLength(); j++) {
                  System.out.print("Take: " + takes.item(j).getAttributes().getNamedItem("number").getNodeValue() + " ");
                  Element shot = (Element) takes.item(j);
                  NodeList shotPosit = shot.getElementsByTagName("area");
                  area(shotPosit);
               }
               NodeList parts = setElement.getElementsByTagName("part");
               System.out.println("Extras: " + parts.getLength());
               for (int j = 0; j < parts.getLength(); j++) {
                  System.out.print("Part: " + parts.item(j).getAttributes().getNamedItem("name").getNodeValue() + " ");
                  System.out.print("Level: " + parts.item(j).getAttributes().getNamedItem("level").getNodeValue() + " ");
                  Element part = (Element) parts.item(j);
                  NodeList partLine = part.getElementsByTagName("line");
                  System.out.print("Part Line: " + partLine.item(0).getTextContent());
                  NodeList partPosit = part.getElementsByTagName("area");
                  area(partPosit);
               }
           }   
        }
        NodeList trailers = d.getElementsByTagName("trailer"); 
        Node trailer = trailers.item(0);
        if (trailer.getNodeType() == Node.ELEMENT_NODE) {
            Element setElement = (Element) trailer;
            room(setElement, "Trailers");
        }
        NodeList offices = d.getElementsByTagName("office"); 
        Node office = offices.item(0);
        if (office.getNodeType() == Node.ELEMENT_NODE) {
            Element setElement = (Element) office;
            room(setElement, "Casting Office");
            NodeList upgrades = d.getElementsByTagName("upgrade"); 
            for (int i = 0; i < upgrades.getLength(); i++) {
               Node upgrade = upgrades.item(i);
               if (upgrade.getNodeType() == Node.ELEMENT_NODE) {
                  Element upgradeElement = (Element) upgrade;
                  System.out.print("Upgrade Level: " + upgrades.item(i).getAttributes().getNamedItem("level").getNodeValue() + " ");
                  System.out.print("Cost: " + upgrades.item(i).getAttributes().getNamedItem("amt").getNodeValue() + " ");
                  System.out.println(upgrades.item(i).getAttributes().getNamedItem("currency").getNodeValue() + "s");
                  NodeList area = upgradeElement.getElementsByTagName("area");
                  area(area);
               }
            }
        }
        
        return rooms;
    }
    public void room(Element setElement, String name){
      System.out.println("Room: " + name);         
      NodeList neighbors = setElement.getElementsByTagName("neighbor");
      neighbors(neighbors);
      NodeList area = setElement.getElementsByTagName("area");
      area(area);
    }
    public String areaStringified(NodeList area){
    return "" + area.item(0).getAttributes().getNamedItem("w").getNodeValue() +
    "@" + area.item(0).getAttributes().getNamedItem("h").getNodeValue() +
    "@" + area.item(0).getAttributes().getNamedItem("x").getNodeValue() +
    "@" + area.item(0).getAttributes().getNamedItem("y").getNodeValue() + "@";
    }
    public void area(NodeList area){
      System.out.print("area: w = " + area.item(0).getAttributes().getNamedItem("w").getNodeValue());
      System.out.print(", h = " + area.item(0).getAttributes().getNamedItem("h").getNodeValue());
      System.out.print(", x = " + area.item(0).getAttributes().getNamedItem("x").getNodeValue());
      System.out.println(", y = " + area.item(0).getAttributes().getNamedItem("y").getNodeValue());
    }
    public void neighbors(NodeList neighbors){
      System.out.print("Neighbors: ");
      for (int j = 0; j < neighbors.getLength() - 1; j++) {
         System.out.print(neighbors.item(j).getAttributes().getNamedItem("name").getNodeValue() + ", ");
       }
       System.out.println(neighbors.item(neighbors.getLength() - 1).getAttributes().getNamedItem("name").getNodeValue());
    }
    // method

    public String[] readSceneData(Document d) {
      String[] deck = new String[40];
      d.getDocumentElement().normalize();
      NodeList cards = d.getElementsByTagName("card");
      for(int i =0; i < cards.getLength(); i++){
         Node card = cards.item(i);
            if (card.getNodeType() == Node.ELEMENT_NODE) {
               Element setElement = (Element) card;
               deck[i]= "" + cards.item(i).getAttributes().getNamedItem("name").getNodeValue() + "@" +
               cards.item(i).getAttributes().getNamedItem("img").getNodeValue() + "@" +
               cards.item(i).getAttributes().getNamedItem("budget").getNodeValue() + "@";
               NodeList scenes = setElement.getElementsByTagName("scene");
               deck[i] += "" + scenes.item(0).getAttributes().getNamedItem("number").getNodeValue() + "@" +
               scenes.item(0).getTextContent() + "@";
               NodeList parts = setElement.getElementsByTagName("part");
               deck[i] += "" + parts.getLength() + "@";
               for (int j = 0; j < parts.getLength(); j++) {
                  deck[i] += "" + parts.item(j).getAttributes().getNamedItem("name").getNodeValue() + "@" +
                  parts.item(j).getAttributes().getNamedItem("level").getNodeValue() + "@";
                  Element part = (Element) parts.item(j);
                  NodeList partLine = part.getElementsByTagName("line");
                  deck[i] += "" + partLine.item(0).getTextContent() + "@";
                  NodeList partPosit = part.getElementsByTagName("area");
                  deck[i] += areaStringified(partPosit);
               }
               //System.out.println(deck[i]);
            }
            
       }
       return deck;
    }// method

}