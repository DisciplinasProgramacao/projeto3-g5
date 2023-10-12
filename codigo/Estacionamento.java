import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Estacionamento {

	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		//
	}

	public void setNome(String nome){
		this.nome =nome;
	}
	
	public String getNome(){
		return this.nome;
	}

	public void setId(Cliente[] id){
		this.id = id;
	}

	public Cliente[] getId(){
		return this.id;
	}

	public void setVaga(Vaga[] vaga){
		this.vagas = vaga;
	}

	public Vaga[] getVaga(){
		return this.vagas;
	}

	public void setQuantFileiras(int quantFileiras){
		this.quantFileiras = quantFileiras;
	}

	public int getQuantFileiras(){
		return this.quantFileiras;
	}

	public void setVagasPorFileira(int vagasPorFileira){
		this.vagasPorFileira = vagasPorFileira;
	}

	public int getVagasPorFileira(){
		return this.vagasPorFileira;
	}
	public void addVeiculo(Veiculo veiculo, String idCli) {
		
	}

	public void addCliente(Cliente cliente) {

	}

	private void gerarVagas() {
		
	}

	public void estacionar(String placa) {
		
	}

	public double sair(String placa) {
		
	}

	public double totalArrecadado() throws IOException{
		double sum = 0;
		BufferedReader buffRead = new BufferedReader(new FileReader("path"));
		String linha = "";
		while (true) {
			if (linha != null) {
				String col[] = linha.split(";");
				if(col[3] != "Valor"){
					sum += Integer.parseInt(col[3]);
				}
			} else
				break;
			linha = buffRead.readLine();
		}
		buffRead.close();
		return sum;
	}

	public double arrecadacaoNoMes(int mes, registros) {
		double arrecadacao = 0;
        for (int i = 0; i< registros.length; i++) {
            if (registro[i].mes == mes) {
                arrecadacao += registro[i].tarifa;
            }
        }
        return arrecadacao;
    }

	public double valorMedioPorUso() throws IOException{
		double sum = 0;
		int count = 0;
		BufferedReader buffRead = new BufferedReader(new FileReader("path"));
		String linha = "";
		while (true) {
			if (linha != null) {
				String col[] = linha.split(";");
				if(col[3] != "Valor"){
					sum += Integer.parseInt(col[3]);
					count++;
				}
			} else
				break;
			linha = buffRead.readLine();
		}
		buffRead.close();
		return sum/count;
	}

	public String top5Clientes(int mes) {
		Map<String, Double> despesasMensais = new HashMap<>();
        for (Map.Entry<String, Double> entry : despesasPorCliente.entrySet()) {
            String chave = entry.getKey();
            double despesa = entry.getValue();
            String[] partes = chave.split("-");
            int mesRegistro = Integer.parseInt(partes[1]);
            if (mesRegistro == mes) {
                if (despesasMensais.containsKey(partes[0])) {
                    double despesaAtual = despesasMensais.get(partes[0]);
                    despesasMensais.put(partes[0], despesaAtual + despesa);
                } else {
                    despesasMensais.put(partes[0], despesa);
                }
            }
        }

        List<String> topClientes = new ArrayList<>(despesasMensais.keySet());

        topClientes.sort((cliente1, cliente2) -> Double.compare(despesasMensais.get(cliente2), despesasMensais.get(cliente1));

        if (topClientes.size() > 5) {
            topClientes = topClientes.subList(0, 5);
        }

        return topClientes;
	}

}
