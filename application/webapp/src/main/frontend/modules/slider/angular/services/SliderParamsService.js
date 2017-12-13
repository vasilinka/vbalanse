/**
 * Created by Zhuk on 18.08.15.
 */
var SliderParamsService = function(DataService){

    this.bxSliderParams = {
        moveSlides: 1,
        slideMargin: 10,  //SLIDE_MARGIN
        maxSlides: 6,
        minSlides:6,
        slideWidth:200,
        pager: true,
        auto: false,
        infiniteLoop: false
        //onSliderLoad: function () {
           // $('.wrap-image').css("width", "200px");
       // }
    };

    this.magnificPopUp = {
        delegate: 'img', // child items selector, by clicking on it popup will open
        type: 'image',
        gallery: {
            enabled: true
        },
        callbacks: {
            elementParse: function (item) {
                item.src = item.el.attr('src');
            }
        }
    };

    this.magnificPopUpArrows = {
        arrows: {next: ".bx-next", prev: ".bx-prev"},
        wrapper: ".bx-wrapper"
    };

    this.elements =  {
        saveGallery: '#saveGallery',
        galleryItem: "#gallery-item",
        carousel: '#carousel',
        carouselId: '#carouselId',
        carouselTitleId: '#carouselTitleId',
        id: undefined
    };

    this.contentParam = {
        galleryName: "elementGallery",
        id: "contentId",
        paramName: "contentName",
        removeContent: DataService.removeContent
    };

};