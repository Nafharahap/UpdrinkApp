package com.mobcom.updrinkapps.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.updrinkapps.R;
import com.mobcom.updrinkapps.models.Order;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ListViewHolder> {

    private static final String TAG = "OrderListAdapter";

    List<Order> orderList;

    public OrderListAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycler_view, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ListViewHolder holder, int position) {
        holder.full_name.setText(orderList.get(position).getFullName());
        holder.transaction_code.setText(orderList.get(position).getTransactionCode());
        holder.order_date.setText(orderList.get(position).getDate());
        String statusOrder = orderList.get(position).getStatusOrder();
        Log.d(TAG, "onBindViewHolder: " + statusOrder);
        if (statusOrder.equals("0")) {
            holder.status_order.setText("Order Cancel");
        } else if (statusOrder.equals("1")) {
            holder.status_order.setText("Order Pending");
        } else if (statusOrder.equals("2")) {
            holder.status_order.setText("Order Accept");
        } else {
            holder.status_order.setText("Order Finish");
        }
        holder.order_total_price.setText(orderList.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView full_name, transaction_code, order_date, status_order, order_total_price;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            full_name = itemView.findViewById(R.id.full_name);
            transaction_code = itemView.findViewById(R.id.transaction_code);
            order_date = itemView.findViewById(R.id.order_date);
            status_order = itemView.findViewById(R.id.status_order);
            order_total_price = itemView.findViewById(R.id.order_total_price);
        }
    }
}
