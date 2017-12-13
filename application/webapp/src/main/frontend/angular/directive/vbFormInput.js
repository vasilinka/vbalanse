/**
 * Created by Василинка on 05.12.2014.
 */
vbFormInput = function ($compile) {
  return {
    restrict: "AE",
    scope: {
      fieldName: '@fieldName',
      fieldTitle: '@fieldTitle',
      fieldType: '@fieldType',
      ngModel: '=',
      bindAttr: '='
    },
    //require: 'ngModel',
    replace: true,
    //transclude: true,
    templateUrl: "angular/template/formInput.html",
    link: function (scope, element, attrs, ngModel) {
      if (scope.fieldType === undefined) {
        scope.fieldType = "text";
      }
      console.log(scope.fieldType);
      var model = element.attr('ng-model');
      //$compile(element.contents())(scope.$parent);
    }
  }
}