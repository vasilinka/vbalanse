/**
 * Created by Zhuk on 25.09.15.
 */
var checkCarouselItems = (function(){

    var init = function(obj){
        var SliderParamsService = obj.SliderParamsService,
            dz = obj.dropZone,
            val = SliderParamsService.elements,
            SliderService = obj.SliderService,
            SliderFactory = obj.SliderFactory,
            value = obj.value,
            params = {},
            slideNum = 1,
            maxSlides = SliderParamsService.bxSliderParams.maxSlides;

        function _removeSlider(){
            dz.areaShow();
            if(val.id){
                params.id = val.id;
                SliderService.fire(DESTROY_EVENT, params);
                SliderService.deleteSlider(params.id);
            }
        }

        function _showSlider(){
            dz.areaHide();
            _checkSliderId();
        }

        var _reloadSlider = function(){
            if(value > maxSlides){
                slideNum = value - maxSlides;
            }
            params.id = val.id;
            alert(val.id);
            SliderService.fire(RELOAD_EVENT, params);
        };

        var _createSlider = function(){
            SliderFactory.createSlider("BxSlider");
            params.id = val.id;
            SliderService.fire(LOAD_EVENT, params);
        };

        var checkItemsCount = function(){
            value == 0 ? _removeSlider() : _showSlider();
        };

        function _checkSliderId(){
            val.id ? _reloadSlider() : _createSlider();
        }

        return {checkItemsCount: checkItemsCount};
    };

    return init;

})();

