'use strict';





module.exports = function(grunt) {

  var writeDao = function (entityName, packagePath) {
    grunt.file.defaultEncoding = 'utf8';
        
    var interfacePath = "assets/sampleDao.tpl";
    var contents = grunt.file.read(interfacePath);
    
    var implementPath = "assets/sampleDaoImpl.tpl";
    var implementContent = grunt.file.read(implementPath);
    
    var modelClass = entityName + "Entity";
    var relPath = packagePath.replace("\/", ".");
    
    var distFolder = "e:\\projects\\vbalanse\\application\\dao\\src\\main\\java\\by\\vbalanse\\dao\\"+packagePath+'\\';
    var distImplementFolder = "e:\\projects\\vbalanse\\application\\dao\\src\\main\\java\\by\\vbalanse\\dao\\jpa\\"+packagePath+'\\';
    var distInterfaceFile = distFolder + entityName +'Dao.java';
    var distImplementFile = distImplementFolder + entityName +'DaoImpl.java';
    var map = {
          entityName: entityName,
          modelClass: modelClass,
          path: relPath
      };
    grunt.file.write(distInterfaceFile, grunt.template.process(contents, {data: map}));
    grunt.file.write(distImplementFile, grunt.template.process(implementContent, {data: map}));
    shell.exec('svn add '+distFolder
    );
    shell.exec('svn add '+distInterfaceFile);
    shell.exec('svn add '+distImplementFolder);
    shell.exec('svn add '+distImplementFile);
    grunt.log.write('write finish').ok();
  }

  var createDao = function (action, filepath, target) {
      grunt.log.writeln("some task");
      var filename = filepath.replace(/^.*[\\\/]/, '');
      var path = filepath.substring(filepath.lastIndexOf("model")+"model".length + 1, filepath.lastIndexOf('\\'));
      var modelClass = filename.substring(0, filename.lastIndexOf('.'));
      var entityName = modelClass.substring(0, modelClass.lastIndexOf('Entity'));
      var extension = filename.substring(filename.lastIndexOf('.')+1);
      grunt.log.writeln(target + ': ' + filepath + ' has ' + action + "--" + path + "--" + entityName + " packagepath "+path);
      
      if (extension == "java" && action=="added") {
        writeDao(entityName, path);  
      }

  }
  
  var createEntity = function (name, path) {
    grunt.log.writeln("name " + name);
    grunt.log.writeln("path " + path);
    var distFolder = "e:\\projects\\vbalanse\\application\\model\\src\\main\\java\\by\\vbalanse\\model\\"+path+'\\';
    var modelClass = name + "Entity";

    var distModelFile = distFolder + modelClass + '.java';
    var relPath = path.replace("\/", ".");
    
    var interfacePath = "assets/sampleEntity.tpl";
    var contents = grunt.file.read(interfacePath);
    
    var map = {
        entityName: name,
        modelClass: modelClass,
        path: relPath,
    };
    grunt.file.write(distModelFile, grunt.template.process(contents, {data: map}));
    shell.exec('svn add '+distModelFile);
  } 


  //project configuration
  grunt.initConfig({
      pkg: grunt.file.readJSON('package.json'),
      watch: {
      	scripts: {
      		files: ['model/**/*.*'],
      		tasks: ['dev-watch'],
      		options: {
      			interrupt: true
      		}
      	}
      },
      'template': {
        'process-html-template': {
            'options': {
                'data': {
                    'entityName': 'My blog post',
                    'modelClass': 'Mathias Bynens',
                    'path': 'Lorem ipsum dolor sit amet.'
                }
            },
            'files': {
                'dist/dao.java': ['assets/sampleDao.tpl']
            }
        }
      },
      'htmlSnapshot': {
        all: {
          options: {
            snapshotPath: 'snapshots/',
            sitePath: 'http://localhost:8081/index.html',
            msWaitForPages: 1000,
            urls: [
              '',
              '#/article'
            ]
          }
        }
        
    }
    
  });
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-template');
  grunt.loadNpmTasks('grunt-svn-commander');
  grunt.loadNpmTasks('grunt-exec');
  grunt.loadNpmTasks('grunt-bg-shell');
  grunt.loadNpmTasks('grunt-html-snapshot');

 grunt.registerTask('snapshot', ['htmlSnapshot']);
  
  //register the task
  grunt.registerTask('dev-watch', function(action, filepath, target) {
    //grunt.log.writeln(target + ': ' + filepath + ' has ' + action);  
  });
  grunt.registerTask('default', 'Log some stuff.', function() {
    grunt.log.write('Logging some stuff...').ok();
  });
  var _  = require('underscore.string');
  var shell = require('shelljs');
  grunt.event.on('watch', function(action, filepath, target) {
    grunt.log.writeln("some task");
    createDao(action, filepath, target);
  });
  grunt.registerTask('model', function() {
    var name = grunt.option("name");
    var packagePath = grunt.option("path");
    createEntity(name, packagePath);
    writeDao(name, packagePath);  
  });
  
  grunt.registerTask('dao', function() {
    var name = grunt.option("name");
    var packagePath = grunt.option("path");
    //createEntity(name, packagePath);
    writeDao(name, packagePath);  
  });
    
    
    //var shell = require('shelljs');
}


//grunt dao -name=Comment -path=content