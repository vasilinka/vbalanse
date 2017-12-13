/**
 * Created by Zhuk on 28.10.15.
 */

var TemplateFactory = function(ControlTemplateService, ValidationTemplateService){

    var resultHtmlTemplate = undefined;

    resultHtmlTemplate = angular.element("<div></div>");

    var createHtmlTemplate = function(controlOptions, validationOptions){

        resultHtmlTemplate = angular.element("<div></div>");
        var controlStringTemplate = ControlTemplateService.setTemplateSettings(controlOptions);
        resultHtmlTemplate.append(controlStringTemplate);

        if(validationOptions != undefined){
            var validationStringTemplate = ValidationTemplateService.setTemplateSettings(validationOptions);
            resultHtmlTemplate.append(validationStringTemplate);
        }
        return resultHtmlTemplate.html();
    };

    return {createHtmlTemplate: createHtmlTemplate};
};
