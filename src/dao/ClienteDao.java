/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dominio.Cliente;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public interface ClienteDao extends Dao<Cliente>{
    public List<Cliente> procurarPorNome(String name);
    public Cliente procurarPorRg(String rg);
}