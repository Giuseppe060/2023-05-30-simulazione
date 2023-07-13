package it.polito.tdp.gosales;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.dao.GOsalesDAO;
import it.polito.tdp.gosales.model.Arco;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Retailers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	GOsalesDAO dao = new GOsalesDAO();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private ComboBox<Retailers> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
        Retailers r = cmbRivenditore.getValue();
        if(r == null) {
        	txtResult.appendText("Please select a retailers");
        	return ;
        }
    	
    	txtResult.appendText("La componente connessa di "+r.getName()+" ha dimensione "+model.nComponentiConnesse(r)+"\n");
    	txtResult.appendText("Il peso totale degli archi della componenente connessa è "+model.totalePesoArchi(r));

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String country = cmbNazione.getValue();
    	if (country==null) {
    		this.txtResult.setText("Please select a country");
    		return;
    	}
    	Integer anno = cmbAnno.getValue();
    	if (anno == null) {
    		this.txtResult.setText("Please select a year");
    		return;
    	}
    	
    	int M = Integer.parseInt(txtNProdotti.getText());
    	
    	model.creaGrafo(country, anno, M);
    	
    	txtResult.appendText("GRAFO CREATO CON \n");
    	txtResult.appendText("#"+model.nVertici()+" vertici"+"\n");
    	txtResult.appendText("#"+model.nArchi()+" archi"+"\n");
    	
    	List<Retailers> vertici = dao.getAllVertex(country);
    	Collections.sort(vertici);
    	
    	txtVertici.appendText(vertici.toString()+"\n");
    	
    	List<Arco> archi = model.getListaArchi();
    	Collections.sort(archi);
    	for(Arco a : archi) {
    		txtArchi.appendText(a.getPeso()+": "+a.getR1().getName()+"<->"+a.getR2().getName()+"\n");
    	}
    	
    	for(Retailers r : dao.getAllVertex(country)) {
    		cmbRivenditore.getItems().add(r);
    	}
    	cmbRivenditore.setDisable(false);
    	btnAnalizzaComponente.setDisable(false);

    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";
        
        for(int i=2015; i<2019;i++) {
        	cmbAnno.getItems().add(i);
        }
        for(Retailers r : dao.getAllRetailers()) {
        	if(!cmbNazione.getItems().contains(r.getCountry()))
        	cmbNazione.getItems().add(r.getCountry());
        }

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
