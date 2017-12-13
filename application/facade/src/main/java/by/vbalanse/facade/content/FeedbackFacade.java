package by.vbalanse.facade.content;

import by.vbalanse.model.content.FeedbackEntity;

import java.util.List;

/**
 * Created by Zhuk on 12.10.15.
 */
public interface FeedbackFacade {

    FeedbackEntity saveFeedback(FeedbackInfo feedbackEntity);

    List<FeedbackEntity> findFeedbacks(Long userId);

    long countFeedbacks(Long userId);

}
