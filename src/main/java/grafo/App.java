package grafo;


import grafo.core.Grafo;

public class App
{
    public static void main( String[] args )
    {

        Grafo grafo = new Grafo();

        grafo.adicionarVertice("RJ");
        grafo.adicionarVertice("SP");
        grafo.adicionarVertice("BH");


        grafo.conectarVertices("RJ","SP",1);

        grafo.conectarVertices("RJ","BH",2);

        grafo.conectarVertices("BH","BH",2);

        System.out.println(grafo.getGrafo());
        System.out.println(" ");
        System.out.println(grafo.getMatrizAdjacencia().toString());


        grafo.adicionarVertice("OS");
        grafo.adicionarVertice("PT");

        grafo.conectarVertices("PT","PT",3);

        System.out.println(grafo.getGrafo());
        System.out.println(" ");
        System.out.println(grafo.getMatrizAdjacencia().toString());

    }
}
