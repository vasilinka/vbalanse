package by.vbalanse.dao.jpa.content;

import by.vbalanse.dao.content.FeedbackDao;
import by.vbalanse.dao.common.jpa.AbstractManagedEntityDaoSpringImpl;
import by.vbalanse.model.content.FeedbackEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Repository(value = "FeedbackDao")
public class FeedbackDaoImpl extends AbstractManagedEntityDaoSpringImpl<FeedbackEntity> implements FeedbackDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<FeedbackEntity> findFeedbacks(Long userId) {
        Query query = createQuery("select fe from FeedbackEntity fe where fe.userEntity$evaluated.id=:userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Long countFeedbacks(Long userId) {
        Query query = createQuery("select count(ce) from FeedbackEntity ce where ce.userEntity$evaluated.id=:userId");
        query.setParameter("userId", userId);
        return (long) query.getSingleResult();
    }
}