package by.zinkov.victor.controller.command;

import by.zinkov.victor.controller.command.exception.NoSuchCommandException;
import by.zinkov.victor.controller.command.impl.CommandExample;

import java.util.HashMap;
import java.util.Map;

/**
 * Command Provider
 */
public class CommandProvider {
    private static CommandProvider instance = new CommandProvider();
    private Map<String, Command> commandMap = new HashMap<>();
    public static CommandProvider getInstance() {
        return instance;
    }

    private CommandProvider() {
        commandMap.put("CommandExample", new CommandExample());
    }

    /**
     * Return command by  or ErrorCommand
     * @param command name
     * @return command implementation
     */
    public Command takeCommand(String command) {
       Command neededCommand = commandMap.get(command);
       if(neededCommand == null){
           throw new NoSuchCommandException(command);
       }
       return neededCommand;
    }
}
