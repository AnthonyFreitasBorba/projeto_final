package br.com.uniritter.projeto_final.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import br.com.uniritter.projeto_final.Adapter.PinsAdapter;
import br.com.uniritter.projeto_final.Controller.DeviceController;
import br.com.uniritter.projeto_final.Controller.UserController;
import br.com.uniritter.projeto_final.Interface.UserControllerListener;
import br.com.uniritter.projeto_final.Model.Device;
import br.com.uniritter.projeto_final.Model.Pins;
import br.com.uniritter.projeto_final.R;
import br.com.uniritter.projeto_final.Util.ConfDb;
import br.com.uniritter.projeto_final.View.DeviceView;

public class HomeActivity extends AppCompatActivity implements UserControllerListener {

    //loop
    private Handler handler;
    private Runnable runnable;
    private static final long INTERVALO_ATUALIZACAO = 500; // Intervalo de atualização em milissegundos
    //fim de loop
    Button botao_faz_logoff;
    Button botao_update;
    UserController userController;
    private DeviceController deviceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Init();
        botao_faz_logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userController.doLogoff();
            }
        });
        deviceController = new DeviceController(getApplicationContext());
        DeviceView deviceView = new DeviceView(HomeActivity.this);
// Usar botao de atualizar ou o loop
//        botao_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = "http://192.168.0.86:3000/db";
//                deviceController.fetchDataFromJson(url, deviceView);
//            }
//        });
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.0.86:3000/db";
                deviceController.fetchDataFromJson(url, deviceView);
                handler.postDelayed(this, INTERVALO_ATUALIZACAO); // Executa o Runnable novamente após o intervalo definido
            }
        };
        // Iniciar a atualização automática chamando o Runnable pela primeira vez
        handler.post(runnable);
    }

    public void updateDevice(Device device, DeviceView deviceView) {
        runRecycler(device, deviceView);
    }

    private void runRecycler(Device device, DeviceView deviceView) {
        RecyclerView recycler;
        PinsAdapter adapter;
        recycler = findViewById(R.id.recyclerViewHome);

        adapter = new PinsAdapter(getApplicationContext(),device, deviceView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
    }

    private void Init() {
        userController = new UserController(getApplicationContext(), this);
        botao_faz_logoff = findViewById(R.id.botao_faz_logoff);
        botao_update = findViewById(R.id.botao_update);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void openHome() {
        //Não necessário
    }

    public void doLogoff()
    {
        finish();
    }
}