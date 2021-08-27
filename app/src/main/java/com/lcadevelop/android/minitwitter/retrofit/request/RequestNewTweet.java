package com.lcadevelop.android.minitwitter.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestNewTweet {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public RequestNewTweet() {
    }

    public RequestNewTweet(String mensaje) {
        super();
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}