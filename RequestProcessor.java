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
            if (req.is_uri_aliased(a_config.getAliasMap())) {
                // this is indeed uri aliased
                //System.out.println("It is URI aliased"); 
                //System.out.println(req.path); 
            } else if (req.is_script_aliased(a_config.getScriptAliasMap())){
                //System.out.println("It is script URI aliased"); 
                //System.out.println(req.path); 
            } else {
                //System.out.println("Not aliased or script aliased.");
                req.resolve_document_root(a_config.getDocumentRoot());
                //System.out.println(req.path);
            }

            
            req.resolve_absolute_path(a_config.getDirectoryIndex()); 
            //System.out.println("We have the absolute path: " + req.path); 

            // perform the HT access Check 

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