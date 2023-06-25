package br.com.uniritter.projeto_final.Controller;
import android.content.Context;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.com.uniritter.projeto_final.Model.Device;
import br.com.uniritter.projeto_final.Model.Pins;
import br.com.uniritter.projeto_final.View.DeviceView;

public class DeviceController {
    private Context context;

    public DeviceController(Context context) {
        this.context = context;
    }

    public void fetchDataFromJson(String url, DeviceView deviceView) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("devices")) {
                        Object devicesData = response.get("devices");

                        if (devicesData instanceof JSONObject) {
                            // Processar objeto JSON
                            JSONObject devicesObject = (JSONObject) devicesData;
                            processDeviceData(devicesObject,deviceView);
                        } else if (devicesData instanceof JSONArray) {
                            // Processar matriz JSON
                            JSONArray devicesArray = (JSONArray) devicesData;
                            for (int i = 0; i < devicesArray.length(); i++) {
                                JSONObject deviceObject = devicesArray.getJSONObject(i);
                                processDeviceData(deviceObject,deviceView);
                            }
                        } else {
                            System.out.println("Resposta inválida para a chave 'devices'");
                        }
                    } else {
                        System.out.println("Chave 'devices' não encontrada na resposta");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void processDeviceData(JSONObject devicesObject, DeviceView deviceView) throws JSONException {
        String id = devicesObject.getString("id");
        String device = devicesObject.getString("device");

        System.out.println("ID: " + id);
        System.out.println("Device: " + device);

        Device jsonDevice = new Device();
        jsonDevice.setId(id);
        jsonDevice.setDevice_model(device);

        if (devicesObject.has("outputPinState")) {
            JSONObject outputPinStateObject = devicesObject.getJSONObject("outputPinState");

            System.out.println("Output Pin State:");

            Iterator<String> keys = outputPinStateObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = outputPinStateObject.getString(key);
                System.out.println(key + ": " + value);

                Pins pin = new Pins(key,value);
                jsonDevice.getPin_and_state().add(pin);
            }
        }
        deviceView.onDeviceReceived(jsonDevice, this);
    }

    public void handlePinStateChange(String pin_id, boolean isChecked, Device device) {
        String state;
        state = bolleanFormat(isChecked);

        if(!device.getPinStateById(pin_id).equals(state))
        {
            System.out.println(device.getPinById(pin_id));
            device.setPinStateById(pin_id,state);
            sendDataToJsonServer("http://192.168.0.86:3000/devices", device.toJsonString());
        }else{System.out.println("Não precisa alterar");}
    }

    public String bolleanFormat (boolean b) {
        if (b) {
            return "HIGH";
        } else if (!b) {
             return "LOW";
        }
        else{
            System.out.println("Valor da chave desconhecido");
        }
        return null;
    }

    public void sendDataToJsonServer(String url, String json) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Processar a resposta após o envio do JSON
                System.out.println("JSON enviado com sucesso via PUT");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Tratar erros na requisição
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() {
                return json.getBytes();  // Substitua 'json' pela sua string JSON
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

// Adicionar a requisição à fila de solicitações
        requestQueue.add(stringRequest);
    }

}