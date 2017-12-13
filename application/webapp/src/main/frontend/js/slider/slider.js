/**
 * Created by Zhuk on 13.08.15.
 */

//Slider Interface
var Slider = new Interface("Slider", ['load', 'reload', 'destroy']);

var BaseSlider = function(){};

BaseSlider.prototype = {

    addImage: function(result){
        var element = this.param.elements;
        var galleryItemHtml = $(element.galleryItem).html();
        for (var index = 0; index < result.length; index++) {
            var compiled = Handlebars.compile(galleryItemHtml)({img: result[index]});
            $(element.carousel).append($(compiled));
        }
    },

    removeImage: function(){
        var self = this;
        return function(itemId, e) {
            var data = {};
            var options = self.param.contentParam;
            data[options.paramName] = options.galleryName;
            data[options.id] = itemId;
            return options.removeContent(data);
        };
    }
}