package cn.edu.xjtlu.readingnotes.user.registration;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.edu.xjtlu.readingnotes.user.User;
import cn.edu.xjtlu.readingnotes.util.Role;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class RegistrationController {
    
    private static final String CODE = "register.email";

    @Autowired
    private UserService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    private PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
    @PostMapping(path = "/register", params = "!token")
    public String register(
            @RequestParam Map<String,String> params,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
            ) {
        String template = messages.getMessage(CODE, null, Locale.ENGLISH);
        final String name = params.get("username");
        final String key = passwordEncoder.encode(params.get("password"));
        final String email = params.get("email");
        final User user = new User(name, key, email);
        user.setRole(Role.USER);
        user.setIsNonLocked(true);
        user.setIsEnabled(false);
        service.createUser(user);
        StringBuilder urlBuilder = new StringBuilder("http://");
        urlBuilder.append(request.getServerName() + ":");
        urlBuilder.append(request.getServerPort());
        String appUrl = urlBuilder.toString();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, Locale.ENGLISH, appUrl));

        StringBuilder emailAddr = new StringBuilder(email);
        emailAddr.replace(1, emailAddr.indexOf("@") - 1, "***");
        String message = String.format(template, emailAddr.toString());

        redirectAttributes.addFlashAttribute("title", "Registration");
        redirectAttributes.addFlashAttribute("msg", message);
        redirectAttributes.addFlashAttribute("jump", false);

        return "redirect:/registered";
    }
    
    @GetMapping(path = "/register", params = "token")
    public String activateAccount(
            HttpServletRequest request,
            @RequestParam("token") String uuid,
            RedirectAttributes redirectAttributes)
            throws Exception {
        VerificationToken token = service.getVerificationToken(uuid);
        Calendar calendar = Calendar.getInstance();
        if (token.getExpiryDate().getTime() > calendar.getTime().getTime()) {
            service.enableUser(token.getUser());
            service.cleanToken(token);

            String msg = messages.getMessage("register.ok", null, Locale.ENGLISH);
            redirectAttributes.addFlashAttribute("title", "Login");
            redirectAttributes.addFlashAttribute("msg", msg);
            redirectAttributes.addFlashAttribute("jump", true);
            return "redirect:/registered";
        }
        throw new Exception();
    }

    @GetMapping("/registered")
    public String jump() {
        return "registered";
    }
    
}
