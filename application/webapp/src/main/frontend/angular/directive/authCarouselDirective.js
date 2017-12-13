/**
 * Created by Zhuk on 16.09.15.
 */

vbCarouselAuth = ['SliderParamsService', 'SliderFactory', 'SliderService', function (SliderParamsService, SliderFactory, SliderService) {
    return {
        restrict: 'A',
        scope: {},
        link: function ($scope, element) {

            var val = SliderParamsService.elements;

            $scope.$watch(function () {

                    return element.children().length;
                },
                function (value) {
                    if(value > 0 ){
                        if(!val.id){
                            slider = SliderFactory.createSlider("BxSlider");
                        }
                        $(val.carouselTitleId).show();
                        SliderService.fire(LOAD_EVENT, {id: val.id});
                    }else{
                        $(val.carouselTitleId).hide();
                    }
                }
            );
        }
    }
}];
