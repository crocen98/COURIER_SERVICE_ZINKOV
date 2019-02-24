package by.zinkov.victor.controller.command;

import by.zinkov.victor.controller.command.exception.NoSuchCommandException;
import by.zinkov.victor.controller.command.impl.*;
import by.zinkov.victor.controller.command.impl.administrator.AddTransportTypeCommand;
import by.zinkov.victor.controller.command.impl.administrator.DeleteTransportTypeCommand;
import by.zinkov.victor.controller.command.impl.administrator.EditTransportType;
import by.zinkov.victor.controller.command.impl.administrator.ShowTranspotTypesCommand;

import java.util.EnumMap;
import java.util.Map;

/**
 * Command Provider
 */
public class CommandProvider {
    private static CommandProvider instance = new CommandProvider();
    private Map<CommandEnum, Command> commandMap = new EnumMap<>(CommandEnum.class);
 
    public static CommandProvider getInstance() {
        return instance;
    }

    private CommandProvider() {
        commandMap.put(CommandEnum.DELETE_TRANSPORT_TYPE, new DeleteTransportTypeCommand());
        commandMap.put(CommandEnum.TO_LOG_IN_PAGE, new GoPageCommand(Page.LOG_IN));
        commandMap.put(CommandEnum.SIGN_UP, new GoSignUpPageCommand());
        commandMap.put(CommandEnum.ALL_TRANSPORT_TYPES, new ShowTranspotTypesCommand());
        commandMap.put(CommandEnum.ADD_TRANSPORT_TYPE,new  AddTransportTypeCommand());
        commandMap.put(CommandEnum.CHANGE_TRANSPORT_TYPE, new EditTransportType());
        commandMap.put(CommandEnum.REGISTER_COMMAND,new SignUpCommand());
        commandMap.put(CommandEnum.LOG_IN,new LoginCommand());
        commandMap.put(CommandEnum.ACTIVATE, new ActivateCommand());
    }

    /**
     * Return command by  or ErrorCommand
     * @param command name
     * @return command implementation
     */
    public Command takeCommand(CommandEnum command) {
        System.out.println(command);
       Command neededCommand = commandMap.get(command);
       if(neededCommand == null){
           throw new NoSuchCommandException(command.toString());
       }
       return neededCommand;
    }
}
