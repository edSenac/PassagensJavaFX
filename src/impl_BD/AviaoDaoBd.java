package impl_BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dominio.Aviao;
import dao.AviaoDao;

public class AviaoDaoBd extends DaoBd<Aviao> implements AviaoDao {
  

    //Metodo salvar: trabalhar com data e recebe o id auto-increment 
    //e já relaciona no objeto paciente (recebido por parâmetro)
    //Caso queira retornar, só retornar id.
    @Override
    public void salvar(Aviao aviao) {
        int id = 0;
        try {
            String sql = "INSERT INTO aviao (nome, assentos) "
                    + "VALUES (?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, aviao.getNome());
            comando.setInt(2, aviao.getAssentos());
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                aviao.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar aviao no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Aviao aviao) {
        try {
            String sql = "DELETE FROM aviao WHERE id_aviao = ?";

            conectar(sql);
            comando.setInt(1, aviao.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar paciente no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

    }

    @Override
    public void atualizar(Aviao aviao) {
        try {
            String sql = "UPDATE aviao SET nome=?, assentos=? "
                    + "WHERE id_aviao=?";

            conectar(sql);
            comando.setString(1, aviao.getNome());
            comando.setInt(2, aviao.getAssentos());
            comando.setInt(3, aviao.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar aviao no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

    }

    @Override
    public List<Aviao> listar() {
        List<Aviao> listaAvioes = new ArrayList<>();

        String sql = "SELECT * FROM aviao";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id_aviao");
                String nome = resultado.getString("nome");
                int assentos = resultado.getInt("assentos");
                
                Aviao aviao = new Aviao(id, nome, assentos);

                listaAvioes.add(aviao);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os avioes do Banco de Dados!");
            System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaAvioes);
    }

    @Override
    public Aviao procurarPorId(int id) {
        String sql = "SELECT * FROM aviao WHERE id_aviao = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome");
                int assentos = resultado.getInt("assentos");

                Aviao aviao = new Aviao(id, nome, assentos);

                return aviao;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo id do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public Aviao procurarPorNome(String nome) {
        String sql = "SELECT * FROM aviao WHERE nome = ?";

        try {
            conectar(sql);
            comando.setString(1, nome);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id_aviao");
                int assentos = resultado.getInt("assentos");

                Aviao aviao = new Aviao(id, nome, assentos);

                return aviao;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo rg do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
    
}
