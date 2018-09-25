package udacity.storyteller.UI.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import udacity.storyteller.R;
import udacity.storyteller.UI.Signup.Signup;
import udacity.storyteller.UI.Storyboard.Storyboard;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        progressBar = findViewById(R.id.progressBarLogin);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.textViewLogin).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check if user is already logged in

        //log in automatically
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, Storyboard.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, Signup.class));
                break;
            case R.id.buttonLogin:
                userLogin();
        }
    }

    private void userLogin() {
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        if(email.isEmpty()){
            this.email.setError("Email is required");
            this.email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.setError("Please enter a valid email");
            this.email.requestFocus();
            return;
        }


        if(password.isEmpty()){
            this.password.setError("Password is requered");
            this.password.requestFocus();
            return;
        }

        if(password.length() < 6){
            this.password.setError("Minimum length of password is 6");
            this.password.requestFocus();
            return;
        }

        //progress bar
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();   //so you don't go to login screen when pressing a back button
                    Intent intent = new Intent(Login.this, Storyboard.class);
                    //clear all activities on top of the stack so user can't go back to the login screen using back button
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
