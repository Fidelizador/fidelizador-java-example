import fidelizador.Credentials;
import fidelizador.ListManager;
import fidelizador.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.*;

public class TestList {

    public static void main(String[] args) throws IOException {
        TestList test = new TestList();
        test.execute(args);   
    }

    public void execute(String[] args) {
        System.out.println("Getting credentials");
        Credentials cr = new Credentials();
        cr.setClientId(System.getenv("CLIENT_ID"));
        cr.setClientSecret(System.getenv("CLIENT_SECRET"));
        String access_token = cr.getCredentials();

        if (access_token == null) {
            System.out.println("Cannot retrieve access token");
            System.exit(0);
        }

        System.out.println("Credentials OK");

        String slug = System.getenv("SLUG");
        
        //getlists
        ListManager lm = new ListManager();
        String response = lm.getLists(slug, access_token);

        Document doc = XMLHelper.convertStringToXMLDocument(response);
        this.printLists(doc);

        System.out.println("creating list");

        response = lm.createList(slug, access_token, "prueba1");
        doc = XMLHelper.convertStringToXMLDocument(response);

        int list_id = this.getListId(doc);
        if (list_id == 0) {
            System.out.println("Error parsing xml and extracting list_id");
        }
        System.out.println("List created with ID: " + list_id);
        
        String filepath = null;
        try {
            filepath = args[0];   
        }
        catch(Exception e) {
            System.out.println("To execute importlist you must pass a path as first parameter in console (Example ./data.csv)");
            System.exit(0);
        }

        response = lm.createImport(slug, access_token, list_id, filepath);
        System.out.println(response);
                     
    }

    public void printLists(Document doc) {
        //response -> lists
        NodeList nodeList = doc.getFirstChild().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            try {
                Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName() == "lists") {
                    Node id = currentNode.getFirstChild();
                    System.out.println(id.getNodeName() + " : " + id.getTextContent());
                    Node name = currentNode.getFirstChild().getNextSibling();
                    System.out.println(name.getNodeName() + " : " + name.getTextContent());
                }
            }
            catch (Exception e) {

            }  
        }
    }

    public int getListId(Document doc) {
        NodeList nodeList = doc.getFirstChild().getChildNodes();
        int list_id = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            try {
                Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName() == "list_id") {
                    list_id = Integer.parseInt(currentNode.getTextContent());
                }
            }
            catch (Exception e) {

            } 
        }
        return list_id;
    }
}