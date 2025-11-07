<?php
/*
Name:.
*/

// 此版本由《红火火工作室》开发 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室

if (!isset($app_res) or !is_array($app_res)) out(100); //如果需要调用应用配置请先判断是否加载app配置

$db = Db::table('application');

$db->field("id, name, only, icon");

$list = $db->order('RAND()')->limit(getRequestParam("limit") ?: 5)->select();

out(200, $list, $app_res);

?>