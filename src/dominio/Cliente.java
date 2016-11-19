/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author Eduardo
 */
public class Cliente {
    
    private int id;
    private String rg;
    private String nome;
    private String telefone;

    public Cliente(int id, String nome, String rg, String telefone) {
       this.id = id;
       this.nome = nome;
       this.rg = rg;
       this.telefone = telefone;
    }

    public Cliente(String nome, String rg, String telefone) {
       this.nome = nome;
       this.rg = rg;
       this.telefone = telefone;
    }
    

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCodigo(int id) {
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    
}
