import java.util.*;
import java.time.format.DateTimeFormatter;
import java.io.*; 
import java.nio.file.attribute.FileTime; 
import java.nio.file.*; 
import java.time.*; 

public class Tester {

    public static void main(String... args) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss O");
        DateTimeFormatter clgFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
        System.out.println(clgFormatter.format(ZonedDateTime.now(ZoneId.systemDefault()).minusDays(11)));

        ZonedDateTime zdt = ZonedDateTime.parse("Wed, 09 Sep 2020 08:05:12 GMT", 
            DateTimeFormatter.RFC_1123_DATE_TIME);
        
        ZonedDateTime zdtNow = ZonedDateTime.now(ZoneOffset.UTC).minusMinutes(4); 
       // System.out.println("now: " + zdtNow);

        Path path = new File("C:/Users/akhil/School/Fall20/web_dev/web-server-katie-akhil/public_html/abc.txt").toPath(); 

        FileTime fileTime;
        try {
            fileTime = Files.getLastModifiedTime(path);
            //System.out.println(fileTime); 

            // ZoneId.systemDefault()
            ZonedDateTime zdtFile = ZonedDateTime.ofInstant(fileTime.toInstant(), ZoneOffset.UTC); 
            //System.out.println("file: " + zdtFile); 


        } catch (IOException e) {
            //System.err.println("Cannot get the last modified time - " + e);
        }

    }
}