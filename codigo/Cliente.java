public class Cliente {

	private String nome;
	private String id;
	private Veiculo[] veiculos;




	public Cliente(String nome, String id) {
		
		this.nome = nome;
        this.id = id;
	}

	public void addVeiculo(Veiculo veiculo) {
		
	}

	public Veiculo possuiVeiculo(String placa) {
		
	}

	public int totalDeUsos() {a
		
	}

	public double arrecadadoPorVeiculo(String placa) {
		
	}

	public double arrecadadoTotal() {
		
	}

	public double arrecadadoNoMes(int mes) {
		
	}
	
    // Getter para o campo nome
    public String getNome() {
        return nome;
    }

    // Setter para o campo nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter para o campo id
    public String getId() {
        return id;
    }

    // Setter para o campo id
    public void setId(String id) {
        this.id = id;
    }

    // Getter para o campo veiculos
    public Veiculo[] getVeiculos() {
        return veiculos;
    }

    // Setter para o campo veiculos
    public void setVeiculos(Veiculo[] veiculos) {
        this.veiculos = veiculos;
    }

}
