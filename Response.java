import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class Response {

    public Response(Request req, OutputStream out, Config a) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n";
        response += ("Date: " + LocalDateTime.now().toString() + "\r\n"); 
        response += "Server: akws\r\n";
        response += "Connection: close\r\n";

        int len = 0; 
        byte[] lbfile = null; 
        if (req.http_method.equals("GET")) {
            String DocumentRoot = a.getDocumentRoot(); 
            String path = req.path; 
            List<Byte> bfile = new ArrayList<>(); 
            try (
                FileInputStream in = new FileInputStream(DocumentRoot + "index.html");
            ) {
                int c; 
                while ((c = in.read()) != -1)  {
                    bfile.add((byte) c); 
                }
            }
            len = bfile.size(); 
            lbfile = new byte[len]; 
            for (int i = 0; i < len; i++) {
                lbfile[i] = bfile.get(i); 
            }
        }
        response += "Content-Length: " + len + "\r\n\r\n"; 
    
        byte[] b = response.getBytes(Charset.forName("UTF-8")); 
        
        byte[] final_res = new byte[b.length + lbfile.length];
        int i = 0, j = 0; 
        for (i = 0; i < b.length; i++) {
            final_res[j] = b[i]; 
            j++; 
        }

        for (i =0; i < lbfile.length; i++) {
            final_res[j] = lbfile[i]; 
            j++; 
        }
        
        out.write(final_res); 

    }
}