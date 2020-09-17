import java.util.*; 
import java.io.*;  
import java.nio.file.*; 
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



    public Boolean is_script_aliased(Map<String, String> ScriptAlias){
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
      if (ScriptAlias.containsKey(prefix)) {
          // so it is uri aliased
          String full_path = ScriptAlias.get(prefix) + path.substring(i + 1, path.length()); 
          path = full_path; 

          is_script = true; 
          return true; 
      }

        is_script = false; 
      return false; 
    }

    // create method void resolve_document_root(String doc_root)
    public void resolve_document_root(String doc_root){
      String full_path = doc_root + path;
      path = full_path;
    }


    // create method void is_file(), if it is a file, we're good to go, otherwise, check directory index
    public void resolve_absolute_path(String directory_index) {
        Path file = new File(path).toPath();

        System.out.println("The path we have is: " + path); 
        
        if (Files.exists(file)) {
            if (Files.isDirectory(file)) {
                System.out.println("It's a directory"); 
                path = path + directory_index; 
            } else
                System.out.println("It is not a directory"); 
        } else
            System.out.println("This file (directory or file) does not exist"); 

        // now we have our absolute path
    }

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

}


