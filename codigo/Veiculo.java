import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;

// Classe que representa um Veículo no estacionamento
public class Veiculo implements Serializable {

    private String placa;       // Identificação única do veículo (placa)
    private UsoDeVaga[] usos;   // Array para armazenar os usos da vaga associados ao veículo

    // Construtor para criar um Veículo com uma placa e um número máximo de usos
    public Veiculo(String placa, int maxUsos) {
        this.placa = placa;
        this.usos = new UsoDeVaga[maxUsos];
    }

    // Método para estacionar o veículo em uma vaga com um horário de entrada
    public void estacionar(Vaga vaga, LocalDateTime entrada) {
        if (this.usos != null) {
            for (int i = 0; i < this.usos.length; i++) {
                if (this.usos[i] == null) {
                    if (vaga.getDisponivel()) {
                        // Cria um novo uso de vaga e o associa ao veículo e à vaga
                        this.usos[i] = new UsoDeVaga(vaga, entrada);
                        // Estaciona o veículo na vaga
                        vaga.estacionar();
                        break;
                    }
                }
            }
        } else {
            // Se o array de usos for nulo, cria um novo uso na posição 0
            this.usos[0] = new UsoDeVaga(vaga, entrada);
        }
    }

    // Método para escrever as informações do veículo em um arquivo
    public void escreverArquivo(String cliente, String estacionamento) {
        try {
            FileWriter fileWriter = new FileWriter("veiculo.txt", true);
            // Escreve as informações do cliente e a placa do veículo no arquivo
            fileWriter.write(cliente + "," + this.placa + ";");
            // Escreve as informações dos usos de vaga associados ao veículo no arquivo
            for (UsoDeVaga usoDeVaga : usos) {
                if (usoDeVaga != null)
                    usoDeVaga.escreverArquivo(this.placa, estacionamento);
            }
            // Fecha o FileWriter
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para realizar a saída do veículo em um determinado horário
    public double sair(LocalDateTime time, TipoCliente tipoCliente) {
        double totalPago = 0.0;
        for (int i = 0; i < usos.length; i++) {
            if (usos[i] != null && usos[i].getSaida() == null) {
                if (usos[i].getEntrada() == null) {
                    System.out.println("Não é possível sair com veículo não estacionado");
                    break;
                }
                // Configura a hora de saída e realiza as operações necessárias
                usos[i].setSaida(time);
                usos[i].sair(tipoCliente);
                // Calcula o valor total pago pelo uso da vaga
                totalPago += usos[i].valorPago();
                return totalPago;
            }
        }
        return totalPago;
    }

    // Método para calcular o total arrecadado pelo veículo
    public double totalArrecadado() {
        double totalArrecadado = 0.0;
        for (UsoDeVaga uso : usos) {
            if (uso != null && uso.getSaida() != null) {
                totalArrecadado += uso.valorPago();
            }
        }
        return totalArrecadado;
    }

    // Método para calcular o total arrecadado pelo veículo em um determinado mês
    public double arrecadadoNoMes(int mes) {
        double totalArrecadadoNoMes = 0.0;
        for (UsoDeVaga uso : usos) {
            if (uso != null && uso.getSaida() != null) {
                LocalDateTime data = uso.getSaida();
                int mesData = data.getMonthValue();
                // Verifica se o uso de vaga ocorreu no mês especificado
                if (data != null && mesData == mes) {
                    totalArrecadadoNoMes += uso.valorPago();
                }
            }
        }
        return totalArrecadadoNoMes;
    }

    // Método para obter o total de usos associados ao veículo
    public int totalDeUsos() {
        int totalUsos = 0;
        for (UsoDeVaga uso : usos) {
            if (uso != null) {
                totalUsos++;
            }
        }
        return totalUsos;
    }

    // Métodos getters e setters para acessar e modificar os atributos da classe

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public UsoDeVaga[] getUsos() {
        return this.usos;
    }

    public void setUsos(UsoDeVaga[] usos) {
        this.usos = usos;
    }
}
