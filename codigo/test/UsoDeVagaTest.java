import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
public class UsoDeVagaTest {

    @Test
    public void testSair() {
        // Crie um objeto Vaga
        Vaga vaga = new Vaga("1", true); 

        // Crie um objeto LocalDateTime para entrada e saída
        LocalDateTime entrada = LocalDateTime.now();
        LocalDateTime saida = LocalDateTime.now().plusHours(1);

        // Crie um objeto UsoDeVaga
        UsoDeVaga usoDeVaga = new UsoDeVaga(vaga, entrada);

        // Defina a saída
        usoDeVaga.setSaida(saida);

        // Crie um objeto TipoCliente
        TipoCliente tipoCliente = TipoCliente.HORISTA; 
        // Verifique o valor a ser pago para um cliente horista
        double valorPago = usoDeVaga.sair(tipoCliente);
        assertTrue(valorPago > 0);

        // Verifique o valor a ser pago para um cliente mensalista (deve ser 0)
        tipoCliente = TipoCliente.MENSALISTA; 
        valorPago = usoDeVaga.sair(tipoCliente);
        assertEquals(0, valorPago, 0.001);
    }

    @Test
    public void testValorPago() {
        // Crie um objeto UsoDeVaga
        UsoDeVaga usoDeVaga = new UsoDeVaga(new Vaga("1", true), LocalDateTime.now());

        // Defina o valor pago
        usoDeVaga.setValorPago(50.0);

        // Verifique se o valor pago é correto
        assertEquals(50.0, usoDeVaga.valorPago(), 0.001);
    }
}
