/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dominio.Voo;
import impl_BD.VooDaoBd;
import java.util.List;
import dao.VooDao;
/**
 *
 * @author Eduardo
 */
public class VooNegocio {
    
    private VooDao vooDao;
    
    public VooNegocio(){
        vooDao = new VooDaoBd();
    }
    
    public void salvar(Voo v) throws NegocioException {
        this.validarCamposObrigatorios(v);
        vooDao.salvar(v);
    }
    
    public List<Voo> listar(){
        return (vooDao.listar());
    }
    
    
    public void deletar(Voo voo) throws NegocioException {
        if (voo == null) {
            throw new NegocioException("Voo nao existe!");
        }
        vooDao.deletar(voo);
    }

    public void atualizar(Voo voo) throws NegocioException {
        if (voo == null) {
            throw new NegocioException("Voo nao existe!");
        }
        this.validarCamposObrigatorios(voo);
        vooDao.atualizar(voo);
    }

    private void validarCamposObrigatorios(Voo v) throws NegocioException {
        if (v.getAviao() == null) {
            throw new NegocioException("Aviao assentos nao informado");
        }

        if (v.getOrigem() == null || v.getOrigem().isEmpty()) {
            throw new NegocioException("Origem nao informada");
        }
        
        if (v.getDestino() == null || v.getDestino().isEmpty()) {
            throw new NegocioException("Destino nao informada");
        }
        
        if (v.getHorario() == null) {
            throw new NegocioException("Horario nao informado");
        }
        
    }
    
}
