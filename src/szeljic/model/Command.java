package szeljic.model;

import java.util.List;

public class Command {
	
	private String name, command;

	public Command(String name, String command) {
		super();
		this.name = name;
		this.command = command;
	}
	
	public void addCommand(String name, String command) {
		//add command to database
		new Command(name, command);
	}
	
	public void editCommand(Command command) {
		//replace command with specified name with input
	}
	
	public void removeCommand(String name) {
		//delete command with specified name from database
	}
	
	public String executeCommand(Command command) {
		//execute command in terminal and return output
		return null;
	}
	
	public Command getCommand(String name) {
		//get command from database with specified name
		return null;
	}
	
	public List<Command> getAllCommands() {
		//get all commands from database
		return null;
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

}
