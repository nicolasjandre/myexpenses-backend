package com.example.myexpenses.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

    public static String formatDate(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
    
}