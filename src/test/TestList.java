import fidelizador.Credentials;
import fidelizador.ListManager;
import fidelizador.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class TestList {

    public static void main(String[] args) {
        TestList test = new TestList();
        test.execute();
        
    }

    public void execute() {
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
}