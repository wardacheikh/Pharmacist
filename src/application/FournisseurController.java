package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.fournisseurs;

public class FournisseurController implements Initializable{
    Connection cn;
    public PreparedStatement st;
    public ResultSet result;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delet;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_edit;

    @FXML
    private Button btn_serch;

    @FXML
    private TableColumn<fournisseurs, String> fournisseur_address;

    @FXML
    private TableColumn<fournisseurs, Integer> fournisseur_id;

    @FXML
    private TableColumn<fournisseurs, String> fournisseur_nom;

    @FXML
    private TableColumn<fournisseurs, String> fournisseur_tel;

    @FXML
    private TableView<fournisseurs> table_fournisseur;

    @FXML
    private TextField txt_tel;

    @FXML
    private TextField txt_adress;

    @FXML
    private TextField txt_cin;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_serchCIN;
    public ObservableList<fournisseurs> data= FXCollections.observableArrayList();

    @FXML
    void addFournisseur() {

    	  String nom=txt_nom.getText();
          String adress=txt_adress.getText();
          String tel=txt_tel.getText();
          String cin=txt_cin.getText();

          String sql="INSERT INTO fournisseurs(ID_fournisseur, Nom_fournisseur, Adresse, Num_telephone) values (?,?,?,?)";

          if(!nom.equals("")&&!cin.equals("")&&!tel.equals("")){

              try {
                  st= cn.prepareStatement(sql);
                  st.setString(1,cin);
                  st.setString(2,nom);
                  st.setString(3,adress);
                  st.setString(4,tel);
                  st.execute();
                  txt_cin.setText("");
                  txt_nom.setText("");
                  txt_adress.setText("");
                  txt_tel.setText("");
              	Alert alert = new Alert(AlertType.CONFIRMATION,"le fournisseur est ajouter avec succee ",ButtonType.OK);
                alert.showAndWait();
                  showFournisseur();
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }

          }
    }

    @FXML
    void deletFournisseur() {
    	  String sql="delete from fournisseurs where ID_fournisseur = '"+txt_serchCIN.getText()+"'";
          int m=0;

          try {
              st=cn.prepareStatement(sql);
              st.executeUpdate();
              txt_cin.setText("");
              txt_nom.setText("");
              txt_adress.setText("");
              txt_tel.setText("");
              showFournisseur();
              m=1;
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
          if(m==0) {
          	Alert alert = new Alert(AlertType.ERROR,"Aucun fournisseurs trouver avec le CIN que vous avez saisie ",ButtonType.OK);

          }

    }

    @FXML
    void editFournisseur() {
    	String nom = txt_nom.getText();
        String cin = txt_cin.getText();
        String adress = txt_adress.getText();
        String tel = txt_tel.getText();


        String sql = "update fournisseurs set Nom_fournisseur=?, Adresse=?, Num_telephone=? where ID_fournisseur=  '" + txt_serchCIN.getText() + "'";
        if(!nom.equals("")&&!cin.equals("")&&!tel.equals("")){

            try {
                st = cn.prepareStatement(sql);
                st.setString(1, nom);
                st.setString(2, adress);
                st.setString(3, tel);

                st.executeUpdate();
                txt_cin.setText("");
                txt_nom.setText("");
                txt_adress.setText("");
                txt_tel.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"le fournisseur est mise a jour ",ButtonType.OK);
                alert.showAndWait();
                showFournisseur();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void showFournisseur() {
    	 table_fournisseur.getItems().clear();
        String sql="SELECT * FROM fournisseurs";
        try {
            st=cn.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next ()){
                data.add(new fournisseurs(result.getInt("ID_fournisseur"),
                		result.getString("Nom_fournisseur"),result.getString("Adresse"),result.getString("Num_telephone")));
 }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fournisseur_id.setCellValueFactory(new PropertyValueFactory<fournisseurs,Integer>("id"));
        fournisseur_nom.setCellValueFactory(new PropertyValueFactory<fournisseurs,String>("nom"));
        fournisseur_address.setCellValueFactory(new PropertyValueFactory<fournisseurs,String>("address"));
        fournisseur_tel.setCellValueFactory(new PropertyValueFactory<fournisseurs,String>("tel"));
        table_fournisseur.setItems(data);

    }
    @FXML
    void serchFournisseur() {
    	cn=Mysql.getConection();
        String sql="select ID_fournisseur ,Nom_fournisseur ,Adresse ,Num_telephone  FROM fournisseurs where ID_fournisseur= '"+txt_serchCIN.getText()+"'";
       boolean m=true;
        try {
       st=cn.prepareStatement(sql);
            result=st.executeQuery();
            if(result.next()){
                txt_cin.setText(result.getString("ID_fournisseur"));
                txt_nom.setText(result.getString("Nom_fournisseur"));
                txt_adress.setText(result.getString("Adresse"));
                txt_tel.setText(result.getString("Num_telephone"));
        m=false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(m) {
			Alert alert = new Alert(AlertType.ERROR,"Aucun fournisseurs trouver avec le CIN que vous avez saisie ",ButtonType.OK);
			alert.showAndWait();
        }
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	   cn=Mysql.getConection();
	   showFournisseur();

	}
    public void switchtoHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = loader.load();
            Stage stage =(Stage)btn_home.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
