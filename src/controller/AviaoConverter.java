/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AviaoDao;
import dominio.Aviao;
import impl_BD.AviaoDaoBd;
import javafx.util.StringConverter;

/**
 *
 * @author 631620220
 */
public class AviaoConverter extends StringConverter<Aviao>{

    private AviaoDao aviaoDao;

    @Override
    public String toString(Aviao cat) {
        //retorna propriedade que quero que visualize.
        if(cat==null) return null;
        return(cat.getNome());
    }

    @Override
    public Aviao fromString(String string) {
       aviaoDao = new AviaoDaoBd();
       Aviao a = aviaoDao.procurarPorNome(string);
    
       return a;
    }
    
}
