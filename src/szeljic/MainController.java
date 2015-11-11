package szeljic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import szeljic.db.DBConnection;
import szeljic.model.Command;

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
		
		ArrayList<String> listOfColumns = new ArrayList<>();
		listOfColumns.add("name");
		
		ArrayList<String> listOfValues = new ArrayList<>();
		listOfValues.add("gulp");
		
		try {
			ResultSet resultSet = DBConnection.select("COMMAND", listOfColumns, listOfValues);
			while(resultSet.next()) {
				System.out.println(resultSet.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML protected void executeCommand(ActionEvent action) {
		// 1) create a java calendar instance
		Calendar calendar = Calendar.getInstance();
		 
		// 2) get a java.util.Date from the calendar instance.
//		    this date will represent the current instant, or "now".
		java.util.Date now = calendar.getTime();
		calendar.setTime(now);
		 System.out.println(calendar.getTimeInMillis());
		// 3) a java current time (now) instance
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.out.println(currentTimestamp);
	}
	
	@FXML protected void addCommand(ActionEvent action) {
		
		Command command = new Command();
		command.setName("gulp");
		command.setCommand("gulp basset:watch");
		
		try {
			command.addCommand();
		} catch (Exception e) {
			System.out.println("Unable to add command into database!");
		}
		
		command.setName("list all files in folder");
		command.setCommand("ls -l");
		
		try {
			command.addCommand();
		} catch (Exception e) {
			System.out.println("Unable to add command into database!");
		}
		
	}
	
	@FXML protected void editCommand(ActionEvent action) {
		
		ArrayList<String> listOfColumns = new ArrayList<>();
		listOfColumns.add("name");
		listOfColumns.add("command");
		listOfColumns.add("created_at");
		
		ArrayList<String> listOfValues = new ArrayList<>();
		listOfValues.add("update");
		listOfValues.add("sudo apt-get update");
		listOfValues.add("now");
		
		try {
			DBConnection.insert("COMMAND", listOfColumns, listOfValues);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML protected void removeCommand(ActionEvent action) {
		
		ResultSet resultSet = DBConnection.selectAll("COMMAND");
		try {
			while(resultSet.next()){
				System.out.println(resultSet.getString(2));
				System.out.println(resultSet.getString(3));
				System.out.println(resultSet.getString(4));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML protected void onEnter(KeyEvent key) {
		
		List<Command> list = Command.getAllCommands();
		
		for (Command command : list) {
			
			System.out.println(command.getId());
			System.out.println(command.getName());
			System.out.println(command.getCommand());
			System.out.println(command.getCreatedAt());
			System.out.println(command.getUpdatedAt());
			System.out.println("---------------------------\n\n");
			
		}
		
	}
}
