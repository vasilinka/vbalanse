/**
 * Created by Vasilina on 03.04.2015.
 */
(function ($, window, document, undefined) {
  var Overlay = {
    init_: function (options, elem) {
      var self = this;

      self.elem = elem;
      self.$elem = $(elem);

      self.options = $.extend({}, $.fn.overlay.options, options);
      self.showOverlay();
      //self.refresh_(1);
    },

    showOverlay: function () {
      var self = this;
      var $overlay = $("#" + self.options.templateId);
      //$("#overlay-image :first-child");
      var compiled = Handlebars.compile($overlay.html());
      var html = compiled(self.options);

      var content = self.$elem;
      content.after(html);
      //if (content.children().length == 0) {
      //  content.append(html);
      //}
      //$('.overlay-title', html).html("Test title");
      content.parent().css("position", "relative");
      var $html = $(html);
      if (self.options.position == "bottom") {
        $html.removeClass("overlay-center");
        $html.addClass("overlay");
      }

    }
  }

  $.fn.overlay = function (options) {
    return this.each(function () {
      var overlay = Object.create(Overlay);

      overlay.init_(options, this);

      $.data(this, 'overlay', overlay);
    });
  };

  $.fn.overlay.options = {
    position: "center",
    title: "Test title",
    description: "Test description",
    templateId: "overlay-image"
  }


})(jQuery, window, document);
