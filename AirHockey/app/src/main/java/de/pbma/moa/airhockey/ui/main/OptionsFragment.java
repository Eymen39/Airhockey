package de.pbma.moa.airhockey.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.pbma.moa.airhockey.MqTTLoginData;
import de.pbma.moa.airhockey.R;
import de.pbma.moa.airhockey.SecureStorage;
import de.pbma.moa.airhockey.databinding.FragmentOptionsBinding;


public class OptionsFragment extends Fragment {
    private String username;
    int targetGoals;
    private int playstyle;
    FragmentOptionsBinding binding;
    private int mDefaultColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding= FragmentOptionsBinding.inflate(inflater,container,false);

     binding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
        playstyle= isChecked? 1:0; //wenn isChecked true ist wird swPlaystyle 1 sonst 0

     });
        SharedPreferences preferences = getActivity().getSharedPreferences("PlayerInfo", Context.MODE_PRIVATE);
        String usernameSet=preferences.getString("Username","null");
        int targetGoalsSet=preferences.getInt("targetGoals", 0);
        String mqttUsernameSet= MqTTLoginData.getMqttUsername(requireContext());
        String mqttBrokerSet= MqTTLoginData.getBrokerUrl(requireContext());
        String passwordSet= MqTTLoginData.getPassword(requireContext());

        if(preferences.getInt("PlayStyle",2)==1){
            binding.switch1.setChecked(true);
        }else{
            binding.switch1.setChecked(false);
        }


        if(!usernameSet .equals("null")){
            binding.etUsername.setText(usernameSet);
        }

        if(!(targetGoalsSet == 0)){
            binding.etGoalGoal.setText(String.valueOf(targetGoalsSet));
        }
        if(!mqttUsernameSet.equals("null")){
            binding.etMqttUsername.setText(mqttUsernameSet);
        }
        if(!mqttBrokerSet.equals("null")){
            binding.etBrokerUrl.setText(mqttBrokerSet);
        }
        if(!passwordSet.equals("null")){
            binding.etMqTTPassword.setText(passwordSet);
        }




     binding.btnSave.setOnClickListener( v->{
         SharedPreferences.Editor editor= preferences.edit();
         String username=binding.etUsername.getText().toString();
         String targetGoalText = binding.etGoalGoal.getText().toString();
         String brokerUrl= binding.etBrokerUrl.getText().toString();
         String mqttUsername= binding.etMqttUsername.getText().toString();
         String password = binding.etMqTTPassword.getText().toString();


         if(checkGoalstoWin(targetGoalText,editor)==0)return;
         if (checkUserName(username, editor) == 0) return;
         if (checkBrokerUrl(brokerUrl, editor) == 0) return;
         if (checkMqTTUserName(mqttUsername, editor) == 0) return;
         if (checkingPassword(password, editor) == 0) return;

         Toast.makeText(getActivity(), "Die Einstellungen wurde gespeichert", Toast.LENGTH_SHORT).show();
         editor.apply();



     });
        return binding.getRoot();

    }
    private int checkUserName(String username,SharedPreferences.Editor editor){
        if (!username.isBlank()) {
            if(!username.contains("/")) {


                if (username.length() >= 4) {

                    editor.putString("Username", binding.etUsername.getText().toString());
                    editor.putInt("PlayStyle",playstyle);
                    return 1;

                } else {
                    Toast.makeText(getActivity(), "Der Username muss mindestens 4 Zeichen lang sein", Toast.LENGTH_LONG).show();

                }
            }else{
                Toast.makeText(getActivity(),"Der Username darf nicht das zeichen / enthalten",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getActivity(),"Geben sie einen Namen ein",Toast.LENGTH_LONG).show();

        }
        return 0;

    }
    private int checkBrokerUrl(String brokerUrl, SharedPreferences.Editor editor){
        if( brokerUrl.isBlank()){
            Toast.makeText(getActivity(),"Geben sie eine Broker Url ein", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else{
            editor.putString("BrokerUrl", brokerUrl);
        }
        return 1;

    }
    private int checkMqTTUserName(String mqttUsername, SharedPreferences.Editor editor){
        if( mqttUsername.isBlank()){
            Toast.makeText(getActivity(),"Geben sie eine Mqtt Nutzernamen ein", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else{
            editor.putString("MqTTUsername", mqttUsername);
        }
        return 1;

    }

    private int checkingPassword(String password, SharedPreferences.Editor editor){
        if( password.isBlank()){
            Toast.makeText(getActivity(),"Geben sie ein Passwort für den MqTT Server ein", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else{
            try {
                SecureStorage.saveMqttPassword(requireContext(),password);
                return 1;
            } catch (Exception e) {
                Toast.makeText(getActivity(),"Etwas ist schiefgelaufen", Toast.LENGTH_SHORT).show();

            }
            return 0;
        }

    }


    private int checkGoalstoWin(String goals,SharedPreferences.Editor editor){
        if(!goals.isEmpty()){
            try{
                targetGoals = Integer.parseInt(goals);
            }catch(Exception e){
                Toast.makeText(getContext(), "Es wird eine Zahl erwartet", Toast.LENGTH_SHORT).show();
                return 0;
            }
            if(targetGoals <= 0){
                Toast.makeText(getContext(), "nur positive Zahlen sind erlaubt", Toast.LENGTH_SHORT).show();
                return 0;
            }
            editor.putInt("targetGoals", targetGoals);
        }
        return 1;

    }


    @Override
    public void onPause() {
        super.onPause();

    }
}
