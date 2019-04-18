console.log("parsing script416");

var inputField = document.getElementById("numInput");
var outputArea = document.getElementById("output");

var x;
function test() {
    request(inputField.value)
    print(x);
}

function stressTest() {
    var i = 0;
    while (i++ < 100) {
        request(i);
    }
}

function print(s) {
    outputArea.value += s + "\n";
}

function request(n) {
    var xhr = new XMLHttpRequest();
    xhr.open("get", "http://localhost:4567/factorial?numbers=" + n, false);
    xhr.onload = () => {
        outputArea.value = "factorial(" + n + ") " + xhr.response;
        console.log("" + xhr.response);
    };
    xhr.onerror = () => {
        console.log("error: " + statusText);
    };
    xhr.send();
}

console.log("done parsing script416")