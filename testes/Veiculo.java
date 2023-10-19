import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VeiculoTest {
    private Veiculo veiculo;
    private Vaga vaga1;
    private Vaga vaga2;

    @Before
    public void setUp() {
        veiculo = new Veiculo("ABC123", 2);
        vaga1 = new Vaga();
        vaga2 = new Vaga();
    }

    @Test
    public void testEstacionar() {
        veiculo.estacionar(vaga1);
        assertTrue(vaga1.isOcupada());
        veiculo.estacionar(vaga2);
        assertTrue(vaga2.isOcupada());
    }

    @Test
    public void testSair() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        double totalPago = veiculo.sair();
        assertTrue(vaga1.isOcupada() == false);
        assertTrue(vaga2.isOcupada() == false);
        assertEquals(0.0, totalPago, 0.001);
    }

    @Test
    public void testTotalArrecadado() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        veiculo.sair();
        double totalArrecadado = veiculo.totalArrecadado();
        assertEquals(20.0, totalArrecadado, 0.001); // Supondo que o valor pago seja 10 para cada vaga
    }

    @Test
    public void testArrecadadoNoMes() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        veiculo.sair();
        double totalArrecadadoNoMes = veiculo.arrecadadoNoMes(10); // Supondo que o mês seja 10
        assertEquals(20.0, totalArrecadadoNoMes, 0.001); // Supondo que o valor pago seja 10 para cada vaga
    }

    @Test
    public void testTotalDeUsos() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        int totalUsos = veiculo.totalDeUsos();
        assertEquals(2, totalUsos);
    }
    public static void main(){
        setUp();
    }
}
