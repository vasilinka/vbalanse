/**
 * Created by Zhuk on 24.07.15.
 */


var DropZoneService = function(){

    var dropZone = null;

    var ADDED_FILE_EVENT = 'addedfile',
        SUCCESS_EVENT = 'success',
        COMPLETE_EVENT = 'complete',
        RESET_EVENT = 'reset';

    this.params = {
        areaId: "#demo-upload",
        buttonShow: "#showDropzone",
        saveGallery: "#saveGallery",
        remove: ".dz-remove",
        gallery:  []
    };

    var params = this.params;
    var dz = DzView.getInstance(params);
    var _completeHandler = dz.completeHandler;
    var _successHandler = dz.successHandler;
    var _addedfileHandler = dz.addedfileHandler;
    var _resetHandler = dz.resetHandler;

    this.get = function(){
        dropZone =  new Dropzone(params.areaId, {
            url: UPLOAD_URL,
            acceptedFiles: "image/*",
            addRemoveLinks: true,
            init: function(){
                this.on(ADDED_FILE_EVENT, _addedfileHandler),
                    this.on(SUCCESS_EVENT, _successHandler),
                    this.on(COMPLETE_EVENT, _completeHandler),
                    this.on(RESET_EVENT, _resetHandler)
            }
        });
        return dropZone;
    };

    this.removeAllFiles = function(){
        dropZone.removeAllFiles(true);
        this.resetDropZone();
    };

    this.getDropZone = function(){
        return params.gallery;
    };

    this.getDropZoneSize = function(){
        return params.gallery.length;
    };

    this.resetDropZone = function(){
        params.gallery = [];
    };
}