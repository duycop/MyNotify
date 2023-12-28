<?php
	$id = @$_GET['id'];
	if($id=='')$id=1;
	$MAX=116;
	$msg='NOP 100KG GIAY VUN';
	$tieude='KE HOACH TO';
	if($id<$MAX)
		$data = array('ok'=>1, 'id'=>$MAX, 'tieude'=>$tieude,'msg'=>$msg);
	else
		$data = array('ok'=>0);
	echo json_encode($data);