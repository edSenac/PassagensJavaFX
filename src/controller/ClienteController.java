package controller;

import main.Passagens; // MAIN
import dominio.Cliente;
import negocio.NegocioException;
import negocio.ClienteNegocio;
import util.DateUtil;
import view.PrintUtil;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

public class ClienteController implements Initializable {

    @FXML
    private VBox painelTabelaCliente;
    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnRg;
    @FXML
    private TableColumn<Cliente, String> tableColumnNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnTelefone;

    @FXML
    private AnchorPane painelFormularioCliente;
    @FXML
    private TextField textFieldRg;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldTelefone;

    private List<Cliente> listaClientes;
    private Cliente clienteSelecionado;

    private ObservableList<Cliente> observableListaClientes;
    private ClienteNegocio clienteNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteNegocio = new ClienteNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewClientes != null) {
            carregarTableViewClientes();
        }

    }        

    private void carregarTableViewClientes() {
        tableColumnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        listaClientes = clienteNegocio.listar();

        observableListaClientes = FXCollections.observableArrayList(listaClientes);
        tableViewClientes.setItems(observableListaClientes);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        clienteSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Passagens.class.getResource("view/PainelFormularioCliente.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaCliente.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewClientes();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Cliente clienteSelec = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelec != null) {
            FXMLLoader loader = new FXMLLoader(Passagens.class.getResource("view/PainelFormularioCliente.fxml"));
            Parent root = (Parent) loader.load();

            ClienteController controller = (ClienteController) loader.getController();
            controller.setClienteSelecionado(clienteSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaCliente.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewClientes();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um cliente para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException {
        Cliente clienteSelec = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelec != null) {
            try {
                clienteNegocio.deletar(clienteSelec);
                this.carregarTableViewClientes();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar um cliente para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioCliente.getScene().getWindow();
        
        if(clienteSelecionado == null) //Se for cadastrar
        {
            try {
                clienteNegocio.salvar(
                    new Cliente(
                        textFieldRg.getText(), 
                        textFieldNome.getText(), 
                        textFieldTelefone.getText()
                    )
                );
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
        }
        else //Se for editar
        {
            try {
                clienteSelecionado.setNome(textFieldNome.getText());
                clienteSelecionado.setTelefone(textFieldTelefone.getText());                        
                clienteNegocio.atualizar(clienteSelecionado);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioCliente.getScene().getWindow();
        stage.close();

    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
        textFieldRg.setText(clienteSelecionado.getRg());
        textFieldRg.setEditable(false);
        textFieldNome.setText(clienteSelecionado.getNome());
        textFieldTelefone.setText(clienteSelecionado.getTelefone());
    }
}
