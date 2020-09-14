import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class Response {

    public Response(Request req, OutputStream out) throws IOException {

        String response_body = "This request was recieved at " + LocalDateTime.now().toString();
        String response = "HTTP/1.1 200 OK\r\n";
        response += ("Date: " + LocalDateTime.now().toString() + "\r\n"); 
        response += "Server: akws\r\n";
        response += "Connection: close\r\n";
        response += "Content-Length: " + response_body.length() + "\r\n\r\n";
        response += response_body;  

        System.out.println("Sending Response: ");
        System.out.println(response); 

        byte[] b = response.getBytes(Charset.forName("UTF-8")); 
        out.write(b); 

        System.out.println("Sent response"); 
    }
}