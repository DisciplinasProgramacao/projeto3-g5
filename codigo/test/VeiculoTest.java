import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VeiculoTest {

    private Veiculo veiculo;
    private Vaga vaga1;
    private Vaga vaga2;

    @Before
    public void setUp() {
        // Inicialização de objetos antes de cada teste
        veiculo = new Veiculo("ABC123", 5);
        vaga1 = new Vaga("A1");
        vaga2 = new Vaga("B2");
    }

    @Test
    public void testEstacionar() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        // Verifique se o veículo foi estacionado corretamente nas vagas
        assertEquals(2, veiculo.totalDeUsos());
    }

    @Test
    public void testSair() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        double totalPago = veiculo.sair();
        // Verifique se o valor total pago é igual à soma das taxas das vagas
        assertEquals(vaga1.getTaxa() + vaga2.getTaxa(), totalPago, 0.01);
    }

    @Test
    public void testTotalArrecadado() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        veiculo.sair();
        // Verifique se o total arrecadado é igual à soma das taxas das vagas
        assertEquals(vaga1.getTaxa() + vaga2.getTaxa(), veiculo.totalArrecadado(), 0.01);
    }

    @Test
    public void testArrecadadoNoMes() {
        veiculo.estacionar(vaga1);
        veiculo.estacionar(vaga2);
        veiculo.sair();
        // Verifique se o valor arrecadado no mês 10 é igual à soma das taxas das vagas
        assertEquals(vaga1.getTaxa() + vaga2.getTaxa(), veiculo.arrecadadoNoMes(10), 0.01);
    }
}
