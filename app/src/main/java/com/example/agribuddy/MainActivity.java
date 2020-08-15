package com.example.agribuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button joinnow,loginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinnow=(Button)findViewById(R.id.main_join_now);
        loginbutton=(Button)findViewById(R.id.main_login);

                loginbutton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(MainActivity.this,loginActivity.class);
                        startActivity(intent);
                    }
                });

                joinnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                        startActivity(intent);

                    }
                });
    }
}