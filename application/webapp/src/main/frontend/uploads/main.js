/**
 * Created by Василинка on 13.11.2014.
 */
var module = angular.module('exampleApp', ['ngRoute', 'ngGrid', 'ngSanitize', 'smart-table', 'ui.select', 'ngCookies', 'ngTagsInput', 'exampleApp.services', 'ui.bootstrap', 'puElasticInput'/*, 'xeditable'*/])
  .config(
  ['$routeProvider', '$locationProvider', '$httpProvider', function ($routeProvider, $locationProvider, $httpProvider) {

    $routeProvider.when('/article', {
      templateUrl: 'angular/views/logged/article/article.html',
      controller: ArticleController
    });
    $routeProvider.when('/articleList', {
      templateUrl: 'angular/views/logged/article/articleList.html',
      controller: ArticleListController
    });
    $routeProvider.when('/articleGuest', {
      templateUrl: 'angular/views/guest/article.html',
      controller: GuestArticleController
    });
    $routeProvider.when('/profile', {
      templateUrl: 'angular/views/profile/profile.html',
      controller: ProfileController
    });
    $routeProvider.when('/author', {
      templateUrl: 'angular/views/guest/author.html',
      controller: AuthorController
    });
      $routeProvider.when('/test', {
          templateUrl: 'angular/views/guest/test.html',
          controller: TestController
      });
    $routeProvider.otherwise({
      templateUrl: 'angular/views/guest/articleList.html',
      controller: MainController
    });


    // $locationProvider.hashPrefix('!');

    /* Register error provider that shows message on failed requests or redirects to login page on
     * unauthenticated requests */
    $httpProvider.interceptors.push(function ($q, $rootScope, $location, $animate) {
        return {
          'responseError': function (rejection) {
            var status = rejection.status;
            var config = rejection.config;
            var data = rejection.data;
            var method = config.method;
            var url = config.url;
            showError(data, $rootScope);

            if (status == 401) {
              window.location.href = "/auth.html";
            } else {
              $rootScope.error = method + " on " + url + " failed with status " + status;
            }

            return $q.reject(rejection);
          }
        };
      }
    );

    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
     * as soon as there is an authenticated user */
    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
        return {
          'request': function (config) {
            var isRestCall = config.url.indexOf('rest') == 0;
            if (isRestCall && angular.isDefined($rootScope.authToken)) {
              var authToken = $rootScope.authToken;
              if (exampleAppConfig.useAuthTokenHeader) {
                config.headers['X-Auth-Token'] = authToken;
              } else {
                config.url = config.url + "?token=" + authToken;
              }
            }
            return config || $q.when(config);
          }
        };
      }
    );

  }]
).run(function ($rootScope, $location, $cookieStore, Auth, UserService, $timeout, $cacheFactory/*, editableOptions*/) {
    $rootScope.cache = $cacheFactory('cacheId');
    //editableOptions.theme = 'bs3';

    $rootScope.test1 = "qq";

    //UserService.getUser(function (user) {
    //  $rootScope.user = user;
    //});
    //$rootScope.articles = ['Item 1', 'Item 2', 'Item 3', 'Item 4'];
    $rootScope.authToken = Auth.getAuth();
    $rootScope.alert = {type: 'danger', msg: ''};
    $rootScope.alerts = [];
    $rootScope.addAlert = function (message, messageType) {
      $rootScope.alert.msg = message;
      if (angular.isUndefined(messageType)) {
        messageType ="danger";
      }
      $rootScope.alert.type = messageType;
      //$rootScope.alerts = [];
      $rootScope.alerts = [$rootScope.alert];
      /*$timeout(function() {
        $('.alert').delay(2 * 1000).fadeOut();
      });*/
    };
    //$rootScope.addAlert("Это просто тест алертов");
    $rootScope.closeAlert = function (index) {
      $rootScope.alerts.splice(index, 1);
    };
    $('#searchInput').autoGrow({
      comfortZone:70
    });
    /* Reset error when a new view is loaded */
    $rootScope.$on('$viewContentLoaded', function () {
      delete $rootScope.error;
    });
    $rootScope.logout = function () {
      delete $rootScope.user;
      delete $rootScope.authToken;
      $cookieStore.remove('authToken');
      $location.path("/login");
    };
    $rootScope.toBoolean = toBoolean;
    $rootScope.initialized = true;
  });

var services = angular.module('exampleApp.services', ['ngResource']);
services.factory('ArticleService', ArticleService);
services.service('PsychologyCharacteristicsWrapper', PsychologyCharacteristicsWrapper);
services.service('DropZoneService', DropZoneService);
services.service('SliderService', SliderService);
services.service("DropzoneImgService", DropzoneImgService);
services.service("SliderParamsService", SliderParamsService);
services.service("FeedbackService", FeedbackService);


services.service("ControlTemplateService", ControlTemplateService);
services.service("ValidationTemplateService", ValidationTemplateService);
services.factory("TemplateFactory", TemplateFactory);
module.directive("vbControl", vbControl);
module.directive("vbForm", vbForm);
services.controller('TestController', TestController);

services.factory('GuestArticleController', GuestArticleController);
services.factory('UserService', UserService);
services.factory('DataService', DataService);
services.controller('ArticleController', ArticleController);
services.controller('AuthorController', AuthorController);
services.controller('ArticleListController', ArticleListController);
services.controller('ProfileController', ProfileController);
module.filter('propsFilter', propsFilter);
module.controller('MainController', MainController);

services.factory("SliderFactory", SliderFactory);
services.factory('Auth', Auth);
services.factory('ArticleService', ArticleService);

module.directive("ckEditor", ckEditorDirective);
module.directive("vbCarousel", vbCarousel);

module.directive("vbCarouselAuth", vbCarouselAuth);

module.directive("eventDirective", eventDirective);
module.directive("loaddirective", LoadDirective);
module.directive("indiClick", IndiClick);
module.directive("magnificPopup", magnificPopup);
module.directive("overlay", overlay);
module.directive("elasticColumns", elasticColumns);
module.directive("elasticColumnsMy", elasticColumnsMy);
module.directive("vbFormInput", vbFormInput);
module.directive("changeIcon", changeIconDirective);
