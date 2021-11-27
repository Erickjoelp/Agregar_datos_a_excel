package alabs.gsheetwithimage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static alabs.gsheetwithimage.Configuration.KEY_CELU;
import static alabs.gsheetwithimage.Configuration.KEY_FACTURA;
import static alabs.gsheetwithimage.Configuration.KEY_ID;
import static alabs.gsheetwithimage.Configuration.KEY_IMAGE;
import static alabs.gsheetwithimage.Configuration.KEY_MAIL;
import static alabs.gsheetwithimage.Configuration.KEY_NAME;
import static alabs.gsheetwithimage.Configuration.KEY_PUNTO;
import static alabs.gsheetwithimage.Configuration.KEY_USERS;
import static alabs.gsheetwithimage.Configuration.KEY_ASESOR;

public class JsonParser {
    public static String[] uIds;
    public static String[] uNames;
    public static String[] uImages;
    public static String[] uCelu;
    public static String[] uMails;
    public static String[] uFacturas;
    public static String[] uPuntos;
    public static String[] uAsesor;

    private final String json;

    public JsonParser(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray users = jsonObject.getJSONArray(KEY_USERS);

            uNames = new String[users.length()];
            uIds = new String[users.length()];
            uCelu = new String[users.length()];
            uMails = new String[users.length()];
            uFacturas = new String[users.length()];
            uPuntos = new String[users.length()];
            uAsesor = new String[users.length()];
            uImages = new String[users.length()];


            for(int i = 0; i< users.length(); i++){
                JSONObject jo = users.getJSONObject(i);

                uNames[i] = jo.getString(KEY_NAME);
                uIds[i] = jo.getString(KEY_ID);
                uCelu[i] = jo.getString(KEY_CELU);
                uMails[i] = jo.getString(KEY_MAIL);
                uFacturas[i] = jo.getString(KEY_FACTURA);
                uPuntos[i] = jo.getString(KEY_PUNTO);
                uAsesor[i] = jo.getString(KEY_ASESOR);
                uImages[i] = jo.getString(KEY_IMAGE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
