import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;

public class Veiculo {

    private String placa;
    private UsoDeVaga[] usos;

    public Veiculo(String placa, int maxUsos) {
        this.placa = placa;
        this.usos = new UsoDeVaga[maxUsos];
    }

    public void estacionar(Vaga vaga) {
        for (int i = 0; i < usos.length; i++) {
            if (usos[i] == null) {
                if (vaga.disponivel()) {
                    usos[i] = new UsoDeVaga(vaga);
                    vaga.estacionar();
                    break;
                }
            }
        }
    }

    public void escreverArquivo(String cliente, String estacionamento) {
        try {
            FileWriter fileWriter = new FileWriter("veiculo.txt", true);
            fileWriter.write(cliente + "," + this.placa + ";");

            for (UsoDeVaga usoDeVaga: usos) {
                usoDeVaga.escreverArquivo(this.placa, estacionamento);
            }




            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double sair() {
        double totalPago = 0.0;
        for (int i = 0; i < usos.length; i++) {
            if (usos[i] != null && usos[i].getSaida() != null) {
                usos[i].sair();
                totalPago += usos[i].valorPago();
                usos[i] = null;
            }
        }
        return totalPago;
    }

    public double totalArrecadado() {
        double totalArrecadado = 0.0;
        for (UsoDeVaga uso: usos) {
            if (uso != null && uso.getSaida() != null) {
                totalArrecadado += uso.valorPago();
            }
        }
        return totalArrecadado;
    }

    public double arrecadadoNoMes(int mes) {
        double totalArrecadadoNoMes = 0.0;
        for (UsoDeVaga uso: usos) {
            if (uso != null) {
                LocalDateTime data = uso.getSaida();
                int mesData = data.getMonthValue();

                if (data != null && mesData == mes) {
                    totalArrecadadoNoMes += uso.valorPago();
                }
            }
        }
        return totalArrecadadoNoMes;
    }

    public int totalDeUsos() {
        int totalUsos = 0;
        for (UsoDeVaga uso: usos) {
            if (uso != null) {
                totalUsos++;
            }
        }
        return totalUsos;
    }

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
