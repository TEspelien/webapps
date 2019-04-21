console.log("started parsing script418.js")

messageInputHandle = document.getElementById(messageInput);
chatboxHandle = document.getElementById(chatbox);
function pushMessage(){
    
}

function request(obj) {
    return new Promise((resolve, reject) => {
        let xhr = new XMLHttpRequest();
        xhr.open(obj.method || "GET", obj.url);

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
        print(xhr.response);
    };
    xhr.onerror = () => {
        print("error " + xhr.statusText);
    };

    xhr.send(obj.body);
}

console.log("done parsing scrip418.js")