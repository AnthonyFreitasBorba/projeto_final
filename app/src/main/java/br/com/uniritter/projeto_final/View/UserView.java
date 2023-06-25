package br.com.uniritter.projeto_final.View;

import android.content.Context;
import android.widget.Toast;

public class UserView {
    private Context context;

    public UserView(Context context) {
        this.context = context;
    }

    public void sendMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}