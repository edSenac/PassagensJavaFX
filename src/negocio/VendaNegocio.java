/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.List;
import dao.VendaDao;
import dominio.Venda;
import impl_BD.VendaDaoBd;
/**
 *
 * @author Eduardo
 */
public class VendaNegocio {
    
    private VendaDao vendaDao;
    
    public VendaNegocio(){
        vendaDao = new VendaDaoBd();
    }
    
    public void salvar(Venda v) throws NegocioException {
        this.validarCamposObrigatorios(v);
        vendaDao.salvar(v);
    }
    
    public List<Venda> listar(){
        return (vendaDao.listar());
    }
    
    
    public void deletar(Venda venda) throws NegocioException {
        if (venda == null) {
            throw new NegocioException("Voo nao existe!");
        }
        vendaDao.deletar(venda);
    }

    public void atualizar(Venda venda) throws NegocioException {
        if (venda == null) {
            throw new NegocioException("Voo nao existe!");
        }
        this.validarCamposObrigatorios(venda);
        vendaDao.atualizar(venda);
    }

    private void validarCamposObrigatorios(Venda v) throws NegocioException {
        if (v.getCliente() == null) {
            throw new NegocioException("Cliente nao informado");
        }

        if (v.getVoo() == null) {
            throw new NegocioException("Voo nao informado");
        }
        
    }
    
}
