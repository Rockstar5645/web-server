import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class WebServer {

    public static void main(String... args) throws IOException {
        Config a = new Config(); 
        Logger l = new Logger(a.getLogFile()); 

        while (true) {
            try (
                ServerSocket serverSocket = new ServerSocket(a.getPort());
            ) {
                Socket clientSocket = serverSocket.accept();
                RequestProcessor rp = new RequestProcessor(clientSocket, a, l); 
                (new Thread(rp)).start(); 
            }
        }
        
    }
}