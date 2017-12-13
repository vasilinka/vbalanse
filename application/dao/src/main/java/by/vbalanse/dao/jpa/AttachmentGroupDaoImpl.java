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
import by.vbalanse.dao.storage.attachment.AttachmentDao;
import by.vbalanse.dao.storage.attachment.AttachmentGroupDao;
import by.vbalanse.model.storage.attachment.AttachmentGroupEntity;
import org.springframework.stereotype.Repository;

@Repository("attachmentGroupDao")
public class AttachmentGroupDaoImpl extends AbstractManagedEntityDaoSpringImpl<AttachmentGroupEntity> implements AttachmentGroupDao {

    private AttachmentDao attachmentDao;

    public void setAttachmentDao(AttachmentDao attachmentDao) {
        this.attachmentDao = attachmentDao;
    }

    @Override
    public void delete(AttachmentGroupEntity entity) {
        // deleting all attachments from the group
        attachmentDao.deleteAll(entity.getAttachments());

        // deleting the attachment group itself
        super.delete(entity);
    }

}
