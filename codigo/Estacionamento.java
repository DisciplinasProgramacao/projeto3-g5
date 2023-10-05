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
		return this.nome
	}

	public void setId(Cliente[] id){
		this.id = id;
	}

	public Cliente[] getId(){
		return this.id;
	}

	public void setVaga(Vaga[] vaga){
		this.vaga = vaga;
	}

	public Vaga[] getVaga(){
		return this.vaga;
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
		return this.setvagasPorFileira;
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

	public double totalArrecadado() {
		
	}

	public double arrecadacaoNoMes(int mes) {
		
	}

	public double valorMedioPorUso() {
		
	}

	public String top5Clientes(int mes) {
		
	}

}
