package by.vbalanse.facade.content;

import by.vbalanse.dao.content.FeedbackDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.model.content.FeedbackEntity;
import by.vbalanse.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Zhuk on 12.10.15.
 */
@Service("feedbackFacade")
public class FeedbackFacadeImpl implements FeedbackFacade {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private UserDao userDao;

    @Override
    public FeedbackEntity saveFeedback(FeedbackInfo feedbackInfo) {

        FeedbackEntity feedbackEntity = new FeedbackEntity();

        if (feedbackInfo.getUserId() != null) {
            UserEntity userEntity = userDao.find(feedbackInfo.getUserId());
            feedbackEntity.setUserEntity$evaluated(userEntity);
            feedbackEntity.setFirstName(feedbackInfo.getFirstName());
            feedbackEntity.setEmail(feedbackInfo.getEmail());
            feedbackEntity.setMessage(feedbackInfo.getMessage());
            feedbackDao.save(feedbackEntity);
        }

        return feedbackEntity;
    }

    @Override
    @Deprecated
    public List<FeedbackEntity> findFeedbacks(Long userId) {
        return feedbackDao.findFeedbacks(userId);
    }

    @Override
    public long countFeedbacks(Long userId) {
        return feedbackDao.countFeedbacks(userId);
    }
}
