function CircularBuffer(size) {
    this.size = size;
    this.arr = [];
}

CircularBuffer.prototype.size = function () {
    return this.size;
};

CircularBuffer.prototype.add = function (a) {
    if (this.arr.length >= this.size) {
        this.arr.shift();
    }
    this.arr.push(a);
}

CircularBuffer.prototype.addArray = function (arr) {
    for (var i = 0; i < arr.length; i++) {
        this.add(arr[i]);
    }
};

CircularBuffer.prototype.newlineStr = function () {
    var str = "";
    for (var i = 0; i < this.arr.length; i++) {
        str = str + this.arr[i] + "\n";
    }
    return str;
};

CircularBuffer.prototype.getArr = function () {
    return this.arr;
};

var app = angular.module("chatApp", ["ui.router"]);

app.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("selectRoom");

    $stateProvider
        .state('selectRoom', {
            url: '/selectRoom',
            templateUrl: URLS.partialsRooms,
            controller: 'ChatRoomCtrl'
        }).state('chats', {
            url: '/chats/:chatRoomId',
            templateUrl: URLS.partialsChats,
            controller: 'ChatCtrl'
        });
});

app.controller("ChatRoomCtrl", function ($scope, $state) {
    function init() {
        $scope.statusmessage = "";
        $scope.errormessage = '';
    }

    $scope.joinRoom = function () {
        $state.go("chats", {"chatRoomId": $scope.roomName});
    };

    init();
});

app.controller("ChatCtrl", function ($rootScope, $scope, $stateParams) {
    function init() {
        $scope.statusmessage = "";
        $scope.errormessage = '';
        $scope.buffer = new CircularBuffer(50);
        $scope.chatRoomId = $stateParams.chatRoomId;
        $scope.connected = false;
        $rootScope.chatRoomId = $scope.chatRoomId;
    }

    $scope.setConnected = function(status) {
        $scope.$apply(function () {
            $scope.connected = status;
        });
    };

    $scope.initSockets = function () {
        $scope.socket = {};
        $scope.socket.client = new SockJS(URLS.chatWSEndpoint);//new WebSocket('ws://' + window.location.host + URLS.chatWSEndpoint);//;
        $scope.socket.client.onopen = function () {
            console.log('$scope.socket.client open');
        };
        $scope.socket.client.onclose = function () {
            console.log('$scope.socket.client close');
        };
        $scope.socket.stomp = Stomp.over($scope.socket.client);
        $scope.socket.stomp.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            $scope.socket.stomp.subscribe("/topic/chats." + $scope.chatRoomId, $scope.notify);
            $scope.setConnected(true);
        }, function (err) {
            console.log("Error : " + err);
            $scope.setConnected(false);
        });
        $scope.socket.client.onclose = $scope.reconnect;
    };

    $scope.setErrorMessage = function (message) {
        $scope.errormessage = message;
        $scope.statusmessage = '';
    };

    $scope.setStatusMessage = function (message) {
        $scope.statusmessage = message;
        $scope.errormessage = '';
    };

    $scope.notify = function (message) {
        $scope.$apply(function () {
            $scope.buffer.add(message.body);
        });
    };

    $scope.reconnect = function () {
        $scope.setConnected(false);
        setTimeout($scope.initSockets, 10000);
    };

    $scope.submitGreeting = function (chatmessage) {
        $scope.socket.stomp.send("/app/chats/" + $scope.chatRoomId, {}, angular.toJson(chatmessage));
    }

    init();
    $scope.initSockets();
});