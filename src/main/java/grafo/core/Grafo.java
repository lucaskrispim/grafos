package grafo.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

    private Integer qtdVertices;
    private List<Vertice> vertices = new ArrayList<>();

    private Map<String,Integer> rotulosEmIndices = new HashMap<>();

    private MatrizAdjacencia matrizAdjacencia;

    public Grafo(){
        this.qtdVertices = 0;
    }

    public void adicionarVertice(String rotulo){
        this.checkConditions(rotulo == null || (rotulo != null && "".equals(rotulo.trim())),"Não é permitida a inclusão de vértices com rótulo em branco.");
        this.vertices.add(new Vertice(rotulo));
        this.rotulosEmIndices.put(rotulo, qtdVertices);
        qtdVertices++;

    }

    public List<Vertice> getVertices(){
        return this.vertices;
    }

    public Vertice getVertice(String rotulo) {
        this.existeVerticeOrThrow(rotulo);
        int indice = this.rotulosEmIndices.get(rotulo);
        return this.vertices.get(indice);
    }

    public void conectarVertices(String rotuloVerticeInicial, String rotuloVerticeFinal, Integer peso){

        this.checkConditions(!this.existeVertice(rotuloVerticeInicial) || !this.existeVertice(rotuloVerticeFinal),"Para adicionar uma aresta ambos os vértices devem existir.");

        Map<String, Integer> rotulosEmIndices = getRotulosEmIndices();

        this.criarMatrizAdjacencia();

        MatrizAdjacencia matrizAdjacencia = getMatrizAdjacencia();

        int indiceVerticeInicial = rotulosEmIndices.get(rotuloVerticeInicial);
        int indiceVerticeFinal = rotulosEmIndices.get(rotuloVerticeFinal);

        matrizAdjacencia.adicionarArestaDirecionada(indiceVerticeInicial, indiceVerticeFinal, peso);
    }

    private void criarMatrizAdjacencia() {

        if(this.matrizAdjacencia == null){
            this.matrizAdjacencia = new MatrizAdjacencia(new ArrayList<>(this.vertices));
        } else {
            int qtdVerticesNaMatriz = this.matrizAdjacencia.getQtdVertices();

            if(this.vertices.size() != qtdVerticesNaMatriz) {
                MatrizAdjacencia matrizAdjacenciaTemp = new MatrizAdjacencia(this.vertices);
                this.matrizAdjacencia.copiaValoresPara(matrizAdjacenciaTemp);
                this.matrizAdjacencia = matrizAdjacenciaTemp;
            }
        }
    }

    private boolean existeVerticeOrThrow(String vertice) {

        this.checkConditions(!existeVertice(vertice),"O vértice não existe.");

        return true;
    }

    public boolean existeVertice(String rotuloVertice) {
        return !(this.rotulosEmIndices.get(rotuloVertice) == null);
    }

    public Integer getPeso(String rotuloVerticeInicial, String rotuloVerticeFinal)
    {
        this.checkConditions(!this.existeVertice(rotuloVerticeInicial) || !this.existeVertice(rotuloVerticeFinal),"Para receber o peso ambos os vértices devem existir.");

        int indiceVerticeInicial = rotulosEmIndices.get(rotuloVerticeInicial);
        int indiceVerticeFinal = rotulosEmIndices.get(rotuloVerticeFinal);

        return matrizAdjacencia.getPeso(indiceVerticeInicial, indiceVerticeFinal);

    }

    public Map<String, Integer> getRotulosEmIndices(){
        return rotulosEmIndices;
    }

    public MatrizAdjacencia getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public String getGrafo(){

        String s = "";

        for(Vertice vertice: this.vertices){
            if ( matrizAdjacencia.getAdjacencias(rotulosEmIndices.get(vertice.getRotulo())).size() > 0 ) {
                s = s.concat(vertice.getRotulo() + ": ");
                for (Vertice anotherVertice : this.vertices) {
                    if (this.getPeso(vertice.getRotulo(), anotherVertice.getRotulo()) > 0) {
                        s = s.concat(anotherVertice.getRotulo() + "-" + this.getPeso(vertice.getRotulo(), anotherVertice.getRotulo()) + " ");
                    }
                }
                s = s.concat("\n");
            }
        }

        return s;
    }

    private void checkConditions(Boolean cond,String msg) {
        if (cond) throw new IllegalArgumentException(msg);
    }



}
