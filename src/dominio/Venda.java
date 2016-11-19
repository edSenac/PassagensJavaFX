/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.Date;

/**
 * Classe de abstração de Venda
 * 
 * @author Eduardo
 */
public class Venda {
    
    private Cliente cliente;
    private Voo voo;
    private Date horario_compra;
    private int id;
    
    public Venda(int id, Cliente cliente, Voo voo, Date horario_compra) {
        this.id = id;
        this.cliente = cliente;
        this.voo = voo;
        this.horario_compra = horario_compra;
    }

    public Venda(Cliente cliente, Voo voo, Date horario_compra) {
        this.cliente = cliente;
        this.voo = voo;
        this.horario_compra = horario_compra;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Voo getVoo() {
        return voo;
    }

    public void setVoo(Voo voo) {
        this.voo = voo;
    }

    public Date getHorario_compra() {
        return horario_compra;
    }

    public void setHorario_compra(Date horario_compra) {
        this.horario_compra = horario_compra;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
}
