import java.util.*; 
import java.io.*; 


public class Resource {
  

  public Resource(Request req){

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
