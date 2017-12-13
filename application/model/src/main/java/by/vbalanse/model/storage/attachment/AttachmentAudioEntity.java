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
import by.vbalanse.model.storage.StorageFileAudioEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = AttachmentAudioEntity.TABLE_NAME)
@ForeignKey(name = AttachmentAudioEntity.TABLE_NAME + AbstractEntity.DELIMITER_INDEX + AbstractEntity.COLUMN_ID)
public class AttachmentAudioEntity extends AbstractAttachmentEntity {

    public static final String TABLE_NAME = AbstractAttachmentEntity.TABLE_NAME + "$audio";

    public static final String COLUMN_FK_STORAGE_FILE$AUDIO = "fk_" + StorageFileAudioEntity.TABLE_NAME;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_FK_STORAGE_FILE$AUDIO)
    @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_STORAGE_FILE$AUDIO)
    private StorageFileAudioEntity audioFile;

    public StorageFileAudioEntity getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(StorageFileAudioEntity audioFile) {
        this.audioFile = audioFile;
    }

}