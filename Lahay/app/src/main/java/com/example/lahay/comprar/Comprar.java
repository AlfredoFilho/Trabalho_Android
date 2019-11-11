package com.example.lahay.comprar;

public class Comprar {
    private String modelo;
    private String preco;
    private String descricao;

    public Comprar(){

    }

    public Comprar(String modelo, String preco, String descricao) {
        this.modelo = modelo;
        this.preco = preco;
        this.descricao = descricao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
