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

package by.vbalanse.model.mail;

import by.vbalanse.model.common.AbstractManagedVersionedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@NamedQueries(value = {
        @NamedQuery(
                name = "find.Mail.by.maxSendingFailed.order.by.dateOfCreate",
                query = "select me from MailEntity me " +
                        "where me.sent=false and me.sendingFailed<=:maxSendingFailed " +
                        "order by me.dateOfCreate"
        )
})

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:e.terehov@itision.com">Eugene Terehov</a>
 */
@Entity
@Table(name = MailEntity.TABLE_NAME)
public class MailEntity extends AbstractManagedVersionedEntity {

    public static final String TABLE_NAME = "eml_mail";
    public static final String COLUMN_RECIPIENTS_TO = "recipients_cc";
    public static final String COLUMN_RECIPIENTS_CC = "recipients_to";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_DATE_OF_CREATE = "date_of_create";
    public static final String COLUMN_DATE_OF_SEND = "date_of_send";
    public static final String COLUMN_IS_TEXT_PLAIN = "is_text_plain";
    public static final String COLUMN_IS_SENT = "is_sent";
    public static final String COLUMN_SENDING_FAILED = "sending_failed";

    @Column(name = COLUMN_RECIPIENTS_TO)
    private String receipientsTo;

    @Column(name = COLUMN_RECIPIENTS_CC)
    private String receipientsCC;

    @Column(name = COLUMN_SUBJECT)
    @Lob
    @Size(max = 1000)
    private String subject;

    @Column(name = COLUMN_TEXT)
    @Lob
    @Size(max = 200000)
    private String text;

    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = COLUMN_DATE_OF_CREATE)
    private Date dateOfCreate;

    @NotNull
    @Column(name = COLUMN_IS_TEXT_PLAIN)
    private boolean textPlain;

    @NotNull
    @Column(name = COLUMN_IS_SENT)
    private boolean sent;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = COLUMN_DATE_OF_SEND)
    private Date dateOfSend;

    @NotNull
    @Column(name = COLUMN_SENDING_FAILED)
    private long sendingFailed;

    public String getReceipientsTo() {
        return receipientsTo;
    }

    public void setReceipientsTo(String receipientsTo) {
        this.receipientsTo = receipientsTo;
    }

    public String getReceipientsCC() {
        return receipientsCC;
    }

    public void setReceipientsCC(String receipientsCC) {
        this.receipientsCC = receipientsCC;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public boolean isTextPlain() {
        return textPlain;
    }

    public void setTextPlain(boolean textPlain) {
        this.textPlain = textPlain;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public Date getDateOfSend() {
        return dateOfSend;
    }

    public void setDateOfSend(Date dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public long getSendingFailed() {
        return sendingFailed;
    }

    public void setSendingFailed(long sendingFailed) {
        this.sendingFailed = sendingFailed;
    }

}
