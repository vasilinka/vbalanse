/**
 * Created by Vasilina on 31.05.2015.
 */
PsychologyCharacteristicsWrapper = (function ($rootScope) {
    //private static attributes
    //private static function
    function checkCharacteristic(isbn) {

    }

    //return constructor
    return function (EditableRegistry, config, JsUtils) {
        //private attributes
        var testAttr;

        //privileged methods
        this.getDefaultPsychologiesValues = function () {
//      return defaultPsychologyCharacteristics;
        };

        var _initAuthorPsychologyCharacteristics = function (psychologyCharacteristics) {
            var psychologyFieldsInfo = EditableRegistry.getFieldsForClass('AuthorPropertyForListKeyValueEntity');
            var fields = psychologyFieldsInfo['fields'];
            for (var key in  fields) {
                var obj = fields[key];
                if (psychologyCharacteristics != null && angular.isDefined(psychologyCharacteristics) && angular.isDefined(psychologyCharacteristics[key]) && psychologyCharacteristics[key].value != '') {
                    $().extend(obj, psychologyCharacteristics[key]);
                } else {
                    delete fields[key];
                }
            }
            return psychologyFieldsInfo;
        };

        var _getFieldsForClass = function (fieldClass, psychologyCharacteristics) {

            var fields = fieldClass['fields'];
            for (var key in  fields) {
                var obj = fields[key];
                if (psychologyCharacteristics != null && angular.isDefined(psychologyCharacteristics) && angular.isDefined(psychologyCharacteristics[key])/* && psychologyCharacteristics[key].value != null*/) {
                    $().extend(obj, psychologyCharacteristics[key]);
                } else {
                    //obj.value = fields[key].defaultValue;
                }
            }
            return fieldClass;
        };

        var _initPsychologyCharacteristics = function (psychologyCharacteristics) {
            var psychologyFieldsInfo = EditableRegistry.getFieldsForClass('PropertyForListKeyValueEntity');
            /*             var fields = psychologyFieldsInfo['fields'];
             for (var key in  fields) {
             var obj = fields[key];
             if (psychologyCharacteristics != null && angular.isDefined(psychologyCharacteristics) && angular.isDefined(psychologyCharacteristics[key])) {
             $().extend(obj, psychologyCharacteristics[key]);
             } else {
             obj.value = fields[key].defaultValue;
             }
             }
             return psychologyFieldsInfo;*/
            return _getFieldsForClass(psychologyFieldsInfo, psychologyCharacteristics);
        };

        var _initBannerTitles = function (psychologyCharacteristics) {
            var bannerFieldsInfo = EditableRegistry.getFieldsForClass('UserInfoKeyValueEntity');
            return _getFieldsForClass(bannerFieldsInfo, psychologyCharacteristics);

        };

        this.addEditableById = function (element, className, fieldName, id, value) {
            EditableRegistry.addEditable(element, id, className, fieldName, value);
        };

        var _initBonuses = function (bonuses) {
            var bonusEntityFileds = EditableRegistry.getFieldsForClass('BonusEntity');
            var fields = bonusEntityFileds['fields'];
            var returnBonuses = [];
            for (var i = 0; i < Math.max(3, bonuses == null ? 0 : bonuses.length); i++) {
                var obj = $().extend(bonusEntityFileds[fields]);
                if (bonuses != null && angular.isDefined(bonuses[i])) {
                    $().extend(obj, bonuses[i]);
                }
                /* for (var key in fields) {
                 if (!JsUtils.toBoolean(obj[key])) {
                 obj[key] = fields[key].defaultValue;
                 }
                 }*/
                returnBonuses[i] = obj;
            }
            return returnBonuses;
        };

        this.initPsychologist = function (result) {

            var psyObj = {};

            psyObj.psychologyCharacteristics = _initPsychologyCharacteristics(result.userMeta);
            psyObj.bannerTitles = _initBannerTitles(result.userMeta);
            psyObj.bonuses = _initBonuses(result.bonuses);

            return psyObj;
        };

        this.initAuthorPsychologist = function (result) {

            var psyObj = {};

            psyObj.authorPsychologyCharacteristics = _initAuthorPsychologyCharacteristics(result.userMeta);
            psyObj.bannerTitles = _initBannerTitles(result.userMeta);
            psyObj.bonuses = _initBonuses(result.bonuses);

            return psyObj;
        };

    }
})
();
//public static method
PsychologyCharacteristicsWrapper.getDefaultPsychologiesValues = function () {
};
//public, not-privileged methods
PsychologyCharacteristicsWrapper.prototype = {
    display: function () {

    }
};
