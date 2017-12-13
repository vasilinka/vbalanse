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

package by.vbalanse.facade.storage.attachment;

import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;

public class AttachmentClonedWrapper {

    private AbstractAttachmentEntity attachment;
    private StorageEntitiesWrapper storageEntitiesWrapper;

    public AbstractAttachmentEntity getAttachment() {
        return attachment;
    }

    public void setAttachment(AbstractAttachmentEntity attachment) {
        this.attachment = attachment;
    }

    public StorageEntitiesWrapper getStorageEntitiesWrapper() {
        return storageEntitiesWrapper;
    }

    public void setStorageEntitiesWrapper(StorageEntitiesWrapper storageEntitiesWrapper) {
        this.storageEntitiesWrapper = storageEntitiesWrapper;
    }
}