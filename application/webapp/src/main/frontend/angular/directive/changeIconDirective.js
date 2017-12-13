/**
 * Created by Vasilina on 10.05.2015.
 */
changeIconDirective = ['IconPopUpService',function (IconPopUpService) {
    return {
        restrict: 'A',
        scope: {},
        link: function ($scope, element) {

            IconPopUpService.initIconPopUp(element);
        }
    }
}];
