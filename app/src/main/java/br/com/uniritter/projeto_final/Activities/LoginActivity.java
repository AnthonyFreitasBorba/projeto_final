package br.com.uniritter.projeto_final.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import br.com.uniritter.projeto_final.Controller.UserController;
import br.com.uniritter.projeto_final.Interface.UserControllerListener;
import br.com.uniritter.projeto_final.Model.User;
import br.com.uniritter.projeto_final.R;
import br.com.uniritter.projeto_final.Util.ConfDb;

public class LoginActivity extends AppCompatActivity implements UserControllerListener {

    UserController userController;
    Button botao_faz_login;

    EditText email;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button botao_cadastre = findViewById(R.id.botao_cadastre);
        botao_cadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CadastroActivity.class);
                startActivity(i);
            }
        });
        Init();

        botao_faz_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userController.validaAuth(view,email.getText().toString(), senha.getText().toString());
            }
        });
    }

    public void openHome() {
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void doLogoff() {
        //Não precisa implementar
    }

    private void Init() {

        botao_faz_login = findViewById(R.id.botao_faz_login);
        userController = new UserController(getApplicationContext(), this);
        email = findViewById(R.id.editTextEmailLogin);
        senha = findViewById(R.id.editTextSenhaLogin);
    }


}