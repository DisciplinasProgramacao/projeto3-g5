import java.time.LocalDateTime;

public class UsoDeVaga {

	private static final double FRACAO_USO = 0.25;
	private static final double VALOR_FRACAO = 4.0;
	private static final double VALOR_MAXIMO = 50.0;
	private Vaga vaga;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private double valorPago;

	public UsoDeVaga(Vaga vaga) {
		this.vaga = vaga;
		
	}
	//getter para vaga
	public Vaga getVaga() {
		return vaga;
	}

	//sett vaga
	public void setVaga(Vaga vaga){
		this.vaga = vaga;
	}

	//getter entrada
	public LocalDateTime getEntrada(){
		return entrada;
	}

	//sett entrada
	public void setEntrada(LocalDateTime entrada){
		this.entrada = entrada;
	}

	//get saida
	public LocalDateTime getSaida(){
		return saida;
	}

	//sett saida
	public void setSaida(LocalDateTime saida){
		this.saida = saida;
	}

	//gett valorPago
	public double getvalorPago(){
		return valorPago;
	}

	//set valorPago
	public void setvalorPago(double valorPago){
		this.valorPago = valorPago;
	}
	 

	public double sair() {
		
	}

	public double valorPago() {
		
	}

}
