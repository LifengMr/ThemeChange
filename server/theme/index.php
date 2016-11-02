<?php
header('Content-type: text/json');

$result = array (
	"error"=>"0",
	"data"=>array(
		array("name"=>"theme1.apk", "packageName"=>"com.ss.android.theme1", "signature"=>"ZwU2gJYfg4qPrGg2G0FbxV5nxA0", 
			"version"=>1, "title"=>"主题1", "desc"=>"主题1", "url"=>"http://10.2.212.248/theme/theme1.apk")
		)
	);

echo json_encode($result);
?>
