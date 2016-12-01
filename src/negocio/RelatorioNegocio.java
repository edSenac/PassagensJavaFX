/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dao.AviaoDao;
import dao.RelatorioDao;
import impl_BD.RelatorioDaoBd;
import impl_BD.AviaoDaoBd;

import dominio.Voo;
import dao.VooDao;
import impl_BD.VooDaoBd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.scene.chart.PieChart;

/**
 *
 * @author 631620220
 */
public class RelatorioNegocio {
    private VooDao voos;
    private AviaoDao avioes;
    private RelatorioDao relatorio;
    
    
    public List<PieChart.Data> dadosVooPorAviao(){
        voos = new VooDaoBd();
        avioes = new AviaoDaoBd();
        relatorio = new RelatorioDaoBd();
        Map<String, Integer> mapa = new HashMap<String, Integer>();
        
        List<PieChart.Data> resultados = new ArrayList<>();
        for(Voo v : voos.listar()){
            String nomeAviao = v.getAviao().getNome();
            int nVoos = relatorio.nVoosPorAviao(v.getAviao());
            
            
            
            if(mapa.containsKey(nomeAviao)){
                int valor = mapa.get(nomeAviao);
                mapa.put(nomeAviao, valor + 1);
            }else{
                mapa.put(nomeAviao, nVoos);
            }
        }
        
        for(Entry<String, Integer> entry: mapa.entrySet()) {
            resultados.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        
        return resultados;
    }
    
}
