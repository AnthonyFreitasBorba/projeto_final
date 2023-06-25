#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

const char* ssid = "SEU_SSID";        					// Nome da sua rede Wi-Fi
const char* password = "SUA_SENHA";   					// Senha da sua rede Wi-Fi
const char* url = "SEU_LOCAL_HOST";       				// URL da AP REST que retorna os dados JSON

HTTPClient http;

void generateJsonObject(String MAC_ESP)
{
  DynamicJsonDocument jsonDoc(1024*10);

  jsonDoc["id"] = MAC_ESP;
  jsonDoc["device"] = "DOIT ESP32 DEV KIT V1";
  
  JsonObject outputPinState = jsonDoc.createNestedObject("outputPinState");
  outputPinState["D2"] = "LOW";
  outputPinState["D4"] = "LOW";
  outputPinState["D5"] = "LOW";
  outputPinState["D18"] = "LOW";
  outputPinState["D19"] = "LOW";
  outputPinState["D21"] = "LOW";
  outputPinState["D22"] = "LOW";
  outputPinState["D23"] = "LOW";
  outputPinState["D13"] = "LOW";
  outputPinState["D12"] = "LOW";
  outputPinState["D14"] = "LOW";
  outputPinState["D27"] = "LOW";
  outputPinState["D26"] = "LOW";
  outputPinState["D25"] = "LOW";
  outputPinState["D33"] = "LOW";
  outputPinState["D32"] = "LOW";
  outputPinState["D35"] = "LOW";
  outputPinState["D34"] = "LOW";

  String jsonString;
  serializeJson(jsonDoc, jsonString);
  Serial.println("Gerei o json");
  Serial.println(jsonString);
  perfotmHttpRequestPost(jsonString);
}
void perfotmHttpRequestPost(String jsonString)
{
  Serial.println("Fazendo o post");
  if (http.begin(url)) 
  {
    // Envia a requisição PUT com o JSON
    http.addHeader("Content-Type", "application/json");
    int httpCode = http.PUT(jsonString);
    if (httpCode == HTTP_CODE_OK) 
    {
      String response = http.getString();
      Serial.println("Resposta da API: " + response);
    } 
    else 
    {
      Serial.printf("Falha na requisição HTTP. Código de erro: %d\n", httpCode);
    }

    http.end();
  } 
  else
  {
    Serial.println("Falha ao se conectar à API.");
  }
}
void perfotmHttpRequestPut(String jsonString)
{
  Serial.println("Fazendo o put");

  // Inicia a conexão HTTP com a URL fornecida
  if (http.begin(url)) 
  {
    // Adiciona o cabeçalho "Content-Type" para indicar que estamos enviando um JSON
    http.addHeader("Content-Type", "application/json");

    // Envia a requisição PUT com o JSON fornecido
    int httpCode = http.PUT(jsonString);

    // Verifica o código de resposta HTTP
    if (httpCode == HTTP_CODE_OK) 
    {
      // Se a requisição foi bem-sucedida, obtém a resposta da API
      String response = http.getString();
      Serial.println("Resposta da API: " + response);
    } 
    else 
    {
      // Se a requisição falhou, exibe o código de erro
      Serial.printf("Falha na requisição HTTP. Código de erro: %d\n", httpCode);
    }

    // Fecha a conexão HTTP
    http.end();
  } 
  else
  {
    Serial.println("Falha ao se conectar à API.");
  }
}
void performHttpRequestGet() 
{
  http.begin(url);

  int httpCode = http.GET();
  if (httpCode == HTTP_CODE_OK) {
    DynamicJsonDocument doc(1024 * 10);
    String payload = http.getString();

    DeserializationError error = deserializeJson(doc, payload);
    if (error)
    {
      Serial.print("Falha ao deserializar o JSON: ");
      Serial.println(error.c_str());
      return;
    }

    if (doc.is<JsonArray>()) 
    {
      JsonArray jsonArray = doc.as<JsonArray>(); // Obter a matriz JSON

      // Iterar sobre cada objeto JSON na matriz
      for (const auto& json : jsonArray) 
      {
        if (json.is<JsonObject>()) 
        {
          JsonObject jsonObject = json.as<JsonObject>();

          // Processar cada objeto JSON individualmente
          processJsonObjectPinState(jsonObject);
        }
      }
    } 
    else if (doc.is<JsonObject>()) 
    {
      JsonObject jsonObject = doc.as<JsonObject>();

      // Processar objeto JSON individualmente
      processJsonObjectPinState(jsonObject);
    } 
    else 
    {
      Serial.println("O JSON não é uma matriz nem um objeto.");
    }
  } 
  else 
  {
    Serial.printf("A requisição HTTP falhou com o código de erro: %d\n", httpCode);
  }
  http.end();
}
void processJsonObjectPinState(const JsonObject& obj) 
{
  // Verificar se a chave "id" é igual a "3C:71:BF:F1:E8:24"
  if (obj.containsKey("id") && obj["id"].as<String>() == "3C:71:BF:F1:E8:24") 
  {
    // Iterar sobre as chaves e valores do objeto "outputPinState"
    if (obj.containsKey("outputPinState") && obj["outputPinState"].is<JsonObject>()) 
	{
      const JsonObject& pinStateObj = obj["outputPinState"].as<JsonObject>();
      for (const auto& pair : pinStateObj) 
	  {
        const String& pin = pair.key().c_str();
        const String& state = pair.value().as<String>();

        // Realizar as operações desejadas com o pino e o estado
        setPinState(pin, state);
      }
    }
  }
}
void setPinState(const String& pin, const String& state) 
{
  // Verificar se o pino começa com "D" e extrair o número do pino
  if (pin.startsWith("D")) 
  {
    int pinNumber = pin.substring(1).toInt();

    // Verificar o estado do pino e realizar a ação apropriada
    if (state == "LOW") 
	{
      digitalWrite(pinNumber, LOW);
    } else if (state == "HIGH") 
	{
      digitalWrite(pinNumber, HIGH);
    }
  }
}
void setup() 
{
  // Inicializa o pino D2 como saída
  Serial.begin(115200);
  pinMode(2, OUTPUT);
  pinMode(4,OUTPUT);
  pinMode(5,OUTPUT);
  pinMode(18,OUTPUT);
  pinMode(19,OUTPUT);
  pinMode(21,OUTPUT);
  pinMode(22,OUTPUT);
  pinMode(23,OUTPUT);
  pinMode(13,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(14,OUTPUT);
  pinMode(27,OUTPUT);
  pinMode(26,OUTPUT);
  pinMode(25,OUTPUT);
  pinMode(33,OUTPUT);
  pinMode(32,OUTPUT);
  pinMode(35,OUTPUT);
  pinMode(34,OUTPUT);

  //Serial.println(WiFi.scanNetworks());
  WiFi.begin(ssid,password);
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    Serial.println("Connecting to WiFi...");
  }
  /* debug fora da minha rede
    for(int j = 1; j <= 100; j++)
    {
      Serial.println("Escaneando.....");
      WiFi.scanNetworks();
    
      for(int i = 1; i <= 10; i ++)
      {
        Serial.println(WiFi.SSID(i));
        if(WiFi.SSID(i) == "Roberta ")
        {
          Serial.println("Conectando em Roberta...");
          WiFi.begin("Roberta ","31102806");
            while (WiFi.status() != WL_CONNECTED) 
            {
                delay(1000);
                Serial.println("Connecting to WiFi...");
                Serial.println("Wifi conectado");
                Serial.print("Tentativa: ");
                Serial.println(j);
            }
          i = 11;
          j = 101;
        }
      }
      delay(2);
    }
  */
  String MAC_ESP = WiFi.macAddress();
  generateJsonObject(MAC_ESP);
  Serial.println("#");  // Prompt indicando que está pronto para receber um comando
}
void loop() 
{
  // Seu código principal aqui
    if (Serial.available()) 
    {
    String command = Serial.readStringUntil('\n');
      if (command == "http_get")
      {
        performHttpRequestGet();
      }
      else if(command == "http_put")
      {
        generateJsonObjectDebug();
      }
      else if(command == "http_put2")
      {
        generateJsonObjectDebug2();
      }
    }
	performHttpRequestGet();
  delay(500);
}
void generateJsonObjectDebug()
{
  DynamicJsonDocument jsonDoc(1024);

  jsonDoc["id"] = "3C:71:BF:F1:E8:24";
  jsonDoc["device"] = "DOIT ESP32 DEV KIT V1";
  
  JsonObject outputPinState = jsonDoc.createNestedObject("outputPinState");
  outputPinState["D2"] = "HIGH";
  outputPinState["D4"] = "LOW";
  outputPinState["D5"] = "LOW";
  outputPinState["D18"] = "LOW";
  outputPinState["D19"] = "LOW";
  outputPinState["D21"] = "LOW";

  String jsonString;
  serializeJson(jsonDoc, jsonString);
  Serial.println("Gerei o json");
  Serial.println(jsonString);
  perfotmHttpRequestPut(jsonString);
}
void generateJsonObjectDebug2()
{
  DynamicJsonDocument jsonDoc(1024);

  jsonDoc["id"] = "3C:71:BF:F1:E8:24";
  jsonDoc["device"] = "DOIT ESP32 DEV KIT V1";
  
  JsonObject outputPinState = jsonDoc.createNestedObject("outputPinState");
  outputPinState["D2"] = "HIGH";
  outputPinState["D4"] = "LOW";
  outputPinState["D5"] = "LOW";
  outputPinState["D18"] = "LOW";
  outputPinState["D19"] = "LOW";
  outputPinState["D21"] = "LOW";

  String jsonString;
  serializeJson(jsonDoc, jsonString);
  Serial.println("Gerei o json");
  Serial.println(jsonString);
  perfotmHttpRequestPut(jsonString);
}
