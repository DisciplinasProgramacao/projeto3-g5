import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

enum Servico {
    manobrista(5, 0),
    lavagem(20, 1),
    polimento(25, 2);

    private final int horaMinima;
    private final int valor;

    Servico(int valor, int horaMinima) {
        this.horaMinima = horaMinima;
        this.valor = valor;
    }

    public int getHoraMinima() {
        return horaMinima;
    }

    public int getValor() {
        return valor;
    }
}

// Classe que representa o uso de uma vaga de estacionamento
public class UsoDeVaga implements Serializable {


    // Constantes para cálculos de valor
    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;
    private List<Servico> servico = new ArrayList<>();

    // Atributos da classe
    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;


    // Construtor para inicializar um objeto UsoDeVaga com entrada
    public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
        this.vaga = vaga;
        this.entrada = entrada;
    }

    // Construtor para inicializar um objeto UsoDeVaga com entrada, saída e valor pago
    public UsoDeVaga(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = saida;
        this.valorPago = valorPago;
    }

    // Getter para a vaga
    public Vaga getVaga() {
        return vaga;
    }

    // Método para escrever informações sobre o uso da vaga em um arquivo
    public void escreverArquivo(String placa, String estacionamento) {
        try {
            FileWriter fileWriter = new FileWriter("usoDeVaga.txt", true);
            fileWriter.write(placa + "," + this.entrada + "," + this.saida + "," + this.valorPago + ","
                    + this.vaga.getId() + ";");
            this.vaga.escreverArquivo(estacionamento);

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Setter para a vaga
    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public List<Servico> getServico() {
		return this.servico;
	}

    public void setServico(Servico servico) {
        this.servico.add(servico);
        this.valorPago += servico.getValor();
    }

    public double getValorPago() {
        return this.valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
    

    // Getter para a entrada
    public LocalDateTime getEntrada() {
        return entrada;
    }

    // Setter para a entrada
    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    // Getter para a saída
    public LocalDateTime getSaida() {
        return saida;
    }

    // Setter para a saída
    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    // Getter para o valor pago
    public double getvalorPago() {
        return valorPago;
    }

    // Setter para o valor pago
    public void setvalorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    // Método para calcular o valor a ser pago ao sair
    public double sair(TipoCliente tipoCliente) {

        if (entrada != null && saida != null) {
            // Calcula a diferença entre a entrada e a saída com base no tipo de cliente
            switch (tipoCliente) {
                case HORISTA:
                    return calcValue(entrada.toLocalTime(), saida.toLocalTime());
                case TURNO_MANHA:
                case TURNO_TARDE:
                case TURNO_NOITE:
                    return calcDiferenceValue(entrada.toLocalTime(), saida.toLocalTime(), tipoCliente.getHoraInicio(), tipoCliente.getHoraFim());
                case MENSALISTA:
                    return 0.0;
                default:
                    return 0.0;
            }
        }

        return 0;
    }

    // Método para obter o valor pago
    public double valorPago() {
        return valorPago;
    }

    // Método para calcular o valor com base no tempo de uso
    private double calcValue(LocalTime entrada, LocalTime saida) {
        int minutosEstacionados = 0;
        while (entrada.isBefore(saida)) {
            minutosEstacionados++;
            entrada = entrada.plusMinutes(1);
        }
        // Calcula o valor pela taxa de fração de uso
        double valor = minutosEstacionados * FRACAO_USO * VALOR_FRACAO;

        // Limita o valor ao valor máximo permitido
        valorPago +=  Math.min(valor, VALOR_MAXIMO);
        return valorPago;
    }

    // Método para calcular o valor com base na diferença de horários
    private double calcDiferenceValue(LocalTime entrada, LocalTime saida, LocalTime horaInicio, LocalTime horaFim) {
        LocalTime inicio = LocalTime.now();
        LocalTime fim = LocalTime.now();
        if (entrada.isAfter(horaFim)) {
            inicio = entrada;
            fim = saida;
        } else if (saida.isAfter(horaFim)) {
            inicio = horaFim;
            fim = saida;
        }
        if (saida.isBefore(horaInicio)) {
            inicio = entrada;
            fim = saida;
        } else if (entrada.isBefore(horaInicio)) {
            inicio = entrada;
            fim = horaInicio;
        }
        return calcValue(inicio, fim);
    }
    private UsoDeVagaPrototype prototipo;

    // Construtor usando o protótipo
    public UsoDeVaga(UsoDeVagaPrototype prototipo) {
        this.prototipo = prototipo;
    }

    // Método para clonar o protótipo e obter uma nova instância de UsoDeVaga
    public UsoDeVaga clonar() {
        return prototipo.clonar();
    }

    public void setHoraMinima(int horaMinima) {
    }

}
