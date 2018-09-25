package udacity.storyteller.UI.Signup;

import android.app.ActivityOptions;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import udacity.storyteller.R;
import udacity.storyteller.UI.Login.Login;
import udacity.storyteller.UI.Storyboard.Storyboard;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private EditText passwordRepeat;
    
    private ProgressBar progressBar;
    
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        email = findViewById(R.id.editTextEmailSignup);
        password = findViewById(R.id.editTextPasswordSignup);
        passwordRepeat = findViewById(R.id.editTextPasswordSignupRepeat);
        progressBar = findViewById(R.id.progressBarSignup);
        
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonSignup).setOnClickListener(this);
    }
    

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
            case R.id.buttonSignup:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String passwordRepeat = this.passwordRepeat.getText().toString().trim();

        if(!password.equals(passwordRepeat)){
            this.password.setError("Passwords don't match!");
            this.password.requestFocus();
            return;
        }

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
            this.password.setError("Password is required");
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

        //check if registration was successful or not using task object
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), R.string.registration_succes, Toast.LENGTH_LONG).show();
                    //if signup is successful, send the user to the home screen
                    finish();

                    //animation
                    Intent intent = new Intent(Signup.this, Storyboard.class);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(Signup.this).toBundle();
                        startActivity(intent, bundle);
                    } else {
                        startActivity(intent);
                    }
                    //startActivity(new Intent(Signup.this, Storyboard.class));
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), R.string.email_exists, Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}
