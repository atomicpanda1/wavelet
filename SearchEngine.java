import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("This is my search engine!");
        } 
        else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                list.add(parameters[1]);
                return String.format("Successfully added " + parameters[1]);
            }
        }
        else if (url.getPath().contains("/list")) {
            return String.join(", ", list);
        }
        else {
            if (url.getPath().contains("/search")) {
                String searchResults = "";
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String searchFor = parameters[1];
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).contains(searchFor)) {
                            searchResults += list.get(i) + " ";
                        }
                    }
                    return searchResults;
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
