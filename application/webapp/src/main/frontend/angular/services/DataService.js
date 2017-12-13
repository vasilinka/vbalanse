/**
 * Created by Василинка on 12.12.2014.
 */
DataService = function ($resource) {

  return $resource('rest/psy/data/:action'/*"../rest/psy/user/exception1"*/, {},
      {
        workWith: {
          method: 'GET',
          isArray: true,
          params: {'action': 'workWith'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        departments: {
          method: 'GET',
          isArray: true,
          params: {'action': 'departments'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        cities: {
          method: 'GET',
          isArray: true,
          params: {'action': 'cities'},
          //params: {'action': 'exception1'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        allTags: {
          method: 'GET',
          isArray: true,
          params: {'action': 'allTags'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//          transformResponse: function(data, headers){
//            //
//            // transform to array of objects
//            return data;
//          }
        },
        removeContent: {
          method: 'GET',
          params: {'action': 'removeContent',CONTENT_NAME_PARAM:'@name',CONTENT_ID_PARAM:'@id' },
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },

        saveBonus: {
          method: 'POST',
          params: {'action': 'saveBonus', name: '@name', value: '@value', pk: '@pk', className: '@className'},
          headers: {'Content-Type': 'application/json'}
        }

      }
  );
}
