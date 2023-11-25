import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;

enum TipoCliente {
    TURNO_MANHA(LocalTime.of(8, 0), LocalTime.of(12, 0), 200),
    TURNO_TARDE(LocalTime.of(12, 1), LocalTime.of(18, 0), 200),
    TURNO_NOITE(LocalTime.of(18, 1), LocalTime.of(23, 59), 200),
    MENSALISTA(null, null, 500), // Insira os horários apropriados para mensalistas
    HORISTA(null, null, 0); // Insira os horários apropriados para horistas

    private final LocalTime horaInicio;
    private final LocalTime horaFim;
    private final int valor;

    TipoCliente(LocalTime horaInicio, LocalTime horaFim, int valor) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.valor = valor;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public int getValor() {
        return valor;
    }
}

public class Cliente implements Serializable {

    private String nome;
    private String id;
    private Veiculo[] veiculos = new Veiculo[10];
    private int mensalista;
    private LocalDate dateMensalista;
    private TipoCliente tipoCliente;

    public Cliente(String nome, String id, TipoCliente tipoCliente, LocalDate dateMensalista) {
        this.nome = nome;
        this.id = id;
        this.dateMensalista = dateMensalista;
        this.tipoCliente = tipoCliente;
    }

    public void escreverArquivo(String estacionamento) {
        try {
            FileWriter fileWriter = new FileWriter("cliente.txt", true);
            fileWriter.write(estacionamento + "," + this.nome + "," + this.id + "," + this.tipoCliente + ","
                    + this.dateMensalista + ";");

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
        int count = -1;
        LocalDate momentoAtual = LocalDate.now();
        if (this.dateMensalista != null) {
            long mes = 0;
            mes = Period.between(momentoAtual, this.dateMensalista).getMonths();
            do {
                totalArrecadado += this.tipoCliente.getValor();
                count++;
            } while (count < mes);
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

        if (this.dateMensalista != null && mes >= this.dateMensalista.getMonthValue()) {
            arrecadadoNoMes += this.tipoCliente.getValor();
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

    public TipoCliente getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public LocalDate getDateMensalista() {
        return this.dateMensalista;
    }

    public void setDateMensalista(LocalDate dateMensalista) {
        this.dateMensalista = dateMensalista;
    }

}