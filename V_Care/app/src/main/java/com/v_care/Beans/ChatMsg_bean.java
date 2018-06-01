package com.v_care.Beans;

/**
 * Created by lenovo on 13-Apr-17.
 */
public class ChatMsg_bean
{
    String chat_id;
    String sent_by_admin;
    String sent_to_admin;
    String message;
    String created_at;

    // creating Getter Setter for the above string variables

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getSent_by_admin() {
        return sent_by_admin;
    }

    public void setSent_by_admin(String sent_by_admin) {
        this.sent_by_admin = sent_by_admin;
    }

    public String getSent_to_admin() {
        return sent_to_admin;
    }

    public void setSent_to_admin(String sent_to_admin) {
        this.sent_to_admin = sent_to_admin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
