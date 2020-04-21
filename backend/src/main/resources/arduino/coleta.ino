//Programa: Acelerometro com ESP8266 NodeMCU
#include <ESP8266WiFi.h> // biblioteca para usar as funções de Wifi do módulo ESP8266
#include <Wire.h>         // biblioteca de comunicação I2C
#include <ArduinoJson.h>  // biblioteca JSON para sistemas embarcados

// construindo o objeto JSON que irá armazenar os dados do acelerômetro na função populateJSON()
StaticJsonBuffer<300> jsonBuffer;
JsonObject& object = jsonBuffer.createObject();
JsonObject& coordenada = object.createNestedObject("coordenada");

/*
   função que armazena cada dado do sensor em um objeto JSON
   utiliza a biblioteca ArduinoJson
*/
void populateJSON()
{
  object["dispositivoId"] = 1;
  object["indicadorId"] = 1;
  object["medida"] = 0.45;
  object["data"] = "13/06/2018 21:37:07";
  coordenada["latitude"] = -27;
  coordenada["longitude"] = -48;
  
}

void setup() {
  Serial.begin(115200);
  Serial.println("Configurando objeto JSON");
}

void loop() {
  populateJSON();  // transforma os dados em formato JSON
  Serial.println("");
  object.printTo(Serial);
  delay(5000);
}
