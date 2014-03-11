package com.app.MoodApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by PsichO on 19.02.14.
 */
public class AddActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    TextView textDescription;
    Button buttonMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        final SeekBar seekBarMood = (SeekBar)findViewById(R.id.seekBarMood);
        textDescription = (TextView)findViewById(R.id.textDescription);
        buttonMood = (Button)findViewById(R.id.buttonShareMood);

        buttonMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonShareMood:
                        Intent intent = new Intent();
                        intent.putExtra("mood", textDescription.getText());
                        setResult(RESULT_OK,intent);
                        AddActivity.this.finish();
                        break;
                }
            }
        });

        //textExplanation = (EditText)findViewById(R.id.textExplanation);

        textDescription.setText(" ");

        seekBarMood.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBarMood, int progress, boolean fromUser) {
        switch (seekBarMood.getProgress()){
            case 0:
                textDescription.setText("Bad");
                break;
            case 1:
                textDescription.setText("So-so");
                break;
            case 2:
                textDescription.setText("Good");
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBarMood){

        }
    @Override
    public void onStopTrackingTouch(SeekBar seekBarMood) {
        switch (seekBarMood.getProgress()){
            case 0:
                textDescription.setText("Bad");
                break;
            case 1:
                textDescription.setText("So-so");
                break;
            case 2:
                textDescription.setText("Good");
                break;
            default:
                break;
        }
    }

}
