/**
 * Created by Zhuk on 18.08.15.
 */

var SliderService = function(){

    var uid = 0;
    var sliders = {};

    this.addSlider = function(slider){
        if(slider.id == null){
            slider.id = ++uid;
            sliders[uid] = slider;
        }
        return uid;
    };

    this.deleteSlider = function(id){
        delete sliders.id;
    };

    this.fire = function(event, options){
        var slider = _find(options.id);
        var fn = slider[event];
        return angular.bind(slider, fn)(options.param);
    };

    this.sliders =  {
        "BxSlider": BxSlider.prototype.constructor
    };

    function _find(id){
        var slider = undefined;
        angular.forEach(sliders, function(value, key){
            if(key == id){
                slider = value;
            }
        });
        return slider;
    }
}