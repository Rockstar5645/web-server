import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class Response {

    Request req; 
    OutputStream out;
    Config a; 

    public String response_template(String status) {
        String response = "HTTP/1.1 " + status + "\r\n";
        response += ("Date: " + LocalDateTime.now().toString() + "\r\n"); 
        response += "Server: akws\r\n";
        response += "Connection: close\r\n";
        return response;
    }

    public void send_404() throws IOException {
        String response = this.response_template("404 Not Found"); 
        response += "\r\n";
        byte[] b = response.getBytes(Charset.forName("UTF-8")); 
        out.write(b); 
    }

    public void send_401(String realm) throws IOException {
        String response = this.response_template("401 Unauthorized");
        response += "WWW-Authenticate: Basic realm=" + realm + ", charset=\"UTF-8\"\r\n\r\n";
        byte[] b = response.getBytes(Charset.forName("UTF-8")); 
        out.write(b); 
    }

    public void send_403() throws IOException {
        System.out.println("sending 403"); 
        String response = this.response_template("403 Forbidden");
        response += "\r\n";
        byte[] b = response.getBytes(Charset.forName("UTF-8")); 
        out.write(b); 
    }

    // we have a res.return_400 and res.return_500, res.return_404 method 

    // we have a method called file_exists, and if it doesn't, we call return_404

    // create exec_script that accepts path, and instantiates a process builder 

    public Response(Request _req, OutputStream _out, Config _a) throws IOException {
        this.req = _req; 
        this.out = _out; 
        this.a = _a; 

        String response = "HTTP/1.1 200 OK\r\n";
        response += ("Date: " + LocalDateTime.now().toString() + "\r\n"); 
        response += "Server: akws\r\n";
        response += "Connection: close\r\n";
        String response_body = "Hello, how are you?"; 
        response += "Connection-Length: " + response_body.length() + "\r\n\r\n"; 
        response += response_body; 

        /*
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
        */
    
        //byte[] b = response.getBytes(Charset.forName("UTF-8")); 
        
        /*
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
        */
        
        //out.write(b); 

    }
}