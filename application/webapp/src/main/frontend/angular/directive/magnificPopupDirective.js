magnificPopup = ['$rootScope', function ($rootScope) {
  return {
    restrict: 'A',
    link: function ($scope, element, attr) {
      attr.callbacks = {
        ajaxContentAdded: function () {
          var content = this.content;

          var scope = content.scope();
          $compile(content)(scope);
          scope.$digest();
        }
      };

      element.magnificPopup(attr);
    }
  }
}];