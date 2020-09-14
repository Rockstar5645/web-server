import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class Request {  

    public Map<String, String> Headers; 
    public String http_method; 
    public String path; 
    public String version; 
    List<Byte> body; 

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