package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    
    public void setModel(Model model){
    	this.model=model;
    	boxPrimo.getItems().addAll(this.model.getAllAutori());
    }

    @FXML
    void handleCoautori(ActionEvent event) {
    	
    	Author autore = boxPrimo.getValue() ;
    	
    	if(autore==null) {
    		txtResult.appendText("Errore: devi selezionare un autore\n") ;
    		return ;
    	}
    	
    	List<Author> coautori = model.getCoautori(autore) ;
    	
    	txtResult.appendText(coautori.toString()+"\n");
    	
    	boxSecondo.getItems().clear();
    	boxSecondo.getItems().addAll(this.model.getNonCoautori(autore));

    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.appendText(model.trovaSequenza(boxPrimo.getValue(),boxSecondo.getValue()).toString() + "\n");
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
}
