import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EstacionamentoTest {

    private Estacionamento estacionamento;

    @Before
    public void setUp() {
        estacionamento = new Estacionamento("Meu Estacionamento", 5, 10);
        estacionamento.setVagas(new Vaga[estacionamento.getQuantFileiras() * estacionamento.getVagasPorFileira()]);
    }

    @Test
    public void testGerarVagas() {
        estacionamento.gerarVagas();
        // Verifica se o numero de vagas geradas eh o esperado
        assertEquals(estacionamento.getQuantFileiras() * estacionamento.getVagasPorFileira(), estacionamento.getVaga().length);
        // Verifica se as vagas estão inicialmente disponiveis
        for (Vaga vaga : estacionamento.getVaga()) {
            assertTrue(vaga.getDisponivel());
        }
    }
    @Test
    public void testEstacionar() {
        estacionamento.gerarVagas();
        Veiculo veiculo = new Veiculo("ABC123", 20);
        estacionamento.addCliente(new Cliente("Cliente1", "123"));
        estacionamento.addVeiculo(veiculo, "123");
        // Estaciona o veiculo
        estacionamento.estacionar("ABC123");
        // Verifica se a vaga associada ao veiculo esta ocupada
        for (Vaga vaga : estacionamento.getVaga()) {
            if (!vaga.getDisponivel()) {
                assertTrue(vaga.getId().equals(veiculo.getVaga().getId()));
            }
        }
    }

    @Test
    public void testSair() {
        estacionamento.gerarVagas();
        Veiculo veiculo = new Veiculo("XYZ789", 20);
        estacionamento.addCliente(new Cliente("Cliente2", "456"));
        estacionamento.addVeiculo(veiculo, "456");
        estacionamento.estacionar("XYZ789");

        // Veiculo sai do estacionamento, verificando o custo do estacionamento
        double custo = estacionamento.sair("XYZ789");
        // Suponha que o custo seja calculado corretamente de acordo com a lógica da classe Veiculo

        // Verifica se o custo nao e negativo
        assertTrue(custo >= 0);
    }
}
