import java.util.*;

public class Veiculo {

    private String placa;
    private UsoDeVaga[] usos;

    public Veiculo(String placa, int maxUsos) {
        this.placa = placa;
        this.usos = new UsoDeVaga[maxUsos];
    }

    public void estacionar(Vaga vaga) {
        for (int i = 0; i < usos.length; i++) {
            if (usos[i] == null) {
				if (vaga.disponivel()) {
					usos[i] = new UsoDeVaga(vaga, this);
					vaga.estacionar();
					break;
				}
            }
        }
    }

    public double sair() {
        double totalPago = 0.0;
        for (int i = 0; i < usos.length; i++) {
            if (usos[i] != null && !usos[i].getSaida()) {
                usos[i].sair();
                totalPago += usos[i].valorPago();
                usos[i] = null;
            }
        }
        return totalPago;
    }

    public double totalArrecadado() {
        double totalArrecadado = 0.0;
        for (UsoDeVaga uso : usos) {
            if (uso != null && uso.getSaida()) {
                totalArrecadado += uso.valorPago();
            }
        }
        return totalArrecadado;
    }

    public double arrecadadoNoMes(int mes) {
        double totalArrecadadoNoMes = 0.0;
		int mesData = data.getMonth() + 1;
        for (UsoDeVaga uso : usos) {
			if (uso != null) {
				Date data = uso.getSaida();

				if (data != null && mesData == mes) {
					totalArrecadadoNoMes += uso.valorPago();
				}
			}
        }
        return totalArrecadadoNoMes;
    }

    public int totalDeUsos() {
        int totalUsos = 0;
        for (UsoDeVaga uso : usos) {
            if (uso != null) {
                totalUsos++;
            }
        }
        return totalUsos;
    }
	
	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public UsoDeVaga[] getUsos() {
		return this.usos;
	}

	public void setUsos(UsoDeVaga[] usos) {
		this.usos = usos;
	}
}
