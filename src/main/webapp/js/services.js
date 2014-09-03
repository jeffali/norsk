angular.module('myApp.services', ['ngResource'])
  .factory('AngularPerms', function($resource){
    return $resource('/norsk/engine/0/permutation/list.json', {})
  })
  .factory('AngularOnePerm', function($resource){
    return $resource('/norsk/engine/0/permutation/get.json', {id:'@id'})
  })
  .factory('AngularDeclareUser', function($resource){
    return $resource('/norsk/engine/0/permutation/join.json', {uid:'@uid'})
  })
  .factory('AngularSwitchCategory', function($resource){
    return $resource('/norsk/engine/0/permutation/switch.json', {cat:'@cat'})
  })
  .factory('AngularReport', function($resource){
    return $resource('/norsk/engine/0/permutation/report.json', {uid:'@uid',qid:'@id',aid:'aid'})
  })
  .value('version', '0.1');
