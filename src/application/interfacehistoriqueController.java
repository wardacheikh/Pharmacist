package application ;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Facture;

public class interfacehistoriqueController implements Initializable {
    public ObservableList<Facture> data= FXCollections.observableArrayList();
    public ResultSet result;
    public Connection cn;
    public PreparedStatement st;
    @FXML
    private Button btn_home;
    @FXML
    private Button bnt_nvente;

    @FXML
    private TableColumn<Facture, Date> facture_date;

    @FXML
    private TableColumn<Facture, Integer> facture_id;

    @FXML
    private TableColumn<Facture, Float> facture_montant;

    @FXML
    private TableColumn<Facture, String> facture_nomclient;

    @FXML
    private TableColumn<Facture, Integer> facture_numero;

    @FXML
    private TableView<Facture> table_facture;


    public void showFacture(){
        table_facture.getItems().clear();
        String sql="select * from factures";
        try {
            st=cn.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next ()){
                data.add(new Facture(result.getInt("ID_facture"),result.getString("nom_client"),result.getDate("date"),result.getInt("N_facture"),result.getFloat("montant")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        facture_id.setCellValueFactory(new PropertyValueFactory<Facture,Integer>("IDfact"));
        facture_nomclient.setCellValueFactory(new PropertyValueFactory<Facture,String>("nomclient"));
        facture_date.setCellValueFactory(new PropertyValueFactory<Facture,Date>("datevente"));
        facture_numero.setCellValueFactory(new PropertyValueFactory<Facture,Integer>("numfact"));
        facture_montant.setCellValueFactory(new PropertyValueFactory<Facture,Float>("montanttotal"));
        table_facture.setItems(data);
    }


    @FXML
    public void handleHomeButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_home.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void nouvellevente(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interfacevente.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) bnt_nvente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cn=Mysql.getConection();
        showFacture();
    }
}
