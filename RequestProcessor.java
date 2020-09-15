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
            // we are parsing the request now
            Request req = new Request(in); 
            Response res = new Response(req, out, a_config); 

            // check if the req.error is true, and if it is, call res.return_400 or res.return_500

            // check if req.path is URI aliased, by calling req.is_uri_aliased(); 
            // check if req.path is script alised, if it's not uri alised, by calling req.is_script_alised()

            // so if neither of those previous methods, returned true, then call 
            // req.resolve_document_root(a_config.getDocumentRoot())

            // req.is_file()


            // res.file_exists()

            // if (req.is_script) then res.exec_script(string path)
            
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