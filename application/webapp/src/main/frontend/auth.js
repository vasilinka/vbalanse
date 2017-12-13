/**
 * Created by Василинка on 14.08.14.
 */
var module = angular.module('exampleApp', ['ngRoute', 'ngCookies', 'exampleApp.services', 'ngAnimate', 'ui.bootstrap'])
    .config(
    [ '$routeProvider', '$locationProvider', '$httpProvider', function ($routeProvider, $locationProvider, $httpProvider) {

      $routeProvider.when('/login', {
        templateUrl: 'login2/login.html',
        controller: LoginController
      });
      $routeProvider.when('/forgotPassword', {
        templateUrl: 'user/forgotPassword.html',
        controller: ForgotPasswordController
      });
      $routeProvider.when('/changePassword', {
        templateUrl: 'user/changePassword.html',
        controller: ChangePasswordController
      });
      $routeProvider.when('/register', {
        templateUrl: 'user/register.html',
        controller: RegisterController
      });
      $routeProvider.otherwise({
        templateUrl: 'login2/login.html',
        controller: LoginController
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

    } ]
).run(function ($rootScope, $location, $cookieStore, UserService, Auth) {
      $rootScope.alert = {type: 'danger'};
      $rootScope.alerts = [];
      $rootScope.addAlert = function (message) {
        $rootScope.alert.msg = message;
        //$rootScope.alerts = [];
        $rootScope.alert.type = "danger";
        $rootScope.alerts = [$rootScope.alert];
      };
      $rootScope.closeAlert = function (index) {
        $rootScope.alerts.splice(index, 1);
      };


      /* Reset error when a new view is loaded */
      $rootScope.$on('$viewContentLoaded', function () {
        delete $rootScope.error;
      });

      $rootScope.hasRole = function (role) {

        if ($rootScope.user === undefined) {
          return false;
        }

        if ($rootScope.user.roles[role] === undefined) {
          return false;
        }

        return $rootScope.user.roles[role];
      };

      $rootScope.logout = function () {
        delete $rootScope.user;
        delete $rootScope.authToken;
        $cookieStore.remove('authToken');
        $location.path("/login");
      };

      /* Try getting valid user from cookie or go to login page */
      var originalPath = $location.path();
      //$location.path("/login");
      if (Auth.isAuthenticated()) {
        $rootScope.authToken = Auth.getAuth();
        UserService.getUser(function (user) {
          $rootScope.user = user;
          //$location.path(originalPath);
        });
      }

      $rootScope.initialized = true;
    });


function RegisterController($scope, UserService, $http) {
  $scope.showMessage = false;
  $scope.register = function () {
    UserService.register($scope.userInfo, function (authenticationResult) {
      $scope.showMessage = true;
    }, function (errorResult) {
      if (!angular.isUndefined(errorResult.data.fieldErrors)) {
        var validator = $("#registerForm").validate();
        validator.showErrors(errorResult.data.fieldErrors);
      }

    });
  }
}

function ForgotPasswordController($scope, UserService, $http) {
  $scope.showMessage = false;
  var form = $("#forgotPasswordForm");
  jQuery.extend(jQuery.validator.messages, {
    required: "Поле обязательное.",
    max: jQuery.validator.format("Поле не должно быть больше {0}."),
    min: jQuery.validator.format("Пожалуйста введите значение больше {0}."),
    email: jQuery.validator.format("Введите корректный email.")
  });

  $('#forgotPasswordForm').validate({
    rules: {

      username: {
        email: true,
        required: true
      }
    }

  });
  //var form = $scope.myForm;
  $scope.askToResetPassword = function () {
    if (form.valid()) {
      UserService.askToResetPassword($.param({username: $scope.username}), function (authenticationResult) {
        $scope.showMessage = true;
      }, function (errorResult) {
        if (!angular.isUndefined(errorResult.data.fieldErrors)) {
          var validator = $("#forgotPasswordForm").validate();
          validator.showErrors(errorResult.data.fieldErrors);
        }
      });
    }
  }
}

function ChangePasswordController($scope, UserService, $http, $location) {
  $scope.showMessage = false;
  $scope.changePassword = function () {
    $scope.userPasswordInfo.token = $location.search().changePassword;
    UserService.changePassword($scope.userPasswordInfo, function (authenticationResult) {
      $scope.showMessage = true;
    }, function (errorResult) {
    });
  }
}


function LoginController($scope, $rootScope, $location, $cookieStore, UserService, $animate) {
  $scope.name = "Yes";

  $scope.rememberMe = false;

  $scope.login = function () {
    UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function (authenticationResult) {
      var authToken = authenticationResult.token;
      if (!angular.isUndefined(authToken)) {
        $rootScope.authToken = authToken;
        //if ($scope.rememberMe) {
          $cookieStore.put('authToken', authToken);
        //}
        UserService.getUser(function (user) {
          $rootScope.user = user;
          //$location.path("/");
          if (user.roles["A"]) {
            window.location.href = "/";
          } else if (user.roles["P"]) {
            window.location.href = "/";
          } else if (user.roles["RU"]) {
            window.location.href = "/index.html";
          }
        });
      } else {
      }
    }, function (data, status, headers, config) {
      var $myForm = $('#myForm');
      $animate.addClass($myForm, 'shake', function () {
        $animate.removeClass($myForm, 'shake');
      });
    });

  };
};


var services = angular.module('exampleApp.services', ['ngResource']);


services.factory('UserService', UserService);
services.factory('Auth', Auth);
services.factory('DataService', DataService);
var controllers = {};
controllers.LoginController = LoginController;
module.controller(controllers);
module.controller('FormCtrl', ['scope', '$animate', function ($scope, $animate) {

  // hide error messages until 'submit' event
  $scope.submitted = false;

  // hide success message
  $scope.showMessage = false;

  // method called from shakeThat directive
  $scope.submit = function () {
    // show success message
    $scope.showMessage = true;
  };

}])

module.directive("vbFormInput", vbFormInput);
