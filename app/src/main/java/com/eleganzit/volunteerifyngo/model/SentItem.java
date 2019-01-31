package com.eleganzit.volunteerifyngo.model;

import com.eleganzit.volunteerifyngo.utils.ListItem;

public class SentItem extends ListItem {
    private ChatsData pojoOfJsonArray;

    public ChatsData getPojoOfJsonArray() {
        return pojoOfJsonArray;
    }

    public void setPojoOfJsonArray(ChatsData pojoOfJsonArray) {
        this.pojoOfJsonArray = pojoOfJsonArray;
    }

    @Override
    public int getType() {
        return TYPE_MESSAGE_SENT;
    }

}