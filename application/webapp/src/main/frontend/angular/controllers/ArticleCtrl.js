function updateTitle(articleInfo, $scope) {
  if (articleInfo.id !== undefined) {
    $scope.actionTitle = "Редактирование статьи";
  } else {
    $scope.actionTitle = "Создание статьи";
  }
}
/**
 * Created by Василинка on 04.12.2014.
 */
function ArticleController($scope, DataService, ArticleService, $routeParams, $rootScope, $resource, $timeout, $http, $q) {
  //left menu highlight on mouse over
  var avatarAbove = new AvatarAbove();
  avatarAbove.init(function(imageInfo) {
    if ($scope.article.image == null || angular.isUndefined($scope.article.image)) {
      $scope.article.image = {};
    }
    $scope.article.image.newImageId = imageInfo.id;
  });
  var articleId = $routeParams['id'];
  $scope.tags = DataService.allTags();
  $scope.article = {};
  $scope.article.tags = [];

  $scope.refreshTags = function (filterText) {
    var ids = [];
    var index = 0;
    var selected = $scope.article.tags;
    if (selected !== undefined) {
      for (var i = 0; i < selected.length; i++) {
        if (selected[i].id != "") {
          ids[index] = selected[i].id;
          index++;
        }
      }
    }
    var params = {filter: filterText, ids: ids};
    return $resource('rest/psy/data/tags', {}, {
      post: {
        method: 'POST',
        isArray: true,
        headers: {'Content-Type': 'application/json'}
      }
    }).post(params).$promise.then(function (response) {
        $scope.tags = response;
      });
  };

  if (articleId !== undefined) {
    ArticleService.getArticle({articleId: articleId}, function (articleInfo) {
      $scope.article = articleInfo;
      updateTitle(articleInfo, $scope);
      var selected = $scope.article.tags;
      var newSelected = [];
      var tags2 = $scope.tags;
      for (var j = 0; j < selected.length; j++) {
        for (var i = 0; i < tags2.length; i++) {
          if (selected[j].id == tags2[i].id) {
            newSelected[newSelected.length] = tags2[i];
          }
        }
      }
      $scope.article.tags = newSelected;
      $scope.refreshTags("");
    });
  } else {
    updateTitle({}, $scope);
    $scope.article = {};
    $scope.article.department = {};
    $scope.article.department.id = null;
  }

  $scope.createTag = function (tagName) {
    return {"id": "", "title": tagName, "code": tagName};
  };

  $scope.addNoImageStyle = function() {
    //alert("alert2");
    var $drag = $("#drag-image");
    if (angular.isUndefined($drag.attr('src')) || $drag.attr('src') == "") {
      $(".upload").addClass('no-image');
    } else {
      $(".upload").removeClass('no-image');
    }

  };

  //menu over effect
  $(".menu-item").each(function () {
    $(this).mouseover(function () {
      $(this).children(".menu-line").addClass("line-red");
      $(this).find(".inlineImage").addClass("imageHover");
    });
    $(this).mouseout(function () {
      $(this).children(".menu-line").removeClass("line-red");
      $(this).find(".inlineImage").removeClass("imageHover");
    });
  });
  $('#inputUploadFiles').upload({
    progress: function(percentage) {
      console.log(percentage);
    },
    success: function(data) {
      console.log(data);
    }
  });
  //bootstrap select, is it used now?
  $('.selectpicker').selectpicker();
  //create article call
  $scope.saveArticle = function () {
    ArticleService.saveArticle($scope.article, function (createResult) {
      //id and dateOfPublish set on server so we take them
      $scope.article.id = createResult.id;
      $scope.article.dateOfPublish = createResult.dateOfPublish;
      updateTitle(createResult, $scope);
      $scope.showMessage = true;
      $rootScope.addAlert("Статья сохранена успешно", "success");
    }, function (result) {
      if (!angular.isUndefined(result.data.fieldErrors)) {
        var validator = $("#articleForm").validate();
        validator.showErrors(result.data.fieldErrors);
      }
      //alert(result);

    });
  };
  //to demo categories ? tags


  //fix for ui-select angular
  $scope.department = {};

  DataService.departments(function (res) {
    $scope.departments = res;
  });
}
