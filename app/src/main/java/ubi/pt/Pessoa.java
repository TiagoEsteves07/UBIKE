package ubi.pt;

import android.app.DatePickerDialog;

public class Pessoa {

    private String user_id;
    private String nome;
    private String email;
    private String data;
    private String distancia;
    private String idBicicleta;
    private String pontos;
    private String doc_id;

    public Pessoa(){}

    public Pessoa(String user_id, String nome, String email, String data, String distancia, String idBicicleta, String pontos, String doc_id) {
        this.user_id = user_id;
        this.nome = nome;
        this.email = email;
        this.data = data;
        this.distancia = distancia;
        this.idBicicleta = idBicicleta;
        this.pontos = pontos;
        this.doc_id = doc_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}
