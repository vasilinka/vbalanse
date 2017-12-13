package by.vbalanse.dao.user.psychologist;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.article.AuditoryTypeEnum;
import by.vbalanse.model.psychologist.PsychologistEntity;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface PsychologistDao extends AbstractManagedEntityDao<PsychologistEntity> {
  PsychologistEntity findPsychologist(long userId);
}
