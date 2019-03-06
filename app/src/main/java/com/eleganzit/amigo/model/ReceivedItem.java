package com.eleganzit.amigo.model;

import com.eleganzit.amigo.utils.ListItem;


public class ReceivedItem extends ListItem {
    private ChatsData pojoOfJsonArray;

    public ChatsData getPojoOfJsonArray() {
        return pojoOfJsonArray;
    }

    public void setPojoOfJsonArray(ChatsData pojoOfJsonArray) {
        this.pojoOfJsonArray = pojoOfJsonArray;
    }

    @Override
    public int getType() {
        return TYPE_MESSAGE_RECEIVED;
    }

}