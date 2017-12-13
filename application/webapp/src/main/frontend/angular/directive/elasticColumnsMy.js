/**
 * Created by Vasilina on 05.04.2015.
 */
elasticColumnsMy = ['$rootScope', '$timeout', function ($rootScope, $timeout) {
  return {
    restrict: 'A',
    scope: {},
    link: function ($scope, element, attr) {
      var columns = 3;
      element.addClass("clearfix");
      var attrColumns = parseInt(element.attr('data-columns'));
      if (angular.isDefined(attrColumns) && attrColumns>0) {
        columns = attrColumns;
      }
      var countCalled = 0;
      var setColumns = function (countCalled) {
        //var columns2 = Math.min(Math.round(width / idealColumnWidth2), 12) || 1;
        var width2 = element.width();
        console.log(width2 + " elastic");
        if (width2 == 0) {
          if (countCalled < 5) {
            countCalled++;
            $timeout(setColumns(countCalled), 10);
          }
        } else {
          setTimeout(function() {
            // Run code here, resizing has "stopped"
            var children = element.children();
            var columnWidth = Math.floor(width2/columns);
            children.each(function( index ) {
              var find = $(this).find(".one-column");
              find.css("width", columnWidth + "px");
              find.css("float", "left");
              console.log(children[0].style.width);
              console.log(children[0].outerHTML);
            });
          }, 0);

        }
      };
      $(window).on('resize', function(e) {

        clearTimeout(resizeTimer);
        var resizeTimer = setTimeout(function() {
          setColumns();
          // Run code here, resizing has "stopped"

        }, 250);

      });
      $timeout(function(){
        setColumns();
      });
    }
  }
}];