/**
 * Created with IntelliJ IDEA.
 * User: Zhuk
 * Date: 24.07.15
 * Time: 8:48
 * To change this template use File | Settings | File Templates.
 */

var BxSliderService = ['GalleryService', 'DataService', function (GalleryService, DataService) {

  const SLIDE_MARGIN = 10;

  var initCarousel = false;
  var bxSlider = null;

  this.isInitCarousel = function (isInit) {
    if (arguments.length > 0) {
      initCarousel = isInit;
    } else {
      return initCarousel;
    }
  }


  function updateImageWidth(img, onUpdate) {
    var image = new Image();
    image.onload = $.proxy(function () {
      var imageWidth = Math.floor(image.width * 150 / image.height);
      img.css({

        'width': imageWidth
      });
      img.parent().css({'width': imageWidth});
      onUpdate(imageWidth);
    }, this);
    image.src = img.attr('src');
  }

  function getImageElement(item) {
    var $item = $(item);
    $item.attr("id", $item.find(".wrap-image").attr("data-dbid"));
    var img = $item.find("img");
    return img;
  }

  this.init = function (element) {
    bxSlider = element.bxSlider(
      {

        moveSlides: 1,
        slideMargin: SLIDE_MARGIN,
        pager: true,
        auto: false,
        infiniteLoop: false,
        onSliderLoad: function () {
          var that = this;
          const owlElements = $('.wrap-image');
          owlElements.css("width", "auto");
          var count = owlElements.length;
          var initializedCount = 0;
          owlElements.each(function () {
            var img = getImageElement(this);
          });
        }
      }
    );

    bxSlider.id = null;
    return GalleryService.save(bxSlider);
  }


  this.create = function (element) {
    var sliderId = this.init(element);

    element.attr('sliderId', sliderId);

    element.magnificPopup({

      delegate: 'img', // child items selector, by clicking on it popup will open
      type: 'image',
      gallery: {
        enabled: true
      },
      callbacks: {
        elementParse: function (item) {
          item.src = item.el.attr('src');
        }
      }
    });
  }

  this.reload = function (id) {
    bxSlider = this.get(id);
    bxSlider.reloadSlider();
  }

  this.get = function (id) {
    if (id !== undefined) {
      bxSlider = undefined;
      bxSlider = GalleryService.get(id);
      return bxSlider;
    }
  }

  this.delete = function (id) {
    bxSlider = this.get(id);
    GalleryService.delete(bxSlider.id);
    initCarousel = false;
    bxSlider.destroySlider();
  }

  this.goToLastSlide = function (id) {
    bxSlider = this.get(id);
    bxSlider.goToSlide(bxSlider.getSlideCount() - 1);
  }

  this.removeItem = function (itemId, e) {
    var data = {};
    alert('prof');
    data[CONTENT_NAME_PARAM] = CONTENT_GALLERY_ITEM_NAME;
    data[CONTENT_ID_PARAM] = itemId;
    DataService.removeContent(data, function (result) {
      $("#" + itemId).remove();
      BxSliderService.reload($('#carousel').attr('sliderId'));
      if ($('#carousel').children().length == 0) {
        BxSliderService.delete($('#carousel').attr('sliderId'));
      }
    });
  };
}];