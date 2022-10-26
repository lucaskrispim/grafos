package grafo.core;

import java.util.*;
import java.util.stream.IntStream;

public class MatrizAdjacencia {
    private int[][] matriz;
    private List<Vertice> vertices;
    private int qtdVertices;

    public MatrizAdjacencia(List<Vertice> vertices){

        this.checkConditions(vertices == null || vertices.size() == 0,"Os vétices da matriz de incidência não podem ser nulos");

        this.vertices = vertices;
        this.qtdVertices = vertices.size();
        matriz = new int[qtdVertices][qtdVertices];
        inicializarMatriz();
    }

    private void inicializarMatriz(){

        for(int i=0; i<matriz.length; i++){
            for(int j=0; j<matriz[i].length; j++){
                this.escreveNaCelula(i,j,0);
            }
        }

    }

    public void adicionarArestaDirecionada(int indiceVerticeInicial, int indiceVerticeFinal) {

        try{

            Vertice verticeInicial = vertices.get(indiceVerticeInicial);
            if(indiceVerticeInicial == indiceVerticeFinal) {
                matriz[indiceVerticeInicial][indiceVerticeInicial] = 1;
                verticeInicial.addGrau();
            } else {
                matriz[indiceVerticeInicial][indiceVerticeFinal] = 1;
                Vertice verticeFinal = vertices.get(indiceVerticeFinal);
                verticeFinal.addGrau();
            }

        }catch (Exception e){
            System.out.println("Erro: "+e.getMessage());
        }

    }

    public void adicionarAresta(int indiceVerticeInicial, int indiceVerticeFinal) {
        this.adicionarArestaDirecionada(indiceVerticeInicial, indiceVerticeFinal);
    }

    public List<Vertice> getAdjacencias(int linha) {

        this.checkConditions(linha >= matriz[0].length || linha < 0,"Não é posível buscar o índice dentro da matriz de adjacências!");

        List<Vertice> adjacencias = new ArrayList<>();
        for(int j=0; j < qtdVertices; j++) {
            if(matriz[linha][j] != 0) {
                Vertice vertice = vertices.get(j);
                adjacencias.add(vertice);
            }
        }
        return adjacencias;
    }

    public Integer getPeso(int indiceVerticeInicial, int indiceVerticeFinal) {

        try{
            return this.matriz[indiceVerticeInicial][indiceVerticeFinal];
        }catch (Exception e){
            System.out.println("Erro: "+e.getMessage());
        }
        return null;
    }

    public Integer getQtdVertices() {
        return qtdVertices;
    }

    void copiaValoresPara(MatrizAdjacencia matrizDestino){

        this.checkConditions(matrizDestino.getQtdVertices() < this.qtdVertices,"Somente é possível executar cópias em matrizes com dimensões iguais ou a matriz de destino deve ter dimensões maiores que a matriz de origem.");

        for(int i=0; i < matriz.length; i++) {
            for(int j=0; j < matriz[i].length; j++) {
                matrizDestino.escreveNaCelula(i, j, matriz[i][j]);
            }
        }
    }

    private void escreveNaCelula(int linha, int coluna, int valor) {
        this.matriz[linha][coluna] = valor;
    }

    private void checkConditions(Boolean cond,String msg) {
        if (cond) throw new IllegalArgumentException(msg);
    }

    public int getLength(){
        return matriz.length;
    }

    public int[][] getMatriz() {
        return this.matriz;
    }

    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz[i].length; j++){
                s = s.concat(this.matriz[i][j]+" ");
            }
            s = s.concat("\n");
        }

        return s;
    }
}
