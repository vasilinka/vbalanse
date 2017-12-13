package by.vbalanse.dao.content;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.content.FeedbackEntity;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface FeedbackDao extends AbstractManagedEntityDao<FeedbackEntity> {

    List<FeedbackEntity> findFeedbacks(Long userId);

    Long countFeedbacks(Long userId);
}