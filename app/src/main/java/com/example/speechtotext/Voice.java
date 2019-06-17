package com.example.speechtotext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Voice extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    //Vistas de Activity
    TextView mTextView;
    ImageButton mVoiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
        mVoiceBtn = findViewById(R.id.voiceBtn);

        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
    }

    private void speak() {
        //Intent para mostrar el diálogo de voz a texto
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speak something");

        //Inicio de Intent
        try {
            //Si no hubiera error mostrar
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
            //Si hubo algún error
            //Recibir mensaje de error y mostrar
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Recibir entrada de voz y manejarla
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && null!=data){
                    //Obtener matriz de texto de intent de voz
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //Establecer en vista de texto
                    mTextView.setText(result.get(0));
                }
                break;
            }
        }
    }
}
