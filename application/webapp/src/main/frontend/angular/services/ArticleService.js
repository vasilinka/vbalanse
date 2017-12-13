/**
 * Created by Василинка on 12.12.2014.
 */
ArticleService = function ($resource) {

  return $resource('rest/psy/article/:action', {},
      {
        saveArticle: {
          method: 'POST',
          params: {'action': 'saveArticle'},
          headers: {'Content-Type': 'application/json'}
        },
        articleList: {
          method: 'GET',
          params: {
            'action': 'articleList'
          }
        },
        articlesPublic: {
          method: 'GET',
          isArray: true,
          params: {
            'action': 'articlesPublic'
          }
        },
        getArticle: {
          method: 'GET',
          params: {'action': 'getArticle',articleId:'@id'},
          headers: {'Content-Type': 'application/json'}
        },
        removeArticle: {
          method: 'GET',
          params: {'action': 'removeArticle',articleId:'@id'},
          headers: {'Content-Type': 'application/json'}
        }
      }
  );
};
