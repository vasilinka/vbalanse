/**
 * Created by Василинка on 05.12.2014.
 */
var CKEDITOR_BASEPATH = '/VAADIN/widgetsets/vbalanse/ckeditor/';

ckEditorDirective = [function () {
  return {
    require: '?ngModel',
    restrict: 'C',
    link: function (scope, elm, attr, model) {
      var isReady = false;
      var data = [];
      var ck = CKEDITOR.replace(elm[0], /*{customConfig: 'testconfig.js'}*/
      {customConfig: 'testconfig.js'} );//'../../../../testconfig.js'
      /*{toolbar: [
        { name: 'document', items: [ 'Source', '-', 'NewPage', 'Preview', '-', 'Templates' ] },	// Defines toolbar group with name (used to create voice label) and items in 3 subgroups.
        [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ],			// Defines toolbar group without name.
        '/',																					// Line break - next group will be placed in new line.
        { name: 'basicstyles', items: [ 'Bold', 'Italic' ] }
      ]}*/

      function setData() {
        if (!data.length) {
          return;
        }

        var d = data.splice(0, 1);
        ck.setData(d[0] || '<span></span>', function () {
          setData();
          isReady = true;
        });
      }

      ck.on('instanceReady', function (e) {
        if (model) {
          setData();
        }
      });

      elm.bind('$destroy', function () {
        ck.destroy(false);
      });

      if (model) {
        ck.on('change', function () {
          scope.$apply(function () {
            var data = ck.getData();
            if (data == '<span></span>') {
              data = null;
            }
            model.$setViewValue(data);
          });
        });

        model.$render = function (value) {
          if (model.$viewValue === undefined) {
            model.$setViewValue(null);
            model.$viewValue = null;
          }

          data.push(model.$viewValue);

          if (isReady) {
            isReady = false;
            setData();
          }
        };
      }

    }
  };
}]
