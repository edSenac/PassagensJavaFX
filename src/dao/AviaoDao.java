
package dao;

import dominio.Aviao;

/**
 *
 * @author lhries
 */
//Além dos métodos do Crud padronizado na interface Dao, dois metodos sao obrigatorios.
public interface AviaoDao extends Dao<Aviao>{
    public Aviao procurarPorNome(String nome);
}
