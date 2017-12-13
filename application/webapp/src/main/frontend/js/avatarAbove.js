function extend(Child, Parent) {
  var F = function () {
  };
  F.prototype = Parent.prototype;
  Child.prototype = new F();
  Child.prototype.constructor = Child;
  Child.superclass = Parent.prototype;
}

function AvatarAbove(onLoad) {
  AvatarAbove.superclass.constructor.call(this, onLoad);


  //var me = AvatarObj()

  var superInit = this.init;

  this.init = function (onLoad) {
    superInit.call(this, onLoad);
    var $upload = $(".upload");
    $upload.mouseover(function () {
      $(this).addClass("drag");
      $(this).removeClass("drop");
    });
    $upload.mouseout(function () {
      $(this).removeClass("drag");
    });

    s.upload = $upload;
    s.bod.on('drop', function () {
      $upload.removeClass("drag");
      $upload.addClass("drop");
    });
    var $inputUploadFiles = $("#inputUploadFiles");
    $upload.on('click', function (e) {
      e.stopPropagation();
      $inputUploadFiles.trigger("click")
    });
    var that = this;
    $inputUploadFiles.on('change', function (e) {
      var b;
      $upload.addClass("drop");
      b = e.target.files || e.dataTransfer && e.dataTransfer.files;
      that.handleDrop(b);
    })
  };

  this.handleDrop = function (files) {
    this.hideDroppableArea();
    var current = this;

    // Multiple files can be dropped. Lets only deal with the "first" one.
    var file = files[0];

    if (file.type.match('image.*')) {

      //Avatar.resizeImage(file, 256, function (data) {
      //Avatar.placeImage();
      $('#percentLoading').show();
      $.upload({
        files: files,
        progress: function (percentage) {
          //console.log(percentage);
          $('#iconUploadLoading').css("height", percentage + "%");
          $('#percentage').text(percentage);
        },
        success: function (result) {
          //current.placeImage(data);
          s.avatar = result.files[0];
          s.img.attr("src", result.files[0].url);
          s.upload.removeClass('no-image');
          $('#percentLoading').hide();
          //todo: make nice transferer
          s.onLoad({
            url: s.avatar.url,
            id: s.avatar.id,
            name: s.avatar.name
          });
          //current.createCrop();
          //console.log(data);
        }
      });
//      previewfile(file, s.img, function (data) {
//      });
      //});

    } else {

      alert("That file wasn't an image.");

    }

  };
  //me.constructor = arguments.callee;
  //return me;

}
extend(AvatarAbove, AvatarObj);
$.extend(AvatarAbove, AvatarObj);
extend(AvatarAbove.prototype, {});
//this.prototype.bindUIActions();

function mixin(dst, src) {
  // tobj - вспомогательный объект для фильтрации свойств,
  // которые есть у объекта Object и его прототипа
  var tobj = {}
  for (var x in src) {
    // копируем в dst свойства src, кроме тех, которые унаследованы от Object
    if ((typeof tobj[x] == "undefined") || (tobj[x] != src[x])) {
      dst[x] = src[x];
    }
  }
  // В IE пользовательский метод toString отсутствует в for..in
  if (document.all && !document.isOpera) {
    var p = src.toString;
    if (typeof p == "function" && p != dst.toString && p != tobj.toString &&
      p != "\nfunction toString() {\n    [native code]\n}\n") {
      dst.toString = src.toString;
    }
  }
}

