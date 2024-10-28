package application ;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Medicament;

public class gestionstockInterfaceController implements Initializable {
    public ObservableList<Medicament> data= FXCollections.observableArrayList();

    Connection cn;
    public PreparedStatement st;
    public ResultSet result;

    @FXML
    private TableColumn<Medicament, String> ID_Description;

    @FXML
    private TableColumn<Medicament, Integer> ID_ID;

    @FXML
    private TableColumn<Medicament, Date> ID_date;

    @FXML
    private TableColumn<Medicament, String> ID_laboratoire;

    @FXML
    private TableColumn<Medicament, String> ID_medicament;

    @FXML
    private TableColumn<Medicament, Float> ID_prix;

    @FXML
    private TableColumn<Medicament, Integer> ID_quantite;

    @FXML
    private Button btd_edit;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_sech;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Medicament> table_stock;

    @FXML
    private TextField txt_Description;

    @FXML
    private TextField txt_ID;

    @FXML
    private TextField txt_Quantite;

    @FXML
    private TextField txt_laboratoire;

    @FXML
    private TextField txt_medicament;

    @FXML
    private TextField txt_prix;

    @FXML
    private TextField txt_searchID;


    @FXML
    public void deleteMedicament() {
        String sql="delete from medicaments where ID_medicament = '"+txt_searchID.getText()+"'";

        try {
            st=cn.prepareStatement(sql);
            st.executeUpdate();
            txt_ID.setText("");
            txt_laboratoire.setText("");
            txt_medicament.setText("");
            txt_Quantite.setText("");
            txt_Description.setText("");
            txt_prix.setText("");
            datePicker.setValue(null);
            Alert alert = new Alert(AlertType.CONFIRMATION,"le medicament est supprimer ",ButtonType.OK);
            alert.showAndWait();
            showMedicament();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void editMedicament() {
        String nom = txt_medicament.getText();
        String Description = txt_Description.getText();
        String quantite = txt_Quantite.getText();
        String prix = txt_prix.getText();
        String laboratoire = txt_laboratoire.getText();

        String sql = "update medicaments set Nom_medicament=?, Description=?, Prix_unitaire=?, Quantite_stock=?, Laboratoire=?, Date_expiration=? where ID_medicament=  '" + txt_ID.getText() + "'";
        if (!nom.equals("") && !Description.equals("") && !laboratoire.equals("")) {

            try {
                st = cn.prepareStatement(sql);
                st.setString(1, nom);
                st.setString(2, Description);
                st.setString(3, prix);
                st.setString(4, quantite);
                st.setString(5, laboratoire);

                //convertion de date en javasqldate
                java.util.Date date = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.util.Date sqldate = new java.util.Date(date.getTime());
                st.setDate(6, new java.sql.Date(sqldate.getTime()));

                st.executeUpdate();
                txt_ID.setText("");
                txt_laboratoire.setText("");
                txt_medicament.setText("");
                txt_Quantite.setText("");
                txt_Description.setText("");
                txt_prix.setText("");
                datePicker.setValue(null);
                Alert alert = new Alert(AlertType.CONFIRMATION,"le medicament est mise a jour ",ButtonType.OK);
                alert.showAndWait();
                showMedicament();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
            // Handle any exceptions that may occur during loading of the "home.fxml" file
        }
    }

    @FXML
public void searchMedicament(){
    	cn=Mysql.getConection();
    String sql="select ID_medicament ,Nom_medicament ,Description ,Prix_unitaire ,Quantite_stock ,Date_expiration ,Laboratoire FROM medicaments where ID_medicament= '"+txt_searchID.getText()+"'";
    try {
        st=cn.prepareStatement(sql);
        result=st.executeQuery();
        if(result.next()){
            txt_ID.setText(result.getString("ID_medicament"));
            txt_Description.setText(result.getString("Description"));
            txt_medicament.setText(result.getString("Nom_medicament"));
            txt_prix.setText(result.getString("Prix_unitaire"));
            txt_Quantite.setText(result.getString("Quantite_stock"));
            txt_laboratoire.setText(result.getString("Laboratoire"));
            Date date=result.getDate("Date_expiration");
            datePicker.setValue(((java.sql.Date) date).toLocalDate());

        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}

    public void showMedicament(){
        table_stock.getItems().clear();
        String sql="select * from medicaments";
        try {
            st=cn.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next ()){
                data.add(new Medicament(result.getInt("ID_medicament"),result.getString("Nom_medicament"),result.getString("Description"),result.getFloat("Prix_unitaire"),result.getInt("Quantite_stock"),result.getString("Laboratoire"),result.getDate("Date_expiration")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ID_ID.setCellValueFactory(new PropertyValueFactory<Medicament,Integer>("ID"));
        ID_medicament.setCellValueFactory(new PropertyValueFactory<Medicament,String>("nom"));
        ID_date.setCellValueFactory(new PropertyValueFactory<Medicament,Date>("dateExpir"));
        ID_Description.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Description"));
        ID_prix.setCellValueFactory(new PropertyValueFactory<Medicament,Float>("prixunit"));
        ID_quantite.setCellValueFactory(new PropertyValueFactory<Medicament,Integer>("quantite"));
        ID_laboratoire.setCellValueFactory(new PropertyValueFactory<Medicament,String>("laboratoire"));
        table_stock.setItems(data);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cn=Mysql.getConection();
        showMedicament();
    }

    @FXML
    public void addMedicament() {
        String nom=txt_medicament.getText();
        String Description=txt_Description.getText();
        String quantite=txt_Quantite.getText();
        String prix=txt_prix.getText();
        String laboratoire=txt_laboratoire.getText();

        String sql="insert into medicaments(Nom_medicament,Description,Prix_unitaire, Quantite_stock, Date_expiration, Laboratoire) values (?,?,?,?,?,?)";

        if(!nom.equals("")&&!Description.equals("")&&!laboratoire.equals("")){

            try {
                st= cn.prepareStatement(sql);
                st.setString(1,nom);
                st.setString(2,Description);
                st.setString(3,quantite);
                st.setString(4,prix);
                //convertion de date en javasqldate
                java.util.Date date = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.util.Date sqldate = new java.util.Date(date.getTime());
                st.setDate(5, new java.sql.Date(sqldate.getTime()));
                st.setString(6,laboratoire);
                st.execute();
                txt_ID.setText("");
                txt_laboratoire.setText("");
                txt_medicament.setText("");
                txt_Quantite.setText("");
                txt_Description.setText("");
                txt_prix.setText("");
                datePicker.setValue(null);
                Alert alert = new Alert(AlertType.CONFIRMATION,"le medicament est enregistrer ",ButtonType.OK);
                alert.showAndWait();
                showMedicament();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
