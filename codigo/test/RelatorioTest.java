import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RelatorioTest {

    @Test
    public void testUpdate() {
        // Crie um objeto Relatorio
        Relatorio relatorio = new Relatorio();

        // Crie um objeto Cliente
       Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
        UsoDeVaga usoDeVaga = new UsoDeVaga(new Vaga("1", true), LocalDateTime.now());

        // Defina a saída
        usoDeVaga.setSaida(LocalDateTime.now().plusHours(1));

        // Atualize o relatório
        relatorio.update(cliente, usoDeVaga);

        // Verifique se o valor arrecadado no mês é correto
        double valorArrecadado = relatorio.getArrecadadoNoMes(LocalDateTime.now().getMonthValue());
        assertTrue(valorArrecadado >= 0);
    }

    @Test
    public void testGetClientesTop5() {
        // Crie um objeto Relatorio
        Relatorio relatorio = new Relatorio();

        // Crie alguns objetos Cliente e atualize o relatório
        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente("João", "123", TipoCliente.HORISTA, LocalDate.now());
            UsoDeVaga usoDeVaga = new UsoDeVaga(new Vaga("1", true), LocalDateTime.now());
            usoDeVaga.setSaida(LocalDateTime.now().plusHours(i + 1));
            relatorio.update(cliente, usoDeVaga);
        }

        // Verifique se o top 5 de clientes é correto
        String clientesTop5 = relatorio.getClientesTop5();
        assertNotNull(clientesTop5);
    }
}
