/**
 * Created by Zhuk on 28.10.15.
 */

var vbControl = ['$compile', 'TemplateFactory', function($compile, TemplateFactory){

    var tmpl = function(obj){
        var tmp = TemplateFactory.createHtmlTemplate(obj.form, obj.validation);
        return tmp;
    };

    return {

        require: '^vbForm',
        controller: function($scope, $element, $attrs){
            $scope.formNameControlName = $scope.formName + "." + $scope.p.form.name;

        },
        replace: true,
        link: function(scope, element, attr, ctrl){
            element.html(tmpl(scope.p));
            $compile(element.contents())(scope);

        }
    };
}];

