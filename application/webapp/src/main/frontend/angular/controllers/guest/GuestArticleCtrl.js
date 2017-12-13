/**
 * Created by Vasilina on 19.01.2015.
 */
function GuestArticleController($scope, $sce, $routeParams, ArticleService, $sanitize, $timeout, $location, $anchorScroll) {
  var articleId = $routeParams['id'];
  var commentParam = $location.search().comments;

  ArticleService.articlesPublic(function(res) {
    for (var j =0; j<res.length; j++) {
      res[j].date = $.timeago(res[j].dateOfPublish);
    }
    $scope.articles = res;
    for (var i =0; i< $scope.articles.length; i++) {
      var article = $scope.articles[i];
      article.text = $sce.trustAsHtml(article.text);
    }
  });

  $('div.comment-container').comment({
    title: 'Comments',
    url_get: 'rest/psy/comment/listComment',
    url_input: 'rest/psy/comment/saveComment',
    url_delete: 'rest/psy/comment/removeComment',
    limit: 10,
    auto_refresh: false,
    refresh: 10000,
    transition: 'slideToggle'
  });


  ArticleService.getArticle({articleId: articleId}, function (articleInfo) {
    $scope.article = articleInfo;
    //$('#articleText').html(articleInfo.text);
    $scope.article.text = $sce.trustAsHtml($scope.article.text);
    $scope.article.date = $.timeago($scope.article.dateOfPublish);
    //$('.content img').load(function(){
    //  var $img = $(this);
    //  $img.attr('style', "max-width:100%;");
    //});
    //$('.content img').load(function () {
    //  $timeout(function() {
    $timeout(function () {
      var options = {
        position:"bottom",
        title:"Test title",
        description:"Test description"
      };
      $('.article-content img').overlay(options);
      //var $overlay = $("#overlay-image");
      //var compiled = Handlebars.compile($overlay.html());
      //var html = compiled(options);
      //
      //var new1 = $('<div>Hello</div>');
      //var content = $('.article-content img');
      //content.after(html);
      ////$('.overlay-title', html).html("Test title");
      //$(content[0]).parent().css("position", "relative");


      $timeout(function () {
        if (commentParam) {
          $scope.scrollToComments();
        }
      }, 1000);

      //alert($content);
      //    });
    });
    $scope.scrollToComments = function () {
      $location.hash('comments');
      $anchorScroll();
      //$location.hash('');
      //$anchorScroll(-50);

    };
    //  $img.attr('style', "max-width:100%;");
    //});
    $scope.showTextarea = function ($event) {
      var beforeElement = $($event.target.parentElement);
      // var parent = $(beforeElement.parentElement);
      beforeElement.before('<div class="reply" align="center"><textarea id="txt" width="100%"></textarea></div>');
      $('#txt').animate({rows: 10}, 500);
      //parent.append('</textarea>');

    }
  });

}