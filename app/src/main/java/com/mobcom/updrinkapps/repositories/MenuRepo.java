package com.mobcom.updrinkapps.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.updrinkapps.adapters.MenuListAdapter;
import com.mobcom.updrinkapps.models.Menu;
import com.mobcom.updrinkapps.models.MenuData;
import com.mobcom.updrinkapps.retrofit.APIClient;
import com.mobcom.updrinkapps.retrofit.APIEndPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuRepo {

    APIEndPoint apiEndPoint;
    MenuListAdapter menuListAdapter;
    List<Menu> menuDataList;
    private static final String TAG = "MenuRepo";

    private MutableLiveData<List<Menu>> mutableMenuList;

    public LiveData<List<Menu>> getMenus() {
        if (mutableMenuList == null) {
            mutableMenuList = new MutableLiveData<>();
            loadMenu();
        }
        return mutableMenuList;
    }

    private void loadMenu() {

        apiEndPoint = APIClient.getRetrofitInstance().create(APIEndPoint.class);

        Call<MenuData> call = apiEndPoint.getAllData();
        call.enqueue(new Callback<MenuData>() {
            @Override
            public void onResponse(Call<MenuData> call, Response<MenuData> response) {
                List<Menu> menuDataList = response.body().getMenu();
                mutableMenuList.setValue(response.body().getMenu());

                Log.d(TAG, "onResponse: " + menuDataList.toString());
            }

            @Override
            public void onFailure(Call<MenuData> call, Throwable t) {
                Log.d(TAG, "onFailure: Fail to fetch data");
            }
        });
    }
}
