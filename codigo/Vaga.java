
public class Vaga {

	String id;
	private boolean disponivel;

	public Vaga(int fila, int numero) {
		this.id = "Fila" + fila + "Vaga" + numero;
		this.disponivel = true;
		
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDisponivel() {
		return this.disponivel;
	}

	public boolean getDisponivel() {
		return this.disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}


	public boolean estacionar() {
		if (disponivel = true){
			return true;
		}
		return false;
				
	}

	public boolean sair() {
		if(!disponivel){
			return true;
		}
		return false;
	
	}

	public boolean disponivel() {
		return disponivel;
		
	}

	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public void setDisponivel(boolean disponivel){
		this.disponivel = disponivel;
	}

}
