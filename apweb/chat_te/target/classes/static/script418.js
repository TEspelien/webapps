console.log("started parsing script418.js")

var messageInputHandle = document.getElementById("messageInput");
var loginInputHandle = document.getElementById("loginInput");
var chatboxHandle = document.getElementById("chatbox");

function outputToChatbox(str) {
    chatboxHandle.value += "\n \n" + str;
}

function outputToConsole(str) {
    console.log(str);
}

function postMessage() {
    var m = messageInputHandle.value;
    console.log(m);
    var data = new FormData();
    data.append("text", m);
    console.log(data);
    request({ verb: "POST", url: "post?", body: data })
        .then(data => {
            completeMessage = data;
            outputToChatbox(completeMessage);
        })
        .catch(error => {
            outputToConsole("Error: " + error);
        });
}

function retrieveMessages() {
    request({ verb: "GET", url: "retrieve?" })
        .then(data => {
            outputToChatbox(data);
            //chatboxHandle.value += data;
        })
        .catch(error => {
            outputToChatbox("Error: " + error);
        });
}

function login() {
    var user = loginInputHandle.value;
    request({ verb: "GET", url: "login?initials=" + user })
        .then(data => {
            outputToChatbox("Login successful");
        })
        .catch(error => {
            output("Login error: " + error);
        });
}

function request(obj) {
    return new Promise((resolve, reject) => {
        let xhr = new XMLHttpRequest();
        xhr.open(obj.verb || "GET", obj.url);
        console.log("verb used: " + obj.verb + " | url used: " + obj.url);
        xhr.onload = () => {
            if (xhr.status >= 200 && xhr.status < 300) {
                resolve(xhr.response);
            } else {
                reject(xhr.statusText);
            }
        };
        xhr.onerror = () => reject(xhr.statusText);

        xhr.send(obj.body);
    });
};

function xmlrequest(verb, url) {
    var xhr = new XMLHttpRequest();
    xhr.open(verb || "GET", url, true)
    xhr.onload = () => {
        outputToConsole(xhr.response);
    };
    xhr.onerror = () => {
        outputToConsole("error " + xhr.statusText);
    };
    xhr.send(obj.body);
}

console.log("done parsing scrip418.js")