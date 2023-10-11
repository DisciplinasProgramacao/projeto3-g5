import java.util.Random;

public class Estacionamento {

	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.setNome(nome);
		this.setQuantFileiras(fileiras);
		this.setVagasPorFileira(vagasPorFila);
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

	public void setVaga(Vaga[] vagas){
		this.vagas = vagas;
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
		int index =  this.quantFileiras * this.vagasPorFileira;
		Vaga[] vagas = new Vaga[index];
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int count = 0;
		for(int i = 0; i< this.quantFileiras; i++){
			for(int j= 0; j < this.vagasPorFileira; j++){
				String id = String.valueOf(characters.charAt(i));
				if(j < 10){
					id += "0";
				}
				id += String.valueOf(j);
				vagas[count].setId(id);
				vagas[count].setDisponivel(true);
			}
		}
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
