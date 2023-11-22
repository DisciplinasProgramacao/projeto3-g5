import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
public class UsoDeVaga {

	private static final double FRACAO_USO = 0.25;
	private static final double VALOR_FRACAO = 4.0;
	private static final double VALOR_MAXIMO = 50.0;
	private Vaga vaga; 
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private double valorPago;

	public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
		this.vaga = vaga;
		this.entrada = entrada;
		
	}
	public UsoDeVaga(Vaga vaga, LocalDateTime entrada,LocalDateTime saida,double valorPago) {
		this.vaga = vaga;
		this.entrada = entrada;
		this.saida = saida;
		this.valorPago = valorPago;
		
	}
	//getter para vaga
	public Vaga getVaga() {
		return vaga;
	}

	public void escreverArquivo(String placa, String estacionamento){
		 try {

        FileWriter fileWriter = new FileWriter("usoDeVaga.txt",true);
		fileWriter.write(placa+","+this.entrada+","+this.saida+","+this.valorPago+","+this.vaga.getId()+";");
		
		this.vaga.escreverArquivo(estacionamento);
	
        
      

        fileWriter.close();

      
    } catch (IOException e) {
        e.printStackTrace();
    }
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
		if(entrada != null && saida != null){
		//diferença entre entrada e saida
		LocalDateTime tempEntrada = entrada;
		LocalDateTime tempSaida = saida;

		long minutosEstacionados = 0;

		while(tempEntrada.isBefore(tempSaida)){
			minutosEstacionados++;
			tempEntrada = tempEntrada.plusMinutes(1);
		}
		//System.out.println(minutosEstacionados);
		//valor pela taxa de fraçao de uso
		double valor = minutosEstacionados * FRACAO_USO * VALOR_FRACAO;
		
		//valor maximo
		valorPago = Math.min(valor, VALOR_MAXIMO);
		return valorPago;

		}
		return 0.0;
		
	}

	public double valorPago() {
		
		return valorPago;
		
	}

}
