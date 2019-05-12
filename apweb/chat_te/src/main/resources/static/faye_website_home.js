//palette (cause im a pro web designer already)
//base "#3C85E8";
//light "#59E1FF";
//shadow "#29268E"
//complimentary "#FE741C";

function mouseXFollow(e) {
    var x = e.clientX;
    var padd = 30 + x/2000; //value between 1500 and 2000
    //console.log(padd);
    document.getElementById("face").style.paddingLeft = "" + padd + "%";
}