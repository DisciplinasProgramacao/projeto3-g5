public void testArrecadadoPorVeiculo() {
    Cliente cliente = new Cliente();
    // teste addVeiculo
    Veiculo veiculo = new Veiculo("KDN4180");
    Veiculo veiculo2 = new Veiculo("KCS7508");
    cliente.addVeiculo(veiculo1);
    cliente.addVeiculo(veiculo2);

    //teste possuiVeiculo
    Veiculo encontrado = cliente.possuiVeiculo("KDN4180");
    assertNotNull(encontrado);
    assertEquals(veiculo1, encontrado);

    //teste totalDeUsos
    int totalUsos = cliente.totalDeUsos();
    assertEquals(10, totalUsos);

    //teste arrecadadoPorVeiculo
    double arrecadacaoABC123 = cliente.arrecadadoPorVeiculo("KDN4180");
    assertEquals(15.0, arrecadacaoABC123, 0.01);
    double arrecadacaoXYZ789 = cliente.arrecadadoPorVeiculo("KCS7508");
    assertEquals(35.0, arrecadacaoXYZ789, 0.01);
    
    //teste arrecadadoTotal
    double totalArrecadado = cliente.arrecadadoTotal();
    assertEquals(150.0, totalArrecadado, 0.01);
    
    //teste arrecadadoNoMes
    double arrecadadoMes1 = cliente.arrecadadoNoMes(1);
    assertEquals(50.0, arrecadadoMes1, 0.01);
    double arrecadadoMes2 = cliente.arrecadadoNoMes(2);
    assertEquals(30.0, arrecadadoMes2, 0.01);
    double arrecadadoMes3 = cliente.arrecadadoNoMes(3);
    assertEquals(0.0, arrecadadoMes3, 0.01);
}