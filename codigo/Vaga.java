import java.io.FileWriter;
import java.io.IOException;

public class Vaga {
	private String id;
	private boolean disponivel;

	public Vaga(String id) {
        this.id = id;
        this.disponivel = true;
	}
	public String getId() {
		return this.id;
	}
	public void escreverArquivo(String estacionamento){
		 try {
        FileWriter fileWriter = new FileWriter("vaga.txt",true);
		fileWriter.write(estacionamento+","+this.id+","+this.disponivel+";");      
		//System.out.println("escrito uma vaga");       
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