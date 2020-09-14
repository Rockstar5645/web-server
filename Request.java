import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.time.*;
import java.nio.charset.*; 

public class Request {

    public Request(InputStream in) throws IOException {
        int d;
        String next_line = ""; 
        while ((d = in.read()) != -1) {
            char c = (char) d; 
            next_line += c; 
            if (c == '\n') {
                // we are at the end of the line, go ahead and print
                System.out.printf("%s", next_line); 
                if (next_line.equals("\r\n"))
                    break; 
                next_line = ""; 
            }
        }
    }
}