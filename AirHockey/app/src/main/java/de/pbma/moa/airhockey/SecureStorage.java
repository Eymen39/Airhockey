package de.pbma.moa.airhockey;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class SecureStorage {


    private static final String PREF_NAME = "secure_mqtt_prefs";
    private static final String KEY_PASSWORD = "mqtt_password";

    public static void saveMqttPassword(Context context, String Password)throws Exception
    {
        MasterKey masterKey = new MasterKey.Builder (context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        SharedPreferences securePrefs= EncryptedSharedPreferences.create(
                context,
                PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

        );

        securePrefs.edit().putString(KEY_PASSWORD,Password).apply();
    }
    public static String getMqttPassword(Context context) throws Exception {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        SharedPreferences securePrefs = EncryptedSharedPreferences.create(
                context,
                PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        return securePrefs.getString(KEY_PASSWORD, null);
    }

}
