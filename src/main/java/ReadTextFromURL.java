import org.jsoup.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


import org.jsoup.nodes.Document;

public class ReadTextFromURL {
    //file of URLs

    public static String parseURL(String Url) throws IOException{
        URL url = new URL(Url);

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = in.readLine())!= null){
            sb.append(line);
        }
        in.close();
        String webpage = sb.toString();
        Document doc = Jsoup.parse(webpage);
        String title = doc.title();
        String body = doc.body().text();

       return (title + "\n" + body);
    }

    public static List<String> getWords(String Url)throws Exception{

        String line = parseURL(Url);
        String newline = line.replaceAll("[^\\p{L}\\p{Z}]", "");
        List<String> words = Arrays.asList(newline.split(" "));

        return words;
    }






}
