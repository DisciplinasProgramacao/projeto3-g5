import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Estacionamento {
	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;
	private int contCli = 0;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome = nome;
		this.id = new Cliente[1000];

		this.vagas = new Vaga[fileiras * vagasPorFila];
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		this.id = new Cliente[1000];

		this.vagas = new Vaga[fileiras * vagasPorFila];
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

	public void setId(Cliente[] id) {
		this.id = id;
	}

	public Cliente[] getId() {
		return this.id;
	}

	public void setVaga(Vaga[] vaga) {
		this.vagas = vaga;
	}

	public Vaga[] getVaga() {
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

	public void carregarArquivo() {
		try {

			FileReader leitor = new FileReader("cliente.txt");
			BufferedReader bufferedReader = new BufferedReader(leitor);
			contCli = 0;
			String linha;
			linha = bufferedReader.readLine();
			String[] clientes = linha.split("[;]");
			leitor.close();
			leitor = new FileReader("veiculo.txt");
			bufferedReader = new BufferedReader(leitor);

			linha = bufferedReader.readLine();
			String[] veiculos = linha.split("[;]");
			leitor.close();
			leitor = new FileReader("usoDeVaga.txt");
			bufferedReader = new BufferedReader(leitor);
			linha = bufferedReader.readLine();
			String[] usos = linha.split("[;]");
			leitor.close();
			leitor = new FileReader("vaga.txt");
			bufferedReader = new BufferedReader(leitor);
			linha = bufferedReader.readLine();
			String[] vagas = linha.split("[;]");
			int contVagas = 0;

			for (String v : vagas) {

				String[] vaga = v.split("[,]");
				if (vaga[0].equals(this.nome) && contVagas < 264) {

					this.vagas[contVagas].setId(vaga[1]);
					this.vagas[contVagas].setDisponivel(Boolean.parseBoolean(vaga[2]));
					contVagas++;
				}
			}

			for (String token : clientes) {
				String[] cliente = token.split("[,]");

				boolean idNaoExiste = true;

				// Verifica se existe algum elemento no vetor id igual a cliente[2]
				for (Cliente clienteExistente : id) {
					if (clienteExistente != null && clienteExistente.getId().equals(cliente[2])) {
						idNaoExiste = false;
						break;
					}
				}

				if (cliente[0].equals(this.nome) && idNaoExiste) {

					Cliente cli = new Cliente(cliente[1], cliente[2]);
					for (String ve : veiculos) {
						String[] veiculo = ve.split("[,]");
						for (String us : usos) {
							String[] uso = us.split("[,]");

							if (veiculo[0].equals(cliente[2]) && uso[0].equals(veiculo[1])) {
								UsoDeVaga[] arrUsoDeVaga = new UsoDeVaga[20];
								int cAUV = 0;

								for (Vaga vaga : this.vagas) {
									if (vaga.getId().equals(uso[4])) {
										DateTimeFormatter formatador = DateTimeFormatter
												.ofPattern("yyyy-MM-dd'T'HH:mm");
										// System.out.println(LocalDateTime.parse(uso[1], formatador));
										UsoDeVaga uv = new UsoDeVaga(vaga, LocalDateTime.parse(uso[1], formatador),
												uso[2].equals("null") ? null : LocalDateTime.parse(uso[2], formatador),
												Double.parseDouble(uso[3]));

										arrUsoDeVaga[cAUV] = uv;
										cAUV++;
									}

								}
								this.id[contCli] = cli;
								contCli++;
								Veiculo v = new Veiculo(veiculo[1], 20);

								v.setUsos(arrUsoDeVaga);

								addVeiculo(v, cli.getId());
							}
						}

					}

				}
			}

			leitor.close();
		} catch (IOException e) {
			System.out.println("erro ao carregar arquivos");
			// e.printStackTrace();
		}
	}

	public void escreverArquivo() {
		try {
			FileWriter fileWriter = new FileWriter("estacionamento.txt", true);
			fileWriter.write(this.nome + "," + this.quantFileiras + "," + this.vagasPorFileira + ";");
			for (Cliente cliente : id) {
				if (cliente != null) {
					cliente.escreverArquivo(this.nome);
					System.out.println(contCli);
				}
			}

			fileWriter.close();

			// System.out.println("Array escrito em " + nomeArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		for (Cliente cliente : this.id) {
			if (cliente != null && cliente.getId().equals(idCli)) {
				cliente.addVeiculo(veiculo);

				return; // Interrompe o loop assim que o cliente é encontrado
			}
		}

		// Se chegou aqui, o cliente com o ID especificado não foi encontrado
		System.out.println("Cliente com ID " + idCli + " não encontrado.");
	}

	public void addCliente(Cliente cliente) {
		System.out.println(contCli);
		this.id[this.contCli] = cliente;
		this.contCli++;

	}

	public void gerarVagas() {
		int index = 0;
		// System.out.println(this.vagas.length);
		for (int i = 0; i < this.quantFileiras; i++) {
			for (int j = 0; j < this.vagasPorFileira; j++) {
				String id = String.format("Fila%dVaga%02d", i, j);
				this.vagas[index] = new Vaga(id);

				index++;
			}
		}
	}

	private Cliente encontrarClientePorPlaca(String placa) {

		for (int i = 0; i < this.contCli; i++) {
			// System.out.println(this.contCli);
			Veiculo veiculo = this.id[i].possuiVeiculo(placa);
			// System.out.println(veiculo);
			if (veiculo != null) {
				return this.id[i];
			}
		}
		return null; // Retorna null se não encontrar o cliente
	}

	public void estacionar(String placa, LocalDateTime time) {
		boolean estacionado = false;
		for (int i = 0; i < this.quantFileiras; i++) {
			for (int j = 0; j < this.vagasPorFileira; j++) {
				int index = i * this.vagasPorFileira + j;
				if (index < this.vagas.length && this.vagas[index].getDisponivel() && !estacionado) {
					Vaga vaga = this.vagas[index];
					// Crie um novo veículo com a placa especificada
					// Veiculo veiculo = new Veiculo(placa, 20); // 20 é o número máximo de usos do
					// veículo

					Cliente cliente = encontrarClientePorPlaca(placa);

					Veiculo veiculo = cliente.possuiVeiculo(placa);
					veiculo.estacionar(vaga, time);
					// Adiciona o veículo ao cliente (você precisa implementar este método em
					// Cliente)

					// System.out.println("Veículo com placa " + placa + " estacionado na vaga " +
					// vaga.getId());
					estacionado = true;
				}
			}
		}
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

	public ArrayList<String> historicoDeUso(Cliente cliente) {
		ArrayList<String> historico = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		int i = 0;
		// for (Cliente cliente : this.id ) {
		if (cliente != null) {
			Veiculo[] veiculos = cliente.getVeiculos();

			for (Veiculo veiculo : veiculos) {
				if (veiculo != null) {
					System.out.println(veiculos.length);
					UsoDeVaga[] usos = veiculo.getUsos();
					System.out.println(veiculo.getPlaca());
					i++;
					for (UsoDeVaga uso : usos) {
						if (uso != null) {

							String infoUso = "Cliente: " + cliente.getNome() +
									", Veículo: " + veiculo.getPlaca() +
									", Entrada: " + uso.getEntrada().format(formatter) +
									", Saída: "
									+ (uso.getSaida() != null ? uso.getSaida().format(formatter) : "ainda estacionado")
									+
									", Valor Pago: R$" + uso.valorPago() +
									", Vaga: " + uso.getVaga().getId() + "\n";
							historico.add(infoUso);
						}
					}
				}
			}
		}
		// }

		return historico;
	}

	public double sair(String placa, LocalDateTime time) {

		for (int k = 0; k < this.contCli; k++) {
			if (this.id[k].possuiVeiculo(placa) != null) {
				return this.id[k].possuiVeiculo(placa).sair(time);
			}
		}
		return contCli;

	}

	public double totalArrecadado() {
		double sum = 0;

		for (int i = 0; i < this.id.length; i++) {
			if (this.id[i] != null) {
				sum += this.id[i].arrecadadoTotal();
			}
		}
		return sum;
	}

	public double arrecadacaoNoMes(int mes) {
		double totalArrecadadoNoMes = 0.0;
		for (int i = 0; i < this.contCli; i++) {
			Veiculo[] veiculos = this.id[i].getVeiculos();
			for (Veiculo veiculo : veiculos) {
				if (veiculo != null) {
					UsoDeVaga[] usos = veiculo.getUsos();
					for (UsoDeVaga uso : usos) {
						if (uso != null && uso.getSaida() != null) {
							LocalDateTime data = uso.getSaida();
							int mesData = data.getMonthValue();
							if (mesData == mes) {
								totalArrecadadoNoMes += uso.valorPago();
							}
						}
					}
				}
			}
		}
		return totalArrecadadoNoMes;
	}

	public double valorMedioPorUso() {
		double sum = 0;
		int count = 0; // Contador para clientes não nulos
		for (int i = 0; i < this.id.length; i++) {
			if (this.id[i] != null) { // Verifica se o cliente não é nulo
				sum += (this.id[i].arrecadadoTotal() / this.id[i].totalDeUsos());
				count++;
			}
		}
		if (count > 0) {
			return sum / count; // Retorna a média apenas se houver clientes não nulos
		} else {
			return 0; // Retorna 0 se não houver clientes não nulos
		}
	}

	public String top5Clientes(int mes) {
		String[] clienteNome = new String[5];
		Double[] value = new Double[5];
		for (int i = 0; i < this.id.length; i++) {
			if (this.id[i] != null) { // Verifica se o cliente não é nulo
				double arrecadado = this.id[i].arrecadadoNoMes(mes);
				for (int j = 0; j < 5; j++) {
					if (value[j] != null) {
						if (arrecadado > value[j]) {
							String temp = clienteNome[j];
							double tempValue = value[j];
							value[j] = arrecadado;
							clienteNome[j] = this.id[i].getNome();

							for (int k = j + 1; k < 5; k++) {
								String temp2 = clienteNome[k];
								double tempValue2 = value[k];
								clienteNome[k] = temp;
								value[k] = tempValue;
								temp = temp2;
								tempValue = tempValue2;
							}
							break;
						}
					} else {
						value[j] = arrecadado;
						clienteNome[j] = this.id[i].getNome();
						break;
					}
				}
			}
		}
		StringBuilder top5 = new StringBuilder();
		for (int i = 0; i < clienteNome.length; i++) {
			if (clienteNome[i] != null) {
				top5.append(clienteNome[i]).append(" ");
			}
		}
		return top5.toString();
	}

	public Veiculo getVeiculoByPlaca(String placa) {
		for (Cliente cliente : this.id) {
			if (cliente != null) {
				Veiculo veiculo = cliente.possuiVeiculo(placa);
				if (veiculo != null) {
					return veiculo;
				}
			}
		}
		return null;
	}

	public double mediaUsoMensalistasNoMes(int mes) {
		int totalUsos = 0;
		int totalClientesMensalistas = 0;

		for (int i = 0; i < this.contCli; i++) {
			if (this.id[i] != null && this.id[i].getCategoria() == CategoriaCliente.MENSALISTA) {
				Veiculo[] veiculos = this.id[i].getVeiculos();

				for (Veiculo veiculo : veiculos) {
					UsoDeVaga[] usos = veiculo.getUsos();

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

		if (totalClientesMensalistas > 0) {
			return (double) totalUsos / totalClientesMensalistas;
		} else {
			return 0.0;
		}
	}

	public int quantidadeUsosMensalistaNoMes(String idCliente, int mes) {
		int totalUsos = 0;

		for (int i = 0; i < this.contCli; i++) {
			if (this.id[i] != null && this.id[i].getId().equals(idCliente)
					&& this.id[i].getCategoria() == CategoriaCliente.MENSALISTA) {
				Veiculo[] veiculos = this.id[i].getVeiculos();

				for (Veiculo veiculo : veiculos) {
					UsoDeVaga[] usos = veiculo.getUsos();

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
			}
		}

		return totalUsos;
	}

}
