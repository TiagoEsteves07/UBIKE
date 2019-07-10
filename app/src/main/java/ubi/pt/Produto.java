package ubi.pt;

public class Produto {

    public String nomeP;
    public int pontosP;

    public Produto(){}

    public Produto(String nomeP, int pontosP) {
        this.nomeP = nomeP;
        this.pontosP = pontosP;
    }

    public String getNomeP() {
        return nomeP;
    }

    public void setNomeP(String nomeP) {
        this.nomeP = nomeP;
    }

    public int getPontosP() {
        return pontosP;
    }

    public void setPontosP(int pontosP) {
        this.pontosP = pontosP;
    }
}
