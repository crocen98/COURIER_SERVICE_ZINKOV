package by.zinkov.victor.builder.impl;

import by.zinkov.victor.builder.FromMapEntityBuilder;
import by.zinkov.victor.domain.User;
import by.zinkov.victor.domain.UserRole;
import by.zinkov.victor.domain.UserStatus;
import by.zinkov.victor.service.ServiceException;
import by.zinkov.victor.service.UserRoleService;
import by.zinkov.victor.service.UserStatusService;
import by.zinkov.victor.service.factory.ServiceFactory;

import java.util.Map;

public class UserBuilder implements FromMapEntityBuilder<User> {

    private static final String USER_ROLE_PARAMETER = "user_role";
    private static final String LOGIN_FIELD = "login";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String PASSWORD_FIELD = "password";
    private static final String LOCATION_FIELD = "location";

    @Override
    public User build(Map<String, String> requestParams) throws ServiceException {
        User user = new User();

        ServiceFactory factory = ServiceFactory.getInstance();

        UserRoleService service = factory.getUserRoleService();
        UserRole role = service.getByName(requestParams.get(USER_ROLE_PARAMETER));
        user.setUserRoleId(role.getId());

        UserStatusService userStatusService = factory.getUserStatusService();
        UserStatus userStatus = userStatusService.getByName(UserStatus.WAITING_CONFIRMATION.toString());
        user.setUserStatusId(userStatus.getId());

        user.setLogin(requestParams.get(LOGIN_FIELD));
        user.setFirstName(requestParams.get(FIRST_NAME_FIELD));
        user.setLastName(requestParams.get(LAST_NAME_FIELD));
        user.setEmail(requestParams.get(EMAIL_FIELD));
        user.setPhone(requestParams.get(PHONE_FIELD));
        user.setPassword(requestParams.get(PASSWORD_FIELD));
        user.setLocation(requestParams.get(LOCATION_FIELD));
        return user;
    }
}
