/**
 * Created by Zhuk on 25.09.15.
 */
var checkCarouselItems = (function(){




    //var maxSlides = SliderParamsService.bxSliderParams.maxSlides;
    /*
     function _removeSlider(){
     dz.areaShow();
     if(val.id){
     params.id = val.id;
     SliderService.fire("destroy", params);
     SliderService.deleteSlider(params.id);
     }

     }


     function _showSlider(){

     dz.areaHide();
     _checkSliderId();

     }


     function _reloadSlider(){

     if(value > maxSlides){
     slideNum = value - maxSlides;
     }
     params = {id:val.id, param: slideNum};
     SliderService.fire("reload", params);
     if(val.addImg){
     SliderService.fire("goToLastSlide", params);
     delete val.addImg;
     }
     }


     function _createSlider(){
     SliderFactory.createSlider("BxSlider");
     params.id = val.id;
     SliderService.fire("load", params);

     }

     function _checkSliderId(){

     val.id ? _reloadSlider() : _createSlider();

     }
     */







    var init = function(obj){


        var SliderParamsService = obj.SliderParamsService;
        var dz = obj.dropZone;
        var val = SliderParamsService.elements;
        var SliderService = SliderService;
        var SliderFactory = SliderFactory;

        var value = obj.value;

        var params = {};
        var slideNum = 1;
        var maxSlides = SliderParamsService.bxSliderParams.maxSlides;

        function _removeSlider(){
            dz.areaShow();
            if(val.id){
                params.id = val.id;
                SliderService.fire("destroy", params);
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
            params = {id:val.id, param: slideNum};
            SliderService.fire("reload", params);
            if(val.addImg){
                SliderService.fire("goToLastSlide", params);
                delete val.addImg;
            }
        };


        var _createSlider = function(){
            SliderFactory.createSlider("BxSlider");
            params.id = val.id;
            SliderService.fire("load", params);

        };

        function _checkSliderId(){

            val.id ? _reloadSlider() : _createSlider();

        }


        var checkItemsCount = function(){

            value == 0 ? _removeSlider() : _showSlider();

        };


        return {checkItemsCount: checkItemsCount};

    };

    return init;

})();

