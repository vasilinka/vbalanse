/**
 * Created by Василинка on 14.08.14.
 */
var module = angular.module('exampleApp', ['ngRoute', 'ngCookies', 'ngAnimate']);


module.controller("shakeTest", function ($scope, $animate) {
 // jQuery(document).ready(function () {
    /*var $hello = $('#my-element');
    $hello.click(function () {
      $animate.addClass($hello, 'ng-hide', function () {
        $animate.removeClass($hello, 'ng-hide');
      });

    });*/

 // });
});

module.directive('shakeThat', ['$animate', function($animate) {

    return {
      /*require: '^form',
      scope: {
        submit: '&',
        submitted: '='
      },*/
      link: function(scope, element, attrs, form) {

        // listen on submit event
        element.on('click', function() {

          // tell angular to update scope
          scope.$apply(function() {

            // everything ok -> call submit fn from controller
         //   if (form.$valid) return scope.submit();

            // show error messages on submit
           // scope.submitted = true;

            // shake that form
            $animate.addClass(element, 'shake', function() {
              $animate.removeClass(element, 'shake');
            });

          });

        });

      }
    };

  }]);


  /*$scope.newsEntries = NewsService.query();

   $scope.deleteEntry = function (newsEntry) {
   newsEntry.$remove(function () {
   $scope.newsEntries = NewsService.query();
   });
   };*/

