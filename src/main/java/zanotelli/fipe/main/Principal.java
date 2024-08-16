package zanotelli.fipe.main;

import zanotelli.fipe.model.Dados;
import zanotelli.fipe.model.ModeloCarro;
import zanotelli.fipe.model.Modelos;
import zanotelli.fipe.service.DataConverter;
import zanotelli.fipe.service.GetRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    Scanner scanner = new Scanner(System.in);
    final String ENDERECO_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void Main() {
        System.out.println("****************\n");
        System.out.println("Escolha o tipo de veículo que está buscando");
        System.out.println("Carro\nMoto\nCaminhão\n");
        System.out.println("****************");

        String escolha = scanner.nextLine();

        String busca;
        if(escolha.toLowerCase().contains("carr")) {
            busca = ENDERECO_BASE+"carros/marcas/";
        } else if(escolha.toLowerCase().contains("moto")) {
            busca = ENDERECO_BASE+"motos/marcas/";
        } else {
            busca = ENDERECO_BASE+"caminhoes/marcas/";
        }


        var json = GetRequest.Request(busca);

        var marcas= DataConverter.listConvert(json, Dados.class);
        marcas.forEach(m -> System.out.println("Marca: " + m.nome() + ", Código: ["+m.codigo()+"]"));

        System.out.println("\nDigite o código da marca que deseja buscar: ");
        escolha = scanner.nextLine();
        busca = busca + escolha +"/modelos/";
        json = GetRequest.Request(busca);

        var modelosLista = DataConverter.dataConvert(json,Modelos.class);
        modelosLista.modelos()
                .forEach(m -> System.out.println("Modelo: " + m.nome() + ", Código: ["+m.codigo()+"]"));

        System.out.println("\nDigite o código do modelo que você deseja: ");
        escolha = scanner.nextLine();
        busca = busca + escolha +"/anos/";
        json = GetRequest.Request(busca);

        var modelosCarros = DataConverter.listConvert(json, Dados.class);
        List<String> CodigoModelos = modelosCarros.stream()
                .map(Dados::codigo).toList();
        List<ModeloCarro> carrosModelos = new ArrayList<>();
        for(String c : CodigoModelos) {
            json = GetRequest.Request(busca +c);
            var carros = DataConverter.dataConvert(json, ModeloCarro.class);
            carrosModelos.add(carros);
        }
        carrosModelos.forEach(c -> System.out.println(
                "Modelo: " + c.Modelo() +
                ", Marca: " + c.Marca() + ", Valor: " + c.Valor() + ", Combustivel: " + c.Combustivel() +
                        ", Ano: " + c.AnoModelo()
        ));
    }
}
