import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class RequestProcessor implements Runnable {

    Socket clientSocket = null; 
    Config a_config = null; 

    public void run() {
        try (
            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();
        ) {
            Request req = new Request(in); 
            Response res = new Response(req, out, a_config); 
        } catch (Exception e) {
            // do nothing 
            System.out.println("Something happenned here"); 
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close(); 
                }
            } catch (Exception e) {
                // do nothing 
            }   
        }
    }
    
    public RequestProcessor(Socket clientSocket, Config a) {
        this.clientSocket = clientSocket; 
        this.a_config = a; 
    }
}