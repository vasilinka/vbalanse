/**
 * Created by Vasilina on 05.04.2015.
 */
elasticColumns = ['$rootScope', '$timeout', function ($rootScope, $timeout) {
  return {
    restrict: 'A',
    scope: {},
    link: function ($scope, element, attr) {
      var columns = 3;
      var setColumns = function () {
        //var columns2 = Math.min(Math.round(width / idealColumnWidth2), 12) || 1;
        var width2 = element.width();
        if (width2 == 0) {
          $timeout(setColumns(), 10);
        } else {
          var width = width2 - 140;
          //var idealColumnWidth = 250;
          //columns = Math.min(Math.round(width / idealColumnWidth), 12) || 1;
          //columns = width / idealColumnWidth;
          element.elasticColumns('set', 'columns', columns);
          element.elasticColumns('refresh');
        }
      };
      $(window).resize(setColumns);
      $timeout(function(){
        setColumns();
      });
    }
  }
}];