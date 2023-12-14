import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

public class EstacionamentoTest {

    @Test
    public void testAddCliente() {
        Estacionamento estacionamento = new Estacionamento("TestEstacionamento", 3, 5);
          Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
        estacionamento.addCliente(cliente);

        assertEquals(1, estacionamento.getId().size());
        assertEquals(cliente, estacionamento.getId().get(0));
    }

    @Test
    public void testAddVeiculo() {
        Estacionamento estacionamento = new Estacionamento("TestEstacionamento", 3, 5);
          Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
        estacionamento.addCliente(cliente);

           Veiculo veiculo = new Veiculo("ABC-1234");
        estacionamento.addVeiculo(veiculo, "1");

        assertEquals(1, cliente.getVeiculos().size());
        assertEquals(veiculo, cliente.getVeiculos().get(0));
    }

    @Test
    public void testEstacionar() {
         Estacionamento estacionamento = new Estacionamento("TestEstacionamento", 3, 5);
    Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
    estacionamento.addCliente(cliente);

    Veiculo veiculo = new Veiculo("ABC-1234");
    estacionamento.addVeiculo(veiculo, "1");

    estacionamento.estacionar("ABC-1234", LocalDateTime.now(), cliente);

    // Verifique se o veículo foi estacionado corretamente
    assertEquals(1, veiculo.getUsos().size());
    }

    @Test
    public void testSair() {
        Estacionamento estacionamento = new Estacionamento("TestEstacionamento", 3, 5);
        Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
        estacionamento.addCliente(cliente);
    
        Veiculo veiculo = new Veiculo("ABC-1234");
        estacionamento.addVeiculo(veiculo, "1");
    
        LocalDateTime entrada = LocalDateTime.now().minusHours(2);
        estacionamento.estacionar("ABC-1234", entrada, cliente);
    
        assertTrue(estacionamento.sair("ABC-1234", LocalDateTime.now()));
    
        // Verifique se o veículo saiu corretamente
        List<UsoDeVaga> usos = veiculo.getUsos();
        if (!usos.isEmpty()) {
            assertNotNull(usos.get(0).getSaida());
        }    }

    @Test
    public void testAdicionarServico() {
        Estacionamento estacionamento = new Estacionamento("TestEstacionamento", 3, 5);
        Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
        estacionamento.addCliente(cliente);
    
        Veiculo veiculo = new Veiculo("ABC12345");
        estacionamento.addVeiculo(veiculo, "1");
    
        LocalDateTime entrada = LocalDateTime.now().minusHours(2);
        estacionamento.estacionar("ABC12345", entrada, cliente);
    
        estacionamento.adicionarServico("1", 1, "ABC12345");
    
        List<UsoDeVaga> usos = veiculo.getUsos();
    
       
    
        // Verifique se o serviço foi adicionado corretamente
        if (!usos.isEmpty()) {
            assertEquals(Servico.manobrista, usos.get(0).getServico());
        }
    }

    @Test
    public void testTotalArrecadado() {
        Estacionamento estacionamento = new Estacionamento("TestEstacionamento", 3, 5);
        Cliente cliente1 = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
        Cliente cliente2 = new Cliente("Jose", "1234", TipoCliente.HORISTA, LocalDate.now());

        estacionamento.addCliente(cliente1);
        estacionamento.addCliente(cliente2);

        Veiculo veiculo1 = new Veiculo("ABC-1234");
        Veiculo veiculo2 = new Veiculo("ABC-123");

        estacionamento.addVeiculo(veiculo1, "1");
        estacionamento.addVeiculo(veiculo2, "2");

        estacionamento.estacionar("ABC-1234", LocalDateTime.now(), cliente1);
        estacionamento.estacionar("ABC-123", LocalDateTime.now(), cliente2);

        estacionamento.sair("ABC-1234", LocalDateTime.now().plusHours(1));
        estacionamento.sair("ABC-123", LocalDateTime.now().plusHours(2));

        assertEquals(veiculo1.totalArrecadado() + veiculo2.totalArrecadado(), estacionamento.totalArrecadado(), 0.01);
    }

    
}
