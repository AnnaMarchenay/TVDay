package pm.iut.fr.tvday.OutilsXML;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by annam on 27/01/2018.
 * Permet de récupérer le contenu du fichier XML
 */

public class XmlCode extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... params) {
            URL siteUrl;
            URLConnection siteConnection;
            BufferedReader in;
            String codeHtml = null;
            String rl;

            try {
                siteUrl = new URL(params[0]);
                siteConnection = siteUrl.openConnection();
                in = new BufferedReader(new InputStreamReader(siteConnection.getInputStream()));

                codeHtml = "";
                // Récupération du code HTML du site ligne par ligne
                while((rl = in.readLine()) != null){
                    codeHtml += rl;

                    // Test si le l'AsyncTask est cancel pour annuler
                    // la lecture du site
                    if(isCancelled()){
                        in.close();
                        return null;
                    }
                }

                // Fermeture du BufferReader
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }catch (Exception e){
                e.printStackTrace();
            }
            return codeHtml;
        }
}
