/**
 * Created by Vasilina on 19.07.2015.
 */
psyDirective = ['$rootScope', 'PsychologyCharacteristicsWrapper', '$parse', function ($rootScope, PsychologyCharacteristicsWrapper, $parse) {
  return {
    restrict: 'A',
    //scope: {
    //  qq : '=',
    //  qqId : '=elId'
    //},
    link: function ($scope, element, attr) {
      //console.log("init element " + attr.className + " " + attr.fieldName + " " + attr.elId + " " + $scope.$eval(attr.elId) + " " + element.innerHTML);
      PsychologyCharacteristicsWrapper.addEditableById(element, attr.className, attr.fieldName, $scope.$eval(attr.elId), $scope.$eval(element.text().replace(/{|}/g, "")));
    }
  }
}]
