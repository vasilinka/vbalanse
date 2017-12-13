/**
 * Created by Василинка on 04.12.2014.
 */
function ArticleListController($scope, $sce, $http, $filter, ArticleService) {
  //left menu highlight on mouse over
  $scope.refreshGrid = function (page) {
    ArticleService.articleList({
        params: {
          page: page,
          sortFields: $scope.sortInfo.fields[0],
          sortDirections: $scope.sortInfo.directions[0]
        }
      },
      function (data) {
        $scope.articles = data;
        $scope.displayedCollection = [].concat($scope.articles.list);
      });
  };

  // Do something when the grid is sorted.
  // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
  // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
  $scope.$on('ngGridEventSorted', function (event, sortInfo) {
    $scope.sortInfo = sortInfo;
  });

  // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
  // This also works for the first page access, since we assign the initial sorting in the initialize section.
  $scope.$watch('sortInfo', function () {
    $scope.refreshGrid($scope.articles.currentPage);
  }, true);

  // Initialize required information: sorting, the first page to show and the grid options.
  $scope.sortInfo = {fields: ['id'], directions: ['asc']};
  $scope.articles = {currentPage: 1};
  /*$scope.columnDefinitions = [
   {field: 'title', displayName: 'Название', width: 180},                                                        */
  /*{field: 'dateOfPublish', displayName: 'Дата публикации', width: 180,     cellFilter: 'date:\'MM/dd/yyyy\''}/*,
   /* {field: 'title', displayName: 'Название', width: 90}*/
  $scope.gridOptions = {
    data: 'articles.list',
    useExternalSorting: true,
    sortInfo: $scope.sortInfo,
    columnDefs: $scope.columnDefinitions
  };
  $scope.removeRow = function removeRow(row) {
    var index = $scope.articles.list.indexOf(row);
    if (index !== -1) {
      var article = $scope.articles.list[index];
      if (confirm('Вы уверены, что хотите удалить статью "'+article.title + '"')) {
        ArticleService.removeArticle({articleId: article.id}, function (result) {
          $scope.articles.list.splice(index, 1);
        });
      }
    }
  };
  $scope.predicates = ['title', 'dateOfPublish'];
  $scope.selectedPredicate = $scope.predicates[0];
}
