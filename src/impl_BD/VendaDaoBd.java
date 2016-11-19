/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl_BD;

import dominio.Cliente;
import dominio.Venda;
import dominio.Voo;
import dao.VendaDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 631620220
 */
public class VendaDaoBd extends DaoBd<Venda> implements VendaDao{
    private VooDaoBd voos = new VooDaoBd();
    
    @Override
    public void salvar(Venda venda){
       int id = 0;
        try{
            String sql = "INSERT INTO venda (id_cliente, id_voo, horario) VALUES (?, ?, ?)";
            
            conectarObtendoId(sql);
            comando.setInt(1, venda.getCliente().getId());
            comando.setInt(2, venda.getVoo().getId());
            Timestamp timestamp;
            timestamp = new Timestamp(venda.getHorario_compra().getTime());
            comando.setTimestamp(3, timestamp);
            comando.executeUpdate();
            ResultSet resultado = comando.getGeneratedKeys();
            if(resultado.next()){
                id = resultado.getInt(1);
                venda.setId(id);
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
    public void atualizar(Venda venda){
        try{
            String sql = "UPDATE venda SET id_cliente=?, id_voo=?, horario=? WHERE id_venda=?";
            conectar(sql);
            comando.setInt(1, venda.getCliente().getId());
            comando.setInt(2, venda.getVoo().getId());
            Timestamp timestamp;
            timestamp = new Timestamp(venda.getHorario_compra().getTime());
            comando.setTimestamp(3, timestamp);
            comando.setInt(4, venda.getId());

            comando.executeUpdate();
            
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao atualizar paciente no Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
    }
    
    @Override
    public void deletar(Venda venda){
        try {
            String sql = "DELETE FROM venda WHERE id_venda = ?";

            conectar(sql);
            comando.setInt(1, venda.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar venda no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
    public void deletarPorCliente(int id_cliente){
        try {
            String sql = "DELETE FROM venda WHERE id_cliente = ?";

            conectar(sql);
            comando.setInt(1, id_cliente);
            comando.executeUpdate();
            System.out.println("Venda(s) removida(s) com sucesso.");
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar venda no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }    
    public void deletarPorAviao(int id_aviao){
        try {
            String sql = "DELETE FROM venda WHERE id_aviao = ?";

            conectar(sql);
            comando.setInt(1, id_aviao);
            comando.executeUpdate();
            System.out.println("Venda(s) removida(s) com sucesso.");
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar venda no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
        
    public void deletarPorVoo(int id_voo){
        try {
            String sql = "DELETE FROM venda WHERE id_voo = ?";

            conectar(sql);
            comando.setInt(1, id_voo);
            comando.executeUpdate();
            System.out.println("Venda(s) removida(s) com sucesso.");
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar venda no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
    @Override
    public List<Venda> listar(){
        List<Venda> listaVendas = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        
        try{
            conectar(sql);
            
            ResultSet resultado = comando.executeQuery();
            
            while(resultado.next()){
                int id = resultado.getInt("id_venda");
                int idCliente = resultado.getInt("id_cliente");
                int idVoo = resultado.getInt("id_voo");
                Date horario = resultado.getTimestamp("horario");
                
                ClienteDaoBd clienteDb = new ClienteDaoBd();
                Cliente cliente = clienteDb.procurarPorId(idCliente);
                
                VooDaoBd vooDb = new VooDaoBd();
                Voo voo = vooDb.procurarPorId(idVoo);
                
                Venda venda = new Venda(id, cliente, voo, horario);
                
                listaVendas.add(venda);
            }
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
        return listaVendas;
    }
    
    @Override
    public Venda procurarPorId(int id){
        String sql = "SELECT * FROM venda WHERE id_venda = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int idCliente = resultado.getInt("id_cliente");
                int idVoo = resultado.getInt("id_voo");
                Date horario = resultado.getTimestamp("horario");
                
                ClienteDaoBd clienteDao = new ClienteDaoBd();
                Cliente cliente = clienteDao.procurarPorId(idCliente);
                
                VooDaoBd vooDb = new VooDaoBd();
                Voo voo = vooDb.procurarPorId(idVoo);
                
                Venda venda = new Venda(id, cliente, voo, horario);

                return venda;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return null;
    }

    public void cadastraVenda(Cliente cliente, Voo voo) {
        int lugares = voo.getLugares();
        if(lugares > 0){
            this.salvar(new Venda(cliente, voo, new Date()));
            voo.setLugares(lugares -1);
            voos.atualizar(voo);
            System.out.println("Venda cadastrada com sucesso!");
        }
        else{
            System.out.println("Não há mais lugares disponíveis nesse vôo!");
        }
    }
}