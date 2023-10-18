import java.util.Random;

public class Estacionamento {

	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;
	private int contCli=0;
	// private int contVei=0;
	// private Veiculo[] veiculo;
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
	
		for (int i = 0;i<this.contCli;i++){
			if(this.id[this.contCli].equals(idCli)){
				this.id[this.contCli].addVeiculo(veiculo);
			} 
		}
		
	}

	public void addCliente(Cliente cliente) {

	this.id[this.contCli]=cliente;
	this.contCli++;
	}

	private void gerarVagas() {
		int index =  this.quantFileiras * this.vagasPorFileira;
		//Vaga[] vagas = new Vaga[index];
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int count = 0;
		for(int i = 0; i< this.quantFileiras; i++){
			for(int j= 0; j < this.vagasPorFileira; j++){
				String id = String.valueOf(characters.charAt(i));
				if(j < 10){
					id += "0";
				}
				id += String.valueOf(j);

				this.vagas[count].setId(id);
				this.vagas[count].setDisponivel(true);
				count++;
			}
		}
	}

	public void estacionar(String placa) {
		int count = 0;
		for(int i = 0; i< this.quantFileiras; i++){
			for(int j = 0; j< this.vagasPorFileira; j++){
				if(this.vagas[count].disponivel()){
					i=this.quantFileiras;
					j=this.vagasPorFileira;
					for(int k=0;k<this.contCli;k++){
						if(this.id[k].possuiVeiculo(placa) != null){
						this.id[k].possuiVeiculo(placa).estacionar(this.vagas[count]);	
						}
					}
					// Veiculo veiculo =new Veiculo(placa);
					// this.veiculo[this.contVei]=veiculo;
					// this.contVei++;
					//veiculo.estacionar(this.vagas[count]);
					//this.vagas[count].estacionar();			
				}
				count++;
			}
		}

	}

	public double sair(String placa) {
		// for(int i=0;i<this.contVei;i++){
		// 	if(this.veiculo[i].getPlaca().equals(placa)){
		// 		return this.veiculo[i].sair();
		// 	}
		// }
		for(int k=0;k<this.contCli;k++){
						if(this.id[k].possuiVeiculo(placa) != null){
						return this.id[k].possuiVeiculo(placa).sair();
						}
					}
					

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
