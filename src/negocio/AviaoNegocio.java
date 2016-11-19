package negocio;

import dominio.Aviao;
import impl_BD.AviaoDaoBd;
import java.util.List;
import dao.AviaoDao;

public class AviaoNegocio {

    private AviaoDao aviaoDao;

    public AviaoNegocio() {
        aviaoDao = new AviaoDaoBd();
    }

    public void salvar(Aviao p) throws NegocioException {
        this.validarCamposObrigatorios(p);
        this.validarNomeExistente(p);
        aviaoDao.salvar(p);
    }

    public List<Aviao> listar() {
        return (aviaoDao.listar());
    }

    public void deletar(Aviao aviao) throws NegocioException {
        if (aviao == null || aviao.getNome()== null) {
            throw new NegocioException("Aviao nao existe!");
        }
        aviaoDao.deletar(aviao);
    }

    public void atualizar(Aviao aviao) throws NegocioException {
        if (aviao == null || aviao.getNome() == null) {
            throw new NegocioException("Aviao nao existe!");
        }
        this.validarCamposObrigatorios(aviao);
        aviaoDao.atualizar(aviao);
    }

    public Aviao procurarPorNome(String nome) throws NegocioException {
        if (nome == null || nome.isEmpty()) {
            throw new NegocioException("Nome nao informado");
        }
        Aviao aviao = aviaoDao.procurarPorNome(nome);
        if (aviao == null) {
            throw new NegocioException("Aviao nao encontrado");
        }
        return (aviao);
    }

    public boolean aviaoExiste(String nome) {
        Aviao aviao = aviaoDao.procurarPorNome(nome);
        return (aviao != null);
    }

    private void validarCamposObrigatorios(Aviao p) throws NegocioException {
        if (p.getAssentos() <= 0) {
            throw new NegocioException("Numero de assentos nao informado");
        }

        if (p.getNome() == null || p.getNome().isEmpty()) {
            throw new NegocioException("Nome nao informado");
        }
    }

    private void validarNomeExistente(Aviao p) throws NegocioException {
        if (aviaoExiste(p.getNome())) {
            throw new NegocioException("Nome ja existente");
        }
    }

}
