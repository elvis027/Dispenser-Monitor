<?php
$ch = curl_init();
curl_setopt($ch,CURLOPT_URL,"https://iot.martinintw.com/api/v1/data/12345614");
curl_setopt($ch,CURLOPT_HEADER,false);
curl_setopt($ch,CURLOPT_RETURNTRANSFER,1);
$temp = curl_exec($ch);

curl_close($ch);
$myJSON = json_encode($temp);
echo $temp;
?>

