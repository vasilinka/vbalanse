/**
 * Created by Vasilina on 26.01.2015.
 */
var module = angular.module('exampleApp', ['ngResource']);
var controllers = {};
controllers.SimpleController = function ($scope, $resource) {
  $resource('../rest/psy/data/images', {}, {
    get: {
      method: 'GET',
      isArray: true,
      headers: {'Content-Type': 'application/json'}
    }
  }).get().$promise.then(function (response) {
      $scope.tags = response;
      //alert($scope.tags);
      showImages(response);
    });

};
module.controller(controllers);
$.ajax({
  type: "GET",
  url: "http://localhost:8081/rest/psy/data/images",
  success: function (data) {
    var counter = 0;
    //alert(data);
    $('g', data).each(function () {
      //alert($(this));
//      var group_name = $(this).find("name").text();
//      var group_id = $(this).find("id").text();
//      var group = {
//        id: group_id,
//        name: group_name
//      }
//      groups[counter] = group;
//      counter++;
    });
    //return groups;
  }
});

function _setReturnData(url) {
  window.top.opener['CKEDITOR'].tools.callFunction(parseUrl('CKEditorFuncNum'), url, '');
  window.top.close();
  window.top.opener.focus();
  return true;
}


function insertImageEditor(imageInfo) {
  _setReturnData(imageInfo.fullUrl);

}

function parseUrl(name)
{
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if (null == results) {
    return '';
  }
  return results[1];
}


function showImages(images) {
  var filemanager = $('.filemanager');
  for (var i=0; i< images.length;i++) {
    var image = images[i];
    var folder = $('<li class="folders"><a href="' + "Some" + '" title="' + "Some" + '" class="folders">' + "Some" + '<span class="name">' + "name" + '</span> <span class="details">' + "2" + '</span></a></li>');
    folder = $('<li tabindex="0" role="checkbox" aria-label="mustache-wallpaper" aria-checked="false" data-id="3847"' +
    'class="attachment save-ready">' +
    '<div class="attachment-preview js--select-attachment type-image subtype-png landscape">' +
    '<div class="thumbnail">' +
    '<div class="centered">' +
    '<img onclick="_setReturnData(\''+image.fullUrl+'\')" src="' + image.fullUrl +
    '"' +
    'draggable="false" alt="">' +
    '</div>' +
    '</div>' +
    '</div>' +
    '<a class="check" href="#" title="Снять выделение" tabindex="-1">' +
    '<div class="media-modal-icon"></div>' +
    '</a>' +
    '</li>');
    folder.appendTo(filemanager);
  }

}
// $.get('http://localhost:8081/rest/psy/data/images', function(data) {
//
//   var response = [data];
//   render(response);
//   //alert(response);
// });

function render(data) {

  var scannedFolders = [],
    scannedFiles = [];

  for (var i = 0; i < data.length; i++) {
    alert(data[i]);
  }

  // Empty the old result and make the new one

//  fileList.empty().hide();
//
//  if(!scannedFolders.length && !scannedFiles.length) {
//    filemanager.find('.nothingfound').show();
//  }
//  else {
//    filemanager.find('.nothingfound').hide();
//  }

//  if(scannedFolders.length) {

//    scannedFolders.forEach(function(f) {
//
//      var itemsLength = f.items.length,
//          name = escapeHTML(f.name),
//          icon = '<span class="icon folder"></span>';
//
//      if(itemsLength) {
//        icon = '<span class="icon folder full"></span>';
//      }
//
//      if(itemsLength == 1) {
//        itemsLength += ' item';
//      }
//      else if(itemsLength > 1) {
//        itemsLength += ' items';
//      }
//      else {
//        itemsLength = 'Empty';
//      }
//
//      var folder = $('<li class="folders"><a href="'+ f.path +'" title="'+ f.path +'" class="folders">'+icon+'<span class="name">' + name + '</span> <span class="details">' + itemsLength + '</span></a></li>');
//      folder.appendTo(fileList);
//    });
//
//  }
//
//  if(scannedFiles.length) {
//
//    scannedFiles.forEach(function(f) {
//
//      var fileSize = bytesToSize(f.size),
//          name = escapeHTML(f.name),
//          fileType = name.split('.'),
//          icon = '<span class="icon file"></span>';
//
//      fileType = fileType[fileType.length-1];
//
//      icon = '<span class="icon file f-'+fileType+'">.'+fileType+'</span>';
//
//      var file = $('<li class="files"><a href="'+ f.path+'" title="'+ f.path +'" class="files">'+icon+'<span class="name">'+ name +'</span> <span class="details">'+fileSize+'</span></a></li>');
//      file.appendTo(fileList);
//    });
//
//  }
//
//
//  // Generate the breadcrumbs
//
//  var url = '';
//
//  if(filemanager.hasClass('searching')){
//
//    url = '<span>Search results: </span>';
//    fileList.removeClass('animated');
//
//  }
//  else {
//
//    fileList.addClass('animated');
//
//    breadcrumbsUrls.forEach(function (u, i) {
//
//      var name = u.split('/');
//
//      if (i !== breadcrumbsUrls.length - 1) {
//        url += '<a href="'+u+'"><span class="folderName">' + name[name.length-1] + '</span></a> <span class="arrow">→</span> ';
//      }
//      else {
//        url += '<span class="folderName">' + name[name.length-1] + '</span>';
//      }
//
//    });

//  }

  //breadcrumbs.text('').append(url);


  // Show the generated elements

  //fileList.animate({'display':'inline-block'});

}

function escapeHTML(text) {
  return text.replace(/\&/g, '&amp;').replace(/\</g, '&lt;').replace(/\>/g, '&gt;');
}

