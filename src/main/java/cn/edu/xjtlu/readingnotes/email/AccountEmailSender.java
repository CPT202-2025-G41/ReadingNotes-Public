package cn.edu.xjtlu.readingnotes.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;

@Component
public class AccountEmailSender {
    private static final Logger logger = LoggerFactory.getLogger(AccountEmailSender.class);
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender mailSender;

    private static String linesToHtmlTags(String tpl) {
        String[] paragraphs = Arrays.stream(tpl.split("\r\n\r\n"))
            .map(p -> "<p>" + p.replaceAll("\r\n", "<br>") + "</p>")
            .toArray(size -> new String[size]);
        return String.join("\r\n\r\n", paragraphs);
    }

    public void send(String to, String subject, String tpl, Object... args) 
            throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String htmlTpl = linesToHtmlTags(tpl);
        Object[] plainArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                plainArgs[i] = ((String) args[i]).replaceAll("</?.*?>", "");
            } else {
                plainArgs[i] = args[i];
            }
        }
        try {
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(
                String.format(tpl, plainArgs),
                String.format(htmlTpl, args));
            mailSender.send(helper.getMimeMessage());
        } 
        catch (MailException ex) {
            logger.error("e-mail sending failed: {}", ex.getMessage());
            throw ex;
        }
        catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw e;
        }
    }
}
