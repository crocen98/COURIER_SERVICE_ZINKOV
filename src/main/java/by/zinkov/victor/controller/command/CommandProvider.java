package by.zinkov.victor.controller.command;

import by.zinkov.victor.controller.command.exception.NoSuchCommandException;
import by.zinkov.victor.controller.command.impl.GoPageCommand;
import by.zinkov.victor.controller.command.impl.administrator.AddTransportTypeCommand;
import by.zinkov.victor.controller.command.impl.administrator.DeleteTransportTypeCommand;
import by.zinkov.victor.controller.command.impl.administrator.EditTransportType;
import by.zinkov.victor.controller.command.impl.administrator.ShowTranspotTypesCommand;

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
        commandMap.put("delete_transport_type", new DeleteTransportTypeCommand());
        commandMap.put("log_in", new GoPageCommand(Page.LOG_IN));
        commandMap.put("sign_up", new GoPageCommand(Page.SIGN_UP));
        commandMap.put("all_cargo_types", new ShowTranspotTypesCommand());
        commandMap.put("add_transport_type",new  AddTransportTypeCommand());
        commandMap.put("change_transport_type", new EditTransportType());
    }

    /**
     * Return command by  or ErrorCommand
     * @param command name
     * @return command implementation
     */
    public Command takeCommand(String command) {
        System.out.println(command);
       Command neededCommand = commandMap.get(command);
       if(neededCommand == null){
           throw new NoSuchCommandException(command);
       }
       return neededCommand;
    }
}
