/**
 * Created by Vasilina on 19.01.2015.
 */
function MainController($scope, $sce, ArticleService, $timeout) {
  ArticleService.articlesPublic(function (res) {
    for (var j = 0; j < res.length; j++) {
      res[j].date = $.timeago(res[j].dateOfPublish);
    }
    $scope.articles = res;
    for (var i = 0; i < $scope.articles.length; i++) {
      var article = $scope.articles[i];
      article.text = $sce.trustAsHtml(article.text);
    }
  });
  $timeout(function () {
    $(".menu-item").each(function () {
      $(this).mouseover(function () {
        $(this).children(".menu-line").addClass("line-red");
        $(this).find(".inlineImage").addClass("imageHover");
      });
      $(this).mouseout(function () {
        $(this).children(".menu-line").removeClass("line-red");
        $(this).find(".inlineImage").removeClass("imageHover");
        //$(this).find("img").removeAttr("fill");
      });

    });

  }, 300);
};

