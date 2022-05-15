package com.example.mealfactoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class history extends AppCompatActivity {


    EditText editTextTextPersonName4, editTextTextPersonID, editTextTextPersonTime;
    Button Updatebtn, Deletebtn,view;

    DatabaseReference dbRef;
    fdorder up;

    private String id_no ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        id_no = getIntent().getStringExtra("id");

        editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonID = findViewById(R.id.editTextTextPersonID);
        editTextTextPersonTime = findViewById(R.id.editTextTextPersonTime);

        Updatebtn = findViewById(R.id.Updatebtn);
        Deletebtn = findViewById(R.id.Deletebtn);
        view = findViewById(R.id.viewwbtn);

        up = new fdorder();

        String id = getIntent().getStringExtra("id");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    editTextTextPersonName4.setText(snapshot.child("name").getValue().toString());
                    editTextTextPersonID.setText(snapshot.child("id").getValue().toString());
                    editTextTextPersonTime.setText(snapshot.child("phone").getValue().toString());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("user").child(id);

                readRef.child("name").setValue(editTextTextPersonName4.getText().toString());
                readRef.child("phone").setValue(editTextTextPersonTime.getText().toString());
                readRef.child("id").setValue(editTextTextPersonID.getText().toString());

                Toast.makeText(history.this, "Update Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("user").child(id_no);
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            try {
                                editTextTextPersonName4.setText(dataSnapshot.child("editTextTextPersonName4").getValue().toString());
                                editTextTextPersonID.setText(dataSnapshot.child("editTextTextPersonID").getValue().toString());
                                editTextTextPersonTime.setText(dataSnapshot.child("editTextTextPersonTime").getValue().toString());
                            }
                            catch (Exception e) {
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        //id = getIntent().getStringExtra("id");

        //editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);
        //editTextTextPersonID = findViewById(R.id.editTextTextPersonID) ;
        //editTextTextPersonTime = findViewById(R.id.editTextTextPersonTime);

        //Updatebtn = findViewById(R.id.Updatebtn);
        //Deletebtn = findViewById(R.id.Deletebtn);
        //viewwbtn = findViewById(R.id.viewwbtn);

        //viewwbtn.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View view) {
        //  dbRef = FirebaseDatabase.getInstance().getReference().child("user").child("600");
        //dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
        //  @Override
        //public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //  if (dataSnapshot.hasChildren()) ;
        //String Name = dataSnapshot.child("name").getValue().toString();
        //String ID = dataSnapshot.child("id").getValue().toString();
        //String Phone = dataSnapshot.child("phone").getValue().toString();
        //editTextTextPersonName4.setText(Name);
        //editTextTextPersonID.setText(ID);
        //editTextTextPersonTime.setText(Phone);
        //}
        //else

        // {
        //Toast.makeText(getApplicationContext(), "No source to Display", Toast.LENGTH_SHORT).show();
        // }
        // }
        // });
        //}
        //});


        //viewwbtn.setOnClickListener(new View.OnClickListener() {
        //@Override
        // public void onClick(View view) {

        // DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("user").child("600");
        //readRef.child(id).addValueEventListener(new ValueEventListener() {
        // @Override
        // public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //if(dataSnapshot.hasChildren()){
        // user user = dataSnapshot.getValue(user.class);
        //editTextTextPersonName4.setText(user.getName());
        // editTextTextPersonID.setText(user.getID());
        // editTextTextPersonTime.setText(user.getPhone());


        // }
        //}

        // @Override
        // public void onCancelled(@NonNull DatabaseError databaseError) {

        //}
        //});


        //}
        // });

//        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("user").child(id);
//        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.hasChildren()){
//                    editTextTextPersonName4.setText(dataSnapshot.child("name").getValue().toString());
//
//                    editTextTextPersonID.setText(dataSnapshot.child("id").getValue().toString());
//
//                    editTextTextPersonTime.setText(dataSnapshot.child("phone").getValue().toString());
//
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        Deletebtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            System.out.println(id);

             DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("user").child(id);
             try {
                 delRef.removeValue();
                 Intent intent = new Intent(history.this, Takeaway.class);
                 startActivity(intent);
                 Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
             }catch (Exception e){
                 System.out.println(e.getMessage());
             }
        }
        });


    }
}
