/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.Date;

/**
 * Classe de abstração de Voo
 *
 * @author Eduardo
 */
public class Voo {
    
    private int id;
    private String origem;
    private String destino;
    private Date horario;
    private Aviao aviao;
    private int lugares;
    
    /**
     * Método construtor da classe
     * 
     * @param id
     * @param origem
     * @param destino
     * @param horario
     * @param aviao 
     */
    public Voo(int id, String origem, String destino, Date horario, Aviao aviao, int lugares) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.horario = horario;
        this.aviao = aviao;
        this.lugares = lugares;
    }
    
    public Voo(String origem, String destino, Date horario, Aviao aviao, int lugares) {
        this.origem = origem;
        this.destino = destino;
        this.horario = horario;
        this.aviao = aviao;
        this.lugares = lugares;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * Getter
     * @return  códito do voo
     */
    public int getId(){
        return id;
    }
    
    /**
     * Getter
     * @return String origem do voo
     */
    public String getOrigem() {
        return origem;
    }
    
    /**
     * Setter
     * @param origem do voo
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * Getter
     * @return destino do voo
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Setter
     * @param destino do voo
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * Getter
     * @return horário do voo
     */
    public Date getHorario() {
        return horario;
    }

    /**
     * Setter
     * @param horario do voo
     */
    public void setHorario(Date horario) {
        this.horario = horario;
    }

    /**
     * Getter
     * @return objeto avião do voo
     */
    public Aviao getAviao() {
        return aviao;
    }

    /**
     * Setter
     * @param aviao do voo
     */
    public void setAviao(Aviao aviao) {
        this.aviao = aviao;
    }
    
    public int getLugares(){
        return lugares;
    }
    
    public void setLugares(int lugares){
        this.lugares = lugares;
    }
    
}
