package com.drmmx.devmax.randomuserapp.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String GENDER = "gender";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String USER_AGE = "user_age";
    public static final String USER_PHONE_NUMBER = "user_phone_number";
    public static final String USER_EMAIL = "user_email";
    public static final String IMAGE_URL = "image_url";

    //Format first letter to upper case
    public static String firstUpperCase(String word) {
        if (word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    //Format Date of birth
    public static String viewSimpleDate(String inputDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = null;
        try {
            Date date = sdf.parse(inputDate);
            formattedDate = output.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}
