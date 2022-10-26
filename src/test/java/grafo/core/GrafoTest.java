package grafo.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GrafoTest {

    private Grafo grafo;

    @BeforeAll
    public void setUp() {
        this.grafo = new Grafo();
        this.grafo.adicionarVertice("um");
        this.grafo.adicionarVertice("dois");

        this.grafo.conectarVertices("um","dois",1);
        this.grafo.conectarVertices("dois","um",1);
    }

    @Test
    public void grafoDeveEnviarErroSeRotuloForEmBranco()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.grafo.adicionarVertice(" "));
        assertEquals("Não é permitida a inclusão de vértices com rótulo em branco.", exception.getMessage());
    }

    @Test
    public void grafoDeveEnviarErroSeRotuloForNulo()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.grafo.adicionarVertice(null));
        assertEquals("Não é permitida a inclusão de vértices com rótulo em branco.", exception.getMessage());
    }

    @Test
    public void grafoComDoisRotulosDeveterSizeDois(){
        assertEquals(this.grafo.getVertices().size(),2);
    }

    @Test
    public void grafoDeveTerUmEDoisComoRotulo(){

        List<String> listaDerotulosDeVertices = this.grafo.getVertices().stream().map(x->x.getRotulo()).collect(Collectors.toList());

        assertTrue(listaDerotulosDeVertices.contains("um"));
        assertTrue(listaDerotulosDeVertices.contains("dois"));

    }

    @Test
    public void verificarRotulos(){
        assertEquals(this.grafo.getVertices().get(0).getRotulo(),"um");
    }

    @Test
    public void verificaQueTemORotulo(){
        assertTrue(this.grafo.existeVertice("um"));
    }

    @Test
    public void verificaQueNaoTemORotulo(){
        assertFalse(this.grafo.existeVertice("tres"));
    }

    @Test
    public void verificaQueNaoFazConexaoComRotulosInexistentes(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> this.grafo.conectarVertices("um", "tres", 1));
        assertEquals("Para adicionar uma aresta ambos os vértices devem existir.", exception.getMessage());
    }

    @Test
    public void verificarVerticePeloRotulo(){
        assertTrue(this.grafo.getVertice("um").getRotulo().equals(new Vertice("um").getRotulo() ));
    }

    @Test
    public void verificarPeso(){
        assertTrue(this.grafo.getPeso("um","dois").equals(1));
    }

    @Test
    public void verificarPeso2(){
        assertTrue(this.grafo.getPeso("dois","um").equals(1));
    }

    @Test
    public void verificarPesoLiberaExcessao(){
        //Exception exception = assertThrows(Exception.class, () -> this.grafo.getPeso("dois","quatro"));

        assertThrowsExactly(Exception.class, () -> this.grafo.getPeso("dois","quatro"));

        //assertEquals("Para adicionar uma aresta ambos os vértices devem existir.", exception.getMessage());
    }


}
