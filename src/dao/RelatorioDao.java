/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dominio.Aviao;

/**
 *
 * @author Eduardo
 */
public interface RelatorioDao {
    public String porCliente(int id);
    public String porPassageiro(int id);
    public String porOrigem(String origem);
    public String porDestino(String destino);

    public int nVoosPorAviao(Aviao aviao);
}