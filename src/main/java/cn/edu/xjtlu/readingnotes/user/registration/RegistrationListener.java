package cn.edu.xjtlu.readingnotes.user.registration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import cn.edu.xjtlu.readingnotes.email.AccountEmailSender;
import cn.edu.xjtlu.readingnotes.user.User;

@Component
public class RegistrationListener
        implements ApplicationListener<OnRegistrationCompleteEvent> {
    
    private static final String CODE = "email.register";

    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private AccountEmailSender sender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        confirmRegistration(event);
    }
    
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        String token = UUID.randomUUID().toString();
        User user = event.getUser();

        String to = user.getEmail();
        String subject = "[ReadingNotes] Registration Confirmation";
        String url = event.getAppUrl() + "/register?token=" + token;
        String anchor = String.format("<a href=\"%s\">%s</a>", url, url);
        String template = messages.getMessage(CODE, null, event.getLocale());

        try {
            sender.send(to, subject, template, anchor);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        service.createVerificationToken(user, token);
    }
}
