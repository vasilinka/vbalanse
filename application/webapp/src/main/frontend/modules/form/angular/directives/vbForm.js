/**
 * Created by Zhuk on 28.10.15.
 */

var vbForm = function(){

    var getFormName = function(obj){
        return  obj.name;
    };

    return {
        transclude: true,
        template: '<form name={{formObj.name}} data-ng-submit="saveFeedback()"><div ng-transclude></div></form>',
        replace: true,
        controller: function($scope, $element){
            $scope.formName = getFormName($scope.formObj);
            $element.attr($scope.formObj);
        }
    };
};
