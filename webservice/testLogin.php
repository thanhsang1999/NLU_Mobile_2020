<?php
$url = 'http://localhost:8080/mobile/login.php';
$data = array('username' => 'abc123', 'password' => 'aA@123');
$data = http_build_query($data);
// use key 'http' even if you send the request to https://...
$options = array(
    'http' => array(
        'header'  => "Content-type: application/x-www-form-urlencoded\r\n"
		
		,
        'method'  => 'POST',
        'content' => $data
    )
);
$context  = stream_context_create($options);
$result = file_get_contents($url, false, $context);
if ($result === FALSE) { /* Handle error */ }
var_dump($result);

?>