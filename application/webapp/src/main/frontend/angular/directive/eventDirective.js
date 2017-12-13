/**
 * Created by Vasilina on 14.04.2015.
 */
/**
 * Created by Vasilina on 05.04.2015.
 */
eventDirective = ['$rootScope', '$timeout', '$compile', function ($rootScope, $timeout, $compile) {
  return {
    restrict: 'A',
    scope: {
      callFunction: '&'
    },
    link: function ($scope, element, attr) {
      //$scope.callFunction();
      $scope.$watch(
        function(scope) {
          // watch the 'compile' expression for changes
          return element.attr('src');
        },
        function(value) {
          //if (toBoolean(angular.isUndefined(value))) {
            //alert(value);
            $scope.callFunction();

          //}
        });

    }
  }
}];
