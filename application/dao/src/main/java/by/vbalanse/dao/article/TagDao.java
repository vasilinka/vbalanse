package by.vbalanse.dao.article;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.article.TagEntity;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface TagDao extends AbstractManagedEntityDao<TagEntity> {
  List<TagEntity> findTags(String filter, List<Long> selectedIds);
}