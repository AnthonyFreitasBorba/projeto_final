package br.com.uniritter.projeto_final.Model;

public class Pins {
    private String pin_id;
    private String state;

    public Pins(String pin_id, String state) {
        this.pin_id = pin_id;
        this.state = state;
    }

    public String getPin_id() {
        return pin_id;
    }

    public void setPin_id(String pin_id) {
        this.pin_id = pin_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
