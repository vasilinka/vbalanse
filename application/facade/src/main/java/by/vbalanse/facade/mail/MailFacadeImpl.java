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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "mailFacade")
public class MailFacadeImpl implements MailFacade {

  @Autowired
  private MailDao mailDao;


  public long addMailToQueue(String recipientTo, String subject, String text, boolean isTextPlain) {
    MailEntity mail = new MailEntity();
    mail.setReceipientsTo(recipientTo);
    mail.setSubject(subject);
    mail.setText(text);
    mail.setDateOfCreate(new Date());
    mail.setTextPlain(isTextPlain);

    mailDao.save(mail);

    return mail.getId();
  }

  public boolean wasMailSent(long mailId) {
    MailEntity mail = mailDao.find(mailId);
    if (mail == null) {
      throw new MailNotFoundException();
    }

    return mail.isSent();
  }

}
