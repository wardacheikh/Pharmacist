package application ;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.LigneVente;



public class interfaceventeController implements Initializable {
    public ObservableList<LigneVente> data = FXCollections.observableArrayList();

    Connection cn;
    public PreparedStatement st;
    public ResultSet result;

    @FXML
    private TableColumn<LigneVente, String> ID_medicament;

    @FXML
    private TableColumn<LigneVente, Float> ID_prixtotal;

    @FXML
    private TableColumn<LigneVente, Integer> ID_numfact;

    @FXML
    private TableColumn<LigneVente, Float> ID_prixunit;

    @FXML
    private TableColumn<LigneVente, Integer> ID_quantite;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_save;
    @FXML
    private Button btn_historique;

    @FXML
    private ComboBox<String> cb_medicaments;

    @FXML
    private TableView<LigneVente> table_vente;

    @FXML
    private TextField txt_numfacture;

    @FXML
    private TextField txt_nomclient;

    @FXML
    private TextField txt_nom;
    @FXML
    private TextField txt_idfacture;

    @FXML
    private TextField txt_qte;

    @FXML
    private TextField txt_stock;

    @FXML
    private TextField txt_total;

    @FXML
    private TextField txt_ptotal;

    @FXML
    private TextField txt_pu;



    @FXML
    void ajouterafacture(MouseEvent event) {

        String qte = txt_qte.getText();
        String nommedic = txt_nom.getText();
        String prixunit = txt_pu.getText();
        String totalprix = txt_ptotal.getText();
        String numfact = txt_numfacture.getText();


        int verifstock = 0;

        cn = Mysql.getConection();
        String sql = "select * FROM medicaments where Nom_medicament= '" + nommedic + "'";
        try {
            st = cn.prepareStatement(sql);
            result = st.executeQuery();
            while (result.next()) {

                if (result.getInt("Quantite_stock") >= Integer.parseInt(qte)) {
                    verifstock = 1;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Stock insuffisant");
                    alert.setHeaderText(null);
                    alert.setContentText("Attention : Stock insuffisant !");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (verifstock == 1) {


                String sql2 = "insert into ligne_facture(nom_medicament,prix_unit,quantite, prix_total, N_facture) values (?,?,?,?,?)";


                try {
                    st = cn.prepareStatement(sql2);
                    st.setString(1, nommedic);
                    st.setString(2, prixunit);
                    st.setString(3, qte);
                    st.setString(4, totalprix);
                    st.setString(5, numfact);
                    st.execute();

                    txt_qte.setText("");
                    txt_nom.setText("");
                    txt_pu.setText("");
                    txt_ptotal.setText("");

                    String sql3 = "SELECT N_facture, SUM(prix_total) AS montant_total FROM ligne_facture GROUP BY N_facture";
                    st= cn.prepareStatement(sql3);
                    try {
                    result = st.executeQuery();
                    while (result.next()) {
                        float montant_total = result.getFloat("montant_total");
                        txt_total.setText(String.valueOf(montant_total));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                    showVente2();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


        }
    }



    @FXML
    void supprimerligne(MouseEvent event) {
       String sql="delete from ligne_facture where nom_medicament = '"+txt_nom.getText()+"'";
        try {
            st=cn.prepareStatement(sql);
            st.executeUpdate();
            txt_qte.setText("");
            txt_nom.setText("");
            txt_pu.setText("");
            txt_ptotal.setText("");
            showVente2();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void verifierqte(KeyEvent event) {
        String qte = txt_qte.getText();
        if (!qte.equals("")) {
            String prix = txt_pu.getText();
            float prixtotal = Float.parseFloat(qte) * Float.parseFloat(prix);
            txt_ptotal.setText(String.valueOf(prixtotal));
        } else {
            txt_ptotal.setText("");
        }
    }


    @FXML
    void handleComboBoxSelection(ActionEvent event) {

        int selectedIndex = cb_medicaments.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Un élément est sélectionné dans la combobox
            String selectedMedicament = cb_medicaments.getItems().get(selectedIndex);
            txt_nom.setText(selectedMedicament);
            String sql = "SELECT Prix_unitaire, Quantite_stock, Nom_medicament FROM Medicaments WHERE Nom_medicament = '" + selectedMedicament + "'";
            try {
                st = cn.prepareStatement(sql);
                result = st.executeQuery();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (result.next()) {
                    txt_stock.setText(String.valueOf(result.getInt("Quantite_stock")));
                    txt_pu.setText(String.valueOf(result.getFloat("Prix_unitaire")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    void remplirmedicaments() {
        String sql = "Select Nom_medicament from medicaments";
        List<String> medicaments = new ArrayList<>();
        try {
            st = cn.prepareStatement(sql);
            result = st.executeQuery();
            while (result.next()) {
                medicaments.add(result.getString("Nom_medicament"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cb_medicaments.setItems(FXCollections.observableArrayList(medicaments));

    }

    @FXML
    void enregistrervente(MouseEvent event) {
       String nomclient=txt_nomclient.getText();
        String numFacture = txt_numfacture.getText();
        String total=txt_total.getText();

        cn = Mysql.getConection();
        String sql2="INSERT INTO factures (nom_client,date,N_facture,montant) values(?,CURDATE(),?,?) ";
        try {
            st=cn.prepareStatement(sql2);

        st.setString(1,nomclient);
        st.setString(2,numFacture);
        st.setString(3,total);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

// Modifier le stock
        List<LigneVente> lignesVente = table_vente.getItems();
// iterer sur les elements de lignesVente en utilisant la variable ligne de type LigneVente
        for (LigneVente ligne : lignesVente) {
            String nomMedicament = ligne.getNommedic();
            int quantiteVendue = ligne.getQuantiteVendu();

            // Mettre à jour le stock du médicament
            try {
                cn = Mysql.getConection();
                String sql = "UPDATE medicaments SET Quantite_stock = Quantite_stock - ? WHERE Nom_medicament = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, quantiteVendue);
                st.setString(2, nomMedicament);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        txt_numfacture.setText("");
        txt_nomclient.setText("");
        table_vente.getItems().clear();
        txt_total.setText("");

        remplirmedicaments();

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
    public void handlehistoriqueButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interfacehistorique.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_historique.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showVente2() {
        cn = Mysql.getConection();

        table_vente.getItems().clear();
        String numfact = txt_numfacture.getText();
        String sql = "SELECT * FROM ligne_facture WHERE N_facture='" +numfact+ "'";
        try {
            st = cn.prepareStatement(sql);
            result = st.executeQuery();
            while (result.next()) {
                data.add(new LigneVente(result.getInt("id"), result.getString("nom_medicament"), result.getFloat("prix_unit"), result.getInt("quantite"), result.getFloat("prix_total"), result.getInt("N_facture")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Mettre à jour les colonnes du tableau avec les nouvelles données
        ID_medicament.setCellValueFactory(new PropertyValueFactory<LigneVente, String>("Nommedic"));
        ID_prixunit.setCellValueFactory(new PropertyValueFactory<LigneVente, Float>("prixunit"));
        ID_quantite.setCellValueFactory(new PropertyValueFactory<LigneVente, Integer>("QuantiteVendu"));
        ID_prixtotal.setCellValueFactory(new PropertyValueFactory<LigneVente, Float>("Montanttotal"));
        ID_numfact.setCellValueFactory(new PropertyValueFactory<LigneVente, Integer>("Numfact"));

        table_vente.setItems(data); // Assigner les nouvelles données au tableau
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table_vente.getItems().clear();
        cn=Mysql.getConection();
        remplirmedicaments();
    }
}