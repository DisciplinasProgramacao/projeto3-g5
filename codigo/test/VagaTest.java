import org.junit.Test;
import static org.junit.Assert.*;

public class VagaTest {

    @Test
    public void testEstacionar() {
        Vaga vaga = new Vaga("1", true);
        boolean estacionou = vaga.estacionar();
        assertFalse(vaga.getDisponivel());
        assertTrue(estacionou);
    }

    @Test
    public void testSair() {
        Vaga vaga = new Vaga("1", true);
        vaga.estacionar();  
        boolean saiu = vaga.sair();
        assertTrue(vaga.getDisponivel());
        assertTrue(saiu);
    }

    @Test
    public void testGetId() {
        Vaga vaga = new Vaga("1", true);
        assertEquals("1", vaga.getId());
    }

    @Test
    public void testSetId() {
        Vaga vaga = new Vaga("1", true);
        vaga.setId("2");
        assertEquals("2", vaga.getId());
    }

    @Test
    public void testGetDisponivel() {
        Vaga vaga = new Vaga("1", true);
        assertTrue(vaga.getDisponivel());
    }

    @Test
    public void testSetDisponivel() {
        Vaga vaga = new Vaga("1", true);
        vaga.setDisponivel(false);
        assertFalse(vaga.getDisponivel());
    }

    @Test
    public void testEscreverArquivo() {
        Vaga vaga = new Vaga("1", true);
        vaga.escreverArquivo("Estacionamento XYZ");
        
    }
}
