package samuel.oliveira.silva.roomschedulerapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import samuel.oliveira.silva.roomschedulerapi.domain.EmailEvent;
import samuel.oliveira.silva.roomschedulerapi.service.EmailService;

/**
 * Class to send email to the user.
 */
@Service
public class EmailServiceImpl implements EmailService {

  @Value("${spring.mail.username}")
  private String emailHost;

  @Autowired private JavaMailSender mailSender;

  @Override
  public void sendEmail(EmailEvent event, String emailDestiny) {
    var email = new SimpleMailMessage();
    email.setFrom(emailHost);
    email.setTo(emailDestiny);
    email.setSubject(event.getTitle());
    email.setText(event.getMessage());
    mailSender.send(email);
  }
}
