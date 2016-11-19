/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dominio.Cliente;
import impl_BD.ClienteDaoBd;
import java.util.List;
import dao.ClienteDao;
/**
 *
 * @author Eduardo
 */
public class ClienteNegocio {
    
    private ClienteDao clienteDao;

    public ClienteNegocio() {
        clienteDao = new ClienteDaoBd();
    }

    public void salvar(Cliente p) throws NegocioException {
        this.validarCamposObrigatorios(p);
        this.validarRgExistente(p);
        clienteDao.salvar(p);
    }

    public List<Cliente> listar() {
        return (clienteDao.listar());
    }

    public void deletar(Cliente cliente) throws NegocioException {
        if (cliente == null || cliente.getRg() == null) {
            throw new NegocioException("Cliente nao existe!");
        }
        clienteDao.deletar(cliente);
    }

    public void atualizar(Cliente cliente) throws NegocioException {
        if (cliente == null || cliente.getRg() == null) {
            throw new NegocioException("Cliente nao existe!");
        }
        this.validarCamposObrigatorios(cliente);
        clienteDao.atualizar(cliente);
    }

    public Cliente procurarPorRg(String rg) throws NegocioException {
        if (rg == null || rg.isEmpty()) {
            throw new NegocioException("Campo RG nao informado");
        }
        Cliente cliente = clienteDao.procurarPorRg(rg);
        if (cliente == null) {
            throw new NegocioException("Cliente nao encontrado");
        }
        return (cliente);
    }

    public List<Cliente> procurarPorNome(String nome) throws NegocioException {
        if (nome == null || nome.isEmpty()) {
            throw new NegocioException("Campo nome nao informado");
        }
        return(clienteDao.procurarPorNome(nome));
    }

    public boolean clienteExiste(String rg) {
        Cliente cliente = clienteDao.procurarPorRg(rg);
        return (cliente != null);
    }

    private void validarCamposObrigatorios(Cliente p) throws NegocioException {
        if (p.getRg() == null || p.getRg().isEmpty()) {
            throw new NegocioException("Campo RG nao informado");
        }

        if (p.getNome() == null || p.getNome().isEmpty()) {
            throw new NegocioException("Campo nome nao informado");
        }
    }

    private void validarRgExistente(Cliente p) throws NegocioException {
        if (clienteExiste(p.getRg())) {
            throw new NegocioException("RG ja existente");
        }
    }
}
