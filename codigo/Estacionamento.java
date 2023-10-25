import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Estacionamento {
	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;
	private int contCli = 0;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.setNome(nome);
		this.setQuantFileiras(fileiras);
		this.setVagasPorFileira(vagasPorFila);
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
			LocalDateTime momentoAtual = LocalDateTime.now();
			String linha;
			linha = bufferedReader.readLine();
			String[] clientes = linha.split("[;]");
			leitor = new FileReader("veiculo.txt");
			bufferedReader = new BufferedReader(leitor);
			linha = bufferedReader.readLine();
			String[] veiculos = linha.split("[;]");
			leitor = new FileReader("usoDeVaga.txt");
			bufferedReader = new BufferedReader(leitor);
			linha = bufferedReader.readLine();
			String[] usos = linha.split("[;]");
			leitor = new FileReader("vaga.txt");
			bufferedReader = new BufferedReader(leitor);
			linha = bufferedReader.readLine();
			String[] vagas = linha.split("[;]");
			int contVagas = 0;
			for (String v : vagas) {
				String[] vaga = v.split("[,]");
				if (vaga[0].equals(this.nome)) {
					this.vagas[contVagas].setId(vaga[1]);
					this.vagas[contVagas].setDisponivel(Boolean.parseBoolean(vaga[2]));
					contVagas++;
				}
			}

			for (String token : clientes) {
				String[] cliente = token.split("[,]");
				if (cliente[0].equals(this.nome)) {
					Cliente cli = new Cliente(cliente[1], cliente[2]);
					for (String ve : veiculos) {
						String[] veiculo = ve.split("[,]");
						for (String us : usos) {
							String[] uso = us.split("[,]");

							if (veiculo[0].equals(cliente[1]) && uso[0].equals(veiculo[1])) {
								UsoDeVaga[] arrUsoDeVaga = new UsoDeVaga[20];
								int cAUV = 0;
								for (Vaga vaga : this.vagas) {
									if (vaga.getId().equals(uso[4])) {
										UsoDeVaga uv = new UsoDeVaga(vaga, momentoAtual);
										arrUsoDeVaga[cAUV] = uv;
										cAUV++;
									}
								}
								Veiculo v = new Veiculo(veiculo[1], 20);
								v.setUsos(arrUsoDeVaga);
								addVeiculo(v, cli.getId());
							}
						}

					}
					this.id[contCli] = cli;
					contCli++;
				}
			}

			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void escreverArquivo() {
		try {
			FileWriter fileWriter = new FileWriter("estacionamento.txt", true);
			fileWriter.write(this.nome + "," + this.quantFileiras + "," + this.vagasPorFileira + ";");
			for (Cliente cliente : id) {
				cliente.escreverArquivo(this.nome);
			}
			for (Vaga vaga : vagas) {
				vaga.escreverArquivo(this.nome);
			}

			fileWriter.close();

			// System.out.println("Array escrito em " + nomeArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {

		for (int i = 0; i < this.contCli; i++) {
			if (this.id[i].getId().equals(idCli)) {
				this.id[i].addVeiculo(veiculo);
			}
		}

	}

	public void addCliente(Cliente cliente) {
		this.id[this.contCli] = cliente;
		this.contCli++;

	}

	public void gerarVagas() {
		// int index = this.quantFileiras * this.vagasPorFileira;
		// Vaga[] vagas = new Vaga[index];
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int count = 0;
		for (int i = 0; i < this.quantFileiras; i++) {
			for (int j = 0; j < this.vagasPorFileira; j++) {
				String id = String.valueOf(characters.charAt(i));
				if (j < 10) {
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
		LocalDateTime momentoAtual = LocalDateTime.now();
		int count = 0;
		for (int i = 0; i < this.quantFileiras; i++) {
			for (int j = 0; j < this.vagasPorFileira; j++) {
				if (this.vagas[count].getDisponivel()) {
					i = this.quantFileiras;
					j = this.vagasPorFileira;
					for (int k = 0; k < this.contCli; k++) {
						if (this.id[k].possuiVeiculo(placa) != null) {
							this.id[k].possuiVeiculo(placa).estacionar(this.vagas[count], momentoAtual);
						}
					}
				}
				count++;
			}
		}

	}

	public double sair(String placa) {
		for (int k = 0; k < this.contCli; k++) {
			if (this.id[k].possuiVeiculo(placa) != null) {
				return this.id[k].possuiVeiculo(placa).sair();
			}
		}
		return contCli;

	}

	public double totalArrecadado() {
		double sum = 0;
		for (int i = 0; i < this.id.length; i++) {
			sum += this.id[i].arrecadadoTotal();
		}
		return sum;
	}

	public double arrecadacaoNoMes(int mes) {
		int total = 0;
		for (int i = 0; i < this.id.length; i++) {
			total += this.id[i].arrecadadoNoMes(mes);
		}
		return total;
	}

	public double valorMedioPorUso() {
		double sum = 0;
		for (int i = 0; i < this.id.length; i++) {
			sum += (this.id[i].arrecadadoTotal() / this.id[i].totalDeUsos());
		}
		return sum / this.id.length;
	}

	public String top5Clientes(int mes) {
		String[] clienteNome = new String[5];
		Double[] value = new Double[5];
		for (int i = 0; i < this.id.length; i++) {
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
		String top5 = "";
		for (int i = 0; i < clienteNome.length; i++) {
			top5 += clienteNome[i];
		}
		return top5;
	}
}
