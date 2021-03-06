/**
 * Created by Zhuk on 13.08.15.
 */
var BxSlider = function(options){
    this.param = options;
};

BxSlider.prototype = {

    _arrowsVisibility: (function(){

        var setVisibility = function(obj){
            angular.forEach(obj.arrows, function(value, key){
                angular.bind($(value), obj.fn)();
            });
        };

        var showWrapperArrows = function(obj){
            $(obj.wrapper).hover(
                function() {
                    obj.fn = $.fn.show;
                    setVisibility(obj);
                }, function() {
                    obj.fn = $.fn.hide;
                    setVisibility(obj);
                }
            );
        };

        return {showWrapperArrows: showWrapperArrows, setVisibility: setVisibility};
    })(),

    _initArrows: function(){
        var arrowParams = this.param.magnificPopUpArrows;
        arrowParams.fn = $.fn.hide;
        this._arrowsVisibility.setVisibility(arrowParams);
        this._arrowsVisibility.showWrapperArrows(arrowParams);
    },

    load: function(){
        var params = this.param;
        var element = $(params.elements.carousel);
        var slider = element.bxSlider(params.bxSliderParams);
        element.magnificPopup(params.magnificPopUp);
        params.elements.slider = slider;
        this._initArrows();
        return slider;
    },
    reload: function(){
        var slider = this.param.elements.slider;
        slider.reloadSlider();
        this._initArrows();
    },
    destroy: function(){
        var params = this.param;
        params.elements.id = undefined;
        params.elements.slider.destroySlider();
        delete params.elements.slider;
    },
    goToLastSlide: function(count){
        var slider = this.param.elements.slider;
        slider.goToSlide(count);
        this._initArrows();
    }
};

addMethods(BxSlider, BaseSlider);