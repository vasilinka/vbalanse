/**
 * Created by Василинка on 07.10.14.
 */
var module = angular.module('registerApp', ['registerApp.services', 'ngGrid',"xeditable", "ngDialog", "ngMockE2E", 'angularFileUpload']);
var services = angular.module('registerApp.services', ['ngResource']);

module.controller('FirstDialogCtrl', function ($scope, ngDialog) {
  /*$("#input-1").load(function() {
    alert("here");
  }); */

  //alert($scope.$parent.dimension);
  $scope.next = function () {
    /*ngDialog.close('ngdialog1');
    ngDialog.open({
      template: 'secondDialog',
      className: 'ngdialog-theme-flat ngdialog-theme-custom'
    });*/


  };
});


module.controller("registerController", function ($scope, $filter, $http, DataService, UserService, ngDialog, FileUploader) {
  var uploader = $scope.uploader = new FileUploader({
    url: '../upload'//,
    //isEmptyAfterSelection: false
  });

  // FILTERS

  uploader.filters.push({
    name: 'customFilter',
    fn: function(item /*{File|FileLikeObject}*/, options) {
      return this.queue.length < 10;
    }
  });

  // CALLBACKS

  uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
    console.info('onWhenAddingFileFailed', item, filter, options);
  };
  uploader.onAfterAddingFile = function(fileItem) {
    console.info('onAfterAddingFile', fileItem);
  };
  uploader.onAfterAddingAll = function(addedFileItems) {
    console.info('onAfterAddingAll', addedFileItems);
  };
  uploader.onBeforeUploadItem = function(item) {
    console.info('onBeforeUploadItem', item);
  };
  uploader.onProgressItem = function(fileItem, progress) {
    console.info('onProgressItem', fileItem, progress);
  };
  uploader.onProgressAll = function(progress) {
    console.info('onProgressAll', progress);
  };
  uploader.onSuccessItem = function(fileItem, response, status, headers) {
    console.info('onSuccessItem', fileItem, response, status, headers);
  };
  uploader.onErrorItem = function(fileItem, response, status, headers) {
    console.info('onErrorItem', fileItem, response, status, headers);
  };
  uploader.onCancelItem = function(fileItem, response, status, headers) {
    console.info('onCancelItem', fileItem, response, status, headers);
  };
  uploader.onCompleteItem = function(fileItem, response, status, headers) {
    console.info('onCompleteItem', fileItem, response, status, headers);
  };
  uploader.onCompleteAll = function() {
    console.info('onCompleteAll');
  };
  $("#input-1").fileinput({
    //initialPreview: ["<img src='Desert.jpg' class='file-preview-image'>", "<img src='Jellyfish.jpg' class='file-preview-image'>"],
    overwriteInitial: false,
    maxFileSize: 1000,
    maxFilesNum: 10,
    allowedFileTypes: ['image', 'video', 'flash']
  });

  console.info('uploader', uploader);

  //$scope.url = "/psy/data/";
  $scope.therapyWays = null;
  $scope.cities = null;
  //$scope.loadTherapyWays = null;// = function () {
  DataService.workWith(function (res) {
    $scope.therapyWays = res;
  });
  DataService.cities(function (res) {
    $scope.cities = res;
  });
  $scope.register = function () {
    if ($scope.registerForm.$valid) {
      UserService.register($scope.psychologist, function (psychologistEntity) {
        alert('here');
      });
    }
  };
  $scope.educationList = [
                          {"name": "Гештальт-институт", "yearStart": 1945, yearFinish: 1999},
                          {"name": "Психоанализ", "yearStart": 1945, yearFinish: 1999},
                          {"name": "Бодинамика", "yearStart": 1945}
                        ];
  $scope.selectGridRow = function () {
    if ($scope.selectedItem[0].total != 0) {
      $location.path('items/' + $scope.selecteditem[0].id);
    }};
  $scope.$on('ngDialog.opened', function (e, $dialog) {
    // window.history.pushState('', '', '/#/');
    //alert("window opened");
    $("#input-1").fileinput({
      //initialPreview: ["<img src='Desert.jpg' class='file-preview-image'>", "<img src='Jellyfish.jpg' class='file-preview-image'>"],
      overwriteInitial: false,
      maxFileSize: 1000,
      maxFilesNum: 10,
      allowedFileTypes: ['image', 'video', 'flash']
    });
  });
  /*$scope.$on("ngGridEventData", function (ev, gridId) {
    dataWasLoaded = false;
    if (dataWasLoaded && $scope.gridOptions.ngGrid.data.length > 0) {
      dataWasLoaded = true;
      $scope.gridOptions.selectRow(0, true);
    }
  });*/
  $scope.gridOptions = {
    data: 'educationList',
    // ,  ng-click="selectGridRow()"
      // multiSelect: false,
    enableRowSelection: true,
    columnDefs: [{ field: 'name', displayName: 'Название'},
      { field: 'yearStart', displayName: 'Года', cellTemplate: '<div>{{row.entity.yearStart}}-{{row.entity.yearFinish}}</div>'},
      { displayName: 'Actions', cellTemplate:
          '<div class="grid-action-cell">'+
          '<a ng-click="$event.stopPropagation(); editThisRow(row.entity);" href="#">Edit&nbsp;</a>' +
          '<a ng-click="$event.stopPropagation(); deleteThisRow(row.entity);" href="#">Delete</a></div>'}
    ]
  };
  $scope.editThisRow = function() {
    $scope.$parent.dimension = this.row.entity;
    ngDialog.openConfirm({
      template: 'firstDialog',
      controller: 'FirstDialogCtrl',
      className: 'ngdialog-theme-default ngdialog-theme-custom'
    });
  };
  $scope.deleteThisRow = function() {
    $scope.gridOptions.selectAll(false); //remove all selections: necessary for issues with the selection array
    var index = $scope.educationList.indexOf(this.row.entity); //get the correct index to splice
    $scope.educationList.splice(index, 1); //remove the element

//    var index = this.row.rowIndex;
//    setTimeout(function(){
//    },2000);

//    $scope.gridOptions.selectRow(1, true);
//    $scope.educationList.splice(index, 1);

    //$scope.$apply();

  };
  $scope.users = [
    {id: 1, name: 'awesome user1', status: 2, group: 4, groupName: 'admin'},
    {id: 2, name: 'awesome user2', status: undefined, group: 3, groupName: 'vip'},
    {id: 3, name: 'awesome user3', status: 2, group: null}
  ];

  $scope.statuses = [
    {value: 1, text: 'status1'},
    {value: 2, text: 'status2'},
    {value: 3, text: 'status3'},
    {value: 4, text: 'status4'}
  ];

  $scope.groups = [];
  $scope.loadGroups = function() {
    return $scope.groups.length ? null : $http.get('/groups').success(function(data) {
      $scope.groups = data;
    });
  };

  $scope.showGroup = function(user) {
    if(user.group && $scope.groups.length) {
      var selected = $filter('filter')($scope.groups, {id: user.group});
      return selected.length ? selected[0].text : 'Not set';
    } else {
      return user.groupName || 'Not set';
    }
  };

  $scope.showStatus = function(user) {
    var selected = [];
    if(user.status) {
      selected = $filter('filter')($scope.statuses, {value: user.status});
    }
    return selected.length ? selected[0].text : 'Not set';
  };

  $scope.checkName = function(data, id) {
    if (id === 2 && data !== 'awesome') {
      return "Username 2 should be `awesome`";
    }
  };

  $scope.saveUser = function(data, id) {
    //$scope.user not updated yet
    angular.extend(data, {id: id});
    return $http.post('/savePsychologist', data);
  };

  // remove user
  $scope.removeUser = function(index) {
    $scope.users.splice(index, 1);
  };

  // add user
  $scope.addUser = function() {
    $scope.inserted = {
      id: $scope.users.length+1,
      name: '',
      status: null,
      group: null
    };
    $scope.users.push($scope.inserted);
  };
});

module.run(function($httpBackend) {
  $httpBackend.whenGET('/groups').respond([
    {id: 1, text: 'user'},
    {id: 2, text: 'customer'},
    {id: 3, text: 'vip'},
    {id: 4, text: 'admin'}
  ]);

  $httpBackend.whenPOST(/\/saveUser/).respond(function(method, url, data) {
    data = angular.fromJson(data);
    return [200, {status: 'ok'}];
  });
  $httpBackend.whenGET(/..\/rest\/psy\/data\/workWith/).passThrough();
  $httpBackend.whenGET(/..\/rest\/psy\/data\/cities/).passThrough();
});


