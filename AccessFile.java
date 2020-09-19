import java.io.*;
import java.util.*;

public class AccessFile {

    private String authUserFilePath;
    private Boolean authTypeBasic;
    private ArrayList <String> authNames;
    private String require;

    public AccessFile(String path){
        try(
            BufferedReader inputStream = new BufferedReader(new FileReader(path));
        ) {
            String l;
            while ((l = inputStream.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(l); 
                String directive = st.nextToken();

                if (directive.equals("AuthUserFile")) {
                    String authUserFilePath = st.nextToken(); 
                    authUserFilePath = authUserFilePath.replace("\"", "");
                    
                } else if (directive.equals("AuthType")) {
                    String arg = st.nextToken();
                    if(arg == "basic"){
                        authTypeBasic = true;
                    }

                } else if(directive.equals("AuthName")){
                    String nameArg = st.nextToken();
                    authNames.add(nameArg);

                } else if(directive.equals("Require")){
                    String arg = st.nextToken();
                    if(arg == "valid-user"){

                    }
                }
            }

        }
    }

    public void parse_htaccess_file(){

    }


    public void parse_pw_file(){
    BufferedReader pwFile = new BufferedReader(new FileReader(pwFilepath));
    String data = pwFile.readLine();
    String user = "";
    String pwHash = "";
    char ch;
    int i = 0;

    while(ch != ':'){
        ch = data.charAt(i);
        user = user + ch;
        i++;
    }

    for(int j = i+1; j < data.length(); j++){
        pwHash = pwHash + data.charAt(j);
    }
    }



}