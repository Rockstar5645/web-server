

import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class Request {  

    public Map<String, String> Headers; 
    public String http_method; 

    public String path; // this is the path
    
    public String version; 

    List<Byte> body; 
    public Boolean error; 
    public Boolean is_script; 

    // create method Boolean is_uri_alised(), if it is, also modify the path, and resolve the full URI
    public Boolean is_uri_aliased(Map<String, String> Alias){
        
        int i = 0; 
        while (i < path.length()) {
            if (path.charAt(i) == '/')
                break;
            else
                i++; 
        }

        if (i == path.length())
            return false;       // it is not script aliased 

        
        String prefix = "/" + path.substring(0, i + 1); 
        if (Alias.containsKey(prefix)) {
            // so it is uri aliased
            String full_path = Alias.get(prefix) + path.substring(i + 1, path.length()); 
            path = full_path; 

            is_script = false; 
            return true; 
        }

        return false; 
    }

    // create method BOolean is_script_alised(), if it is, modify the path, and resolve the FULL URI, also,
    // we can set a variable, saying it's a script

    // create method void resolve_document_root(String doc_root)

    // create method void is_file(), if it is a file, we're good tog, otherwise, check directory index, 
    // 

    /*
        If at any point, there is an error in parsing the request, break, and set the error flag to true; 
        Otherwise, we go through the entire parseing process, and set it to false
    */

    public Request(InputStream in) throws IOException {

        char c;
        // read the request line 
        String requestLine = ""; 
        while (true) {
            c = (char) in.read(); 
            if (c == '\r') {
                c = (char) in.read(); // get the \n as well 
                StringTokenizer st = new StringTokenizer(requestLine); 
                http_method = st.nextToken(); 
                path = st.nextToken(); 
                path = path.substring(1, path.length()); 
                version = st.nextToken(); 
                break; 
            }
            requestLine += c; 
        }   

        // read the headers 
        String header_line = ""; 
        Headers = new HashMap<>(); 
        while (true) {
            c = (char) in.read(); 
            if (c == '\r') {
                c = (char) in.read(); // get the \n as well 
                if (header_line.equals("")) {
                    // we are at the end of the headers
                    break; 
                }

                // we are at the end of the line, parse the header 
                StringTokenizer st = new StringTokenizer(header_line, ": "); 
                String header_name = st.nextToken(); 
                String header_val = st.nextToken(); 
                Headers.put(header_name, header_val); 

                header_line = ""; 
                continue; 
            }
            header_line += c; 
        }

        // if there is a body, read that
        body = new ArrayList<>(); 
        if (Headers.containsKey("Content-Length")) {
            Integer length = Integer.parseInt(Headers.get("Content-Length")); 
            byte d; 
            while (length > 0) {
                d = (byte) in.read(); 
                body.add(d);
                length--; 
            }
        }
    }

  // create method Boolean is_uri_alised(), if it is, also modify the path, and resolve the full URI
  public Boolean is_uri_aliased(){
    if(Headers.containsKey("Alias")){

      return true;
    }

    return false;
  }


// create method BOolean is_script_alised(), if it is, modify the path, and resolve the FULL URI, also,
// we can set a variable, saying it's a script
  public Boolean is_script_aliased(){
    if(Headers.containsKey("ScriptAlias")){
      return true;
    }
    else{
      resolve_document_root(path);
    }
    return false;
  }

// create method void resolve_document_root(String doc_root)
  public void resolve_document_root(String doc_root){
   
  }

// create method void is_file(), if it is a file, we're good to go, otherwise, check directory index
  public void is_file(){

  }

}


