/**
 * Created by Василинка on 09.12.2014.
 */
LoadDirective = ['$rootScope', function ($rootScope) {
  return {
    restrict: "EA",
    templateUrl: "",
    compile: function (scope, iElement, attrs) {
      //alert("postlink directive");

    },
    link: function (scope, iElement, attrs) {
      //$(iElement)
      //alert("herer directive");
      var citynames = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        prefetch: {
          url: 'assets/citynames.json',
          filter: function (list) {
            return $.map(list, function (cityname) {
              return { name: cityname };
            });
          }
        }
      });
      citynames.initialize();
     /* $('#test2').tagsinput(
          {
            typeaheadjs: {
              name: 'citynames',
              displayKey: 'name',
              valueKey: 'name',
              source: citynames.ttAdapter()
            }
          });*/





//      $('input, select').on('change', function(event) {
//        var $element = $(event.target),
//            $container = $element.closest('.example');
//
//        if (!$element.data('tagsinput'))
//          return;
//
//        var val = $element.val();
//        if (val === null)
//          val = "null";
//        $('code', $('pre.val', $container)).html( ($.isArray(val) ? JSON.stringify(val) : "\"" + val.replace('"', '\\"') + "\"") );
//        $('code', $('pre.items', $container)).html(JSON.stringify($element.tagsinput('items')));
//      }).trigger('change');
    }
  }
}]