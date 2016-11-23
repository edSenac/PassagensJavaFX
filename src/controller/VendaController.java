/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dominio.Aviao;
import main.Passagens;
import dominio.Venda;
import impl_BD.ClienteDaoBd;
import impl_BD.VendaDaoBd;
import impl_BD.VooDaoBd;
import negocio.NegocioException;
import negocio.VendaNegocio;
import view.PrintUtil;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author 631620220
 */
public class VendaController implements Initializable {
    
    @FXML
    private VBox painelTabelaVenda;
    @FXML
    private TableView<Venda> tableViewVendas;
    @FXML
    private TableColumn<Venda, String> tableColumnCliente;
    @FXML
    private TableColumn<Venda, String> tableColumnVoo;
    @FXML
    private TableColumn<Venda, String> tableColumnHorario;
    @FXML
    private AnchorPane painelFormularioVenda;
    @FXML
    private TextField textFieldCliente;
    @FXML
    private TextField textFieldVoo;
    @FXML
    private TextField textFieldHorario;

    
    private List<Venda> listaVendas;
    private Venda vendaSelecionada;

    private ObservableList<Venda> observableListaVendas;
    private VendaNegocio vendaNegocio;

    private ClienteDaoBd clienteDao;
    private VooDaoBd vooDao;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaNegocio = new VendaNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewVendas != null) {
            carregarTableViewVendas();
        }

    }        

    private void carregarTableViewVendas() {
        tableColumnCliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Venda, String> cell) {
                        final Venda venda = cell.getValue();
                        final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(venda.getCliente().getNome());
                        return simpleObject;
                    }
        });
        tableColumnVoo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Venda, String> cell) {
                        final Venda venda = cell.getValue();
                        final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(String.valueOf(venda.getVoo().getId()));
                        return simpleObject;
                    }
        });
        tableColumnHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Venda, String> cell) {
                        final Venda venda = cell.getValue();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm - dd/MM/yyyy");
                        final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(dateFormat.format(venda.getHorario_compra()));
                        return simpleObject;
                    }
                });
        // mostrar nome do aviao
        /*tableColumnAviao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Venda, String> cell) {
                        final Venda venda = cell.getValue();
                        final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(venda.getAviao().getNome());
                        return simpleObject;
                    }
        });
        */
        listaVendas = vendaNegocio.listar();

        observableListaVendas = FXCollections.observableArrayList(listaVendas);
        tableViewVendas.setItems(observableListaVendas);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        System.out.println("botao cadastrar");
        vendaSelecionada = null;
        
        //comboBoxAviao.setItems();
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Passagens.class.getResource("/view/PainelFormularioVenda.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaVenda.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewVendas();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Venda vendaSelec = tableViewVendas.getSelectionModel().getSelectedItem();
        if (vendaSelec != null) {
            FXMLLoader loader = new FXMLLoader(Passagens.class.getResource("/view/PainelFormularioVenda.fxml"));
            Parent root = (Parent) loader.load();

            VendaController controller = (VendaController) loader.getController();
            controller.setVendaSelecionado(vendaSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaVenda.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewVendas();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um venda para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException {
        Venda vendaSelec = tableViewVendas.getSelectionModel().getSelectedItem();
        if (vendaSelec != null) {
            /*try {
                vendaNegocio.deletar(vendaSelec);
                this.carregarTableViewVendas();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }*/
        } else {
            PrintUtil.printMessageError("Precisa selecionar um venda para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioVenda.getScene().getWindow();
        
        if(vendaSelecionada == null) //Se for cadastrar
        {
            /*try {
                
                int idCliente = Integer.parseInt(textFieldCliente.getText());
                int idVoo = Integer.parseInt(textFieldVoo.getText());
                
                DateFormat format = new SimpleDateFormat("kk:mm - dd/MM/yyyy");
                Date horario = format.parse(textFieldHorario.getText());
                
                // pegar aviao por id
                //int idAviao = Integer.parseInt(textFieldAviao.getText());
                //Aviao a = aviaoDao.procurarPorId(idAviao);
                // pegar aviao por nome
                //String nomeAviao = comboBoxAviao.getSelectionModel().getSelectedItem();
                //Aviao a = aviaoDao.procurarPorNome(nomeAviao);
                
                
                vendaNegocio.salvar(new Venda(clienteDao.procurarPorId(idCliente), vooDao.procurarPorId(idVoo), horario));
                
                stage.close();
            } catch (NegocioException | ParseException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }*/
            
        }
        else //Se for editar
        {
            /*try {
                vendaSelecionada.setCliente(clienteDao.procurarPorId(Integer.parseInt(textFieldCliente.getText())));
                vendaSelecionada.setVoo(vooDao.procurarPorId(Integer.parseInt(textFieldVoo.getText())));

                vendaNegocio.atualizar(vendaSelecionada);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }*/
            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioVenda.getScene().getWindow();
        stage.close();

    }

    public Venda getVendaSelecionado() {
        return vendaSelecionada;
    }

    public void setVendaSelecionado(Venda vendaSelecionada) {
        this.vendaSelecionada = vendaSelecionada;
        textFieldCliente.setText(String.valueOf(vendaSelecionada.getCliente().getId()));
        textFieldVoo.setText(String.valueOf(vendaSelecionada.getVoo().getId()));
    }    
}
