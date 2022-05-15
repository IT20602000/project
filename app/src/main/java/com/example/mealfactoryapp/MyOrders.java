package com.example.mealfactoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MyOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        recyclerView = (RecyclerView) findViewById(R.id.ordersRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phone = extras.getString("phoneNum");
        }

        System.out.println(phone);

        FirebaseRecyclerOptions<OrderModel> options = new FirebaseRecyclerOptions.Builder<OrderModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("order").orderByChild("phone").startAt(phone.toUpperCase(Locale.ROOT)).endAt(phone.toLowerCase(Locale.ROOT)+"/uf8ff"), OrderModel.class).build();

        orderAdapter = new OrderAdapter(options);
        recyclerView.setAdapter(orderAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }
}