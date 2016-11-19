package dominio;

/**
 * @author lhries
 */
public class Aviao {
    private int id;
    private String nome;
    private int assentos;

    public Aviao(String nome, int assentos) {
        this.nome = nome;
        this.assentos = assentos;
    }

    public Aviao(int id, String nome, int assentos) {
        this.id = id;
        this.nome = nome;
        this.assentos = assentos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAssentos() {
        return assentos;
    }

    public void setAssentos(int assentos) {
        this.assentos = assentos;
    }    
    
}
