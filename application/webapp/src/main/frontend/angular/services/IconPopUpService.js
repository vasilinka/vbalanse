/**
 * Created by Alexander on 10.02.2016.
 */


var IconPopUpService = ['$rootScope', '$http', '$compile', 'DataService', function ($rootScope, $http, $compile, DataService) {

    var templateHtml = '<div class="white-popup mfp-hide">\
                        <h3 class="popup-title">Выберите иконку к вашему бонусу</h3>\
                        <alert class="alert" dismiss-on-timeout="2000" ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</alert>\
                        <div class="inner">\
                        <ul id="choose-icon">\
                        <li data-ng-repeat="font in fonts"><div class="box-icon"><span class="fontelloicon-{{font}}" ng-click="saveIcon(font)" ></span></div></li>\
                        </ul>\
                        </div>\
                        </div>\
                        ';

    var result,
        bonusId,
        bonusElement,
        fontsUrl = 'js/fontello.json',
        elemContainer = '#icon_popup',
        bonusIdAttr = 'attr-bonus-id',
        spanSelector,
        selectorClass = 'choose-icon-active';

    var scope = $rootScope.$new();
    scope.alerts = [];


    scope.closeAlert = function (index) {
        scope.alerts.splice(index, 1);
    };

    scope.saveIcon = function (icon) {

        var bonus = {};
        bonus.name = 'icon';
        bonus.pk = bonusId;
        bonus.value = icon;
        bonus.className = 'BonusEntity';

        DataService.saveBonus(bonus, function (data) {

                scope.alerts = [{type: 'success', msg: 'Иконка бонуса сохранена успешно.'}];

                result.find('.' + selectorClass).removeClass(selectorClass);
                result.find('ul li div span.fontelloicon-' + icon).addClass(selectorClass);
                bonusElement.children().first().attr('class', 'fontelloicon-' + icon);

            }, function (err) {

                if (!angular.isUndefined(err.message)) {
                    var message = "Ошибка: " + err.message;
                } else {
                    message = "Непредсказуемая ошибка произошла на сервере! Пожалуйста, попробуйте позже.";
                }
                scope.alerts = [{type: 'danger', msg: message}];
            }
        );
    };

    var _getFonts = function () {
        return $http.get(fontsUrl)
            .then(function (res) {
                return res.data;
            });
    };

    var _initPopUpContent = function () {
        _getFonts().then(function (data) {
            scope.fonts = data;
            result = $compile(angular.element(templateHtml))(scope);
            $(elemContainer).append(result);
        });
    };

    _initPopUpContent();

    this.initIconPopUp = function (element) {

        if ($(elemContainer).children().length == 0) {
            result = $compile(angular.element(templateHtml))(scope);
            $(elemContainer).append(result);
        }

        element.magnificPopup({
            items: {
                src: result,
                type: 'inline'
            },
            callbacks: {
                beforeOpen: function () {

                    spanSelector = 'li div span.' + element.find('i').attr('class');
                    var ul = $(elemContainer).find('ul');
                    ul.prepend(ul.find(spanSelector).parents().eq(1));

                    ul.find('li div span.' + selectorClass).removeClass(selectorClass);

                    $(spanSelector).addClass(selectorClass);
                },
                open: function () {
                    bonusId = element.attr(bonusIdAttr);
                    bonusElement = element;
                },
                afterClose: function () {
                    scope.alerts = [];
                    $(spanSelector).removeClass(selectorClass);
                }
            },
            key: 'icon'
        });
    };
}];
