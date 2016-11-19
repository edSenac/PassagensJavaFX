package controller;

import main.Passagens;
import dominio.Aviao;
import negocio.NegocioException;
import negocio.AviaoNegocio;
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

public class AviaoController implements Initializable {

    @FXML
    private VBox painelTabelaAviao;
    @FXML
    private TableView<Aviao> tableViewAvioes;
    @FXML
    private TableColumn<Aviao, String> tableColumnNome;
    @FXML
    private TableColumn<Aviao, String> tableColumnAssentos;
    @FXML
    private AnchorPane painelFormularioAviao;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldAssentos;
    
    private List<Aviao> listaAvioes;
    private Aviao aviaoSelecionado;

    private ObservableList<Aviao> observableListaAvioes;
    private AviaoNegocio aviaoNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aviaoNegocio = new AviaoNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewAvioes != null) {
            carregarTableViewAvioes();
        }

    }        

    private void carregarTableViewAvioes() {
        tableColumnAssentos.setCellValueFactory(new PropertyValueFactory<>("assentos"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        listaAvioes = aviaoNegocio.listar();

        observableListaAvioes = FXCollections.observableArrayList(listaAvioes);
        tableViewAvioes.setItems(observableListaAvioes);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        aviaoSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Passagens.class.getResource("/view/PainelFormularioAviao.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaAviao.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewAvioes();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Aviao aviaoSelec = tableViewAvioes.getSelectionModel().getSelectedItem();
        if (aviaoSelec != null) {
            FXMLLoader loader = new FXMLLoader(Passagens.class.getResource("/view/PainelFormularioAviao.fxml"));
            Parent root = (Parent) loader.load();

            AviaoController controller = (AviaoController) loader.getController();
            controller.setAviaoSelecionado(aviaoSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaAviao.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewAvioes();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um aviao para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException {
        Aviao aviaoSelec = tableViewAvioes.getSelectionModel().getSelectedItem();
        if (aviaoSelec != null) {
            try {
                aviaoNegocio.deletar(aviaoSelec);
                this.carregarTableViewAvioes();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar um aviao para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioAviao.getScene().getWindow();
        
        if(aviaoSelecionado == null) //Se for cadastrar
        {
            try {
                aviaoNegocio.salvar(new Aviao(
                        textFieldNome.getText(), 
                        Integer.parseInt(textFieldAssentos.getText())
                ));                
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
        }
        else //Se for editar
        {
            try {
                aviaoSelecionado.setNome(textFieldNome.getText());
                aviaoSelecionado.setAssentos(Integer.parseInt(textFieldAssentos.getText()));
                aviaoNegocio.atualizar(aviaoSelecionado);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioAviao.getScene().getWindow();
        stage.close();

    }

    public Aviao getAviaoSelecionado() {
        return aviaoSelecionado;
    }

    public void setAviaoSelecionado(Aviao aviaoSelecionado) {
        this.aviaoSelecionado = aviaoSelecionado;
        textFieldNome.setText(aviaoSelecionado.getNome());
        textFieldAssentos.setText(String.valueOf(aviaoSelecionado.getAssentos()));
    }
}
