package alabs.gsheetwithimage;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static alabs.gsheetwithimage.Configuration.ADD_USER_URL;
import static alabs.gsheetwithimage.Configuration.KEY_ACTION;
import static alabs.gsheetwithimage.Configuration.KEY_ASESOR;
import static alabs.gsheetwithimage.Configuration.KEY_CELU;
import static alabs.gsheetwithimage.Configuration.KEY_FACTURA;
import static alabs.gsheetwithimage.Configuration.KEY_ID;
import static alabs.gsheetwithimage.Configuration.KEY_IMAGE;
import static alabs.gsheetwithimage.Configuration.KEY_MAIL;
import static alabs.gsheetwithimage.Configuration.KEY_NAME;
import static alabs.gsheetwithimage.Configuration.KEY_PUNTO;

public class AddUser extends AppCompatActivity implements View.OnClickListener {


//En esta clase se añadira el usuario a la base de datos, se implementaran listeners para los botones
    private EditText editTextUserName;
    private EditText editTextUserId;
    private EditText editTextUcelu;
    private EditText editTextUmail;
    private EditText editTextUfactura;
    private EditText editTextUpunto;
    private String editTextAsesor;

    private ImageView imageViewUserImage;
    private Button buttonAddUser;
    private ImageButton imgButton;
     String userImage;

    private final int PICK_IMAGE_REQUEST = 1;
    Bitmap rbitmap;

    //A continuacion se inicializan los botones y los listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        Info info = (Info) getIntent().getSerializableExtra("InfoClass");

        editTextUcelu = (EditText) findViewById(R.id.et_celu);
        editTextUserId = (EditText) findViewById(R.id.et_uid);
        editTextUserName = (EditText) findViewById(R.id.etDato);
        editTextUmail = (EditText) findViewById(R.id.et_mail);
        editTextUfactura = (EditText) findViewById(R.id.et_factura);
        editTextUpunto = (EditText) findViewById(R.id.et_punto);
        editTextAsesor = (String) info.getTest();

        imageViewUserImage=(ImageView)findViewById(R.id.iv_uphoto);

        buttonAddUser = (Button) findViewById(R.id.btn_add_user);
        imgButton = (ImageButton) findViewById(R.id.imgBuscar);

        buttonAddUser.setOnClickListener(this);
        imgButton.setOnClickListener(this);
    }

 //Esta funcion nos retorna el tamaño de la imagen seleccionada
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }

    //A continuacion el metodo para decodificar la imagen obtenida
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    //Metodo para añadir nuevo usuario
    private void addUser(){


        final ProgressDialog loading = ProgressDialog.show(this,"Guardando...","Por favor espera...",false,false);
        final String userId = editTextUserId.getText().toString().trim();
        final String userName = editTextUserName.getText().toString().trim();
        final String userCelu = editTextUcelu.getText().toString().trim();
        final String userMail= editTextUmail.getText().toString().trim();
        final String userFactura=editTextUfactura.getText().toString().trim();
        final String userPunto = editTextUpunto.getText().toString().trim();
        final String userAsesor = editTextAsesor.trim();

       //Bitmap  rbitmap = getResizedBitmap(bitmap,500);

        Log.e("null","values"+userImage);

        //Cuadro de respuesta o error al intentar conexion
        StringRequest stringRequest = new StringRequest(Request.Method.POST,ADD_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(AddUser.this,response,Toast.LENGTH_LONG).show();
                        //finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.hide();
                        if(userImage == null){
                            Toast.makeText(getApplicationContext(), getString(R.string.error2), Toast.LENGTH_LONG).show();

                        }
                        else if(error instanceof NoConnectionError ){

                            Toast.makeText(getApplicationContext(), getString(R.string.error1), Toast.LENGTH_LONG).show();

                            }
                            else if(error instanceof ClientError){
                            Toast.makeText(getApplicationContext(), getString(R.string.error3), Toast.LENGTH_LONG).show();

                            }
                            else{
                            Toast.makeText(AddUser.this,error.toString(),Toast.LENGTH_LONG).show();

                        }

                    }
                }){

            //Seteo de parametros en la url
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ACTION,"insert");
                params.put(KEY_ID,userId);
                params.put(KEY_NAME,userName);
                params.put(KEY_CELU,userCelu);
                params.put(KEY_MAIL,userMail);
                params.put(KEY_FACTURA,userFactura);
                params.put(KEY_PUNTO,userPunto);
                params.put(KEY_ASESOR,userAsesor);
                params.put(KEY_IMAGE,userImage);

                return params;
            }

        };

        //Limite de 5 segundos para una respuesta
        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Se hace la peticion del string
        requestQueue.add(stringRequest);
    }



        //Aca el metodo que habilita el activity para seleccionar una imagen
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    //Aca se asigna la imagen seleccionada al imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                rbitmap = getResizedBitmap(bitmap,2000);//Setting the Bitmap to ImageView
                userImage = getStringImage(rbitmap);
                imageViewUserImage.setImageBitmap(rbitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //Listeners de los botones
    @Override
    public void onClick(View v) {
        if(v == buttonAddUser){
            addUser();
        }
        if(v == imgButton){
            showFileChooser();
        }

    }
}