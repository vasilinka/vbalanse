/**
 * Created by Zhuk on 01.09.15.
 */

var DzView = (function(){

    var uniqueInstance,
        param,
        text = 'Удалить';

    function constructor(params){

        param = params;

        function removeStyle(){
            $(param.remove).css("text-decoration", "none");
            $(param.remove).text(text);
        }

        function initButton(){
            $(param.buttonShow).on('click', function () {
                $(param.areaId).toggle();
            });

            $(param.saveGallery).hide();
        }

        function areaShow(){
            $(param.areaId).show();
            $(param.buttonShow).hide();
        }

        function areaHide(){
            $(param.areaId).hide();
            $(param.buttonShow).show();
        }

        function successHandler(file, result) {
            for (var file1 in result.files) {
                //needed refactoring - different classes for temp files and uploaded
                var newFile = {};
                newFile.newImageId = result.files[file1].id;
                newFile.id = null;
                newFile.url = result.files[file1].url;
                newFile.name = result.files[file1].name;
                param.gallery[param.gallery.length] = newFile;
            }
            file.newImageId = newFile.newImageId;
        }

        function addedfileHandler(file){
            file.previewElement.lastElementChild.addEventListener('click', function(){
                for(var i in param.gallery){
                    if(param.gallery[i].newImageId == file.newImageId){
                        param.gallery.splice(i, 1);
                        break;
                    }
                }
            });
        }

        function completeHandler(file) {
            if(param.gallery.length > 0) {
                $(param.saveGallery).show();
            }
            removeStyle();
        }

        function resetHandler(){
            $(param.saveGallery).hide();
        }

        return {
            initButton: initButton,
            areaShow: areaShow,
            areaHide: areaHide,
            successHandler: successHandler,
            addedfileHandler: addedfileHandler,
            completeHandler: completeHandler,
            resetHandler: resetHandler
        }
    }

    return {
        getInstance: function(params){
            if(!uniqueInstance){
                uniqueInstance = constructor(params);
            }
            return uniqueInstance;
        }
    }
})();


