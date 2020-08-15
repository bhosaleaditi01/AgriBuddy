package com.example.agribuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
      private Button CreateAccountb;
      private EditText Inputname,Inputphone,Inputpassword;
      private ProgressDialog Loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountb=(Button) findViewById(R.id.register_now);
        Inputname=(EditText) findViewById(R.id.register_name);
        Inputphone=(EditText) findViewById(R.id.register_id);
        Inputpassword=(EditText) findViewById(R.id.register_password);
        Loadingbar = new ProgressDialog(this);

        CreateAccountb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        String name= Inputname.getText().toString();
        String phone= Inputphone.getText().toString();
        String password= Inputpassword.getText().toString();

         if(TextUtils.isEmpty(name))
         {
             Toast.makeText(this,"PLease wrie your name",Toast.LENGTH_SHORT).show();
         }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"PLease wrie your phone number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"PLease wrie password",Toast.LENGTH_SHORT).show();
        }
        else
         {
             Loadingbar.setTitle("Create Account");
             Loadingbar.setMessage("Please wait,While we are checking!!");
             Loadingbar.setCanceledOnTouchOutside(false);
             Loadingbar.show();

             Validatephonenumber(name,phone,password);
         }
    }

    private void Validatephonenumber(final String name, final String phone, final String password)
    {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String,Object> userdataMap=new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("name",name);
                    userdataMap.put("password",password);
                    Rootref.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Congratulation,Your account has created",Toast.LENGTH_SHORT).show();
                                         Loadingbar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this,loginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Loadingbar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network Error:PLease try again!!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(RegisterActivity.this,"This"+phone+"already exist",Toast.LENGTH_LONG).show();
                    Loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Please try again using another account",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}