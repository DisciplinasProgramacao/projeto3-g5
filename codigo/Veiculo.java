import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;

public class Veiculo implements Serializable  {

    private String placa;
    private UsoDeVaga[] usos;

    public Veiculo(String placa, int maxUsos) {
        this.placa = placa;
        this.usos = new UsoDeVaga[maxUsos];
    }

    public void estacionar(Vaga vaga, LocalDateTime entrada) {
         if (this.usos != null) {
        for (int i = 0; i < this.usos.length; i++) {
            if (this.usos[i] == null) {
                if (vaga.getDisponivel()) {
                    
                    this.usos[i] = new UsoDeVaga(vaga, entrada);
                    vaga.estacionar();
                    break;
                }
            }
        }
        }
        else{
           
            this.usos[0]=new UsoDeVaga(vaga, entrada);
            
        }
       // System.out.println(this.usos[0].getVaga());

    }

    public void escreverArquivo(String cliente, String estacionamento) {
        try {
            FileWriter fileWriter = new FileWriter("veiculo.txt", true);
            fileWriter.write(cliente + "," + this.placa + ";");
            //System.out.println("escrito um veiculo");
            for (UsoDeVaga usoDeVaga : usos) {
                if(usoDeVaga!=null)
                usoDeVaga.escreverArquivo(this.placa, estacionamento);
            }

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double sair(LocalDateTime time, TipoCliente tipoCliente) {
        double totalPago = 0.0;
        for (int i = 0; i < usos.length; i++) {
            if (usos[i] != null && usos[i].getSaida() == null) {     
                //System.out.println(usos[i].getEntrada());
                if(usos[i].getEntrada() == null){
                    System.out.println("Não é possível sair com veiculo não estacionado");
                    break;
                }
                usos[i].setSaida(time);           
                usos[i].sair(tipoCliente);
                totalPago += usos[i].valorPago();
                return totalPago;
            }else{
                System.out.println("Não é possível sair com veiculo não estacionado");
                break;
            }
        }
        return totalPago;
    }

    public double totalArrecadado() {
        double totalArrecadado = 0.0;
        for (UsoDeVaga uso : usos) {
            if (uso != null && uso.getSaida() != null) {
                totalArrecadado += uso.valorPago();
            }
        }
        return totalArrecadado;
    }

    public double arrecadadoNoMes(int mes) {
        double totalArrecadadoNoMes = 0.0;
        for (UsoDeVaga uso : usos) {
            if (uso != null && uso.getSaida() != null) {
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
        for (UsoDeVaga uso : usos) {
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
