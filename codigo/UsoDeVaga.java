import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

	public double sair(int mensalista) {
		System.out.println(mensalista);
		valorPago = 0.0;

		if (entrada != null && saida != null) {
			// diferença entre entrada e saida
			LocalDateTime tempEntrada = entrada;
			LocalDateTime tempSaida = saida;
			if (mensalista <= 0) {
				long minutosEstacionados = 0;

				while (tempEntrada.isBefore(tempSaida)) {
					minutosEstacionados++;
					tempEntrada = tempEntrada.plusMinutes(1);
				}
				// System.out.println(minutosEstacionados);
				// valor pela taxa de fraçao de uso
				double valor = minutosEstacionados * FRACAO_USO * VALOR_FRACAO;

				// valor maximo
				valorPago = Math.min(valor, VALOR_MAXIMO);
				return valorPago;

			} else {
				LocalTime entrada = tempEntrada.toLocalTime();
				LocalTime saida = tempSaida.toLocalTime();
				if (mensalista == 1) {
					LocalTime horaInicio = LocalTime.of(8, 0);
					LocalTime horaFim = LocalTime.of(12, 0);
					if (entrada.isAfter(horaFim) || saida.isAfter(horaFim)) {
						valorPago = calcDiferenceValue(entrada, saida, horaInicio, horaFim);
					} else {
						valorPago = 0.0;
					}
				} else if (mensalista == 2) {
					LocalTime horaInicio = LocalTime.of(12, 1);
					LocalTime horaFim = LocalTime.of(18, 0);
					if (entrada.isBefore(horaFim) || entrada.isAfter(horaFim) || saida.isBefore(horaFim)
							|| saida.isAfter(horaFim)) {
						valorPago = calcDiferenceValue(entrada, saida, horaInicio, horaFim);
					} else {
						valorPago = 0.0;
					}

				} else if (mensalista == 3) {
					LocalTime horaInicio = LocalTime.of(18, 1);
					LocalTime horaFim = LocalTime.of(23, 59);
					if (entrada.isBefore(horaFim) || entrada.isAfter(horaFim) || saida.isBefore(horaFim)
							|| saida.isAfter(horaFim)) {
						valorPago = calcDiferenceValue(entrada, saida, horaInicio, horaFim);
					} else {
						valorPago = 0.0;
					}
				} else {
					valorPago = 0.0;
				}
				return valorPago;
			}
		}
		return valorPago;

	}

	public double valorPago() {

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
		System.out.println(inicio);
		System.out.println(fim);
		Duration intervalo = Duration.between(inicio, fim);
		long minutos = intervalo.toMinutes();
		double valor = minutos * FRACAO_USO * VALOR_FRACAO;
		return Math.min(valor, VALOR_MAXIMO);
	}

}
