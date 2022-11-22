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

var stompClient = null;
var messages = new CircularBuffer(20);

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
}

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chats.genericroom', function (message) {
            submitMessages(message.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/chats/genericroom", {}, JSON.stringify({'message': $("#msg").val()}));
}

function submitMessages(message) {
    messages.add(message);
   $('#messages').val(messages.newlineStr());
   return false;
}

$(function () {
    setConnected(false);
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});
