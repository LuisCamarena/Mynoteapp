package practica1.aplicacion.com.mynoteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class login extends AppCompatActivity {

    // SharedPreferences
    private SharedPreferences sharedPreferences;

    private EditText usernameInput;
    private EditText passwordInput;
    private ProgressBar progressBar;
    private View loginPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText)findViewById(R.id.username);
        passwordInput = (EditText)findViewById(R.id.password);
        loginPanel = findViewById(R.id.login_panel);

        // init SharedPreferences
        sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // username remember
        String username = sharedPreferences.getString("username", null);
        if(username != null){
            usernameInput.setText(username);
            passwordInput.requestFocus();
        }

        // islogged remember
        if(sharedPreferences.getBoolean("islogged", false)){
            // Go to Dashboard
            goDashboard();
        }

    }

    public void callLogin(View view){
        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "You must complete these fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login logic
        User user = UserBL.login(username, password);

        if(user == null){
            Toast.makeText(this, "Username or password invalid", Toast.LENGTH_SHORT).show();
            loginPanel.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        Toast.makeText(this, user.getUsername(), Toast.LENGTH_SHORT).show();

        // Save to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean success = editor
                .putString("username", user.getUsername())
                .putBoolean("islogged", true)
                .commit();

        // Go to Dashboard
        goDashboard();
    }

    private void goDashboard(){
        startActivity(new Intent(this, User.class));
        finish();
    }

}
