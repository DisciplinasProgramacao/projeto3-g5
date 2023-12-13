import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import javax.management.RuntimeErrorException;

public class Estacionamento implements Serializable {
	private String nome;
	private List<Cliente> id = new ArrayList<>();
	private List<Vaga> vagas = new ArrayList<>();
	private int quantFileiras;
	private int vagasPorFileira;
	private Relatorio relatorio = new Relatorio();

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome = nome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		gerarVagas();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setId(List<Cliente> id) {
		this.id = id;
	}

	public List<Cliente> getId() {
		return this.id;
	}

	public void setVaga(List<Vaga> vaga) {
		this.vagas = vaga;
	}

	public List<Vaga> getVaga() {
		return this.vagas;
	}

	public void setQuantFileiras(int quantFileiras) {
		this.quantFileiras = quantFileiras;
	}

	public int getQuantFileiras() {
		return this.quantFileiras;
	}

	public void setVagasPorFileira(int vagasPorFileira) {
		this.vagasPorFileira = vagasPorFileira;
	}

	public int getVagasPorFileira() {
		return this.vagasPorFileira;
	}

	public void salvarEstado() throws IOException {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.nome + ".ser"))) {
			outputStream.writeObject(this);
		}
	}

	public Estacionamento carregarEstado() throws IOException, ClassNotFoundException {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.nome + ".ser"))) {
			return (Estacionamento) inputStream.readObject();
		}

	}

	public void addVeiculo(Veiculo veiculo, String idCli) {

		if (encontrarClientePorPlaca(veiculo.getPlaca()) != null) {
			System.out.println("Veiculo já existe");
			return;
		}
		Cliente cliente = this.id.stream().filter(c -> c.getId().equals(idCli)).findFirst().orElse(null);
		if (cliente != null) {
			cliente.addVeiculo(veiculo);
			return;
		}
		// Se chegou aqui, o cliente com o ID especificado não foi encontrado
		System.out.println("Cliente com ID " + idCli + " não encontrado.");
	}

	public void addCliente(Cliente cliente) {
		cliente.addObserver(relatorio);
		this.id.add(cliente);
	}

	public void gerarVagas() {
		for (int i = 0; i < this.quantFileiras; i++) {
			for (int j = 0; j < this.vagasPorFileira; j++) {
				String id = String.format("Fila%dVaga%02d", i, j);

				this.vagas.add(new Vaga(id));
			}
		}
	}

	private Cliente encontrarClientePorPlaca(String placa) {
		Cliente c = this.id.stream().filter(cli -> cli.possuiVeiculo(placa) != null).findFirst().orElse(null);
		return c;
	}

	public void estacionar(String placa, LocalDateTime time, Cliente c) {
		Vaga vaga = vagas.stream()
				.filter(va -> va.getDisponivel())
				.findFirst()
				.orElse(null);
		Veiculo veiculo = c.possuiVeiculo(placa);

		if (veiculo == null) {
			System.out.println("Veículo não encontrado");
		} else if (vaga == null) {
			System.out.println("Não há vagas disponíveis");
		} else
			veiculo.estacionar(vaga, time);
	}

	public static double calcularCustoServicos(Map<String, Boolean> servicosAtivos) {
		double valorTotal = 0.0;

		for (Map.Entry<String, Boolean> entry : servicosAtivos.entrySet()) {
			String servico = entry.getKey();
			boolean ativo = entry.getValue();

			if (ativo) {
				double valorServico = obterValorServico(servico);
				valorTotal += valorServico;
				System.out.println("Cobrando serviço: " + servico + " - Valor: R$" + valorServico);
			}
		}

		return valorTotal;
	}

	private static double obterValorServico(String servico) {
		switch (servico) {
			case "manobrista":
				return 5.0;
			case "lavagem":
				return 30.0;
			case "polimento":
				return 50.0;
			default:
				return 0.0;
		}
	}

	public ArrayList<String> historicoUsoData(Cliente cliente, int month) {
		ArrayList<String> historico = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		cliente.getVeiculos().stream().forEach(
				v -> {
					String infoUso = v.getUsos().stream().filter(u -> u.getEntrada().getMonth().getValue() == month)
							.map(filteredU -> {
								return "Cliente: " + cliente.getNome() +
										", Veículo: " + v.getPlaca() +
										", Entrada: " + filteredU.getEntrada().format(formatter) +
										", Saída: "
										+ (filteredU.getSaida() != null ? filteredU.getSaida().format(formatter)
												: "ainda estacionado")
										+
										", Valor Pago: R$" + filteredU.valorPago() +
										", Vaga: " + filteredU.getVaga().getId() + "\n";
							}).collect(Collectors.joining(",", "{", "}"));
					historico.add(infoUso);
				});

		return historico;
	}

	public ArrayList<String> historicoDeUso(Cliente cliente) {
		ArrayList<String> historico = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		if (cliente != null) {
			List<Veiculo> veiculos = cliente.getVeiculos();
			if (veiculos != null) {
				for (Veiculo veiculo : veiculos) {
					if (veiculo != null) {
						List<UsoDeVaga> usos = veiculo.getUsos();
						if (usos != null) {
							for (UsoDeVaga uso : usos) {
								if (uso != null) {
									String infoUso = "Cliente: " + cliente.getNome() +
											", Veículo: " + veiculo.getPlaca() +
											", Entrada: " + uso.getEntrada().format(formatter) +
											", Saída: "
											+ (uso.getSaida() != null ? uso.getSaida().format(formatter)
													: "ainda estacionado")
											+
											", Valor Pago: R$" + uso.valorPago() +
											", Vaga: " + uso.getVaga().getId() + "\n";
									historico.add(infoUso);
								}
							}
						}
					}
				}
			}

		}
		return historico;
	}

	public boolean sair(String placa, LocalDateTime time) {
		Cliente cliente = encontrarClientePorPlaca(placa);

		if (cliente != null && cliente.possuiVeiculo(placa) != null) {
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			List<UsoDeVaga> usos = veiculo.getUsos();
			for (UsoDeVaga uso : usos) {
				if (uso != null && uso.getSaida() == null) {
					double diff = Duration.between(uso.getEntrada(), time).toMinutes();
					diff = diff / 60;
					if (diff <= 0) {
						System.out.println("digite um horario posterior ao de entrada");
						return false;
					}
					if (uso.getServico() != null && uso.getServico().getHoraMinima() > diff) {
						System.out.println("Tempo insuficiente para o serviço adquirido");
						return false;
					} else {

						veiculo.sair(time, cliente.getTipoCliente());

						relatorio.update(cliente, uso);
						return true;
					}
				}
			}
		}

		System.out.println("Veiculo não cadastrado, favor cadastrar");
		return true;

	}

	public void adicionarServico(String idCli, int tipo, String placa) {
		for (Cliente cliente : id) {
			if (cliente != null && cliente.getId().equals(idCli)) {
				Veiculo v = cliente.possuiVeiculo(placa);
				List<UsoDeVaga> usos = v.getUsos();
				for (UsoDeVaga usoDeVaga : usos) {
					if (usoDeVaga != null && usoDeVaga.getSaida() == null) {
						if (usoDeVaga.getEntrada() == null) {
							System.out.println("Não é possível adicionar um serviço com veiculo não estacionado");
							break;
						}
						double total = 0;
						switch (tipo) {
							case 1:
								usoDeVaga.setServico(Servico.manobrista);
								total = usoDeVaga.getvalorPago() + Servico.manobrista.getValor();
								usoDeVaga.setValorPago(total);
								break;
							case 2:
								usoDeVaga.setServico(Servico.lavagem);
								total = usoDeVaga.getvalorPago() + Servico.lavagem.getValor();
								usoDeVaga.setValorPago(total);
								break;
							case 3:
								usoDeVaga.setServico(Servico.polimento);
								total = usoDeVaga.getvalorPago() + Servico.polimento.getValor();
								usoDeVaga.setValorPago(total);
								break;

							default:
								break;
						}
					}
				}

			}
		}
	}

	public double totalArrecadado() {
		return this.id.stream()
				.filter(cliente -> cliente != null)
				.mapToDouble(Cliente::arrecadadoTotal)
				.sum();
	}

	public double arrecadacaoNoMes(int mes) {
		// return this.id.stream().filter(cliente -> cliente !=
		// null).mapToDouble(cliente -> cliente.arrecadadoNoMes(mes)).sum();
		return relatorio.getArrecadadoNoMes(mes);
	}

	public double valorMedioPorUso() {
		return this.id.stream()
				.filter(cliente -> cliente != null)
				.mapToDouble(cliente -> cliente.arrecadadoTotal() / cliente.totalDeUsos())
				.average()
				.orElse(0D);
	}

	public String top5Clientes(int mes) {
		// return this.id.stream().filter(cliente -> cliente != null).sorted((c1, c2) ->
		// Double.compare(c2.arrecadadoNoMes(mes),
		// c1.arrecadadoNoMes(mes))).limit(5).map(Cliente::getNome).collect(Collectors.joining("
		// "));
		return relatorio.getClientesTop5();
	}

	public double mediaUsoMensalistasNoMes(int mes) {
		int totalUsos = 0;
		int totalClientesMensalistas = 0;
		for (Cliente cliente : id) {
			if (cliente != null && cliente.getTipoCliente() == TipoCliente.MENSALISTA
					&& cliente.getDateMensalista().getMonthValue() >= mes) {
				List<Veiculo> veiculos = cliente.getVeiculos();
				for (Veiculo veiculo : veiculos) {
					if (veiculo != null) {
						List<UsoDeVaga> usos = veiculo.getUsos();
						for (UsoDeVaga uso : usos) {
							if (uso != null && uso.getSaida() != null) {
								LocalDateTime data = uso.getSaida();
								int mesData = data.getMonthValue();

								if (mesData == mes) {
									totalUsos++;
								}
							}
						}
					}
					totalClientesMensalistas++;
				}

			}
		}

		if (totalClientesMensalistas > 0) {
			return (double) totalUsos / totalClientesMensalistas;
		} else {
			return 0.0;
		}
	}

	public double mediasHoristaNoMes(int mes) {
		return this.id.stream()
				.filter(cliente -> cliente != null && cliente.getTipoCliente() == TipoCliente.HORISTA)
				.mapToDouble(cliente -> cliente.arrecadadoNoMes(mes))
				.average()
				.orElse(0D);
	}

	public void setMensalista(TipoCliente tipoCliente, String idCli) {
		for (Cliente cliente : id) {
			if (cliente != null && cliente.getId().equals(idCli)) {
				LocalDate today = LocalDate.now();
				cliente.setTipoCliente(tipoCliente);
				cliente.setDateMensalista(today);
			}
		}

	}

}
