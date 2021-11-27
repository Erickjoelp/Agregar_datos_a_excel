package alabs.gsheetwithimage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText etDato;
    Button btnGuardar;

    Info info = new Info();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_inicial);


        //
        final Context context = this;
        final SharedPreferences sharprefs = getSharedPreferences("ArchivoSP", MODE_PRIVATE);
        etDato = (EditText)findViewById(R.id.etDato);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);

        //

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no deja continuar si el campo esta bacio.
                final String strName = etDato.getText().toString();
                boolean isEmpty = false;
                if (TextUtils.isEmpty(strName)) {
                    etDato.setError(getString(R.string.empty_field));
                    isEmpty = true;
                }
                if (isEmpty) return;

                SharedPreferences sharpref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharpref.edit();
                editor.putString("MiDato", etDato.getText().toString());
                editor.apply();


                info.setTest(sharpref.getString("MiDato","to register"));

                Intent intent = new Intent(getApplicationContext(),Inicio.class);
                intent.putExtra("InfoClass",info);
                startActivity(intent);
                finish();
            }

        });
        SharedPreferences sharpref = getPreferences(MODE_PRIVATE);
        info.setTest(sharpref.getString("MiDato","to register"));


        if (!"to register".equals(info.getTest())){
            Intent intent = new Intent(getApplicationContext(),Inicio.class);
            intent.putExtra("InfoClass",info);
            startActivity(intent);
            finish();

        }



    }


}