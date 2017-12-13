'use strict';


module.exports = function (grunt) {
  //project configuration
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    'wiredep': {
      task: {

        // Point to the files that should be updated when
        // you run `grunt wiredep`
        src: [
          'index.html'//,   // .html support...
        ],

        options: {
        }
      }
    },

    bower: {
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
    },
    uglify: {
      libs: {
        files: [
          {
            expand: true,
            cwd: 'bower-components',
            src: '**/*.js',
            dest: 'build/components'
          }
        ]
      }
    },
    useminPrepare: {
      options: {
        dest: 'dist'
      },
      html: 'index.html'
    },
    usemin: {
      options: {
        dirs: ['dist']
      },
      html: ['dist/{,*/}*.html'],
      css: ['dist/styles/{,*/}*.css']
    },
    copy: {
      main: {
        files: [
          // includes files within path
          //{expand: true, src: ['path*//*'], dest: 'dest/', filter: 'isFile'},
          // includes files within path and its sub-directories
          {expand: true,src: ['../bower_components/**'], dest: '../../../target/vbalanse-server.application-webapp-0.0.0.4-SNAPSHOT/bower_components/'}
        ]
      }
    }
  });

  grunt.loadNpmTasks('grunt-wiredep');
  grunt.loadNpmTasks('grunt-bower-task');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-version-copy-bower-components');
  grunt.loadNpmTasks('grunt-usemin');
  grunt.loadNpmTasks('grunt-contrib-copy');

  grunt.registerTask('wire', ['wiredep']);
  grunt.registerTask('min', ['useminPrepare', 'usemin']);
  grunt.registerTask('html', ['versionCopyBowerComponents']);
  grunt.registerTask('lib', ['bower', 'bower:app']);
  grunt.registerTask('default', 'Log some stuff.', function () {
    grunt.log.write('Logging some stuff...').ok();
  });

  grunt.registerTask('qq', ['bower', 'wiredep', "copy:main"]);



}

