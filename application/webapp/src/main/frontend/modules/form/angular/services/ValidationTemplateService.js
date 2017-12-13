/**
 * Created by Zhuk on 27.10.15.
 */

var ValidationTemplateService = function(){

    var propertyNames = ['state', 'error'];
    var errorPropertyNames = ['token', 'message'];

    this.setTemplateSettings = function(options){
        return  _createHtmlTemplate(options);
    };

    var _createHtmlTemplate = function(options){
        var state = propertyNames[0];
        var error = propertyNames[1];
        var message = errorPropertyNames[1];
        var errorElement = undefined;

        if(options.hasOwnProperty(state) && options[state] != undefined){
            var stateElement = angular.element("<div></div>");
            stateElement.attr(options[state]);
        }

        if(options.hasOwnProperty(error) && options[error] != undefined){

            angular.forEach(options[error], function(value, key){

                errorElement = angular.element("<div></div>");
                errorElement.text(value[message]);
                delete value[message];
                errorElement.attr(value);
                stateElement.append(errorElement);
            });
        }

        return stateElement;
    };

};
