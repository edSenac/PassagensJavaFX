package dao;

import dominio.Aviao;
import java.util.List;

/**
 *
 * @author lhries
 */

//A ideia da interface Dao é que padronizar todos os métodos do CRUD da aplicação.
public interface Dao<T> {
    public void salvar(T dominio);
    public void deletar(T dominio);
    public void atualizar(T dominio);
    public List<T> listar();
    public T procurarPorId(int id);
}
