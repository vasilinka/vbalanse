/**
 * Created by Vasilina on 03.02.2015.
 */
function AuthorController($scope, $rootScope, $rootScope, $routeParams, UserService, FeedbackService, PsychologyCharacteristicsWrapper) {
    $scope.userId = $routeParams['userId'];
    $scope.feedback = {};
    $scope.feedback.userId = $scope.userId;
    $scope.feedback.firstName = "";
    $scope.feedback.email = "";
    $scope.feedback.message = "";

    $scope.saveFeedback = function () {

        if ($('.home-contact-frm').valid()) {
            FeedbackService.saveFeedback($scope.feedback, function (result) {
                    $scope.feedback = {userId: $scope.userId};
                    $rootScope.addAlert('Отзыв приянт успешно на модерацию', 'success');
                },
                function (error) {
                    $rootScope.addAlert('Непредсказуемая ошибка произошла на сервере! Пожалуйста, попробуйте позже.', 'danger');
                });
        }
    };

    UserService.getPsychologist({psychologistId: $scope.userId}, function (result) {
        $scope.psychologist = result;
        $scope.psychologist.avatar.url = result.avatar.url;
       /* $rootScope.psychologyCharacteristics = PsychologyCharacteristicsWrapper.initPsychologyCharacteristics(result.userMeta);
        $rootScope.bonuses = PsychologyCharacteristicsWrapper.initBonuses($scope.psychologist.bonuses);*/

        var psyObj = PsychologyCharacteristicsWrapper.initAuthorPsychologist(result);
        $rootScope.authorPsychologyCharacteristics = psyObj.authorPsychologyCharacteristics;
        $rootScope.bannerTitles = psyObj.bannerTitles;
        $rootScope.bonuses = psyObj.bonuses;
    });

   // $rootScope.bonuses = PsychologyCharacteristicsWrapper.initBonuses(null);
  //  $rootScope.psychologyCharacteristics = PsychologyCharacteristicsWrapper.getDefaultPsychologiesValues();

    /*  $rootScope.bonuses = [
     {
     "title": "внимательность",
     "description": "What does this team member to? Keep it short! This is also a great spot for social links!"
     },
     {
     "title": "лояльность",
     "description": "What does this team member to? Keep it short! This is also a great spot for social links!"
     },
     {
     "title": "терпение",
     "description": "What does this team member to? Keep it short! This is also a great spot for social links!"
     }
     ];*/
    $rootScope.articles = [
        {
            "title": "Статья автора 1",
            "excerpt": "This portfolio entry has a custom excerpt using the built-in WP excerpt field."
        },
        {
            "title": "Статья автора 2",
            "excerpt": "This portfolio entry has a custom excerpt using the built-in WP excerpt field."
        }, {
            "title": "Статья автора 1",
            "excerpt": "This portfolio entry has a custom excerpt using the built-in WP excerpt field."
        },
        {
            "title": "Статья автора 3",
            "excerpt": "This portfolio entry has a custom excerpt using the built-in WP excerpt field."
        },
        {
            "title": "Статья автора 4",
            "excerpt": "This portfolio entry has a custom excerpt using the built-in WP excerpt field."
        },
        {
            "title": "Статья автора 5",
            "excerpt": "This portfolio entry has a custom excerpt using the built-in WP excerpt field."
        }

    ];

    $scope.load = function () {
        var map = new GMaps({
            el: '#map',
            lat: -12.043333,
            lng: -77.028333
        });
    };


    $('.home-contact-frm').validate({
            errorPlacement: function (error, element) {
                error.insertBefore(element);
            },
            rules: {
                name: {
                    required: true,
                    minlength: 2
                },
                email: {
                    required: true,
                    email: true
                },
                message: {
                    required: true,
                    rangelength: [10, 1000]
                }
            },
            messages: {
                name: {
                    required: "Поле обязательное для заполнения",
                    minlength: "Короткое имя"
                },
                email: {
                    required: "Поле обязательное для заполнения",
                    email: "Некорректный email"
                },
                message: {
                    required: "Поле обязательное для заполнения",
                    rangelength: "Сообщение может быть от 10 до 1000 символов"
                }
            }
        }
    );
}
