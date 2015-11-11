package szeljic.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import szeljic.db.DBConnection;

public class Command {
	
	private int id;
	
	private String name, command;
	
	private Date createdAt, updatedAt;
	
	public Command(){
		
	}

	public Command(String name, String command) {
		super();
		this.name = name;
		this.command = command;
	}
	
	public Command(int id, String name, String command, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.command = command;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public void addCommand() throws Exception {
		
		ArrayList<String> listOfColumns = new ArrayList<>();
		listOfColumns.add("NAME");
		listOfColumns.add("COMMAND");
		listOfColumns.add("CREATED_AT");
		listOfColumns.add("UPDATED_AT");
		
		ArrayList<String> listOfValues = new ArrayList<>();
		listOfValues.add(this.name);
		listOfValues.add(this.command);
		listOfValues.add("now");
		listOfValues.add("now");
		
		DBConnection.insert("COMMAND", listOfColumns, listOfValues);
		
	}
	
	public void editCommand() throws Exception {
		ArrayList<String> listOfColumns = new ArrayList<>();
		listOfColumns.add("NAME");
		listOfColumns.add("COMMAND");
		listOfColumns.add("UPDATED_AT");
		
		ArrayList<String> listOfValues = new ArrayList<>();
		listOfValues.add(this.name);
		listOfValues.add(this.command);
		listOfValues.add("now");
		
		DBConnection.update("COMMAND", listOfColumns, listOfValues, this.id);
	}
	
	//implement remove on id or on name
	public void removeCommand(int id) {
		DBConnection.remove("COMMAND", this.id);
	}
	
	public String executeCommand(Command command) {
		//execute command in terminal and return output
		return null;
	}
	
	public Command getCommand(String name) throws Exception {
		ArrayList<String> listOfColumns = new ArrayList<>();
		listOfColumns.add("NAME");
		
		ArrayList<String> listOfValues = new ArrayList<>();
		listOfValues.add(name);
		
		ResultSet resultSet = DBConnection.select("COMMAND", listOfColumns, listOfValues);
		
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				name = resultSet.getString(2);
				String command = resultSet.getString(3);
				String createdAtString = resultSet.getString(4);
				String updatedAtString = resultSet.getString(5);
				
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date createdAt = format.parse(createdAtString);
				Date updatedAt = format.parse(updatedAtString);
				
				return new Command(id, name, command, createdAt, updatedAt);
				
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		
		return new Command();
	}
	
	public static List<Command> getAllCommands() {
	
		List<Command> allCommands = new ArrayList<>();
		
		ResultSet resultSet = DBConnection.selectAll("COMMAND");
		
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String command = resultSet.getString(3);
				String createdAtString = resultSet.getString(4);
				String updatedAtString = resultSet.getString(5);
				
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date createdAt = null;
				Date updatedAt = null;
				try {
					createdAt = format.parse(createdAtString);
					updatedAt = format.parse(updatedAtString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Command newCommand = new Command(id, name, command, createdAt, updatedAt);
				allCommands.add(newCommand);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allCommands;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}

}
