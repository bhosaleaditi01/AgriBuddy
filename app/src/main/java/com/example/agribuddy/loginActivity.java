package com.example.agribuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agribuddy.Model.Users;
import com.example.agribuddy.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    private EditText Inputnumber,Inputpassword;
    private Button Loginbutton;
    private ProgressDialog Loadingbar;

    private String parentDbName="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Loginbutton=(Button) findViewById(R.id.login_now);
        Inputpassword=(EditText) findViewById(R.id.login_password);
        Inputnumber=(EditText) findViewById(R.id.login_id);
        Loadingbar = new ProgressDialog(this);

        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginuser();
            }
        });
    }

    private void Loginuser()
    {
        String phone= Inputnumber.getText().toString();
        String password= Inputpassword.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"PLease write your phone number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"PLease write password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Loadingbar.setTitle("Login Account");
            Loadingbar.setMessage("Please wait");
            Loadingbar.setCanceledOnTouchOutside(false);
            Loadingbar.show();

            AllowAccesstoaccount(phone,password);
        }
    }
    private void AllowAccesstoaccount(final String phone, final String password)
    {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersdata = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersdata.getPhone().equals(phone))
                    {
                        if(usersdata.getPassword().equals(password))
                        {
                            Toast.makeText(loginActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            Loadingbar.dismiss();

                            Intent intent = new Intent(loginActivity.this,HomeActivity.class);
                            startActivity(intent);

                        }
                    }
                }
                else
                {
                    Toast.makeText(loginActivity.this,"Account with this"+phone+"number do not exist",Toast.LENGTH_SHORT).show();
                    Loadingbar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}