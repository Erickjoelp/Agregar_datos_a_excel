package alabs.gsheetwithimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    Button addUser,viewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUser=(Button)findViewById(R.id.btn_add_user);
        viewUser=(Button)findViewById(R.id.btn_list_user);

        Info info = (Info) getIntent().getSerializableExtra("InfoClass");

        addUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddUser.class);
                intent.putExtra("InfoClass",info);
                startActivity(intent);

            }

        });

        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserList.class);
                startActivity(intent);

            }
        });

    }

}
