import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        Veiculo veiculo1 = new Veiculo("KDN4180");
        Veiculo veiculo2 = new Veiculo("KCS7508");
        cliente.addVeiculo(veiculo1);
        cliente.addVeiculo(veiculo2);
    }

    @Test
    public void testPossuiVeiculo() {
        Veiculo encontrado = cliente.possuiVeiculo("KDN4180");
        assertNotNull(encontrado);
        assertEquals("KDN4180", encontrado.getPlaca());
    }

    @Test
    public void testTotalDeUsos() {
        int totalUsos = cliente.totalDeUsos();
        assertEquals(10, totalUsos);
    }

    @Test
    public void testArrecadadoPorVeiculo() {
        double arrecadacaoKDN4180 = cliente.arrecadadoPorVeiculo("KDN4180");
        assertEquals(15.0, arrecadacaoKDN4180, 0.01);

        double arrecadacaoKCS7508 = cliente.arrecadadoPorVeiculo("KCS7508");
        assertEquals(35.0, arrecadacaoKCS7508, 0.01);
    }

    @Test
    public void testArrecadadoTotal() {
        double totalArrecadado = cliente.arrecadadoTotal();
        assertEquals(150.0, totalArrecadado, 0.01);
    }

    @Test
    public void testArrecadadoNoMes() {
        double arrecadadoMes1 = cliente.arrecadadoNoMes(1);
        assertEquals(50.0, arrecadadoMes1, 0.01);

        double arrecadadoMes2 = cliente.arrecadadoNoMes(2);
        assertEquals(30.0, arrecadadoMes2, 0.01);

        double arrecadadoMes3 = cliente.arrecadadoNoMes(3);
        assertEquals(0.0, arrecadadoMes3, 0.01);
    }
}
