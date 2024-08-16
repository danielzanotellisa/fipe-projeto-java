package zanotelli.fipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModeloCarro(String Marca,
                          String Valor,
                          String Modelo,
                          Integer AnoModelo,
                          String Combustivel) {
}
