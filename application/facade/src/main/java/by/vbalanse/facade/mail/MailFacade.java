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

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface MailFacade {

    /**
     * Adds Mail to the mailing queue
     *
     * @param recipientTo Mail recipient
     * @param subject     Mail subject
     * @param text        Mail text
     * @param isTextPlain is Mail should be sent as text or html
     * @return unique Mail id
     */
    @Transactional
    long addMailToQueue(String recipientTo, String subject, String text, boolean isTextPlain);

    /**
     * Check if Mail was sent
     *
     * @param mailId Mail unique identifier
     * @return <code>true</code> if mail was sent to recipient
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    boolean wasMailSent(long mailId);

}
