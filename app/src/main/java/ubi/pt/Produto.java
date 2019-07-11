package ubi.pt;

public class Produto {

    public String nome;
    public String pontos;
    public String quantidade;

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", pontos='" + pontos + '\'' +
                ", quantidade='" + quantidade + '\'' +
                '}';
    }

    public Produto(){}

    public Produto(String nome, String pontos, String quantidade) {
        this.nome = nome;
        this.pontos = pontos;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}
