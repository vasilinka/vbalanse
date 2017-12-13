/**
 * Created by Vasilina on 05.04.2015.
 */

vbCarousel = ['DropZoneService', 'DataService', 'SliderParamsService', 'SliderFactory', 'SliderService', function (DropZoneService, DataService, SliderParamsService, SliderFactory, SliderService) {
    return {
        restrict: 'A',
        scope: {},
        link: function ($scope, element) {
            var dz = DzView.getInstance(DropZoneService.params);
            dz.initButton();

            $scope.$watch(function () {
                    return element.children().length;
                },
                function (value) {

                    var checkItems =  checkCarouselItems({dropZone: dz,
                        SliderParamsService: SliderParamsService,
                        SliderFactory: SliderFactory,
                        SliderService: SliderService,
                        value: value});

                    checkItems.checkItemsCount();
                }
            );
        }
    }
}];