package szeljic.model;

public class Command {
	
	private String name, command;

	public Command(String name, String command) {
		super();
		this.name = name;
		this.command = command;
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
