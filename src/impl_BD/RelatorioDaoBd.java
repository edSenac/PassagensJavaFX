/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl_BD;

import dao.RelatorioDao;
import dominio.Aviao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Eduardo
 */
public class RelatorioDaoBd implements RelatorioDao{
    protected Connection conexao;
    protected PreparedStatement comando;
    
    public Connection conectar(String sql) throws SQLException {
        conexao = BDUtil.getConnection();
        comando = conexao.prepareStatement(sql);
        return conexao;
    }

    public void conectarObtendoId(String sql) throws SQLException {
        conexao = BDUtil.getConnection();
        comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    public void fecharConexao() {
        try {
            if (comando != null) {
                comando.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Erro ao encerrar a conexao");
            throw new BDException(ex);

        }

    }
    @Override
    public String porCliente(int id) {
        String sql = "SELECT * FROM venda "
                + "JOIN cliente USING(id_cliente) "
                + "WHERE id_cliente = ?";
        
        String retorno = new String();
        ClienteDaoBd cliente = new ClienteDaoBd();
        
        try{
            conectar(sql);
            
            comando.setInt(1, id);
            
            ResultSet resultado = comando.executeQuery();
            
            while(resultado.next()){
                String nomeCliente = resultado.getString("nome");
                int idVoo = resultado.getInt("id_voo");
                int idVenda = resultado.getInt("id_venda");
                Date horario = resultado.getTimestamp("horario");
                
                retorno = String.format("%-20s", cliente.procurarPorId(id).getNome()) + "\t"
                + String.format("%-20s", "|" + idVoo) + "\t"
                + String.format("%-20s", "|" + idVenda) + "\t"
                + String.format("%-20s", "|" + horario) + "\n";
            }
            return retorno;
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
    }

    @Override
    public String porPassageiro(int id) {
        String sql = "SELECT id_voo, aviao.nome, origem, destino, voo.horario"
                + " FROM venda "
                + "JOIN voo USING(id_voo) "
                + "JOIN aviao USING (id_aviao)"
                + "WHERE id_cliente = ?";

        String retorno = new String();
        
        try{
            conectar(sql);
            
            comando.setInt(1, id);
            
            ResultSet resultado = comando.executeQuery();
            
            while(resultado.next()){
                int idVoo = resultado.getInt("id_voo");
                String nomeAviao = resultado.getString("nome");
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                Date horario = resultado.getTimestamp("horario");
                
                retorno = String.format("%-20s", idVoo) + "\t"
                + String.format("%-20s", "|" + nomeAviao) + "\t"
                + String.format("%-20s", "|" + origem) + "\t"
                + String.format("%-20s", "|" + destino) + "\t"
                + String.format("%-20s", "|" + horario) + "\n";               
            }
            
            return retorno;
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
    }

    @Override
    public String porOrigem(String origem) {
         String sql = "SELECT id_voo, nome, destino, horario"
                + " FROM voo "
                + "JOIN aviao USING(id_aviao) "
                + "WHERE origem = ?";

        String retorno = new String();
         
        try{
            conectar(sql);
            
            comando.setString(1, origem);
            
            ResultSet resultado = comando.executeQuery();
            
            while(resultado.next()){
                int idVoo = resultado.getInt("id_voo");
                String nomeAviao = resultado.getString("nome");
                String destino = resultado.getString("destino");
                Date horario = resultado.getTimestamp("horario");
                
                retorno = String.format("%-20s", idVoo) + "\t"
                + String.format("%-20s", "|" + nomeAviao) + "\t"
                + String.format("%-20s", "|" + origem) + "\t"
                + String.format("%-20s", "|" + destino) + "\t"
                + String.format("%-20s", "|" + horario) + "\n";
            }
            return retorno;
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
    }

    @Override
    public String porDestino(String destino) {
        String sql = "SELECT id_voo, nome, origem, horario FROM voo "
                + "JOIN aviao USING(id_aviao) "
                + "WHERE destino = ?";

        String retorno = new String();
        
        try{
            conectar(sql);
            
            comando.setString(1, destino);
            
            ResultSet resultado = comando.executeQuery();
            
            while(resultado.next()){
                int idVoo = resultado.getInt("id_voo");
                String nomeAviao = resultado.getString("nome");
                String origem = resultado.getString("origem");
                Date horario = resultado.getTimestamp("horario");
                
                retorno = String.format("%-20s", idVoo) + "\t"
                + String.format("%-20s", "|" + nomeAviao) + "\t"
                + String.format("%-20s", "|" + origem) + "\t"
                + String.format("%-20s", "|" + destino) + "\t"
                + String.format("%-20s", "|" + horario) + "\n";
                
                return retorno;
            }
        }catch(SQLException ex){
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
        return null;
    }
    
    public int nVoosPorAviao(Aviao a){
        String sql = "SELECT COUNT(id_voo) AS voos FROM voo "
                + "WHERE id_aviao = ?";

        try{
            conectar(sql);
            
            comando.setInt(1, a.getId());
            System.out.println(comando);
            
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                int voos = resultado.getInt("voos");
                return voos;
            }
            
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            System.err.println("Erro de Sistema - Problema ao buscar os Voos do Banco de Dados!");
            throw new BDException(ex);
        }finally{
            fecharConexao();
        }
        return 0;
        
    }

    
    
}