/**
 * Created by Василинка on 26.11.2014.
 */
UserService = function ($resource) {

  return $resource('rest/psy/user/:action', {},
      {
        authenticate: {
          method: 'POST',
          params: {'action': 'authenticate'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        getUser: {
          method: 'GET',
          params: {'action': 'getUser'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        getPsychologist: {
          method: 'GET',
          params: {'action': 'getPsychologist',psychologistId:'@id'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        register: {
          method: 'POST',
          params: {'action': 'fastRegister'},
          headers: {'Content-Type': 'application/json'}
        },
        changePassword: {
          method: 'POST',
          params: {'action': 'changePassword'},
          headers: {'Content-Type': 'application/json'}
        },
        askToResetPassword: {
          method: 'POST',
          params: {'action': 'askToResetPassword'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        saveAvatar : {
          method: 'GET',
          params: {'action': 'saveAvatar',imageId:'@id'},
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        },
        saveGallery: {
          method: 'POST',
          isArray: true,
          params: {'action': 'saveGallery'},
          headers: {'Content-Type': 'application/json'}
        }
      }
  );
}
