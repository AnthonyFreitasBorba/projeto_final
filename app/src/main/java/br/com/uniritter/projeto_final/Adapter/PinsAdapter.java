package br.com.uniritter.projeto_final.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.uniritter.projeto_final.Model.Device;
import br.com.uniritter.projeto_final.Model.Pins;
import br.com.uniritter.projeto_final.R;
import br.com.uniritter.projeto_final.View.DeviceView;

public class PinsAdapter extends RecyclerView.Adapter<PinsViewHolder> {

    private Device device;
    private Context context;
    private ArrayList<Pins> pinos;

    private DeviceView deviceView;


    public PinsAdapter(Context context, Device device, DeviceView deviceView) {
        this.context = context;
        this.device = device;
        this.pinos = device.getPin_and_state();
        this.deviceView = deviceView;
    }

    @NonNull
    @Override
    public PinsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pin_states, parent, false);
        PinsViewHolder viewHolder =  new PinsViewHolder(view, deviceView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PinsViewHolder holder, int position) {
        Pins pino = pinos.get(position);
        holder.textViewPino.setText(pino.getPin_id());
        holder.textViewPinoEstado.setText(pino.getState());
        holder.textViewDevice.setText(device.getDevice_model());

        holder.setDevice(device);
        holder.setDeviceView(deviceView);
        holder.setPin_id(pino.getPin_id());
        if (pino.getState().equals("HIGH")){
            holder.switchState.setChecked(true);
        }else if(pino.getState().equals("LOW")){
            holder.switchState.setChecked(false);
        }
    }


    @Override
    public int getItemCount() {
        return pinos.size();
    }
}
