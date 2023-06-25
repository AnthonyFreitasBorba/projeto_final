package br.com.uniritter.projeto_final.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.uniritter.projeto_final.R;


public class InitActivity extends AppCompatActivity {
    private static final long SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(InitActivity.this,CadastroActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_DURATION);
    }
}