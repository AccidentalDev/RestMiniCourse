package com.example.restminicourse;

import android.view.View;
import android.widget.Button;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class ButtonListener implements View.OnClickListener{
    private List<Button> myButtons;
    private ButtonListener singleInstance;

    private ButtonListener(){
        myButtons = new ArrayList<>();
    }

    public ButtonListener getSingleInstance(Button newButton) {
        if(singleInstance == null){
            singleInstance = new ButtonListener();
        }
        myButtons.add(newButton);

        return singleInstance;
    }

    @Override
    public void onClick(View view){
        if(myButtons.contains(view)){
            Button thisButton = (Button)view;
            if(thisButton.getText().charAt(5) == '1'){

            }
        }
    }
}
