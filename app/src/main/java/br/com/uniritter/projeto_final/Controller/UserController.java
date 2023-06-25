package br.com.uniritter.projeto_final.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.uniritter.projeto_final.Activities.CadastroActivity;
import br.com.uniritter.projeto_final.Activities.LoginActivity;
import br.com.uniritter.projeto_final.Interface.UserControllerListener;
import br.com.uniritter.projeto_final.Model.User;
import br.com.uniritter.projeto_final.Util.ConfDb;
import br.com.uniritter.projeto_final.View.UserView;

public class UserController {
    private Context context;
    private FirebaseAuth aut;
    private UserControllerListener listener;

    public UserController(Context context, UserControllerListener listener){

        this.context = context;
        this.listener = listener;
    }

    public void validaAuth(View v, String email_valida,String senha_valida) {
        UserView userView= new UserView(context);
        if(!email_valida.isEmpty())
        {
            if(!senha_valida.isEmpty())
            {
                User u1 = new User();
                u1.setSenha(senha_valida);
                u1.setEmail(email_valida);
                doLogin(u1);
            }
            else
            {
                userView.sendMessage("Preencha a senha");
            }
        }
        else
        {
            userView.sendMessage("Preencha o e-mail");
        }
    }

    private void doLogin(User u1) {
        UserView userView = new UserView(context);
        aut = ConfDb.FirebaseAuthentication();
        aut.signInWithEmailAndPassword(
                u1.getEmail(),
                u1.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.openHome();
                } else {
                    String exc = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        userView.sendMessage("Usuário não está cadastrado");
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        userView.sendMessage("Email ou senha incorreto");
                    } catch (Exception e) {
                        exc = "Erro ao realizar o login" + e.getMessage();
                        e.printStackTrace();
                    }
                    userView.sendMessage(exc);
                }
            }
        });
    }

    public void doLogoff() {
        UserView userView = new UserView(context);
        aut = ConfDb.FirebaseAuthentication();
        try {
            aut.signOut();
            listener.doLogoff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validaCampos(View v, String nome_valida, String email_valida, String senha_valida){
        UserView userView= new UserView(context);

        if (!nome_valida.isEmpty())
        {
            if(!email_valida.isEmpty())
            {
                if(!senha_valida.isEmpty())
                {
                    User u1 = new User();
                    u1.setName(nome_valida);
                    u1.setEmail(email_valida);
                    u1.setSenha(senha_valida);
                    cadastrarUsuario(u1);
                }
                else
                {
                    userView.sendMessage("Preencha a senha");
                }
            }
            else
            {
                userView.sendMessage("Preencha o email");
            }
        }
        else
        {
            userView.sendMessage("Preencha o nome");
        }
    }

    private void cadastrarUsuario(User u1){
        UserView userView= new UserView(context);
        aut = ConfDb.FirebaseAuthentication();

        aut.createUserWithEmailAndPassword(
                u1.getEmail(),
                u1.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    userView.sendMessage("Usuário cadastrado com sucesso!!!");
                }
                else
                {
                    String exc = "";
                    try
                    {
                        throw task.getException();
                    }
                    catch(FirebaseAuthWeakPasswordException e)
                    {
                        exc = "Digite uma senha forte";
                    }
                    catch(FirebaseAuthInvalidCredentialsException e)
                    {
                        exc = "Digite um e-mail válido";
                    }
                    catch(FirebaseAuthUserCollisionException e)
                    {
                        exc = "Esta conta já está cadastrada";
                    }
                    catch(Exception e)
                    {
                        exc = "Erro ao cadastrar usuário"+e.getMessage();
                    }
                    userView.sendMessage(exc);
                }
            }
        });
    }
}
