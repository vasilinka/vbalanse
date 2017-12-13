/**
 * Created by Vasilina on 05.04.2015.
 */
overlay = ['$rootScope', function ($rootScope) {
  return {
    restrict: 'A',
    scope: {},
    link: function ($scope, element, attr) {
      var elementId = element.attr('overlay-div');
      var content = document.getElementById(elementId);
      element.on('mouseover', function () {
        content.style.display = "block";
      });
      element.on('mouseout', function () {
        content.style.display = "none";
      });
      //element.

      //var scope = content.scope();
      //$compile(content)(scope);
      //scope.$digest();

//      element.overlay(attr);
    }
  }
}];