import java.io.*;

public class Authentication {
  
  private String pwFilePath;
  private String authType;
  private String authName;
  private String requireArg;

  public Authentication(){

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