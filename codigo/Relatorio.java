import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

public class Relatorio implements Observer {

    private List<Cliente> clientesTop5;
    private Map<Integer, Double> arrecadadoNoMesPorCliente;

    public Relatorio() {
        this.clientesTop5 = new ArrayList<>();
        this.arrecadadoNoMesPorCliente = new HashMap<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Cliente) {
            Cliente cliente = (Cliente) o;
            UsoDeVaga uso = (UsoDeVaga) arg;
            int mes = uso.getSaida().getMonthValue();
            //Cliente cliente = uso.getCliente();
            if (!arrecadadoNoMesPorCliente.containsKey(mes)) {
                arrecadadoNoMesPorCliente.put(mes, 0.0);
            }
            arrecadadoNoMesPorCliente.put(mes, arrecadadoNoMesPorCliente.get(mes) + uso.valorPago());

            // Atualiza o Top5 de clientes com maior arrecadação no mês atual
            this.clientesTop5.add(cliente);
            this.clientesTop5.sort((c1, c2) -> (int) (c2.arrecadadoNoMes(mes) - c1.arrecadadoNoMes(mes)));
            for (Cliente cliente2 : clientesTop5) {
                System.err.println(cliente2.arrecadadoNoMes(mes));
            }
            
            if (this.clientesTop5.size() > 5) {
                this.clientesTop5.remove(0);
            }
            
        }
    }

    public List<Cliente> getClientesTop5() {
        return this.clientesTop5;
    }

    public double getArrecadadoNoMes(int mes) {
        return arrecadadoNoMesPorCliente.getOrDefault(mes, 0.0);
    }
}
