package com.example.lahay.vender;

public class VendaData {

    public String modeloCarro;
    public String estilo;
    public String ano;
    public String cor;
    public String cambio;
    public String descricao;
    public String preco;

    public VendaData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public VendaData(String modeloCarro, String estilo, String ano, String cor, String cambio, String descricao, String preco) {
        this.modeloCarro = modeloCarro;
        this.estilo = estilo;
        this.ano = ano;
        this.cor = cor;
        this.cambio = cambio;
        this.descricao = descricao;
        this.preco = preco;
    }

}
