//Programa: Renovar - Modulo de Comunicação - ESP8266 NodeMCU
#include <ESP8266WiFi.h> // biblioteca para usar as funções de Wifi do módulo ESP8266
#include <ArduinoJson.h>  // biblioteca JSON para sistemas embarcados


// Definições da rede Wifi
const char* SSID = "NOME_DA_REDE";
const char* PASSWORD = "SENHA_DA_REDE";


// endereço IP local do Servidor Web para onde serão enviados os dados
const char* rpiHost = "IP_DO_SERVIDOR";

WiFiClient client;

// construindo o objeto JSON que irá armazenar os dados do coletor na função populateJSON()
StaticJsonBuffer<300> jsonBuffer;
JsonObject& object = jsonBuffer.createObject();

void initWiFi()
{
  delay(10);
  Serial.print("Conectando-se na rede: ");
  Serial.println(SSID);
  Serial.println("Aguarde");

  reconnectWiFi();
}

/*
   função que conecta o NodeMCU na rede Wifi
   SSID e PASSWORD devem ser indicados nas variáveis
*/
void reconnectWiFi()
{
  if (WiFi.status() == WL_CONNECTED)
    return;

  WiFi.begin(SSID, PASSWORD);

  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.print(".");
  }

  Serial.println();
  Serial.print("Conectado com sucesso na rede: ");
  Serial.println(SSID);
  Serial.print("IP obtido: ");
  Serial.println(WiFi.localIP());
}

/*
   função que armazena cada dado do sensor em um objeto JSON
   utiliza a biblioteca ArduinoJson
*/
void popularJSON()
{
  object["dispositivoId"] = ID_DISPOSITIVO;
  object["indicadorId"] = ID_INDICADOR;
  object["medida"] = MEDIDA;
}

/*
   função que envia os dados do sensor para o servidor em formato JSON
   faz um POST request ao servidor
*/
void realizarPOST()
{
  if (!client.connect(rpiHost, 8080))    // aqui conectamos ao servidor
  {
    Serial.println("Não foi possível conectar ao servidor!n");
  }
  else
  {
    Serial.println("Conectado ao servidor");
    client.println("POST /coletas HTTP/1.1"); 
    client.println("Host: IP_DO_SERVIDOR");
    client.println("Content-Type: application/json");
    client.print("Content-Length: ");
    client.println(object.measureLength());
    client.println();
    object.printTo(client);    // Prenchendo o corpo da requisição, com os dados definidos no método popularJSON()
  }
}

void setup() {
  pinMode(13, OUTPUT);
  Serial.begin(115200);

  Serial.println("nIniciando configuração WiFin");
  initWiFi();

  Serial.println("nConfiguração finalizada, iniciando loopn");
}

void loop() {
  popularJSON();  // transforma os dados em formato JSON
  realizarPOST(); // envia os dados ao servidor
  delay(10000);
}
