import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class clienteTest {

    @Test
    public void testAddVeiculo() {
        // Crie um objeto Cliente
        Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());

        // Crie um objeto Veiculo
        Veiculo veiculo = new Veiculo("ABC-1234"); 
        // Adicione o veículo ao cliente
        cliente.addVeiculo(veiculo);

        // Verifique se o veículo foi adicionado corretamente
        assertEquals(veiculo, cliente.possuiVeiculo(veiculo.getPlaca()));
    }

    @Test
    public void testTotalDeUsos() {
        

        Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
        Vaga vaga = new Vaga("1", true); 
          
        LocalDateTime saida = LocalDateTime.now().plusHours(1);

        for (int i = 0; i < 5; i++) {
            Veiculo veiculo = new Veiculo("ABC-1234"); 
            List<UsoDeVaga> usos = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                usos.add(new UsoDeVaga(vaga, saida)); 
            }
            veiculo.setUsos(usos);
            cliente.addVeiculo(veiculo);
    }
}

    @Test
    public void testArrecadadoTotal() {
        Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());

        double totalArrecadado = 0.0;
    
        for (int i = 0; i < 5; i++) {
            Veiculo veiculo = new Veiculo("ABC-1234"); 
            LocalDateTime entrada = LocalDateTime.now().minusHours(i);
            LocalDateTime saida = LocalDateTime.now();
            veiculo.estacionar(new Vaga("1", true), entrada);
            totalArrecadado += veiculo.sair(saida, TipoCliente.HORISTA);
            cliente.addVeiculo(veiculo);
        }
    
        // Verifique se a arrecadação total é correta
        assertEquals(totalArrecadado, cliente.arrecadadoTotal(), 0.001);
    }

    @Test
    public void testArrecadadoNoMes() {
        // Crie um objeto Cliente
        Cliente cliente = new Cliente("João", "123", TipoCliente.MENSALISTA, LocalDate.now());

        double totalArrecadado = 0.0;
    
        for (int i = 0; i < 5; i++) {
            Veiculo veiculo = new Veiculo("ABC-1234"); 
            LocalDateTime entrada = LocalDateTime.now().minusHours(i);
            LocalDateTime saida = LocalDateTime.now();
            veiculo.estacionar(new Vaga("1", true), entrada);
            totalArrecadado += veiculo.sair(saida, TipoCliente.HORISTA);
            cliente.addVeiculo(veiculo);
        }
    
        // Verifique se a arrecadação no mês atual é correta
        assertEquals(totalArrecadado, cliente.arrecadadoNoMes(LocalDate.now().getMonthValue()), 0.001);
    }
}
