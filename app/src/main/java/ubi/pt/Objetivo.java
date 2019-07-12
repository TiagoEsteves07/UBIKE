package ubi.pt;


public class Objetivo{
    public String descricao;
    public int id;
    public String pontos;

    public Objetivo(){}

    public Objetivo(String descricao, int id, String pontos) {
        this.descricao = descricao;
        this.id = id;
        this.pontos = pontos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}
