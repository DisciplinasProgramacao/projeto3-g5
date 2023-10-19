import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VagaTeste {
    private Vaga vaga;

    @Before
    public void setUp() {
        vaga = new Vaga(1, 1);
    }

    @Test
    public void testEstacionar() {
        assertTrue(vaga.estacionar());
        assertFalse(vaga.estacionar());
    }

    @Test
    public void testSair() {
        assertFalse(vaga.sair());
        vaga.estacionar();
        assertTrue(vaga.sair());
        assertFalse(vaga.sair());
    }

    @Test
    public void testDisponivel() {
        assertTrue(vaga.disponivel());
        vaga.estacionar();
        assertFalse(vaga.disponivel());
        vaga.sair();
        assertTrue(vaga.disponivel());
    }
}
