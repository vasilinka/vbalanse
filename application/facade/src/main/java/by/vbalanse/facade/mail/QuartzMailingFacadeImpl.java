/*
 * This Learning Management System (“Software”) is the exclusive and sole property of Baja Education. Inc. (“Baja”).
 * Baja has the sole rights to copy the software, create derivatives or modified versions of it, distribute copies
 * to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights which also includes
 * indirect copying  such as unauthorized translation of the code into a different programming language without
 * written explicit permission from Baja is an infringer and subject to liability for damages or statutory fines.
 * Interested parties may contact bpozos@gmail.com.
 *
 * (c) 2012 Baja Education
 */

package by.vbalanse.facade.mail;

import by.vbalanse.dao.mail.MailDao;
import by.vbalanse.model.mail.MailEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

@Service(value = "quartzMailingFacade")
public class QuartzMailingFacadeImpl implements QuartzMailingFacade {

  private static final Logger LOG = Logger.getLogger(QuartzMailingFacadeImpl.class);

  @Autowired
  private MailDao mailDao;

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${mailing.enabled}")
  private boolean enabled;

  @Value("${mail.from}")
  private String from;

  public void sendUnsentMessages() {
    if (enabled) {
      // getting all emails that were not sent and has failures count less than 30
      List<MailEntity> emails = mailDao.findNotSentMails(30);

      for (MailEntity email : emails) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // preparing email to be sent
        MimeMessageHelper helper = null;
        try {
          helper = new MimeMessageHelper(mimeMessage, "UTF-8");
          helper.setTo(email.getReceipientsTo());
          helper.setCc(email.getReceipientsTo());
          helper.setFrom(from);
          helper.setSubject(email.getSubject());
          helper.setText(email.getText(), !email.isTextPlain());
          helper.setSentDate(email.getDateOfCreate());

          javaMailSender.send(helper.getMimeMessage());

          email.setSent(true);
          email.setDateOfSend(new Date());
        } catch (Exception e) {
          LOG.error("Failed to send message " + e, e);

          email.setSendingFailed(email.getSendingFailed() + 1);
        }

        mailDao.update(email);
      }
    }
  }

}
