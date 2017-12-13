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

import by.vbalanse.model.common.AbstractEntity;
import by.vbalanse.model.storage.StorageFileArchiveEntity;
import by.vbalanse.model.storage.StorageSubfolderEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@Entity
@Table(name = AttachmentContentPageEntity.TABLE_NAME)
@ForeignKey(name = AttachmentContentPageEntity.TABLE_NAME + AbstractEntity.DELIMITER_INDEX + AbstractEntity.COLUMN_ID)
public class AttachmentContentPageEntity extends AbstractAttachmentEntity {

    public static final String TABLE_NAME = AbstractAttachmentEntity.TABLE_NAME + "$content_page";

    public static final String COLUMN_FK_FILE_ARCHIVE = "fk_" + StorageFileArchiveEntity.TABLE_NAME;
    public static final String COLUMN_FK_SUBFOLDER_PREVIEW = "fk_" + StorageSubfolderEntity.TABLE_NAME;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_FK_FILE_ARCHIVE)
    @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_FILE_ARCHIVE)
    private StorageFileArchiveEntity archiveFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_FK_SUBFOLDER_PREVIEW)
    @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_SUBFOLDER_PREVIEW)
    private StorageSubfolderEntity previewFolder;

    public StorageFileArchiveEntity getArchiveFile() {
        return archiveFile;
    }

    public void setArchiveFile(StorageFileArchiveEntity archiveFile) {
        this.archiveFile = archiveFile;
    }

    public StorageSubfolderEntity getPreviewFolder() {
        return previewFolder;
    }

    public void setPreviewFolder(StorageSubfolderEntity previewFolder) {
        this.previewFolder = previewFolder;
    }

}