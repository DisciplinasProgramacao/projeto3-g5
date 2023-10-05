public class Veiculo {

	private String placa;
	private UsoDeVaga[] usos;

	public Veiculo(String placa, UsoDeVaga[] usos) {
		this.placa = placa;
		this.usos = usos;
	}

	public void estacionar(Vaga vaga) {
		
	}

	public double sair() {
		
	}

	public double totalArrecadado() {
		
	}

	public double arrecadadoNoMes(int mes) {
		
	}

	public int totalDeUsos() {
		
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public UsoDeVaga[] getUsos() {
		return this.usos;
	}

	public void setUsos(UsoDeVaga[] usos) {
		this.usos = usos;
	}
}
