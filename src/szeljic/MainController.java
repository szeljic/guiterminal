package szeljic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {
	
	@FXML AnchorPane application;
	
	@FXML MenuItem mi_gui_terminal_close;
	@FXML MenuItem mi_command_execute;
	@FXML MenuItem mi_command_add;
	@FXML MenuItem mi_command_edit;
	@FXML MenuItem mi_command_remove;
	@FXML MenuItem mi_help_about;
	
	@FXML TextField tf_add_command;
	
	@FXML Button btn_add_command;
	@FXML Button btn_execute;
	@FXML Button btn_edit;
	@FXML Button btn_remove;
	
	@FXML ListView<String> lv_commands;
	
	@FXML TextArea ta_output;
	
	@FXML protected void closeApplication(ActionEvent action) {
		Stage stage = (Stage)application.getScene().getWindow();
		System.out.println("Closing application...");
		stage.close();
		System.out.println("Application closed!");
	}
	
	@FXML protected void openAbout(ActionEvent action) {
		ta_output.setText("Some Text");
	}
	
	@FXML protected void executeCommand(ActionEvent action) {
		
	}
	
	@FXML protected void addCommand(ActionEvent action) {
		
		System.out.println(action);
		
		ta_output.setText(tf_add_command.getText());
	}
	
	@FXML protected void editCommand(ActionEvent action) {
		
	}
	
	@FXML protected void removeCommand(ActionEvent action) {
		
	}
	
	@FXML protected void onEnter(KeyEvent key) {
		
		System.out.println(key.getCode() == KeyCode.ENTER);
		
	}
}
