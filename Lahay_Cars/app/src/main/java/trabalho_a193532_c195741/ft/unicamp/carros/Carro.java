package trabalho_a193532_c195741.ft.unicamp.carros;

public class Carro {
    private int foto;
    private String descricao;
    private String modelo;
    private String ano;
    private String cor;
    private String preco;
    private String cambio;

    public Carro(String modelo, int foto, String descricao, String cor, String preco, String ano, String cambio) {
        this.foto = foto;
        this.descricao = descricao;
        this.modelo = modelo;
        this.cor= cor;
        this.ano = ano;
        this.preco = preco;
        this.cambio = cambio;
    }

    public String getCambio() { return cambio; }

    public void setCambio(String cambio) { this.cambio = cambio; }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
