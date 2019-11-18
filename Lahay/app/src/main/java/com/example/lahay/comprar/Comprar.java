package com.example.lahay.comprar;

import android.graphics.Bitmap;

public class Comprar {

    public String modeloCarro;
    public String estilo;
    public String ano;
    public String cor;
    public String cambio;
    public String descricao;
    public String preco;
    public Bitmap fotoCarrro;

    public Comprar() {

    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public Comprar(String modeloCarro, String estilo, String ano, String cor, String cambio, String descricao, String preco) {

    }

    public Bitmap getFotoCarrro() {
        return fotoCarrro;
    }

    public void setFotoCarrro(Bitmap fotoCarrro) {
        this.fotoCarrro = fotoCarrro;
    }
}