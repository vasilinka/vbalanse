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
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NamedQueries(
        value = {
                @NamedQuery(
                        name = "find.Attachment.by.attachmentGroupId",
                        query = "select aae from AbstractAttachmentEntity aae where aae.attachmentGroup.id = :attachmentGroupId"
                )
        }
)

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:e.terehov@itision.com">Eugene Terehov</a>
 */

@Entity
@Table(name = AbstractAttachmentEntity.TABLE_NAME)
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public abstract class AbstractAttachmentEntity extends AbstractManagedEntity {

    public static final String TABLE_NAME = "att_attachment";

    public static final String COLUMN_ATTACHMENT_GROUP = "fk_" + AttachmentGroupEntity.TABLE_NAME;
    public static final String COLUMN_NAME = "name_";
    public static final String COLUMN_DESCRIPTION = "description_";
    public static final String COLUMN_THUMBNAIL_WIDTH = "thumbnail_width_";

    public static final String DEFAULT_THUMBNAIL_WIDTH = "150";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_ATTACHMENT_GROUP)
    @ForeignKey(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_ATTACHMENT_GROUP)
    private AttachmentGroupEntity attachmentGroup;

    @Length(max = 100)
    @Column(name = COLUMN_NAME)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Length(max = 1000)
    @Column(name = COLUMN_DESCRIPTION)
    private String description;

    @Min(value = 150, message = "Thumbnail width must be more than 150")
    @Max(value = 1000, message = "Thumbnail width must be less than 1000")
    @Column (name = COLUMN_THUMBNAIL_WIDTH)
    private Short thumbnailWidth;

    public AttachmentGroupEntity getAttachmentGroup() {
        return attachmentGroup;
    }

    public void setAttachmentGroup(AttachmentGroupEntity attachmentGroup) {
        this.attachmentGroup = attachmentGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

  public Short getThumbnailWidth() {
    return thumbnailWidth;
  }

  public void setThumbnailWidth(Short thumbnailWidth) {
    this.thumbnailWidth = thumbnailWidth;
  }
}
