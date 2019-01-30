package pm.iut.fr.tvday.masterDetail;


import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pm.iut.fr.tvday.programmeDUneChaine.Program;
import pm.iut.fr.tvday.OutilsXML.XmlCode;
import pm.iut.fr.tvday.OutilsXML.XmlTVParser;


public class Content {
    private static String element;
    private static ArrayList<Program> ps=recupPrograms();
    private int i = 0;

    public final List<Item> ITEMS = new ArrayList<>();

    public static final Map<String, Program> ITEM_MAP = new HashMap<>();

    private static final int COUNT = ps.size()-1;

    public Content(String chaine) {
        element=chaine;
        addItems(createItems());
    }

    private void addItems(ArrayList<Item> items) {
        ITEMS.addAll(items);
        for (Item item:items) {
            ITEM_MAP.put(String.valueOf(i), item.details);
            i++;
        }
    }

    /**
     * recupère tous les programmes
     * @return une liste de ces programmes
     */
    private static ArrayList<Program> recupPrograms(){
        XmlCode task =new XmlCode();
        String contenu="";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.FRANCE);
        String date = sdf.format(new Date());
        ArrayList<Program> programs = new ArrayList<>();
        try {
            contenu=task.execute("https://webnext.fr/epg_cache/programme-tv-rss_"+date+".xml").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            InputStream in =new ByteArrayInputStream(contenu.getBytes());
            XmlTVParser xmlparser = new XmlTVParser();
            programs=xmlparser.parse(in);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return programs;
    }

    /**
     * Récupère les programmes du moment
     * @return une liste de ces programmes
     */
    private static  ArrayList<Program> liveProgram(){
        ArrayList<Program> prgCourants=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.FRANCE);

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int i=0;

        for (Program program: ps) {
            try {
                Date dateDeb=sdf.parse(ps.get(i).heureDebut);
                if(i==COUNT)i--;
                Date dateFin=sdf.parse(ps.get(i+1).heureDebut);
                Date currentDate = sdf.parse(hour+":"+minute);
                if((dateDeb.before(currentDate)&&dateFin.after(currentDate)) || dateDeb.equals(currentDate)) {
                    prgCourants.add(ps.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            i++;
        }
        return prgCourants;
    }

    /**
     * Permet de récupérer les programmes d'une chaine
     * @param nomChaine String
     * @return une liste de ces programmes
     */
    private static ArrayList<Program> chainePrograms(String nomChaine){
        ArrayList<Program> pgChaine = new ArrayList<>();
        for (Program program : ps){
            if (program.chaine.equals(nomChaine)){
                pgChaine.add(program);
            }
        }
        return pgChaine;
    }


    private static ArrayList<Item> createItems() {
        ArrayList<Item> res = new ArrayList<>();
        int i=0;
        switch (element){
            case "chaine" :
                for(Program p : ps){
                    if(i==0) {
                        res.add(new Item(p,String.valueOf(i)));
                        i++;
                    }else{
                        if (!(res.get(i-1).details.chaine).equals(p.chaine)) {
                            res.add(new Item(p,String.valueOf(i)));
                            i++;
                        }
                    }
                }
                break;
            case "live" :
                ArrayList<Program> pgActuel = liveProgram();
                for (Program p :pgActuel) {
                    res.add(new Item(p,String.valueOf(i)));
                    i++;
                }
                break;
            default :
                ArrayList<Program> pgUneChaine = chainePrograms(element);
                for(Program p : pgUneChaine){
                    res.add(new Item(p,String.valueOf(i)));
                    i++;
                }
        }
        return res;

    }

}
