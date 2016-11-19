/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import main.Passagens;
import dominio.Voo;
import dominio.Voo;
import negocio.NegocioException;
import negocio.VooNegocio;
import view.PrintUtil;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 631620220
 */
public class VooController implements Initializable {
    
    @FXML
    private VBox painelTabelaVoo;
    @FXML
    private TableView<Voo> tableViewAvioes;
    @FXML
    private TableColumn<Voo, String> tableColumnNome;
    @FXML
    private TableColumn<Voo, String> tableColumnAssentos;
    @FXML
    private AnchorPane painelFormularioVoo;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldAssentos;
    
    private List<Voo> listaAvioes;
    private Voo vooSelecionado;

    private ObservableList<Voo> observableListaAvioes;
    private VooNegocio vooNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vooNegocio = new VooNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewAvioes != null) {
            carregarTableViewAvioes();
        }

    }        

    private void carregarTableViewAvioes() {
        tableColumnAssentos.setCellValueFactory(new PropertyValueFactory<>("assentos"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        //listaAvioes = vooNegocio.listar();

        observableListaAvioes = FXCollections.observableArrayList(listaAvioes);
        tableViewAvioes.setItems(observableListaAvioes);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        vooSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Passagens.class.getResource("/view/PainelFormularioVoo.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaVoo.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewAvioes();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Voo vooSelec = tableViewAvioes.getSelectionModel().getSelectedItem();
        if (vooSelec != null) {
            FXMLLoader loader = new FXMLLoader(Passagens.class.getResource("/view/PainelFormularioVoo.fxml"));
            Parent root = (Parent) loader.load();

            VooController controller = (VooController) loader.getController();
            controller.setVooSelecionado(vooSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaVoo.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewAvioes();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um voo para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException {
        Voo vooSelec = tableViewAvioes.getSelectionModel().getSelectedItem();
        if (vooSelec != null) {
            /*try {
                vooNegocio.deletar(vooSelec);
                this.carregarTableViewAvioes();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }*/
        } else {
            PrintUtil.printMessageError("Precisa selecionar um voo para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioVoo.getScene().getWindow();
        
        if(vooSelecionado == null) //Se for cadastrar
        {
            /*try {
                vooNegocio.salvar(new Voo(
                        textFieldNome.getText(), 
                        Integer.parseInt(textFieldAssentos.getText())
                ));                
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            */
        }
        else //Se for editar
        {/*
            try {
                vooSelecionado.setNome(textFieldNome.getText());
                vooSelecionado.setAssentos(Integer.parseInt(textFieldAssentos.getText()));
                vooNegocio.atualizar(vooSelecionado);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
*/
            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioVoo.getScene().getWindow();
        stage.close();

    }

    public Voo getVooSelecionado() {
        return vooSelecionado;
    }

    public void setVooSelecionado(Voo vooSelecionado) {
        this.vooSelecionado = vooSelecionado;
        /*textFieldNome.setText(vooSelecionado.getNome());
        textFieldAssentos.setText(String.valueOf(vooSelecionado.getAssentos()));*/
    }    
}
