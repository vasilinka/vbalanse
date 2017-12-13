/**
 * Created by Zhuk on 12.08.15.
 */

var SliderFactory = function(SliderService, SliderParamsService){
    var slider;
    var val = SliderParamsService;
    var sliderTypes = SliderService.sliders;

    var _loadSlider = function(){
        var id = SliderService.addSlider(slider);
        val.elements.id = id;
    };

    var createSlider = function(type){

        try{
            if(sliderTypes.hasOwnProperty(type) && typeof sliderTypes[type] === 'function'){
                if(arguments.length > 0){
                    angular.forEach(sliderTypes, function(value, key){
                        if(key == type){
                            slider = new value(val);
                        }
                    })};

                Interface.ensureImplements(slider, Slider);
                _loadSlider();

                return slider;
            }
        }catch(e){
            console.log("Function createSlider "
                + "does not have " + type + " property or "
                + type + " property is not a function");
        }
    };

    return {
        createSlider: createSlider
    };
};