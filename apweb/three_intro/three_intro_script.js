console.log("started parsing script");
clock = new THREE.Clock();
function addSphere(r, center) {
    console.log("adding sphere...")
    const sphereMaterial =
        new THREE.MeshLambertMaterial(//lambert is without highlights or smth like that
            {
                color: 0xCC0000 //red
            });

    const sphere = new THREE.Mesh(
        new THREE.SphereGeometry(
            r,
            16,
            16),
        sphereMaterial);

    //i wanna see!
    sphere.position.x = center.x;
    sphere.position.y = center.y;
    sphere.position.z = center.z;

    // Changes to the vertices
    sphere.geometry.verticesNeedUpdate = true;

    // Changes to the normals
    sphere.geometry.normalsNeedUpdate = true;
    //without these two things it wont re draw every time
    return sphere;
}

function orbitObjectXZ(o, r, center) {
    var t = 1.4 * clock.getElapsedTime();
    o.position.x = center.x + r * (Math.sin(t));
    o.position.z = center.z + r * (Math.cos(t));
    //console.log(o.position.x);
    //console.log(o.position.z);
}

function orbitObjectXY(o, r, center) {
    var t = 1.4 * clock.getElapsedTime();
    o.position.x = -(center.x + r * (Math.sin(t)));
    o.position.y = center.y + r * (Math.cos(t));
}

function orbitObjectYZ(o, r, center) {
    var t = 1.4 * clock.getElapsedTime();
    o.position.z = center.z + r * (Math.sin(t));
    o.position.y = center.y + r * (Math.cos(t));
}

function rotateObject(o, s) {
    o.rotation += s;
    console.log(o.rotation);
}

function addLight(center) {
    console.log("adding light...");
    const pointLight =
        new THREE.PointLight(0xFFFFFF);//big boi white light

    // set its position
    var x = center.x;
    var y = center.y;
    var z = center.z;
    pointLight.position.x = x;
    pointLight.position.y = y;
    pointLight.position.z = z;

    return pointLight;
}

function addBox(w, d, h, center) {
    var x = center.x;
    var y = center.y;
    var z = center.z;
    console.log("adding box");
    var geometry = new THREE.BoxGeometry(w, d, h);
    var material = new THREE.MeshBasicMaterial({ color: 0x009999 });
    var box = new THREE.Mesh(geometry, material);
    box.position.x = x;
    box.position.y = y;
    box.position.z = z;
    return box;
}
console.log("finished parsing script")