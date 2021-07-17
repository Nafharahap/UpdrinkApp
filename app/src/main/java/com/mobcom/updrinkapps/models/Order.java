package com.mobcom.updrinkapps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id_order")
    @Expose
    private String idOrder;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("transaction_code")
    @Expose
    private String transactionCode;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status_order")
    @Expose
    private String statusOrder;

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

}
