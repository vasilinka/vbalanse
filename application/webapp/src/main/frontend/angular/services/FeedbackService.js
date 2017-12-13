/**
 * Created by Zhuk on 12.10.15.
 */

var actions = {
    saveFeedback: {
        method: 'POST',
        params: {'action': 'saveFeedback'},
        headers: {'Content-Type': 'application/json'}
    },
    findFeedbacks: {
        method: 'GET',
        params: {'action': 'findFeedbacks', userId:'@userId'},
        isArray: true
    }
};

var FeedbackService = function($resource){
    return $resource('rest/psy/feedback/:action', {}, actions);
};

