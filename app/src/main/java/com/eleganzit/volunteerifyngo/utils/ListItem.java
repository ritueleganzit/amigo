package com.eleganzit.volunteerifyngo.utils;

public abstract class ListItem {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_MESSAGE_SENT = 1;
    public static final int TYPE_MESSAGE_RECEIVED = 2;

    abstract public int getType();
}
