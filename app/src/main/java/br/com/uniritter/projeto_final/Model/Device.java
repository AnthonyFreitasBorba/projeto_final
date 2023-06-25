package br.com.uniritter.projeto_final.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Device {
    private String id;
    private String device_model;
    private ArrayList<Pins> pin_and_state;

    public Device() {
        pin_and_state = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public ArrayList<Pins> getPin_and_state() {
        return pin_and_state;
    }

    public void setPin_and_state(ArrayList<Pins> pin_and_state) {
        this.pin_and_state = pin_and_state;
    }

    public String getPinStateById(String pinId) {
        for (Pins pin : pin_and_state) {
            if (pin.getPin_id().equals(pinId)) {
                return pin.getState();
            }
        }
        return "Pino nao existe ou estado nao encontrado";
    }

    public String getPinById(String pinId) {
        for (Pins pin : pin_and_state) {
            if (pin.getPin_id().equals(pinId)) {
                return pin.getPin_id();
            }
        }
        return "Pino nao existe ou estado nao encontrado";
    }

    public void setPinStateById(String pinId, String newState) {
        for (Pins pin : pin_and_state) {
            if (pin.getPin_id().equals(pinId)) {
                pin.setState(newState);
                return;
            }
        }
        throw new IllegalArgumentException("Pino não encontrado: " + pinId);
    }

    public String toJsonString() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("device", device_model);

            JSONObject outputPinState = new JSONObject();
            for (Pins pin : pin_and_state) {
                outputPinState.put(pin.getPin_id(), pin.getState());
            }
            jsonObject.put("outputPinState", outputPinState);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
