package grafo.core;

public class Vertice {
    private String rotulo;
    private Integer grau;

    public Vertice(String rotulo) {

        this.checkConditions(rotulo == null || rotulo != null && "".equals(rotulo.trim()),"Não é permitida a inclusão de vértices com rótulo em branco.");
        this.grau = 0;
        this.rotulo = rotulo;
    }

    public String getRotulo(){
        return this.rotulo;
    }

    private void checkConditions(Boolean cond,String msg) {
        if (cond) throw new IllegalArgumentException(msg);
    }

    void addGrau(){
        this.grau++;
    }
    public int getGrau() {
        return this.grau;
    }
}
