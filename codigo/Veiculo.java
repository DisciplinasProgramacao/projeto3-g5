import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Classe que representa um Veículo no estacionamento
public class Veiculo implements Serializable {

    private String placa; // Identificação única do veículo (placa)
    private List<UsoDeVaga> usos = new ArrayList<>(); // Array para armazenar os usos da vaga associados ao veículo

    // Construtor para criar um Veículo com uma placa e um número máximo de usos
    public Veiculo(String placa) {
        this.placa = placa;
        // this.usos = new UsoDeVaga[maxUsos];
    }

    // Método para estacionar o veículo em uma vaga com um horário de entrada
    public void estacionar(Vaga vaga, LocalDateTime entrada) {
        if (vaga.getDisponivel()) {
            if(usos.stream().filter(u->u.getEntrada()!=null && u.getSaida()==null).findFirst().orElse(null)!=null){
                System.out.println("veiculo ja estacionado");
                return;
            }

            UsoDeVaga uso = new UsoDeVaga(vaga, entrada);
            this.usos.add(uso);
        }
    }

    // Método para realizar a saída do veículo em um determinado horário
    public double sair(LocalDateTime time, TipoCliente tipoCliente) {
        UsoDeVaga uso = this.usos.stream()
                .filter(u -> u.getEntrada() != null && u.getSaida() == null)
                .findFirst()
                .orElse(null);

        if (uso != null) {
            uso.setSaida(time);
            return uso.sair(tipoCliente);
        } else {
            System.out.println("Não é possível sair com veículo não estacionado");
            return 0.0;
        }
    }

    // Método para calcular o total arrecadado pelo veículo
    public double totalArrecadado() {
        return usos.stream().mapToDouble(u -> u.valorPago()).sum();
    }

    public String gerarRelatorio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.usos.stream().map(filteredU -> {
            return "Entrada: " + filteredU.getEntrada().format(formatter) +
                    ", Saída: "
                    + (filteredU.getSaida() != null ? filteredU.getSaida().format(formatter)
                            : "ainda estacionado")
                    +
                    ", Valor Pago: R$" + filteredU.valorPago() +
                    ", Vaga: " + filteredU.getVaga().getId() + "\n";
        }).collect(Collectors.joining(",", "{", "}"));
    }

    // Método para calcular o total arrecadado pelo veículo em um determinado mês
    public double arrecadadoNoMes(int mes) {
        double total = this.usos.stream()
                .filter(u -> u.getSaida().getMonthValue() == mes)
                .mapToDouble(u -> u.valorPago())
                .sum();

        return total;
    }

    // Método para obter o total de usos associados ao veículo
    public int totalDeUsos() {
        return this.usos.size();
    }

    // Métodos getters e setters para acessar e modificar os atributos da classe

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public List<UsoDeVaga> getUsos() {
        return this.usos;
    }

    public void setUsos(List<UsoDeVaga> usos) {
        this.usos = usos;
    }
}
