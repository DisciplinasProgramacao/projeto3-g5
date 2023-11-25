import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

// Classe que representa uma Vaga no estacionamento
public class Vaga implements Serializable {
	private String id;           // Identificação única da vaga
	private boolean disponivel;  // Indica se a vaga está disponível

	// Construtor para criar uma Vaga com uma identificação única
	public Vaga(String id) {
        this.id = id;
        this.disponivel = true;  // Inicialmente, a vaga está disponível
	}

	// Método para obter a identificação da vaga
	public String getId() {
		return this.id;
	}

	// Método para escrever informações da vaga em um arquivo
	public void escreverArquivo(String estacionamento) {
		 try {
        FileWriter fileWriter = new FileWriter("vaga.txt", true);
		fileWriter.write(estacionamento + "," + this.id + "," + this.disponivel + ";");      
		System.out.println("Escrito uma vaga no arquivo.");       
        fileWriter.close();
      
    } catch (IOException e) {
        e.printStackTrace();
    }
	}

	// Método para configurar a identificação da vaga
	public void setId(String id) {
		this.id = id;
	}

	// Método para verificar se a vaga está disponível
	public boolean getDisponivel() {
		return this.disponivel;
	}
	
	// Método para configurar a disponibilidade da vaga
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	// Método para estacionar um veículo na vaga
	public boolean estacionar() {
		if (disponivel) {
			this.disponivel = false;
			return true;  // Retorna verdadeiro se o veículo foi estacionado com sucesso
		} else {
			return false;  // Retorna falso se a vaga já estiver ocupada
		}
	}

	// Método para liberar a vaga, indicando que o veículo saiu
	public boolean sair() {
		if (!disponivel) {
			this.disponivel = true;
			return true;  // Retorna verdadeiro se a vaga foi liberada com sucesso
		} else {
			return false;  // Retorna falso se a vaga já estiver livre
		}
	}
}
