/**
 * Created by Василинка on 25.11.2014.
 */
//var authModule = angular.module('Auth', ['ngCookies']).
//    provider('$route', Auth);

Auth = function ($rootScope, $cookieStore, UserService) {
  UserService.getUser(function (user) {
    $rootScope.user = user;
    if (user.roles['ROLE_ANONYMOUS'] == true) {
      $rootScope.authenticated = false;
    } else if (user.roles['RU']== true  || user.roles['P']== true || user.roles['ROLE_A']== true) {
      $rootScope.authenticated = true;
    }
    //if (user.roles[])
  });
  return {
    getAuth: function () {
      return $cookieStore.get('authToken');
    },
    isAuthenticated: function () {
      var authToken = this.getAuth();
      return authToken !== undefined;
    }}
}

function showError(data, $rootScope) {
  if (data.status != 401 && isValidateError(data)) {
    if (!angular.isUndefined(data.message)) {
      message = "Ошибка: " + data.message;
    } else {
      message = "Непредсказуемая ошибка произошла на сервере! Пожалуйста, попробуйте позже.";
    }
    var alert = {};
    alert.msg = message;
    alert.type = "danger";
    $rootScope.addAlert(message);
    //alert("Произошла ошибка на сервере: " + data.message);
  }
}
function isValidateError(data) {
  return data.type != "V";
}

