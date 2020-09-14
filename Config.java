import java.util.*; 
import java.io.*; 
import java.nio.file.*; 

public class Config {

    private String ServerRoot; 
    private String DocumentRoot; 
    private Integer Listen; 
    private String LogFile; 
    private Map<String, String> ScriptAlias; 
    private Map<String, String> Alias; 
    
    // TODO: add if clause to read these two directives as well 
    private String AccessFile; 
    private String DirectoryIndex; 

    public String getServerRoot() {
        return ServerRoot; 
    }

    public String getDocumentRoot() {
        return DocumentRoot; 
    }

    public Integer getPort() {
        return Listen; 
    }

    public String getLogFile() {
        return LogFile; 
    }

    public String getScriptAlias(String key) {
        return ScriptAlias.get(key); 
    }

    public String getAlias(String key) {
        return Alias.get(key); 
    }

    public Config() throws IOException {
        // go through the individual lines in the httpd.conf file 
        Path conf = Paths.get("./conf/httpd.conf"); 
        
        Alias = new HashMap<>(); 
        ScriptAlias = new HashMap<>(); 

        BufferedReader inputStream = null; 

        try {
            inputStream = new BufferedReader(new FileReader(conf.toString())); 

            String l;
            while ((l = inputStream.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(l); 
                String directive = st.nextToken();
                if (directive.equals("ServerRoot")) {
                    String sroot = st.nextToken(); 
                    sroot = sroot.replace("\"", ""); 
                    ServerRoot = sroot; 

                } else if (directive.equals("DocumentRoot")) {
                    String droot = st.nextToken();
                    droot = droot.replace("\"", ""); 
                    DocumentRoot = droot; 

                } else if (directive.equals("Listen")) {
                    Listen = Integer.parseInt(st.nextToken()); 

                } else if (directive.equals("LogFile")) {
                    String lfile = st.nextToken();
                    lfile = lfile.replace("\"", "");
                    LogFile = lfile; 

                } else if (directive.equals("ScriptAlias")) {
                    String route = st.nextToken(); 
                    String full_path = st.nextToken(); 
                    full_path = full_path.replace("\"", ""); 
                    ScriptAlias.put(route, full_path); 

                } else if (directive.equals("Alias")) {
                    String route = st.nextToken();
                    String full_path = st.nextToken(); 
                    full_path = full_path.replace("\"", ""); 
                    Alias.put(route, full_path); 
                }

            }
        } finally {
            if (inputStream != null) {
                inputStream.close(); 
            }
        }
        
    }
}