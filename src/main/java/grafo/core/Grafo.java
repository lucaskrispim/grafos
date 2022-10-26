package grafo.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grafo {

    private Integer qtdVertices;
    private List<Vertice> vertices = new ArrayList<>();

    private Map<String,Integer> rotulosEmIndices;

    private Map<String,Map<String, Integer>> ligacoes;

    private MatrizAdjacencia matrizAdjacencia;

    public Grafo(){

        this.qtdVertices = 0;
        this.rotulosEmIndices = new HashMap<>();
        this.ligacoes = new HashMap<>();
    }

    public void adicionarVertice(String rotulo){
        this.checkConditions(rotulo == null || (rotulo != null && "".equals(rotulo.trim())),"Não é permitida a inclusão de vértices com rótulo em branco.");

        this.vertices.add(new Vertice(rotulo));
        this.rotulosEmIndices.put(rotulo, qtdVertices);

        ligacoes.put(rotulo,new HashMap<>());

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

        matrizAdjacencia.adicionarArestaDirecionada(indiceVerticeInicial, indiceVerticeFinal);

        ligacoes.get(rotuloVerticeInicial).put(rotuloVerticeFinal,peso);

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

        return ligacoes.get(rotuloVerticeInicial).get(rotuloVerticeFinal);

    }

    public Map<String, Integer> getRotulosEmIndices(){
        return rotulosEmIndices;
    }

    public MatrizAdjacencia getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public String getGrafo(){

        String s = "";

        for (String rotulo: ligacoes.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()) ){
            if( !ligacoes.get(rotulo).isEmpty()) {
                s = s.concat(rotulo + ": ");
                s = s.concat(ligacoes.get(rotulo).toString());
                s = s.concat("\n");
            }
        }

        return s;

    }

    private void checkConditions(Boolean cond,String msg) {
        if (cond) throw new IllegalArgumentException(msg);
    }



}
