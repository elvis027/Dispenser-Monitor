var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
    if(this.readyState==4 && this.status==200){
        var obj = JSON.parse(this.responseText);
        for(let i=0;i<obj.length;i++){
            console.log(obj[i].id);
        }
        document.getElementById("demo").innerHTML = obj[0].id;
        console.log('T');
    }
    else{

        
    }
}
xhttp.open("GET","http://localhost:8080/first.php",true);
xhttp.send(null);
console.log(xhttp);