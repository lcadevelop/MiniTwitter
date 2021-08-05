package com.lcadevelop.android.minitwitter.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSignUp {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("active")
    @Expose
    private Boolean active;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseSignUp() {
    }

    /**
     *
     * @param photoUrl
     * @param role
     * @param created
     * @param active
     * @param email
     * @param token
     * @param username
     */
    public ResponseSignUp(String token, String username, String email, String role, String photoUrl, String created, Boolean active) {
        super();
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
        this.photoUrl = photoUrl;
        this.created = created;
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
