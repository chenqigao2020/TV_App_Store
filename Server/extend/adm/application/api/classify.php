<?php
/*
Name:分类管理API
Version:1.0
Author:易如意
Author QQ:51154393
Author Url:www.eruyi.cn
*/

// 此版本由《红火火工作室》开发 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室

if (!isset($islogin)) header("Location: /"); //非法访问
if ($act == 'add') {
    $name = isset($_POST['name']) ? purge($_POST['name']) : '';
    if ($name == '') json(201, '名字不能为空');
	$add = ['name' => $name, 'id'=> 0];
	$res = Db::table('classify')->add($add);
    if ($res) {
		json(200, '添加成功');
	} else {
		json(201, "添加失败，请检查名称是否重复");
	}
}

if ($act == 'del') {
	$id = isset($_POST['id']) ? $_POST['id'] : '';
	if ($id) {
		$ids = '';
		foreach ($id as $value) {
			$ids .= intval($value) . ",";
		}
		$ids = rtrim($ids, ",");
		$res = Db::table('classify')->where('id', 'in', '(' . $ids . ')')->del(); //false
		if ($res) {
			json(200, '删除成功');
		} else {
			json(201, '删除失败');
		}
	} else {
		json(201, '没有需要删除的数据');
	}
}

if ($act == 'edit') {
	$id = isset($_POST['id']) ? intval($_POST['id']) : 0;
	$update['name'] = isset($_POST['name']) ? purge($_POST['name']) : '';
	$res = Db::table('classify')->where('id', $id)->update($update);
	if ($res) {
		json(200, '编辑成功');
	} else {
		json(201, "添加失败，请检查名称是否重复");
	}
}
?>