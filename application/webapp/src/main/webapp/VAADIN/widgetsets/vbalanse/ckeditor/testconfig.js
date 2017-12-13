/**
 * Created by Василинка on 09.12.2014.
 */
/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
  // Define changes to default configuration here.
  // For complete reference see:
  // http://docs.ckeditor.com/#!/api/CKEDITOR.config

  config.filebrowserBrowseUrl = 'browse/my.html';
  config.filebrowserImageBrowseUrl = 'browse/my.html';
  //config.filebrowserFlashBrowseUrl = 'http://localhost/kcfinder/browse.php?opener=ckeditor&type=flash';
  config.filebrowserUploadUrl = '/ckupload';
  config.filebrowserImageUploadUrl = '/ckupload';
  //config.filebrowserFlashUploadUrl = 'http://localhost/kcfinder/upload.php?opener=ckeditor&type=flash';

  // The toolbar groups arrangement, optimized for two toolbar rows.
  config.toolbarGroups = [
    { name: 'editing' },
    { name: 'links' },
    { name: 'insert'},
    { name: 'forms' },
    { name: 'tools' },
    { name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
    { name: 'others' },
    '/',
    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
    { name: 'styles' },
    { name: 'colors' },
    { name: 'about' }
  ];
  config.toolbar = [
    { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ]},
    { name: 'styles', items: [ 'Styles', 'Format' ] },
    { name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
    { name: 'insert', items: [ 'Image', 'HorizontalRule' ] },
    { name: 'links', items: [ 'Link', 'Unlink', 'Anchor']},
    { name: 'tools', items: [ 'Maximize' ] },
    { name: 'document', items: [ 'Source'] }
  ];

  // Remove some buttons provided by the standard plugins, which are
  // not needed in the Standard(s) toolbar.
  config.removeButtons = 'Underline,Subscript,Superscript';

  // Set the most common block elements.
  config.format_tags = 'p;h1;h2;h3;pre';

  // Simplify the dialog windows.
  config.removeDialogTabs = 'image:advanced;link:advanced';
};

