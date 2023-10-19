package projeto3-g5.testes;

public class VeiculoTest {
    public void testArrecadadoNoMes() {
        // Crie uma instância da classe ArrecadacaoAnual
        Veiculo arrecadacao = new Veiculo();

        // Defina um valor de exemplo para o mês (por exemplo, janeiro é representado pelo número 1)
        int mes = 1;

        // Chame o método arrecadadoNoMes com o mês definido
        double valorArrecadado = arrecadacao.arrecadadoNoMes(mes);

        // Defina o valor esperado com base nos dados de teste
        double valorEsperado = 1000.0; // Substitua pelo valor esperado real

        // Verifique se o valor retornado é igual ao valor esperado com um delta de precisão (por exemplo, 0.001)
        assertEquals(valorEsperado, valorArrecadado, 0.001);
    }
}
