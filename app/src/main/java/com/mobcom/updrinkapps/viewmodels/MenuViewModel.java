package com.mobcom.updrinkapps.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mobcom.updrinkapps.models.CartItem;
import com.mobcom.updrinkapps.models.Menu;
import com.mobcom.updrinkapps.repositories.CartRepo;
import com.mobcom.updrinkapps.repositories.MenuRepo;

import java.util.List;

public class MenuViewModel extends ViewModel {

    MenuRepo menuRepo = new MenuRepo();
    CartRepo cartRepo = new CartRepo();

    MutableLiveData<Menu> mutableMenu = new MutableLiveData<>();

    public LiveData<List<Menu>> getMenus() {
        return menuRepo.getMenus();
    }

    public void setMenu(Menu menu) {
        mutableMenu.setValue(menu);
    }

    public LiveData<Menu> getMenu() {
        return mutableMenu;
    }

    public LiveData<List<CartItem>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Menu menu) {
        return cartRepo.addItemToCart(menu);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Integer> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }
}
