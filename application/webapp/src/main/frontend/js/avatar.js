/**
 * Created by Vasilina on 28.03.2015.
 */
// Required for drag and drop file access
jQuery.event.props.push('dataTransfer');

// IIFE to prevent globals
//(function () {
const UPLOAD_URL = "/rest/test/upload";

var s;
var IMAGE_PATH = '#test-form img';
tests = {
  filereader: typeof FileReader != 'undefined',
  dnd: 'draggable' in document.createElement('span'),
  formdata: !!window.FormData,
  progress: "upload" in new XMLHttpRequest
},
  support = {
    filereader: document.getElementById('filereader'),
    formdata: document.getElementById('formdata'),
    progress: document.getElementById('progress')
  },
  acceptedTypes = {
    'image/png': true,
    'image/jpeg': true,
    'image/gif': true
  };

function previewfile(file, image, onLoad) {
  if (tests.filereader === true && acceptedTypes[file.type] === true) {
    var reader = new FileReader();
    reader.onload = function (event) {
      //var image = new Image();
      //image.attr('src', event.target.result);
      onLoad(event.target.result);
      //image.width = 250; // a fake resize
      // holder.appendChild(image);
    };

    reader.readAsDataURL(file);
  } else {
    //holder.innerHTML += '<p>Uploaded ' + file.name + ' ' + (file.size ? (file.size / 1024 | 0) + 'K' : '');
    console.log(file);
  }
}

function AvatarObj() {

}
AvatarObj.prototype = {


  init: function (onLoad) {

    var settings = {
      bod: $("body"),
      img: $("#drag-image"),
      fileInput: $("#uploader"),
      onLoad: onLoad,
      //startCropButton: $("#startCropButton"),
      //cropButton: $("#cropButton"),
      okButton: $("#okButton"),
      avatarTitle: $("avatar-title"),
      cropped: null,
      avatar: null
    };

    s = settings;

    this.bindUIActions();

  },
  showButtons: function (crop) {
    if (crop) {
      s.fileInput.hide();
      s.okButton.show();
      s.avatarTitle.text("Теперь выберите нужную часть");
    }
    else {
      s.fileInput.show();
      s.okButton.hide();
    }
  },
  createCrop: function () {
    if (s.avatar != null) {
      this.showButtons(true);
      //avatar already uploaded
      $(IMAGE_PATH).cropper({
        autoCropArea: 0.8,
        aspectRatio: 16 / 16,
        strict: false,
        crop: function (data) {
          //alert(data);
          //Avatar.placeImage(data);
          // Output the result data for cropping image.
          s.cropped = data;
          s.cropped.x = Math.floor(s.cropped.x);
          s.cropped.y = Math.floor(s.cropped.y);
          s.cropped.width = Math.floor(s.cropped.width);
          s.cropped.height = Math.floor(s.cropped.height);
        }
      });
    }
  }, crop: function () {
    var current = this;
    $.ajax({
      url: "/rest/test/crop",
      dataType: 'json',
      method: "POST",
      headers: {'Content-Type': 'application/json'},
      contentType: "application/json; charset=utf-8",
      async: 'true',
      //contentType: "application/json",
      //contentType: 'multipart/form-data',
      data: JSON.stringify({
        'crop': s.cropped,
        'file': s.avatar
      }),
      success: function (result) {
        s.avatar = result.files[0];
        $(IMAGE_PATH).cropper("destroy");
        current.showButtons(false);
        s.img.attr("src", result.files[0].url);
        s.onLoad(s.avatar);
        current.showButtons(false);
        s.avatarTitle.text("Будете ещё раз пробовать?")
      }
      //processData: false,
      //contentType: false
    });
  }, bindUIActions: function () {
    //alert("binUiActions");
    var current = this;

    var timer;

    s.bod.on("dragover", function (event) {
      clearTimeout(timer);
      if (event.currentTarget == s.bod[0]) {
        current.showDroppableArea();
      }

      // Required for drop to work
      return false;
    });

    s.bod.on('dragleave', function (event) {
      if (event.currentTarget == s.bod[0]) {
        // Flicker protection
        timer = setTimeout(function () {
          current.hideDroppableArea();
        }, 200);
      }
    });

    s.bod.on('drop', function (event) {
      // Or else the browser will open the file
      event.preventDefault();

      current.handleDrop(event.dataTransfer.files);
    });

    s.fileInput.on('change', function (event) {
      current.handleDrop(event.target.files);
    });


    s.okButton.on('click', function (event) {
      event.preventDefault();
      current.crop();
    });
    //
    //s.startCropButton.on('click', function (event) {
    //  event.preventDefault();
    //  Avatar.createCrop();
    //})

  }
  ,

  showDroppableArea: function () {
    s.bod.addClass("droppable");
  }
  ,

  hideDroppableArea: function () {
    s.bod.removeClass("droppable");
  }
  ,

  handleDrop: function (files) {
    $(IMAGE_PATH).cropper("destroy");
    this.hideDroppableArea();
    var current = this;

    // Multiple files can be dropped. Lets only deal with the "first" one.
    var file = files[0];

    if (file.type.match('image.*')) {

      //Avatar.resizeImage(file, 256, function (data) {
      //Avatar.placeImage();
      previewfile(file, s.img, function (data) {
        current.placeImage(data);
      });
      //});

    } else {

      alert("That file wasn't an image.");

    }

  }
  ,


  resizeImage: function (file, size, callback) {

    var fileTracker = new FileReader;
    fileTracker.onload = function () {
      Resample(
        this.result,
        size,
        size,
        callback
      );
    }
    fileTracker.readAsDataURL(file);

    fileTracker.onabort = function () {
      alert("The upload was aborted.");
    }
    fileTracker.onerror = function () {
      alert("An error occured while reading the file.");
    }

  }
  ,

  placeImage: function (data) {
    var current = this;
    s.img.attr("src", data);
    //$.post({
    //  url: "/upload/jquery",
    //  data: dataURItoBlob(data)
    //});
    var file = dataURItoBlob(data);
    var fd = new FormData();
    fd.append('file', dataURItoBlob(data));
    $.ajax({
      url: UPLOAD_URL,
      method: "POST",
      async: 'true',
      //contentType: 'multipart/form-data',
      data: fd,
      success: function (result) {
        s.avatar = result.files[0];
        s.img.attr("src", result.files[0].url);
        current.createCrop();

      },
      processData: false,
      contentType: false
    });
    //var xhr = new XMLHttpRequest();
    //var fd = new FormData();
    //xhr.onload = function(result) {
    //}

    //fd.append('file', dataURItoBlob(data));
    //
    //xhr.open('POST', "/upload-jquery", true);
    //
    //xhr.send(fd);
  }

}
/*


Avatar.prototype.constructor = function() {

}
*/

var Avatar = new AvatarObj();
function dataURItoBlob(dataURI) {
  var binary = atob(dataURI.split(',')[1]);
  var array = [];
  for (var i = 0; i < binary.length; i++) {
    array.push(binary.charCodeAt(i));
  }
  return new Blob([new Uint8Array(array)], {type: 'image/jpeg'});
}


//})();


