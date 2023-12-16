import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Relatorio implements Observer, Serializable {

    private List<Cliente> clientesTop5;
    private Map<Integer, Double> arrecadadoNoMesPorCliente;

    public Relatorio() {
        this.clientesTop5 = new ArrayList<>();
        this.arrecadadoNoMesPorCliente = new HashMap<>();
    }

    // ... Outros membros e construtores ...

    @Override
    public void notificarMudanca(Cliente cliente, UsoDeVaga uso) {
        int mes = uso.getSaida().getMonthValue();

        // Atualiza arrecadação por cliente no mês
        atualizarArrecadacaoNoMes(cliente, uso, mes);

        // Atualiza Top 5 de clientes
        atualizarClientesTop5(cliente, mes);
    }

    private void atualizarArrecadacaoNoMes(Cliente cliente, UsoDeVaga uso, int mes) {
          if (!arrecadadoNoMesPorCliente.containsKey(mes)) {
                arrecadadoNoMesPorCliente.put(mes, 0.0);
            }
        arrecadadoNoMesPorCliente.put(mes, arrecadadoNoMesPorCliente.get(mes) + uso.valorPago());
    }

    private void atualizarClientesTop5(Cliente cliente, int mes) {
        clientesTop5.add(cliente);
        clientesTop5.sort((c1, c2) -> (int) (c2.arrecadadoNoMes(mes) - c1.arrecadadoNoMes(mes)));

        if (clientesTop5.size() > 5) {
            clientesTop5.remove(0);
        }
    }

    public String getClientesTop5() {
        return this.clientesTop5.stream().map(Cliente::getNome).collect(Collectors.joining(" "));
    }

    public double getArrecadadoNoMes(int mes) {
        return arrecadadoNoMesPorCliente.getOrDefault(mes, 0.0);
    }
}
