package de.pbma.moa.airhockey;

import android.content.Context;
import android.content.SharedPreferences;

public class MqTTLoginData {



    public static String getBrokerUrl(Context context){

        SharedPreferences preferences = context.getSharedPreferences("PlayerInfo", Context.MODE_PRIVATE);

        return preferences.getString("BrokerUrl","null");


    }public static String getMqttUsername(Context context){

        SharedPreferences preferences = context.getSharedPreferences("PlayerInfo", Context.MODE_PRIVATE);

        return preferences.getString("MqTTUsername","null");


    }public static String getPassword(Context context){


        try {
            return SecureStorage.getMqttPassword(context);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }


    }

}
