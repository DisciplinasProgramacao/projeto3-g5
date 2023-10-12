public void testArrecadadoPorVeiculo() {
    double arrecadacaoABC123 = estacionamento.arrecadadoPorVeiculo("ABC123");
    assertEquals(15.0, arrecadacaoABC123, 0.01);
    double arrecadacaoXYZ789 = estacionamento.arrecadadoPorVeiculo("XYZ789");
    assertEquals(35.0, arrecadacaoXYZ789, 0.01);
    double arrecadacaoInexistente = estacionamento.arrecadadoPorVeiculo("MNO456");
    assertEquals(0.0, arrecadacaoInexistente, 0.01);
}