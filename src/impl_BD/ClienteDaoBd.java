/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl_BD;

import dominio.Cliente;
import dao.ClienteDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Eduardo
 */
public class ClienteDaoBd extends DaoBd<Cliente> implements ClienteDao{
    
    @Override
    public void salvar(Cliente cliente){
        int id = 0;
        try{
            String sql = "INSERT INTO cliente (rg, nome, telefone) VALUES (?, ?, ?)";
            
            conectarObtendoId(sql);
            comando.setString(1, cliente.getRg());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getTelefone());
            comando.executeUpdate();
            ResultSet resultado = comando.getGeneratedKeys();
            if(resultado.next()){
                id = resultado.getInt(1);
                cliente.setCodigo(id);
            }else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao salvar paciente no Banco de Dados!");
            throw new BDException(ex);
        }finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        try{
            String sql = "UPDATE cliente SET rg=?, nome=?, telefone=? WHERE id_cliente=?";
            conectar(sql);
            comando.setString(1, cliente.getRg());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getTelefone());
            comando.setInt(4, cliente.getId());

            comando.executeUpdate();
            
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao atualizar paciente no Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
    }

    @Override
    public void deletar(Cliente cliente) {
        try {
            String sql = "DELETE FROM cliente WHERE id_cliente = ?";

            conectar(sql);
            comando.setInt(1, cliente.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        
        try{
            conectar(sql);
            
            ResultSet resultado = comando.executeQuery();
            
            while(resultado.next()){
                int id = resultado.getInt("id_cliente");
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                
                Cliente cliente = new Cliente(id, nome, rg, telefone);
                
                listaClientes.add(cliente);
            }
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
        return listaClientes;
    }

    @Override
    public List<Cliente> procurarPorNome(String name) {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE nome LIKE ?";

        try {
            conectar(sql);
            comando.setString(1, "%" + name + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id_cliente");
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");

                Cliente cliente = new Cliente(id, rg, nome,telefone);

                listaClientes.add(cliente);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes pelo nome do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return listaClientes;
    }

    @Override
    public Cliente procurarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                
                Cliente cliente = new Cliente(id,nome, rg, telefone);

                return cliente;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return null;
    }

    @Override
    public Cliente procurarPorRg(String rg) {
        String sql = "SELECT * FROM cliente WHERE rg = ?";

        try {
            conectar(sql);
            comando.setString(1, rg);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id_cliente");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                
                Cliente cliente = new Cliente(id, nome, rg, telefone);

                return cliente;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return null;
    }
}