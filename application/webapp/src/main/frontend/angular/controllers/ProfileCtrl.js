/**
 * Created by Василинка on 04.12.2014.
 */
function ProfileController($scope, $rootScope, UserService, PsychologyCharacteristicsWrapper, DropZoneService, DropzoneImgService, SliderService, SliderParamsService) {
    $scope.gallery = [];

    //todo: fix -1 value

    UserService.getPsychologist({psychologistId: -1}, function (result) {
        $scope.psychologist = result;

        var psyObj = PsychologyCharacteristicsWrapper.initPsychologist(result);
        $rootScope.psychologyCharacteristics = psyObj.psychologyCharacteristics;
        $rootScope.bannerTitles = psyObj.bannerTitles;
        $rootScope.bonuses = psyObj.bonuses;
    });

    $scope.load = function () {

        var val = SliderParamsService.elements;
        if (val.id) {
            SliderService.fire(DESTROY_EVENT, {id: val.id});
            SliderService.deleteSlider(val.id);
        }

        var removeItem = function (itemId, e) {
            var val = SliderParamsService.elements;
            var params = {id: val.id};
            SliderService.fire(REMOVE_IMAGE_EVENT, params)(itemId, e)
                .$promise.then(function () {
                    $("#" + itemId).remove();
                });
        };

        window.removeItem = $scope.removeItem = removeItem;

        $scope.total = 2;
        $scope.$parent.total = 2;

        $(document).ready(function () {

            DropZoneService.get();
            DropzoneImgService.saveImg();

            $.fn.editable.defaults.mode = 'popup';

            var init = false;
            $scope.initAvatar = function () {
                if (!init) {
                    Avatar.init(function (imageInfo) {
                        UserService.saveAvatar({imageId: imageInfo.id}, function (result) {
                            $scope.psychologist.avatar = $scope.psychologist.avatar || {};
                            $scope.psychologist.avatar.url = result.url;
                            $scope.psychologist.avatar.newImageId = result.id;
                        });
                    });
                    init = true;
                }
            };

            window.locale = {
                "fileupload": {
                    "errors": {
                        "maxFileSize": "File is too big",
                        "minFileSize": "File is too small",
                        "acceptFileTypes": "Filetype not allowed",
                        "maxNumberOfFiles": "Max number of files exceeded",
                        "uploadedBytes": "Uploaded bytes exceed file size",
                        "emptyResult": "Empty file upload result"
                    },
                    "error": "Error",
                    "start": "Start",
                    "cancel": "Cancel",
                    "destroy": "Delete"
                }
            };


        });
    };

}

function initTitle(defaultValue, psychologistInfo, constantKey, title) {
    var bannerValue = null;
    var bannerTitle1Id = -1;
    if (psychologistInfo.userMeta != null) {
        var userMetaBannerTitle1 = psychologistInfo.userMeta[constantKey];
        if (userMetaBannerTitle1 != null) {
            bannerValue = userMetaBannerTitle1.value;
            bannerTitle1Id = userMetaBannerTitle1.id;
        }
    }
    $('#' + constantKey).editable({
        "title": title,
        "type": "text",
        "placement": "bottom",
        "pk": bannerTitle1Id,
        "url": "/rest/psy/data/saveBonus",
        "mode": "popup",
        "value": bannerValue,
        "defaultValue": defaultValue,
        "emptytext": defaultValue
    });
}

function createOverlay(options, $elem) {
    var self = this;
    var $overlay = $("#" + options.templateId);
    var compiled = Handlebars.compile($overlay.html());
    var html = compiled(options);

    var content = $elem;
    content.append(html);
    content.css("position", "relative");
    var $html = $(html);
    if (options.position == "bottom") {
        $html.removeClass("overlay-center");
        $html.addClass("overlay");
    }
    return $html;

}

//var defaultBonuses = [
//  {
//    "id": EMPTY_VALUE,
//    "defaultTitle": "внимательность",
//    "defaultDescription": "What does this team member to? Keep it short! This is also a great spot for social links!"
//  },
//  {
//    "id": EMPTY_VALUE,
//    "defaultTitle": "лояльность",
//    "defaultDescription": "What does this team member to? Keep it short! This is also a great spot for social links!"
//  },
//  {
//    "id": EMPTY_VALUE,
//    "defaultTitle": "терпение",
//    "defaultDescription": "What does this team member to? Keep it short! This is also a great spot for social links!"
//  },
//];


//function initBonuses(bonuses) {
//  if (bonuses != null) {
//    for (var i = 0; i < defaultBonuses.length; i++) {
//      if (bonuses[i] != null) {
//        $().extend(defaultBonuses[i], bonuses[i]);
//      }
//    }
//  }
//  for (i = 0; i < defaultBonuses.length; i++) {
//    //var id = -1;
//    var id = defaultBonuses[i].id;
//    var titleValue = defaultBonuses[i].title;
//    var descriptionValue = defaultBonuses[i].description;
//    var that = defaultBonuses[i];
//    that.index = i;
//    var defaultTitle = defaultBonuses[i].defaultTitle;
//    var defaultDescription = defaultBonuses[i].defaultDescription;
//
//    $('#bonus' + i).editable(
//      {
//        "title": "Введите ваше преимущество",
//        "type": "text",
//        "pk": id,
//        "url": "/rest/psy/data/saveBonus",
//        "value": titleValue,
//        "defaultValue": defaultTitle,
//        "emptytext": defaultTitle,
//        //    name="bonus"
//        //data-url="/rest/psy/data/saveBonus"
//        success: function (response, newValue) {
//          if (response.status == 'error') return response.msg; //msg will be shown in editable form
//          that.id = response.id;
//          $('#bonus' + that.index).editable('option', 'pk', response.id);
//          $('#bonus_description' + that.index).editable('option', 'pk', response.id);
//          that.title = newValue;
//        }
//      }
//    );
//    $('#bonus_description' + i).editable(
//      {
//        "title": "Введите описание вашего преимущество",
//        "type": "text",
//        "pk": id,
//        "url": "/rest/psy/data/saveBonus",
//        "value": descriptionValue,
//        "defaultValue": defaultDescription,
//        "emptytext": defaultDescription,
//        //    name="bonus_description" data-url="/rest/psy/data/saveBonus"
//        success: function (response, newValue) {
//          if (response.status == 'error') return response.msg; //msg will be shown in editable form
//          that.id = response.id;
//          //$('#bonus' + that.index).attr("data-pk", response.id);
//          //$('#bonus_description' + that.index).attr("data-pk", response.id);
//          $('#bonus' + that.index).editable('option', 'pk', response.id);
//          $('#bonus_description' + that.index).editable('option', 'pk', response.id);
//
//          that.description = newValue;
//        }
//      }
//    );
//
//  }
//  return defaultBonuses;
//}
