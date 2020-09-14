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
                System.out.println(req.Headers);
                System.out.println(req.http_method);
                System.out.println(req.path);
                System.out.println(req.version);
                byte[] b = new byte[req.body.size()]; 
                Byte[] B = req.body.toArray(new Byte[0]); 
                for (int i = 0; i < req.body.size(); i++)
                    b[i] = B[i]; 
                String body = new String(b, "UTF-8"); 
                System.out.println(body); 

                Response res = new Response(req, out); 
            }
        }
        
    }
}