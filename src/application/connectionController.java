package application;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class connectionController {
	@FXML
	private Button cancelButton ;

	@FXML
	private Label loginLabel ;
	@FXML
	private TextField usernameTextfield ;
	@FXML
	private PasswordField passwordfield ;
	@FXML
	  private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



	public void cancelButtonOnAction(ActionEvent e ) {
		usernameTextfield.clear();
	    passwordfield.clear();
	}


	public void LoginButtonOnAction(ActionEvent E ) {

		if(usernameTextfield.getText().isBlank() || passwordfield.getText().isBlank()) {

			loginLabel.setText("entrer vos information ! ");
		}
		else {
			Mysql connectNow=new Mysql();
			Connection connectDB = Mysql.getConection();
			String verifyLogin ="SELECT count(1) FROM utilisateurs WHERE Nom_utilisateur = '"+usernameTextfield.getText()+"' AND Mot_de_passe ='"+passwordfield.getText()+"' ";
			try {
		          Statement statement = connectDB.createStatement();
		            ResultSet queryResult = statement.executeQuery(verifyLogin);

	            if (queryResult.next() && queryResult.getInt(1) == 1) {
	                // Switch to the home scene
	                Node sourceNode = (Node) E.getSource();
	                Stage currentStage = (Stage) sourceNode.getScene().getWindow();

	                FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
	                Parent root = loader.load();
	                Scene scene = new Scene(root);

	                currentStage.setScene(scene);
	                currentStage.show();


	            } else {
	                loginLabel.setText("le mot de pass ou le nom d'utilisateur est incorrecte !");
	            }

			}catch(Exception e ) {

				e.printStackTrace();
			}
		}
	}
	}
