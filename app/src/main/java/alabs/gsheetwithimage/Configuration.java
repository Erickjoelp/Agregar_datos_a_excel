package alabs.gsheetwithimage;

public class Configuration {

    /*En este segmento se crean variables estaticas con el fin de armar la url para solicitar que Google SpreadSheets
    devuelva un JSON y con la informacion solicitada.
     */
    public static final String APP_SCRIPT_WEB_APP_URL = "https://script.google.com/macros/s/AKfycbwuesp0kG8Mdy1qi0-QveXHpFJav5EGmcHJ3mlnqMD3Ou1jRXfUWOy2vrRIul7yxTNSxA/exec";
    public static final String ADD_USER_URL = APP_SCRIPT_WEB_APP_URL;
    public static final String LIST_USER_URL = APP_SCRIPT_WEB_APP_URL+"?action=readAll";

    public static final String KEY_ASESOR = "_Asesor";
    public static final String KEY_ID = "_Cedula";
    public static final String KEY_NAME = "_Nombre";
    public static final String KEY_CELU = "_Celuar";
    public static final String KEY_MAIL = "_Mail";
    public static final String KEY_FACTURA = "_Factura";
    public static final String KEY_PUNTO = "_Punto";
    public static final String KEY_IMAGE = "uImage";


    public  static final String KEY_ACTION = "action";
    public static final String KEY_USERS = "records";

}
