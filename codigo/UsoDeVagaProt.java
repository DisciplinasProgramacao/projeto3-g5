import java.time.LocalDateTime;

public class UsoDeVagaProt implements UsoDeVagaPrototype {
    private Vaga vaga;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorPago;
    private int horaMinima;

    // Construtor privado para evitar a criação direta do protótipo
    private UsoDeVagaProt(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, int horaMinima) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = saida;
        this.valorPago = valorPago;
        this.horaMinima = horaMinima;
    }

    // Método de fábrica para criar instâncias do protótipo
    public static UsoDeVagaProt criarPrototipo(Vaga vaga, LocalDateTime entrada) {
        // Inicialize com valores padrão ou com base em regras de inicialização
        return new UsoDeVagaProt(vaga, entrada, null, 0.0, 0);
    }

    // Método de clonagem
    @Override
    public UsoDeVaga clonar() {
        // Clone o objeto atual
        UsoDeVaga clone = new UsoDeVaga(vaga, entrada);

        clone.setSaida(this.saida);
        clone.setvalorPago(this.valorPago);
        clone.setHoraMinima(this.horaMinima);

        return clone;
    }

    
}
