import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class WebServer {

    public static void main(String... args) throws IOException {
        Config a = new Config(); 
        
        while (true) {
            try (
                ServerSocket serverSocket = new ServerSocket(a.getPort());
                Socket clientSocket = serverSocket.accept();
                OutputStream out = clientSocket.getOutputStream();
                InputStream in = clientSocket.getInputStream();
            ) { 
                Request req = new Request(in); 
                Response res = new Response(req, out); 
            }
        }
        
    }
}