package by.zinkov.victor.command;

import by.zinkov.victor.command.exception.NoSuchCommandException;
import by.zinkov.victor.command.impl.*;
import by.zinkov.victor.command.impl.administrator.AddTransportTypeCommand;
import by.zinkov.victor.command.impl.administrator.DeleteTransportTypeCommand;
import by.zinkov.victor.command.impl.administrator.EditTransportType;
import by.zinkov.victor.command.impl.administrator.ShowTranspotTypesCommand;
import by.zinkov.victor.command.impl.courier.*;
import by.zinkov.victor.command.impl.user.*;

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
        commandMap.put(CommandEnum.TO_PASSWORD_RECOVERY_PAGE, new GoPageCommand((Page.FORGOT_PASSWORD)));
        commandMap.put(CommandEnum.SIGN_UP, new GoSignUpPageCommand());
        commandMap.put(CommandEnum.ALL_TRANSPORT_TYPES, new ShowTranspotTypesCommand());
        commandMap.put(CommandEnum.ADD_TRANSPORT_TYPE, new AddTransportTypeCommand());
        commandMap.put(CommandEnum.CHANGE_TRANSPORT_TYPE, new EditTransportType());
        commandMap.put(CommandEnum.REGISTER_COMMAND, new SignUpCommand());
        commandMap.put(CommandEnum.LOG_IN, new LogInUserCommand());
        commandMap.put(CommandEnum.ACTIVATE, new ActivateCommand());
        commandMap.put(CommandEnum.LOG_OUT, new LogOutCommand());
        commandMap.put(CommandEnum.SEND_RESTORE_TOKEN, new SendRestorePasswordTokenCommand());
        commandMap.put(CommandEnum.RESTORE_PASSWORD, new RestorePasswordCommand());
        commandMap.put(CommandEnum.TO_CHANGE_PASSWORD_PAGE, new GoPageCommand(Page.CHANGE_PASSWORD));
        commandMap.put(CommandEnum.TO_CREATE_ORDER_PAGE, new ToCreateOrderPage());
        commandMap.put(CommandEnum.CREATE_ORDER_PAGE_SECOND_STAGE, new CreateOrderFirstStageStage());
        commandMap.put(CommandEnum.FINISH_CREATING_ORDER, new FinishCreatingOrder());
        commandMap.put(CommandEnum.CHECK_LOGIN, new CheckLoginCommand());
        commandMap.put(CommandEnum.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commandMap.put(CommandEnum.TO_USER_ORDER_PAGE, new UserOrderCommand());
        commandMap.put(CommandEnum.EDIT_COURIER_PROFILE_PAGE, new ToEditProfilePageCommand());
        commandMap.put(CommandEnum.NEW_TRANSPORT_TYPE_FOR_COURIER, new NewTransportTypeForCourier());
        commandMap.put(CommandEnum.ADD_CARGO_TYPE_FOR_COURIER, new AddNewCargoTypeForCourier());
        commandMap.put(CommandEnum.NEW_COURIER_POSITION, new ChangeCourierLocation());
        commandMap.put(CommandEnum.DELETE_CARGO_TYPE_FOR_COURIER, new DeleteCourierCargoType());
        commandMap.put(CommandEnum.TO_COURIER_ACTIVE_ORDER_PAGE, new ToCourierActiveOrderPage());
        commandMap.put(CommandEnum.START_PERFOMING_ORDER_COMMAND, new StartPerfomingOrderCommand());
        commandMap.put(CommandEnum.FINISH_PERFOMING_ORDER_COMMAND, new FinishPerfomingOrderCommand());
        commandMap.put(CommandEnum.TO_CLIENT_COURIERS_PAGE, new ToClientCouriersPage());
        commandMap.put(CommandEnum.SET_USER_MARK, new SetUserMark());
    }

    /**
     * Return command by  or ErrorCommand
     *
     * @param command name
     * @return command implementation
     */
    public Command takeCommand(CommandEnum command) {
        Command neededCommand = commandMap.get(command);
        if (neededCommand == null) {
            throw new NoSuchCommandException(command.toString());
        }
        return neededCommand;
    }
}
