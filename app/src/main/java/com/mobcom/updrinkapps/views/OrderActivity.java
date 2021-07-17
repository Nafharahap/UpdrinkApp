package com.mobcom.updrinkapps.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mobcom.updrinkapps.R;
import com.mobcom.updrinkapps.adapters.OrderListAdapter;
import com.mobcom.updrinkapps.models.Order;
import com.mobcom.updrinkapps.models.OrderData;
import com.mobcom.updrinkapps.retrofit.APIClient;
import com.mobcom.updrinkapps.retrofit.APIEndPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    APIEndPoint apiEndPoint;
    List<Order> orderList;
    OrderListAdapter orderListAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView full_name, transaction_code, order_date, status_order, order_total_price;
    String name, code, date, status, total;

    private static final String TAG = "OrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        apiEndPoint = APIClient.getRetrofitInstance().create(APIEndPoint.class);
        recyclerView = findViewById(R.id.order_menu_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        String id_user = account.getId();

        getOrder(id_user);
    }


    private void getOrder(String id_user) {
        Call<OrderData> call = apiEndPoint.getOrder(id_user);

        call.enqueue(new Callback<OrderData>() {
            @Override
            public void onResponse(Call<OrderData> call, Response<OrderData> response) {
                if (response.body() != null) {
                    orderList = response.body().getData();
                    orderListAdapter = new OrderListAdapter(orderList);

                    recyclerView.setAdapter(orderListAdapter);

                    for (int i = 0; i < orderList.size(); i++) {
                        Log.d(TAG, "Full Name: " + orderList.get(i).getFullName());
                    }
                }
            }
//                Log.d(TAG, "onResponse: ");

            @Override
            public void onFailure(Call<OrderData> call, Throwable t) {

            }
        });
    }
}