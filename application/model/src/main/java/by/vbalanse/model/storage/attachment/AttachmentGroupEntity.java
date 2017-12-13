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

package by.vbalanse.model.storage.attachment;

import by.vbalanse.model.common.AbstractManagedEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = AttachmentGroupEntity.TABLE_NAME)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttachmentGroupEntity extends AbstractManagedEntity {

    public static final String TABLE_NAME = "att_attachment_group";

    @OneToMany(mappedBy = "attachmentGroup")
    private List<AbstractAttachmentEntity> attachments;

    public List<AbstractAttachmentEntity> getAttachments() {
        if (attachments == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(attachments);
    }

}
