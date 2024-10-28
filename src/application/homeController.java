package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class homeController  {
	@FXML
	private Button btn_fournisseur ;
    @FXML
	    private Hyperlink deconnecter;
    @FXML
	    private Button btn_stock;
    @FXML
    private Button btn_vente;

	 public void switchtofournisseur() {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("fournisseur.fxml"));
	            Parent root = loader.load();
	            Stage stage =(Stage)btn_fournisseur.getScene().getWindow();
	            stage.setScene(new Scene(root));
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }}

	        public void deconnecter() {
		        try {
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("connection.fxml"));
		            Parent root = loader.load();
					root.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		            Stage stage =(Stage)deconnecter.getScene().getWindow();
		            stage.setScene(new Scene(root));
		            stage.show();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

}
	        @FXML

	        public void switchtostock() {
	            try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionstockInterface.fxml"));
	                Parent root = loader.load();
	                Stage stage = (Stage)btn_stock.getScene().getWindow();
	                stage.setScene(new Scene(root));
	                stage.show();
	            } catch (IOException e) {
	                e.printStackTrace();

	            }
	        }
	        public void switchtovente() {
	            try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("interfacehistorique.fxml"));
	                Parent root = loader.load();
	                Stage stage = (Stage)btn_vente.getScene().getWindow();
	                stage.setScene(new Scene(root));
	                stage.show();
	            } catch (IOException e) {
	                e.printStackTrace();

	            }
	        }
}


