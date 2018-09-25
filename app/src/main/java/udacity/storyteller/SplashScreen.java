package udacity.storyteller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import udacity.storyteller.UI.Login.Login;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(r, 2000);

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashScreen.this, Login.class);
            startActivity(intent);
            finish();
        }
    };
}
