/**
 * Created by Zhuk on 27.10.15.
 */

var ControlTemplateService = function(){

    var defaults = {
        control: "input",
        "class": "",
        name: ""
    };

    var control = 'control';

    var template = {};

    this.setTemplateSettings = function(options){
        template.settings = angular.extend({}, defaults, options);
        return _createHtmlTemplate(template.settings);
    };

    var _createHtmlTemplate = function(options){

        var element = angular.element("<" + options[control] + ">");
        delete options[control];
        element.attr(options);
        return element;
    };
};



