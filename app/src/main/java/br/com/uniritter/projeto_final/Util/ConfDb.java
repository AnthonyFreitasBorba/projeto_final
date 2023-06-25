package br.com.uniritter.projeto_final.Util;

import com.google.firebase.auth.FirebaseAuth;

public class ConfDb {
    private static FirebaseAuth auth;

    public static FirebaseAuth FirebaseAuthentication ()
    {
        if(auth == null)
        {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
