package alabs.gsheetwithimage;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
                       // Log.e("null","ser image"+response);
                        showJSON(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(UserList.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), getString(R.string.error1), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

        int socketTimeout = 5000; // 30 seconds. You can change it
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
        //Log.e("uImage","ser image"+JsonParser.uImages[1]);
        UserListAdapter userListAdapter = new UserListAdapter(this, JsonParser.uIds,JsonParser.uNames,JsonParser.uImages);
        listView.setAdapter(userListAdapter);
    }



    }
