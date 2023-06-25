package br.com.uniritter.projeto_final.Adapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.uniritter.projeto_final.Model.Device;
import br.com.uniritter.projeto_final.Model.Pins;
import br.com.uniritter.projeto_final.R;
import br.com.uniritter.projeto_final.View.DeviceView;

public class PinsViewHolder extends RecyclerView.ViewHolder {

    private DeviceView deviceView;
    private Device device;
    private String pin_id;
    TextView textViewPino, textViewPinoEstado, textViewDevice;
    Switch switchState;

    public PinsViewHolder(@NonNull View itemView, DeviceView deviceView) {
        super(itemView);
        textViewPino = itemView.findViewById(R.id.textViewPino);
        textViewPinoEstado = itemView.findViewById(R.id.textViewPinoEstado);
        textViewDevice = itemView.findViewById(R.id.textViewDevice);
        switchState = itemView.findViewById(R.id.switchState);

        switchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Notificar a classe DeviceView sobre a mudança de estado
                deviceView.onSwitchStateChanged(pin_id, isChecked, device);
            }
        });
    }
    public void setDeviceView(DeviceView deviceView) {
        this.deviceView = deviceView;
    }
    public void setDevice(Device device) {
        this.device = device;
    }
    public void setPin_id(String pin_id) {
        this.pin_id = pin_id;
    }
}
