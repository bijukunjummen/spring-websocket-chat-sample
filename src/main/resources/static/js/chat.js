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