package alabs.gsheetwithimage;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static alabs.gsheetwithimage.Configuration.LIST_USER_URL;

public class UserList extends AppCompatActivity  {


    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        listView = (ListView) findViewById(R.id.list_view);
        sendRequest();
    }

    private void sendRequest(){
        final ProgressDialog loading = ProgressDialog.show(this,"Actualizando...","Por favor espera...",false,false);

        StringRequest stringRequest = new StringRequest(LIST_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showJSON(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error instanceof NoConnectionError){
                            Toast.makeText(getApplicationContext(), getString(R.string.error1), Toast.LENGTH_LONG).show();

                        }else if(error instanceof ClientError){
                            Toast.makeText(getApplicationContext(), getString(R.string.error3), Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(UserList.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        //finish();
                        loading.hide();
                    }
                });

        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String json){
        JsonParser pj = new JsonParser(json);
        pj.parseJSON();
        UserListAdapter userListAdapter = new UserListAdapter(this, JsonParser.uIds,JsonParser.uNames,JsonParser.uCelu, JsonParser.uMails, JsonParser.uFacturas,JsonParser.uPuntos,JsonParser.uAsesor,JsonParser.uImages);
        listView.setAdapter(userListAdapter);
    }


}
