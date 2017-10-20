package report;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import inventory.*;

public class ReportsExportController implements Initializable{

	@FXML private ChoiceBox<ReportTypes> reportsSelection;
	private ObservableList<ReportTypes> observableReports;
	
	@FXML private TextField fileNameField;
	private String fileName;
	
	@FXML private Button export;
	
	@FXML private void handleExport(ActionEvent ae) {
		Object source = ae.getSource();
		ReportTypes reportType = this.reportsSelection.getSelectionModel().getSelectedItem();
		this.fileName = this.fileNameField.getText();
		
		if(source == export) {
			this.exportSelectedReport(reportType);
		}
	}
	
	private void exportSelectedReport(ReportTypes reportType) {
		if(reportType == ReportTypes.INVENTORY) {
			try {
				InventoryReport inventoryReport = new InventoryReport(this.getInventoryRecords(), this.fileName);
				inventoryReport.populateReport();
				inventoryReport.save();
				inventoryReport.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Inventory> getInventoryRecords() {
		List<Inventory> inventories = MasterController.getInstance().getInventoryGateway().getInventory();
		
		return inventories;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.observableReports = this.reportsSelection.getItems();
		this.observableReports.add(ReportTypes.INVENTORY);
		this.observableReports.add(ReportTypes.QUOTE);
		this.observableReports.add(ReportTypes.BLUEPRINT);
	}
}