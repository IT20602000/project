package com.example.mealfactoryapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class OrderAdapter extends FirebaseRecyclerAdapter<OrderModel, OrderAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull OrderModel orderModel) {

        int quantity = Integer.parseInt(orderModel.getQuantity());
        int price = Integer.parseInt(orderModel.getUnitPrice());

        int total = quantity * price;

        myViewHolder.itemCode.setText(orderModel.getItemCode());
        myViewHolder.price.setText("Rs." + (String.valueOf(total)) + ".00");
        myViewHolder.quan.setText(orderModel.getQuantity());

        myViewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myViewHolder.quan.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("This action will be remove this order permanently!");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("order").child(orderModel.getItemCode()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(myViewHolder.quan.getContext(), "cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        myViewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialogPlus = DialogPlus.newDialog(myViewHolder.itemCode.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 700)
                        .create();

                View view1 = dialogPlus.getHolderView();

                EditText name = view1.findViewById(R.id.orderName);
                EditText quantity = view1.findViewById(R.id.orderQuan);
                Button submit = view1.findViewById(R.id.updateSubmit);

                name.setText(orderModel.getItemCode());
                quantity.setText(orderModel.getQuantity());

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            FirebaseDatabase.getInstance().getReference().child("order").child(orderModel.getItemCode()).child("itemCode").setValue(name.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("order").child(orderModel.getItemCode()).child("quantity").setValue(quantity.getText().toString());

                            Toast.makeText(view1.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                });

                dialogPlus.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView itemCode, quan, price;
        Button editBtn, deleteBtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            itemCode = itemView.findViewById(R.id.itemCodeTV);
            quan = itemView.findViewById(R.id.quanTV);
            price = itemView.findViewById(R.id.priceTV);

            editBtn = itemView.findViewById(R.id.editOrder);
            deleteBtn = itemView.findViewById(R.id.deleteOrder);
        }
    }

}
