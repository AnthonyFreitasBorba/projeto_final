package br.com.uniritter.projeto_final.View;

import br.com.uniritter.projeto_final.Activities.HomeActivity;
import br.com.uniritter.projeto_final.Controller.DeviceController;
import br.com.uniritter.projeto_final.Model.Device;

public class DeviceView {
    private HomeActivity homeActivity;
    private DeviceController deviceController;

    public DeviceView(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    public void onDeviceReceived(Device device, DeviceController deviceController) {


        if(this.deviceController == null)
        {
            this.deviceController = deviceController;
            homeActivity.updateDevice(device, this);
        }

    }
    public void onSwitchStateChanged(String pin_id, boolean isChecked, Device device) {
        // Lógica para lidar com a mudança de estado da chave Switch
        deviceController.handlePinStateChange(pin_id, isChecked, device);
    }
}
