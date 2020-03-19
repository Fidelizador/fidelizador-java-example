import fidelizador.Credentials;

public class TestCredential {

    public static void main(String[] args) {
        TestCredential test = new TestCredential();
        test.execute();
        
    }

    public void execute() {
        Credentials cr = new Credentials();
        cr.setClientId(System.getenv("CLIENT_ID"));
        cr.setClientSecret(System.getenv("CLIENT_SECRET"));
        String access_token = cr.getCredentials();
        System.out.println(access_token);
    }
}