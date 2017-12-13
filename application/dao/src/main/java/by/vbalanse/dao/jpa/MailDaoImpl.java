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

package by.vbalanse.dao.jpa;

import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.dao.mail.MailDao;
import by.vbalanse.model.mail.MailEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "mailDao")
public class MailDaoImpl extends AbstractManagedEntityDaoSpringImpl<MailEntity> implements MailDao {

    @SuppressWarnings("unchecked")
    public List<MailEntity> findNotSentMails(long maxSendingFailed) {
        Query namedQuery = getNamedQuery("find.Mail.by.maxSendingFailed.order.by.dateOfCreate");

        namedQuery.setParameter("maxSendingFailed", maxSendingFailed);

        return namedQuery.getResultList();
    }

}
