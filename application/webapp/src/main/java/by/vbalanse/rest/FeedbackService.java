package by.vbalanse.rest;

import by.vbalanse.convert.Transferer;
import by.vbalanse.facade.ValidationException;
import by.vbalanse.facade.content.FeedbackFacade;
import by.vbalanse.facade.content.FeedbackInfo;
import by.vbalanse.model.content.FeedbackEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Zhuk on 12.10.15.
 */
@Controller
@RequestMapping("/psy/feedback")
public class FeedbackService {

    final static Logger logger = Logger.getLogger(FeedbackService.class);

    @Autowired
    FeedbackFacade feedbackFacade;

    @Autowired
    private Transferer transferer;

    @RequestMapping(value = "saveFeedback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    FeedbackInfo saveFeedback(@RequestBody FeedbackInfo feedback) throws ValidationException {

        FeedbackEntity feedbackEntity = feedbackFacade.saveFeedback(feedback);

        FeedbackInfo feedbackInfo = new FeedbackInfo();

        feedbackInfo.setFirstName(feedbackEntity.getFirstName());
        feedbackInfo.setMessage(feedbackEntity.getMessage());
        feedbackInfo.setEmail(feedbackEntity.getEmail());

        return feedbackInfo;
    }

    @RequestMapping(value = "findFeedbacks", method = RequestMethod.GET)
    public
    @ResponseBody
    List<FeedbackInfo> findFeedbacks(@RequestParam(value = "userId") Long userId){

        List<FeedbackEntity> feedbacks = feedbackFacade.findFeedbacks(userId);

        List<FeedbackInfo> feedbackInfos = transferer.transferList(FeedbackEntity.class, FeedbackInfo.class, feedbacks);
//return null;
        return feedbackInfos;
    }

}
