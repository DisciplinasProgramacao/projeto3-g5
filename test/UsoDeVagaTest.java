import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class UsoDeVagaTest {

    private Vaga vaga;
    private UsoDeVaga usoDeVaga;

    @Before
    public void setUp() {
        vaga = new Vaga();
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 18, 10, 0);
        usoDeVaga = new UsoDeVaga(vaga, entrada);
    }

    @Test
    public void testSairSemTempoDePermanencia() {
        // Não deve haver cobrança se a entrada e saída forem nulas
        assertEquals(0.0, usoDeVaga.sair(), 0.01);
    }

    @Test
    public void testCalcularValorComPermanenciaMenorQueUmaHora() {
        LocalDateTime saida = LocalDateTime.of(2023, 10, 18, 10, 30);
        usoDeVaga.setSaida(saida);
        double valorEsperado = 1.0; // 30 minutos de permanência, 1 hora de cobrança
        assertEquals(valorEsperado, usoDeVaga.sair(), 0.01);
    }

    @Test
    public void testCalcularValorComPermanenciaMaiorQueUmaHora() {
        LocalDateTime saida = LocalDateTime.of(2023, 10, 18, 12, 30);
        usoDeVaga.setSaida(saida);
        double valorEsperado = 10.0; // 2 horas e 30 minutos de permanência, 10 horas de cobrança
        assertEquals(valorEsperado, usoDeVaga.sair(), 0.01);
    }

    @Test
    public void testValorMaximoDeCobranca() {
        LocalDateTime saida = LocalDateTime.of(2023, 10, 18, 22, 0);
        usoDeVaga.setSaida(saida);
        double valorEsperado = 50.0; // 12 horas de permanência, atingindo o valor máximo de cobrança
        assertEquals(valorEsperado, usoDeVaga.sair(), 0.01);
    }

    @Test
    public void testCalcularValorComPermanenciaExcedendoUmDia() {
        LocalDateTime entrada = LocalDateTime.of(2023, 10, 18, 10, 0);
        LocalDateTime saida = LocalDateTime.of(2023, 10, 19, 12, 0);
        usoDeVaga.setEntrada(entrada);
        usoDeVaga.setSaida(saida);
        double valorEsperado = 50.0; // 26 horas de permanência, atingindo o valor máximo de cobrança
        assertEquals(valorEsperado, usoDeVaga.sair(), 0.01);
    }
}
