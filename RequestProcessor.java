import java.util.*; 
import java.io.*; 
import java.nio.file.*; 
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
            File resource = new File(req.path); 
            String cwd = resource.getParent(); 
            //System.out.println("Path requested: " + req.path); 
            //System.out.println("Parent: " + cwd); 
            
            // check if HT access file exists
            Path htpath = Paths.get(cwd, a_config.getAccessFile()); 
            
            Boolean access = true; 
            if (Files.exists(htpath)) {

                // user is attempting to access aprotected resources
                AccessFile af = new AccessFile(htpath.toString()); 
                if (req.Headers.containsKey("Authorization")) {
                    //System.out.println("contents of Authorization header: " + req.Headers.get("Authorization"));
                    // The user is trying to authenticate themselves
    
                    Htpassword htp = new Htpassword(af.authUserFilePath);

                    String header_val = req.Headers.get("Authorization");
                    StringTokenizer st = new StringTokenizer(header_val); 
                    String auth_type = st.nextToken();
                    String creds = st.nextToken(); 

                    if (htp.isAuthorized(creds)) {
                        // go ahead and access the protected resource
                        //System.out.println("Is authorized"); 
                    } else {
                        // send back a 403
                        //System.out.println("User is not authorized"); 
                        access = false; 
                        res.send_403(); 
                    }

                } else {
                    // we send back a 401 telling them to authenticate
                    res.send_401(af.authName); 
                    access = false; 
                }
            }

            if (access) {
                if (!req.http_method.equals("PUT") {

                    Path file = new File(req.path).toPath();
                    if (Files.exists(file)) {
                        if (req.is_script) {
                            
                        } else {

                        }
                    } else {
                        res.send_404(); 
                    }
                } 
            }


            // if (req.is_script) then res.exec_script(string path)

            // if (req.path) contains a .htaccess file, do this
            
            
            
        } catch(IOException e) {
            System.out.println(e.toString());
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