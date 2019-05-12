console.log("started parsing script59");

var tableInputHandle = document.getElementById("input");
var tableOutputHandle = document.getElementById("output");

//need function to read input and request JSON object (table) from server

function dumpTable() {
    var tableName = tableInputHandle.value;
    console.log("trying to dump " + tableName);

    var data = new FormData();
    data.append("text", tableName);
    console.log(data.get("text"));
    request({ verb: "GET", url: "dump?", body: data })
        .then(data => {
            let tableData = JSON.parse(data);
            console.log("server responded with " + tableData);
            outputToChatbox(tableData);
        })
        .catch(error => {
            console.log("Error: " + error);
        });
}

function outputToChatbox(str) {
    tableOutputHandle.value += str;
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

console.log("done parsing script59");