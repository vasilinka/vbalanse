/**
 * Created by Zhuk on 04.11.15.
 */
var feedbackCarousel = ['$rootScope', 'FeedbackService', function ($rootScope, FeedbackService) {
    return {
        scope: {
            userId: '@attrId'
        },
        replace: true,
        controller: function ($scope) {

            $scope.loadCarousel = function () {
                $('.carousel').carousel();
            };
        },
        link: function ($scope) {

            FeedbackService.findFeedbacks({userId: $scope.userId}, function (result) {

                    if (result.length == 0) {
                        result[0] = {firstName: '', message: 'Отзывов нет'};
                    }
                    $scope.feedBacks = result;
                },
                function (error) {
                    $rootScope.addAlert('Непредсказуемая ошибка произошла на сервере! Пожалуйста, попробуйте позже.', 'danger');
                });
        },
        templateUrl: 'angular/template/feedbackCarousel.html'
    };
}];
