
public class Mercadoria {
    private String codigo;
    private String descricao;
    private double preco;

    public Mercadoria(String codigo, String descricao, double preco) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
}