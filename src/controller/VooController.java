/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dominio.Aviao;
import main.Passagens;
import dominio.Voo;
import impl_BD.AviaoDaoBd;
import negocio.NegocioException;
import negocio.VooNegocio;
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
public class VooController implements Initializable {
    
    @FXML
    private VBox painelTabelaVoo;
    @FXML
    private TableView<Voo> tableViewVoos;
    @FXML
    private TableColumn<Voo, String> tableColumnOrigem;
    @FXML
    private TableColumn<Voo, String> tableColumnDestino;
    @FXML
    private TableColumn<Voo, String> tableColumnHorario;
    @FXML
    private TableColumn<Voo, String> tableColumnAviao;
    @FXML
    private TableColumn<Voo, String> tableColumnLugares;
    @FXML
    private AnchorPane painelFormularioVoo;
    @FXML
    private TextField textFieldOrigem;
    @FXML
    private TextField textFieldDestino;
    @FXML
    private TextField textFieldHorario;
    @FXML
    private TextField textFieldAviao;
    @FXML
    private ComboBox<String> comboBoxAviao;
    
    private List<Voo> listaVoos;
    private Voo vooSelecionado;

    private ObservableList<Voo> observableListaVoos;
    private VooNegocio vooNegocio;

    private AviaoDaoBd aviaoDao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vooNegocio = new VooNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewVoos != null) {
            carregarTableViewVoos();
        }

    }        

    private void carregarTableViewVoos() {
        tableColumnOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        tableColumnDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        tableColumnHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Voo, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Voo, String> cell) {
                        final Voo voo = cell.getValue();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm - dd/MM/yyyy");
                        final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(dateFormat.format(voo.getHorario()));
                        return simpleObject;
                    }
                });
        // mostrar nome do aviao
        tableColumnAviao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Voo, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Voo, String> cell) {
                        final Voo voo = cell.getValue();
                        final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(voo.getAviao().getNome());
                        return simpleObject;
                    }
        });
        
        tableColumnLugares.setCellValueFactory(new PropertyValueFactory<>("lugares"));
        listaVoos = vooNegocio.listar();

        observableListaVoos = FXCollections.observableArrayList(listaVoos);
        tableViewVoos.setItems(observableListaVoos);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        System.out.println("botao cadastrar");
        vooSelecionado = null;
        
        //comboBoxAviao.setItems();
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Passagens.class.getResource("/view/PainelFormularioVoo.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaVoo.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewVoos();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Voo vooSelec = tableViewVoos.getSelectionModel().getSelectedItem();
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
            carregarTableViewVoos();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um voo para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException {
        Voo vooSelec = tableViewVoos.getSelectionModel().getSelectedItem();
        if (vooSelec != null) {
            try {
                vooNegocio.deletar(vooSelec);
                this.carregarTableViewVoos();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar um voo para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioVoo.getScene().getWindow();
        
        if(vooSelecionado == null) //Se for cadastrar
        {
            try {
                
                String origem = textFieldOrigem.getText();
                String destino = textFieldDestino.getText();
                
                DateFormat format = new SimpleDateFormat("kk:mm - dd/MM/yyyy");
                Date horario = format.parse(textFieldHorario.getText());
                
                // pegar aviao por id
                int idAviao = Integer.parseInt(textFieldAviao.getText());
                Aviao a = aviaoDao.procurarPorId(idAviao);
                // pegar aviao por nome
                //String nomeAviao = comboBoxAviao.getSelectionModel().getSelectedItem();
                //Aviao a = aviaoDao.procurarPorNome(nomeAviao);
                
                
                vooNegocio.salvar(new Voo(origem, destino, horario, a, a.getAssentos()));
                
                stage.close();
            } catch (NegocioException | ParseException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
        }
        else //Se for editar
        {
            try {
                vooSelecionado.setOrigem(textFieldOrigem.getText());
                vooSelecionado.setDestino(textFieldDestino.getText());
                DateFormat format = new SimpleDateFormat("kk:mm - dd/MM/yyyy");
                Date horario = format.parse(textFieldHorario.getText());
                vooSelecionado.setHorario(horario);
                // 
                //vooSelecionado.setAviao(Aviao a = aviaoDao.procurarPorNome(comboBoxAviao.getValue()));
                vooSelecionado.setAviao(aviaoDao.procurarPorId(Integer.parseInt(textFieldAviao.getText())));
                vooNegocio.atualizar(vooSelecionado);
                stage.close();
            } catch (NegocioException | ParseException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
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
        textFieldOrigem.setText(vooSelecionado.getOrigem());
        textFieldDestino.setText(vooSelecionado.getDestino());
        textFieldHorario.setText(vooSelecionado.getHorario().toString());
        textFieldAviao.setText(String.valueOf(vooSelecionado.getAviao().getId()));
    }    
}
