import java.io.FileWriter;
import java.io.IOException;

public class Vaga {
	private String id;
	private boolean disponivel;

	public Vaga(int fila, int numero) {
		this.id = "Fila" + fila + "Vaga" + numero;
		this.disponivel = true;
	}

	public String getId() {
		return this.id;
	}
	public void escreverArquivo(String estacionamento){
		 try {
        FileWriter fileWriter = new FileWriter("vaga.txt",true);
		fileWriter.write(estacionamento+","+this.id+","+this.disponivel+";");             

        fileWriter.close();

      
    } catch (IOException e) {
        e.printStackTrace();
    }
	}

	public void setId(String id) {
		this.id = id;
	}
	public boolean getDisponivel() {
		return this.disponivel;
	}
	
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public boolean estacionar() {
		if (disponivel) {
			this.disponivel = false;
			return true;
		} else {
			return false;
		}
	}

	public boolean sair() {
		if(!disponivel) {
			this.disponivel = true;
			return true;
		} else {
			return false;
		}
	}
}