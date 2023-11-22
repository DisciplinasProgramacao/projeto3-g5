import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

public class Cliente {

    private String nome;
    private String id;
    private Veiculo[] veiculos = new Veiculo[10];
    private int mensalista;
    private LocalDate dateMensalista;

    public Cliente(String nome, String id, int mensalista, LocalDate dateMensalista) {
        this.nome = nome;
        this.id = id;
        this.mensalista = mensalista;
        this.dateMensalista = dateMensalista;
    }

    public void escreverArquivo(String estacionamento) {
        try {
            FileWriter fileWriter = new FileWriter("cliente.txt", true);
            fileWriter.write(estacionamento + "," + this.nome + "," + this.id + "," + this.mensalista + ","
                    + this.dateMensalista + ";");

            for (Veiculo veiculo : veiculos) {
                if (veiculo != null)
                    veiculo.escreverArquivo(this.nome, estacionamento);

            }

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addVeiculo(Veiculo veiculo) {
        // System.out.println(veiculos.length);

        if (veiculos != null) {
            for (int i = 0; i < veiculos.length; i++) {
                if (veiculos[i] == null) {

                    veiculos[i] = veiculo;
                    break;
                }
            }
        } else {

            veiculos[0] = veiculo;
        }

    }

    public Veiculo possuiVeiculo(String placa) {

        for (Veiculo veiculo : veiculos) {

            if (veiculo != null && veiculo.getPlaca().equals(placa)) {
                return veiculo;
            }
        }
        return null;
    }

    public int totalDeUsos() {
        int totalUsos = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                totalUsos += veiculo.totalDeUsos();
            }
        }
        return totalUsos;
    }

    public double arrecadadoPorVeiculo(String placa) {
        Veiculo veiculo = possuiVeiculo(placa);
        if (veiculo != null) {
            return veiculo.totalArrecadado();
        }
        return 0;
    }

    public double arrecadadoTotal() {
        double totalArrecadado = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                totalArrecadado += veiculo.totalArrecadado();
            }
        }
        if (this.mensalista > 0) {
            LocalDate momentoAtual = LocalDate.now();
            long mes = Period.between(momentoAtual, this.dateMensalista).getMonths();
            if (this.mensalista < 4) {
                totalArrecadado += (mes * 200);
            } else {
                totalArrecadado += (mes * 500);
            }
        }
        return totalArrecadado;
    }

    public double arrecadadoNoMes(int mes) {
        double arrecadadoNoMes = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                arrecadadoNoMes += veiculo.arrecadadoNoMes(mes);
            }
        }
        if (this.mensalista > 0 && mes >= this.dateMensalista.getMonthValue()) {
            if (this.mensalista < 4) {
                arrecadadoNoMes += 200;
            } else {
                arrecadadoNoMes += 500;
            }
        }

        return arrecadadoNoMes;
    }

    // Getter para o campo nome
    public String getNome() {
        return nome;
    }

    // Setter para o campo nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter para o campo id
    public String getId() {
        return id;
    }

    // Setter para o campo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para o campo veiculos
    public Veiculo[] getVeiculos() {
        return veiculos;
    }

    // Setter para o campo veiculos
    public void setVeiculos(Veiculo[] veiculos) {
        this.veiculos = veiculos;
    }

    public int getMensalista() {
        return this.mensalista;
    }

    public void setMensalista(int mensalista) {
        this.mensalista = mensalista;
    }

    public LocalDate getDateMensalista() {
        return this.dateMensalista;
    }

    public void setDateMensalista(LocalDate dateMensalista) {
        this.dateMensalista = dateMensalista;
    }

}