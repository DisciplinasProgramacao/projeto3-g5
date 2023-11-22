import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


enum CategoriaCliente {
    HORISTA, TURNO, MENSALISTA
}

public class Cliente {

    private String nome;
    private String id;
    private Veiculo[] veiculos = new Veiculo[10];
    private CategoriaCliente categoria;

    public Cliente(String nome, String id, CategoriaCliente categoria) {
        this.nome = nome;
        this.id = id;
        System.out.println("criado o cliente" + nome);

        this.categoria = categoria;
    }
    public Cliente(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void escreverArquivo(String estacionamento) {
        try {
            FileWriter fileWriter = new FileWriter("cliente.txt", true);
            fileWriter.write(estacionamento + "," + this.nome + "," + this.id + ";");

            for (Veiculo veiculo : veiculos) {
                if (veiculo != null)
                    veiculo.escreverArquivo(this.id, estacionamento);

            }

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addVeiculo(Veiculo veiculo) {

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
        return totalArrecadado;
    }

    public double arrecadadoNoMes(int mes) {
        double arrecadadoNoMes = 0;
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                arrecadadoNoMes += veiculo.arrecadadoNoMes(mes);
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

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        this.categoria = categoria;
    }
}