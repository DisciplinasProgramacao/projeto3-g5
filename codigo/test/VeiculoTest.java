import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class VeiculoTest {

    @Test
    public void testEstacionar() {
        Veiculo veiculo = new Veiculo("ABC-1234");
          Vaga vaga = new Vaga("1", true);
        LocalDateTime entrada = LocalDateTime.now();

        veiculo.estacionar(vaga, entrada);
        assertEquals(1, veiculo.totalDeUsos());
    }

    @Test
    public void testSair() {
        Veiculo veiculo = new Veiculo("ABC-1234");
         Vaga vaga = new Vaga("1", true);
        LocalDateTime entrada = LocalDateTime.now();
        veiculo.estacionar(vaga, entrada);

        double valorPago = veiculo.sair(LocalDateTime.now().plusHours(1), TipoCliente.HORISTA);
        assertTrue(valorPago > 0);
    }

    @Test
    public void testTotalArrecadado() {
        Veiculo veiculo = new Veiculo("ABC-1234");
          Vaga vaga = new Vaga("1", true);
        LocalDateTime entrada = LocalDateTime.now();
        veiculo.estacionar(vaga, entrada);
        veiculo.sair(LocalDateTime.now().plusHours(1), TipoCliente.HORISTA);

        double total = veiculo.totalArrecadado();
        assertTrue(total > 0);
    }

    @Test
    public void testGerarRelatorio() {
        Veiculo veiculo = new Veiculo("ABC-1234");
        Vaga vaga = new Vaga("1", true);
        LocalDateTime entrada = LocalDateTime.now();
        veiculo.estacionar(vaga, entrada);
        veiculo.sair(LocalDateTime.now().plusHours(1), TipoCliente.HORISTA);

        String relatorio = veiculo.gerarRelatorio();
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("Entrada"));
        assertTrue(relatorio.contains("Saída"));
        assertTrue(relatorio.contains("Valor Pago"));
        assertTrue(relatorio.contains("Vaga"));
    }

    @Test
    public void testArrecadadoNoMes() {
        Veiculo veiculo = new Veiculo("ABC-1234");
       Vaga vaga = new Vaga("1", true);
        LocalDateTime entrada = LocalDateTime.now();
        veiculo.estacionar(vaga, entrada);
        veiculo.sair(LocalDateTime.now().plusHours(1), TipoCliente.HORISTA);

        double total = veiculo.arrecadadoNoMes(LocalDateTime.now().getMonthValue());
        assertTrue(total > 0);
    }

    @Test
    public void testTotalDeUsos() {
        Veiculo veiculo = new Veiculo("ABC-1234");
        Vaga vaga = new Vaga("1", true);
        LocalDateTime entrada = LocalDateTime.now();
        veiculo.estacionar(vaga, entrada);

        int totalUsos = veiculo.totalDeUsos();
        assertEquals(1, totalUsos);
    }

    @Test
    public void testGetPlaca() {
        Veiculo veiculo = new Veiculo("ABC-1234");
        assertEquals("ABC-1234", veiculo.getPlaca());
    }

    @Test
    public void testSetPlaca() {
        Veiculo veiculo = new Veiculo("ABC-1234");
        veiculo.setPlaca("XYZ-7890");
        assertEquals("XYZ-7890", veiculo.getPlaca());
    }

    @Test
    public void testGetUsos() {
        Veiculo veiculo = new Veiculo("ABC-1234");
        Vaga vaga = new Vaga("1", true);
        LocalDateTime entrada = LocalDateTime.now();
        veiculo.estacionar(vaga, entrada);

        List<UsoDeVaga> usos = veiculo.getUsos();
        assertNotNull(usos);
        assertEquals(1, usos.size());
    }

    @Test
    public void testSetUsos() {
        Veiculo veiculo = new Veiculo("ABC-1234");
        List<UsoDeVaga> usos = new ArrayList<>();
        usos.add(new UsoDeVaga(new Vaga("1", true), LocalDateTime.now()));

        veiculo.setUsos(usos);
        assertEquals(1, veiculo.getUsos().size());
    }
}
