import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UsoDeVaga implements Serializable {

	private static final double FRACAO_USO = 0.25;
	private static final double VALOR_FRACAO = 4.0;
	private static final double VALOR_MAXIMO = 50.0;
	private Vaga vaga;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private double valorPago;
	private int horaMinima;


	public int getHoraMinima() {
		return horaMinima;
	}

	public void setHoraMinima(int horaMinima) {
		this.horaMinima = horaMinima;
	}

	public UsoDeVaga(Vaga vaga, LocalDateTime entrada) {
		this.vaga = vaga;
		this.entrada = entrada;

	}

	public UsoDeVaga(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago) {
		this.vaga = vaga;
		this.entrada = entrada;
		this.saida = saida;
		this.valorPago = valorPago;

	}

	// getter para vaga
	public Vaga getVaga() {
		return vaga;
	}

	public void escreverArquivo(String placa, String estacionamento) {
		try {
			FileWriter fileWriter = new FileWriter("usoDeVaga.txt", true);
			fileWriter.write(placa + "," + this.entrada + "," + this.saida + "," + this.valorPago + ","
					+ this.vaga.getId() + ";");
			// System.out.println("escrito um usodevaga");
			this.vaga.escreverArquivo(estacionamento);

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void adicionarServico(int tipo){
		if(tipo==1){
			this.valorPago+=5;
		}
		if(tipo==2){
			this.valorPago+=20;
			this.horaMinima = 1;
		}
		if(tipo==3){
			this.valorPago+=45;
			this.horaMinima = 2;
		}
	}

	// sett vaga
	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	// getter entrada
	public LocalDateTime getEntrada() {
		return entrada;
	}

	// sett entrada
	public void setEntrada(LocalDateTime entrada) {
		this.entrada = entrada;
	}

	// get saida
	public LocalDateTime getSaida() {
		return saida;
	}

	// sett saida
	public void setSaida(LocalDateTime saida) {
		this.saida = saida;
	}

	// gett valorPago
	public double getvalorPago() {
		return valorPago;
	}

	// set valorPago
	public void setvalorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public double sair(TipoCliente tipoCliente) {

		if (entrada != null && saida != null) {
			// diferença entre entrada e saida
			switch (tipoCliente) {
				case HORISTA:
					return calcValue(entrada.toLocalTime(), saida.toLocalTime());
				case TURNO_MANHA:
					return calcDiferenceValue(entrada.toLocalTime(), saida.toLocalTime(), tipoCliente.getHoraInicio(), tipoCliente.getHoraFim());
				case TURNO_TARDE:
					return calcDiferenceValue(entrada.toLocalTime(), saida.toLocalTime(), tipoCliente.getHoraInicio(), tipoCliente.getHoraFim());
				case TURNO_NOITE:
					return calcDiferenceValue(entrada.toLocalTime(), saida.toLocalTime(), tipoCliente.getHoraInicio(), tipoCliente.getHoraFim());
				case MENSALISTA:
					return 0.0;
				default:
					return 0.0;
			}
		}
		
		return 0;

	}

	public double valorPago() {

		return valorPago;

	}

	public double calcValue(LocalTime entrada, LocalTime saida) {
		int minutosEstacionados = 0;
		while (entrada.isBefore(saida)) {
			minutosEstacionados++;
			entrada = entrada.plusMinutes(1);
		}
		// valor pela taxa de fraçao de uso
		double valor = minutosEstacionados * FRACAO_USO * VALOR_FRACAO;

		// valor maximo
		valorPago = Math.min(valor, VALOR_MAXIMO);
		return valorPago;

	}

	public double calcDiferenceValue(LocalTime entrada, LocalTime saida, LocalTime horaInicio, LocalTime horaFim) {
		LocalTime inicio = LocalTime.now();
		LocalTime fim = LocalTime.now();
		if (entrada.isAfter(horaFim)) {
			inicio = entrada;
			fim = saida;
		} else if (saida.isAfter(horaFim)) {
			inicio = horaFim;
			fim = saida;
		}
		if (saida.isBefore(horaInicio)) {
			inicio = entrada;
			fim = saida;
		} else if (entrada.isBefore(horaInicio)) {
			inicio = entrada;
			fim = horaInicio;
		}
		return calcValue(inicio, fim);
	}

}
