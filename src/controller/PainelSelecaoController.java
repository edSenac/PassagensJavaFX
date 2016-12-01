/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Passagens;

/**
 * FXML Controller class
 *
 * @author Eduardo
 */
public class PainelSelecaoController implements Initializable {
    
    @FXML
    private AnchorPane painelSelecao;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void abrePainel(String painel) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Passagens.class.getResource(painel));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelSelecao.getScene().getWindow());
        stage.showAndWait();
    }
    
    public void abreClientes(ActionEvent event) throws IOException {
        System.out.println("Abrindo Clientes");
        abrePainel("/view/PainelTabelaCliente.fxml");
    }
    
    public void abreAvioes(ActionEvent event) throws IOException {
        System.out.println("Abrindo Avioes");
        abrePainel("/view/PainelTabelaAviao.fxml");
        
    }
    
    public void abreVoos(ActionEvent event) throws IOException {
        System.out.println("Abrindo Voos");
        abrePainel("/view/PainelTabelaVoo.fxml");
    }
    
    public void abreVendas(ActionEvent event) throws IOException {
        System.out.println("Abrindo Vendas");
        abrePainel("/view/PainelTabelaVenda.fxml");
    }
    
    public void abreRelatorio(ActionEvent event) throws IOException {
        System.out.println("Abrindo Relatorios");
        abrePainel("/view/PainelGraph.fxml");
    }
    
}
