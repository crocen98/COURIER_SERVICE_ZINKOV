package by.zinkov.victor.util;

import by.zinkov.victor.dao.exception.ConnectionException;
import by.zinkov.victor.service.exception.ServiceException;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailSender {
    private static MailSender ourInstance = new MailSender();

    private final Properties properties = new Properties();
    private final Session session;
    public static MailSender getInstance() {
        return ourInstance;
    }

    private MailSender() {
        try {
            properties.load(MailSender.class.getClassLoader().getResourceAsStream("mail.properties"));
            session =  Session.getDefaultInstance(properties);
        } catch (IOException  e) {
            throw new ConnectionException("cannot find property file mail.properties!", e);
        }
    }

    public void sendEmail(String text , String email) throws ServiceException {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("courier.ervice@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("New account on site \"Couriers service\"");
            message.setText(text);

            Transport transport = session.getTransport();
            transport.connect(null,"1111AAaa");
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new ServiceException("Cannot send email");
        }
    }

}
