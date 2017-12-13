/**
 * Created by Vasilina on 12.01.2015.
 */
$scope.personAsync = {selected : "wladimir@email.com"};
$scope.peopleAsync = [];
$timeout(function(){
  $scope.peopleAsync = [
    { name: 'Adam',      email: 'adam@email.com',      age: 12, country: 'United States' },
    { name: 'Amalie',    email: 'amalie@email.com',    age: 12, country: 'Argentina' },
    { name: 'Estefanía', email: 'estefania@email.com', age: 21, country: 'Argentina' },
    { name: 'Adrian',    email: 'adrian@email.com',    age: 21, country: 'Ecuador' },
    { name: 'Wladimir',  email: 'wladimir@email.com',  age: 30, country: 'Ecuador' },
    { name: 'Samantha',  email: 'samantha@email.com',  age: 30, country: 'United States' },
    { name: 'Nicole',    email: 'nicole@email.com',    age: 43, country: 'Colombia' },
    { name: 'Natasha',   email: 'natasha@email.com',   age: 54, country: 'Ecuador' },
    { name: 'Michael',   email: 'michael@email.com',   age: 15, country: 'Colombia' },
    { name: 'Nicolás',   email: 'nicole@email.com',    age: 43, country: 'Colombia' }
  ];
},3000);
$scope.person = {};
$scope.people = [
  { name: 'Adam',      email: 'adam@email.com',      age: 12, country: 'United States' },
  { name: 'Amalie',    email: 'amalie@email.com',    age: 12, country: 'Argentina' },
  { name: 'Estefanía', email: 'estefania@email.com', age: 21, country: 'Argentina' },
  { name: 'Adrian',    email: 'adrian@email.com',    age: 21, country: 'Ecuador' },
  { name: 'Wladimir',  email: 'wladimir@email.com',  age: 30, country: 'Ecuador' },
  { name: 'Samantha',  email: 'samantha@email.com',  age: 30, country: 'United States' },
  { name: 'Nicole',    email: 'nicole@email.com',    age: 43, country: 'Colombia' },
  { name: 'Natasha',   email: 'natasha@email.com',   age: 54, country: 'Ecuador' },
  { name: 'Michael',   email: 'michael@email.com',   age: 15, country: 'Colombia' },
  { name: 'Nicolás',   email: 'nicolas@email.com',    age: 43, country: 'Colombia' }
];
$scope.selectedPeople = [$scope.people[5], $scope.people[4]];

var states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
  'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
  'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
  'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
  'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
  'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
  'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
  'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
  'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
];
$scope.selectedTags = [];

$scope.address={};
$scope.tag={};
$scope.address.selected = [];
$scope.tag.selected = [];
$scope.refreshAddresses = function(address) {
  var params = {address: address, sensor: false};
  return $http.get(
      'http://maps.googleapis.com/maps/api/geocode/json',
      {params: params}
  ).then(function(response) {
        $scope.addresses = response.data.results;
      });
};
//$scope.regions = Regions.query();
var ids = [];
var params = {filter: "", ids: ids};

$scope.comments = [];
$scope.getDepartments = function () {
  $scope.comments = getDataQ(); // call ajax and "magically" bind it to our array
  return $scope.comments; // need to return this promise
}

function getDataQ() {
  var deferred = $q.defer();
  $http({ method: 'GET', url: 'rest/psy/data/departments' })
      .success(function (data) {
        deferred.resolve(data);
      });
  return deferred.promise;
}
//typeahead tag component
$('#tags').tagsinput(
    {
      typeaheadjs: {
        name: 'states',
        displayKey: 'value',
        /*valueKey: 'name',*/
        source: substringMatcher(states)


      },
      freeInput: false,
      itemValue: 'value'
    }
);
$('#tags').tagsinput('add', { "value": "Washington" });

var tags1 = /*res;*/$resource('tags.json');
$rootScope.tags1 = $scope.tags1;

//substring matcher for typeahead source
var substringMatcher = function (strs) {
  return function findMatches(q, cb) {
    var matches, substrRegex;

    // an array that will be populated with substring matches
    matches = [];

    // regex used to determine if a string contains the substring `q`
    substrRegex = new RegExp(q, 'i');

    // iterate through the pool of strings and for any string that
    // contains the substring `q`, add it to the `matches` array
    $.each(strs, function (i, str) {
      if (substrRegex.test(str)) {
        // the typeahead jQuery plugin expects suggestions to a
        // JavaScript object, refer to typeahead docs for more info
        matches.push({ value: str });
      }
    });

    cb(matches);
  };
};

$scope.rowCollection = [
  {firstName: 'Laurent', lastName: 'Renard', birthDate: new Date('1987-05-21'), balance: 102, email: 'whatever@gmail.com'},
  {firstName: 'Blandine', lastName: 'Faivre', birthDate: new Date('1987-04-25'), balance: -2323.22, email: 'oufblandou@gmail.com'},
  {firstName: 'Francoise', lastName: 'Frere', birthDate: new Date('1955-08-27'), balance: 42343, email: 'raymondef@gmail.com'}
];



var featherEditor = new Aviary.Feather({
  apiKey: '805cf70ae7224ba48643328c6ae472c5',
  theme: 'dark', // Check out our new 'light' and 'dark' themes!
  tools: 'crop',
  appendTo: '#test-form-div',
  image: '#drag-image',
  onSave: function(imageID, newURL) {
    var img = document.getElementById(imageID);
    img.src = newURL;
  },
  onError: function(errorObj) {
    alert(errorObj.message);
  }
});
featherEditor.launch();


var $jqueryOverlay = createOverlay({
  'title': 'Upload new',
  'templateId': 'overlay-avatar',
  position: "bottom"
}, profileAvatar);
init = false;

$(".overlay-title").magnificPopup({
  type: 'inline',
  preloader: false,
  focus: '#name',

  // When elemened is focused, some mobile browsers in some cases zoom in
  // It looks not nice, so we disable it:
  callbacks: {
    beforeOpen: function () {
      if ($(window).width() < 700) {
        this.st.focus = false;
      } else {
        this.st.focus = '#name';
      }
    },
    open: function() {
      if (!init) {
        Avatar.init();
        init = true;
      }
    }
  }
});

var profileAvatar = $('#profile-avatar');

profileAvatar.on('click', function () {
  //$('#overlay-avatar-div').on('click', function() {
  //  alert('clicked text');
  //});
  profileAvatar.removeClass("profile-avatar");
  profileAvatar.addClass("profile-avatar-click");

});

function initList2() {
  var columns = 3;
  //var idealColumnWidth = 250;
  var setColumns = function () {
    var width = $("#list").width() - 140;
    //columns = Math.min(Math.round(width / idealColumnWidth), 12) || 1;
    //columns = width / idealColumnWidth;
    $("#list").elasticColumns('set', 'columns', columns);
    $('#list').elasticColumns('refresh');
  };
  //setColumns();

  $(window).resize(setColumns);
  if ($scope.init) {
    setColumns();

  } else {

    $('#list').elasticColumns(
      {
        columns: 3,  // the number of colums
        innerMargin: 10, // the gap between two tiles
        outerMargin: 20  // the gap between the tiles and
      });
    setColumns();
    //$('#list').elasticColumns('refresh');
    $scope.init = true;
  }
}

var ua = navigator.userAgent;
var isAndroid = ua.indexOf("android") > -1;
var androidversion = parseFloat(ua.slice(ua.indexOf('android') + 8));
if (isAndroid) {
  if (androidversion >= 4.0) {
    animateProgressBar();
  } else {

    $('.progress-bar .bar-meter').each(function() {
      $(this).css('width', $(this).attr('data-meter') + '%');
    });

  }
} else {
  animateProgressBar();
}

function animateProgressBar() {

  if (jQuery().waypoint) {

    $('.progress-bar1').waypoint(function() {

      var meter = $(this).find('.bar-meter');
      $(meter).css('width', 0);
      $(meter).delay(250).animate({
        width : $(meter).attr('data-meter') + '%',
      }, 1400, 'easeOutQuint');

    }, {
      offset : '85%',
      triggerOnce : true,
//context : '#inner-content-container',
    });

  }

}


var carousel = $('.owl-demo');
carousel.on('added.owl.carousel', function (item) {
  var img = getImageElement(item.content);
  updateImageWidth(img, function (imageWidth) {
    $scope.fullWidth += imageWidth + SLIDE_MARGIN * 2;
    $scope.$stage.width($scope.fullWidth);
    $scope.owl.onResize();
  })
});
carousel.on('removed.owl.carousel', function (item) {
  $scope.owl.onResize();
});
carousel.on('initialized.owl.carousel', function () {
    //...
    //alert('initialized!');
    //$stage.find('.owl-item').each(function (i, e) {
    //});

  }
)
;
//element.owlCarousel({
//  navigation: true,
//  navigationText: true,
//  /*itemsScaleUp:false,*/
//  dotData:false,
//  autoWidth: true,
//  margin: 10,
//  /*loop:true,*/
//  items:4,
//  stagePadding: 10,
//  initialize: function (elem) {
//    alert('initialize');
//  }
//});

$("#overlay-image :first-child");

content.after(html);

$('.overlay-title', html).html("Test title");


versionCopyBowerComponents: {
  options: {
    //exclude: ['underscore'],
    //dest: 'e:/dist/libs',
//        filesReferencingComponents: {
//          files: ['e:/dist/test.html', 'e:/dist/test.css'],
//          useComponentMin: true
//        }
  }
}
copy: {
  main: {
    /*expand: true,
     cwd: 'bower_components/',
     src: '**',
     dest: 'dest/'//,*/
    //flatten: true,
    //filter: 'isFile'
    files: [
      // includes files within path
      //{expand: true, src: ['path*//*'], dest: 'dest/', filter: 'isFile'},

      // includes files within path and its sub-directories
      {expand: true,/*cwd: 'bower_components*//**//**', src: ['**']*/src: ['../bower_components/**'], dest: '../../../target/vbalanse-server.application-webapp-0.0.0.4-SNAPSHOT/bower_components/'}

      // makes all src relative to cwd
      //{expand: true, cwd: 'path/', src: ['**'], dest: 'dest/'},

      // flattens results to a single level
      //{expand: true, flatten: true, src: ['path*//**'], dest: 'dest/', filter: 'isFile'},
    ]
  }
}

bower: {
  /*app: {
   html: 'index.html'
   },*/
  install: {

    options: {
      targetDir: './lib',
        layout: 'byType',
        install: true,
        verbose: false,
        cleanTargetDir: true,
        cleanBowerDir: false,
        bowerOptions: {}
    }
    //just run 'grunt bower:install' and you'll see files from your Bower packages in lib directory
  }
}

//$scope.$digest();
//html.appendTo($('.owl-wrapper'));

//var content = $('.owl-wrapper');
//content.append(html);
//initCarouselGallery();

//$('#owl-demo').append(compiled);
//$rootScope.bxSlider.destroySlider();

function getCarousel($rootScope) {

  return $rootScope.bxSlider;

}
/*
 $('#fileupload').fileupload({
 url: UPLOAD_URL,
 autoUpload: false
 */
/*
 imageCrop: true,
 disableImageResize: /Android(?!.*Chrome)|Opera/
 .test(window.navigator && navigator.userAgent),
 imageMaxWidth: 800,
 imageMaxHeight: 800
 */
/*

 });
 */
//if ($scope.$last === true) {
