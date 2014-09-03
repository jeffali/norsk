var app = angular.module("MyApp", ['myApp.services', 'ngResource']);

app.controller('MyCtrl', ['$scope', 'AngularSwitchCategory', 'AngularDeclareUser', 'AngularPerms', function($scope, AngularSwitchCategory, AngularDeclareUser, AngularIssues) {
  $scope.name = "";
  $scope.cat = "";
  $scope.usaved = false;
  $scope.csaved = false;
  $scope.perms = [];
  $scope.mycats=[{name:'family'},{name:'drink'},{name:'device'}];

  $scope.fetchPerms = function() {
    console.log('Fetching perms from remote server !!');

    AngularIssues.get(function(response) {
      $scope.perms = response.permutationList;
    });
  };

  $scope.saveUser = function () {
     if ($scope.name===undefined||$scope.name==null||$scope.name.length<=0) {console.log('Please choose a valid nickname'); return;}
     console.log('user '+ $scope.name + ' saved');
     AngularDeclareUser.get({uid:$scope.name}, function(response) {
        $scope.usaved = true;
     });
  };
  $scope.saveCat = function () {
     console.log('cat '+ $scope.cat.name + ' saved');
     if ($scope.cat.name===undefined||$scope.cat.name==null||$scope.cat.name.length<=0) {console.log('Please choose a valid category'); return;}
     AngularSwitchCategory.get({cat:$scope.cat.name}, function(response) {
        /*$scope.perms =*/ $scope.fetchPerms();
        $scope.csaved = true;
     });
  };
}]);

app.controller('MyCtrl1', ['$scope', '$timeout', 'AngularReport', function($scope, $timeout, AngularReport) {
    $scope._index=0;
    $scope._fail=0;
    $scope._succ=0;
    $scope._cur = $scope.$parent.perms[$scope._index];
    $scope._res = [];
    $scope.mycolor=['normal','normal','normal','normal'];

    $scope.updateColors = function (ouranswer,right) {
         for (i=0; i<4; i++)
         {
             $scope.mycolor[i] = 'normal';
             if (i==ouranswer) { $scope.mycolor[i] = 'red'; }
             if (i==right) { $scope.mycolor[i] = 'blue'; }
         }
    };
    $scope.resetColors = function() {
         console.log('resetting colors');
         for (i=0; i<4; i++)
         {
             $scope.mycolor[i] = 'normal';
         }
    };

   $scope.setResult = function (i) {
        AngularReport.get({qid:$scope._index, aid:i, uid:$scope.name}, function(response) {
            $scope._res[$scope._index] = response.stringList; //[false,right,fail,succ]
            console.log('received result : success=' + response.stringList[0] + ',right=' + response.stringList[1]);
            $scope.updateColors(i,response.stringList[1]);
            $timeout( $scope.postprocess, response.stringList[0] ? 1000 : 3000 );
        });
   };

   $scope.updateScores = function () {
     $scope._fail = $scope._res[$scope._index][2]; 
     $scope._succ = $scope._res[$scope._index][3];
   }

   $scope.showNext = function () {
      $scope._index = ($scope._index < $scope.$parent.perms.length - 1) ? ++$scope._index : 0;
      $scope._cur = $scope.$parent.perms[$scope._index];
   };
   $scope.postprocess = function () {
      console.log($scope._res[$scope._index]); //The response
      $scope.updateScores();
      $scope.showNext();
      $scope.resetColors();
   };

  }]);
