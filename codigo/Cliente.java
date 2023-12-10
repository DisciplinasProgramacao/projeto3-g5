import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

// Enumeração que define os tipos de cliente
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

// Classe que representa um cliente
public class Cliente extends   Observable implements Serializable {

    private String nome;
    private String id;
    private List<Veiculo>  veiculos =new ArrayList<>();
    private int mensalista;
    private LocalDate dateMensalista;
    private TipoCliente tipoCliente;

    // Construtor da classe Cliente
    public Cliente(String nome, String id, TipoCliente tipoCliente, LocalDate dateMensalista) {
        this.nome = nome;
        this.id = id;
        this.dateMensalista = dateMensalista;
        this.tipoCliente = tipoCliente;
    }

    // Método para adicionar um veículo ao cliente
    public void addVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser null.");
        }
    
        if (veiculos.contains(veiculo)) {
            throw new IllegalArgumentException("O veículo já existe na lista.");
        }
    
        veiculos.add(veiculo);
    }

    // Método para verificar se o cliente possui um veículo com uma determinada placa
    public Veiculo possuiVeiculo(String placa) {
        return this.veiculos.stream().
        filter(veiculo -> veiculo != null && veiculo.getPlaca().equals(placa))
        .findFirst().orElse(null);

    }

    // Método para calcular o total de usos de veículos pelo cliente
    public int totalDeUsos() {
        return (int) veiculos.stream().
        filter(veiculo -> veiculo != null).
        mapToInt(veiculo -> veiculo.totalDeUsos()).
        sum();
    }

    // Método para calcular a arrecadação total do cliente
    public double arrecadadoTotal() {
        double totalArrecadado = 0;
       totalArrecadado= veiculos.stream().filter(veiculo -> veiculo != null).mapToDouble(veiculo -> veiculo.totalArrecadado()).sum();     
    if (this.dateMensalista != null) {
        LocalDate hoje = LocalDate.now();
        long meses = Period.between(this.dateMensalista, hoje).getMonths();
        totalArrecadado += meses * this.tipoCliente.getValor();
    }

        return totalArrecadado;
    }

    // Método para calcular a arrecadação do cliente em um determinado mês
    public double arrecadadoNoMes(int mes) {
        double arrecadadoNoMes = 0;
        arrecadadoNoMes = veiculos.stream().filter(veiculo -> veiculo != null).
        mapToDouble(veiculo -> veiculo.
        arrecadadoNoMes(mes)).
        sum()
        + (this.dateMensalista != null && mes >= this.dateMensalista.getMonthValue() ? this.tipoCliente.getValor() : 0);
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
    public List<Veiculo> getVeiculos() {
        return veiculos;
    }


    // Getter para o tipo de cliente
    public TipoCliente getTipoCliente() {
        return this.tipoCliente;
    }

    // Setter para o tipo de cliente
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    // Getter para a data do mensalista
    public LocalDate getDateMensalista() {
        return this.dateMensalista;
    }

    // Setter para a data do mensalista
    public void setDateMensalista(LocalDate dateMensalista) {
        this.dateMensalista = dateMensalista;
    }
}
