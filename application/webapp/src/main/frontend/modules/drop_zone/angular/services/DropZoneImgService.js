/**
 * Created by Zhuk on 24.07.15.
 */

var DropzoneImgService = ['SliderFactory', 'SliderParamsService', 'SliderService', 'DropZoneService', 'UserService', function(SliderFactory, SliderParamsService, SliderService, DropZoneService, UserService){

    this.saveImg = function(){
        var val = SliderParamsService.elements;

        $(val.saveGallery).on('click', function (e) {
            e.preventDefault();
            if (DropZoneService.getDropZoneSize() > 0) {
                var gallery = DropZoneService.getDropZone();
                UserService.saveGallery(gallery,
                    function (result) {
                        var params = {id:val.id, param: result};

                        if(!val.id){
                            SliderFactory.createSlider("BxSlider");
                            params.id = val.id;
                            SliderService.fire(LOAD_EVENT, params);
                        }

                        SliderService.fire(ADD_IMAGE_EVENT, params);

                        DropZoneService.removeAllFiles();
                        $(val.saveGallery).hide();
                    }
                );
            }
        });
    }
}];
