/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import negocio.RelatorioNegocio;

/**
 * FXML Controller class
 *
 * @author 631620220
 */
public class PainelGraphController implements Initializable {

    @FXML
    private PieChart pieChartVoo;
    private RelatorioNegocio relatorio;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        relatorio = new RelatorioNegocio();
        pieChartVoo.setData(FXCollections.observableArrayList(relatorio.dadosVooPorAviao()));
    }
    
}
