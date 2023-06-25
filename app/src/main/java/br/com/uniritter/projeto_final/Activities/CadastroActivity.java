package br.com.uniritter.projeto_final.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.uniritter.projeto_final.Controller.UserController;
import br.com.uniritter.projeto_final.Interface.UserControllerListener;
import br.com.uniritter.projeto_final.R;

public class CadastroActivity extends AppCompatActivity implements UserControllerListener{


    UserController userController;
    Button botao_cadastrar;

    EditText nome;
    EditText email;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button botao_avanca_login = findViewById(R.id.botao_avanca_login);
        botao_avanca_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        Init();
        Button botao_cadastrar = findViewById(R.id.botao_cadastrar);
        botao_cadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userController.validaCampos(view, nome.getText().toString(),
                email.getText().toString(), senha.getText().toString());
            }
        });
    }

    private void Init () {

        userController = new UserController(getApplicationContext(),this);
        nome = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmailCadastro);
        senha = findViewById(R.id.editTextSenhaCadastro);
    }

    @Override
    public void openHome() {
        // Não precisa implementar
    }

    @Override
    public void doLogoff() {
        // Não precisa implementar
    }
}