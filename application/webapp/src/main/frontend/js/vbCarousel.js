/**
 * Created by Vasilina on 22.04.2015.
 */
(function($, window, document, undefined) {
  function vbCarousel(element, options) {
    var $element = $(element);
    element.css(
      {
        'overflow': "hidden",
        'position':"relative"
      });
    var items = $element.children();
    for (var i=0; i<items.length;i++) {
      var item = items[i];
      item.find('img');

    }
    element.append($('<div class="controller"></div>'));
  }

  vbCarousel._updateImageWIdth = function updateImageWidth(img, onUpdate) {
    var image = new Image();
    image.onload = $.proxy(function () {
      var imageWidth = Math.floor(image.width * 150 / image.height);
      img.css({

        'width': imageWidth
      });
      img.parent().css({'width': imageWidth});
//    $scope.fullWidth += newImageWidth;
      onUpdate(imageWidth);
    }, this);
    image.src = img.attr('src');
    //return owl;
  }



}




)(window.jQuery, window, document);