/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl_BD;

import dominio.Aviao;
import dominio.Voo;
import dao.VooDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class VooDaoBd extends DaoBd<Voo> implements VooDao{

    @Override
    public void salvar(Voo voo) {
        int id = 0;
        try{
            String sql = "INSERT INTO voo (origem, destino, horario, id_aviao, lugares) VALUES (?, ?, ?, ?, ?)";
            
            conectarObtendoId(sql);
            comando.setString(1, voo.getOrigem());
            comando.setString(2, voo.getDestino());
            Timestamp timestamp = new Timestamp(voo.getHorario().getTime());
            comando.setTimestamp(3, timestamp);
            comando.setInt(4, voo.getAviao().getId());
            comando.setInt(5, voo.getLugares());

            comando.executeUpdate();
            ResultSet resultado = comando.getGeneratedKeys();
            if(resultado.next()){
                id = resultado.getInt(1);
                voo.setId(id);
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
    public void deletar(Voo voo) {
        try {
            String sql = "DELETE FROM voo WHERE id_voo = ?";

            conectar(sql);
            comando.setInt(1, voo.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
    public void deletarPorAviao(int id_aviao) {
       try {
            String sql = "DELETE FROM voo WHERE id_aviao = ?";

            conectar(sql);
            comando.setInt(1, id_aviao);
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Voo voo) {
        try{
            String sql = "UPDATE voo SET origem=?, destino=?, horario=?, id_aviao=?, lugares=? WHERE id_voo=?";
            conectar(sql);
            comando.setString(1, voo.getOrigem());
            comando.setString(2, voo.getDestino());
            Timestamp timestamp = new Timestamp(voo.getHorario().getTime());
            comando.setTimestamp(3, timestamp);
            comando.setInt(4, voo.getAviao().getId());
            comando.setInt(5, voo.getLugares());
            comando.setInt(6, voo.getId());
            comando.executeUpdate();
            
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao atualizar Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
    }

    @Override
    public List<Voo> listar() {
        List<Voo> listaVoos = new ArrayList<>();
        String sql = "SELECT * FROM voo";
        
        try{
            conectar(sql);
            
            ResultSet resultado = comando.executeQuery();
            
            while(resultado.next()){
                int id = resultado.getInt("id_voo");
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                Date horario = resultado.getTimestamp("horario");
                int idAviao = resultado.getInt("id_aviao");
                AviaoDaoBd aviaoDao = new AviaoDaoBd();
                Aviao aviao = aviaoDao.procurarPorId(idAviao);
                int lugares = resultado.getInt("lugares");
                
                Voo voo = new Voo(id, origem, destino, horario, aviao, lugares);
                
                listaVoos.add(voo);
            }
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
        return listaVoos;
    }

    @Override
    public Voo procurarPorId(int id) {
        String sql = "SELECT * FROM voo WHERE id_voo = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                Date horario = resultado.getTimestamp("horario");
                int idAviao = resultado.getInt("id_aviao");
                AviaoDaoBd aviaoDao = new AviaoDaoBd();
                Aviao aviao = aviaoDao.procurarPorId(idAviao);
                int lugares = resultado.getInt("lugares");
                
                Voo voo = new Voo(id, origem, destino, horario, aviao, lugares);

                return voo;
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
