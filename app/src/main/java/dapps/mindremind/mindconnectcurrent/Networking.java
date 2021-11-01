package dapps.mindremind.mindconnectcurrent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.os.AsyncTask;

public class Networking  {
    protected String AddresstoCordinatePoint(String address){
        String oot = address.replace(" ","-");
        String content = null;
        URLConnection connection = null;
        String urli = "https://atlas.microsoft.com/search/fuzzy/json?subscription-key=INSERT-API-KEY-HERE-sDbn8&api-version=1.0&query=233-S-Wacker-Dr-Chicago-IL-60606";
        //URL url;
        String ooooot = "";
        //URL url;
        // try {
        //   url = new URL(urli);
        // }
        //catch(Exception e){
        //url = new URL("");
        //}
        String testi = "";
        /*
        try {

            URL url = new URL(urli);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download

            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                //writer.write(line);
                testi+=line;
            }
        }
        catch(Exception e){

        }

         */
        String sURL = "http://freegeoip.net/json/"; 

        String ans = "";
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(urli);
            InputStream is = url.openStream();
            int ptr = 0;
            //StringBuffer buffer = new StringBuffer();
            while ((ptr = is.read()) != -1) {
                buffer.append((char) ptr);
            }
        }
        catch(Exception e){

        }

        return "The address is in "+buffer.toString();
    }
}
