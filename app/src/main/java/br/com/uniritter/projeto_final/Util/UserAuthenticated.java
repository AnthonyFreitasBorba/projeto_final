package br.com.uniritter.projeto_final.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuthenticated {

    public static FirebaseUser userOnLogin(){
        FirebaseAuth user = ConfDb.FirebaseAuthentication();
        return user.getCurrentUser();
    }
}
