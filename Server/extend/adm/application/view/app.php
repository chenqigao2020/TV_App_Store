<?php
/*
Sort:3
Hidden:false
Name:APP管理
Url:application_app
Right:application
Version:1.0
Author:易如意
Author QQ:51154393
Author Url:www.eruyi.cn
*/

// 此版本由《红火火工作室》开发 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室

if (!isset($islogin)) header("Location: /"); //非法访问
$nums = Db::table('application')->count();
$page = isset($_GET['page']) ? intval($_GET['page']) : 1;
$url = "./?application_app&page=";
$bnums = ($page - 1) * $ENUMS;
?>

<!-- start page title -->
<div class="row">
	<div class="col-12">
		<div class="page-title-box">
			<div class="page-title-right">
				<ol class="breadcrumb m-0">
					<li class="breadcrumb-item"><a href="javascript: void(0);">首页</a></li>
					<li class="breadcrumb-item active">APP管理</li>
				</ol>
			</div>
			<h4 class="page-title"><?php echo $title; ?></h4>
		</div> <!-- end page-title-box -->
	</div> <!-- end col-->
</div>
<!-- end page title -->

<div class="row">
	<div class="col-12">
		<div class="card">
			<div class="card-body">
				<div class="row mb-2">
					<div class="col-lg-8">
						<button type="button" onclick="toAdd()" class="btn btn-danger mb-2 mr-2"><i class="mdi mdi-briefcase-plus-outline mr-1"></i>添加APP</button>
					</div>
					<div class="col-lg-4">
						<div class="text-lg-right">
							<form action="" method="post">
								<div class="input-group">
									<input type="text" class="form-control" name="so" placeholder="搜索APP" value='<?php echo $so; ?>'>
									<span class="mdi mdi-magnify"></span>
									<div class="input-group-append">
										<button class="btn btn-primary" type="submit">搜索</button>
									</div>
								</div>
							</form>
						</div>
					</div><!-- end col-->
				</div>
				<form action="" method="post" name="form_log" id="form_log">
					<div class="table-responsive">
						<table class="table table-centered table-striped dt-responsive nowrap w-100">
							<thead>
								<tr>
									<th style="width: 20px;">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input" id="all" onclick="checkAll();">
											<label class="custom-control-label" for="all">&nbsp;</label>
										</div>
									</th>

									<th style="width: 20px;">
										<center><span class="badge badge-light-lighten">图标</span></center>
									</th>

									<th >
										<center><span class="badge badge-light-lighten">名称</span></center>
									</th>

									<th >
										<center><span class="badge badge-light-lighten">分类</span></center>
									</th>

									<th >
										<center><span class="badge badge-light-lighten">编辑</span></center>
									</th>
								</tr>
							</thead>
							<tbody>
								<?php
								$app = Db::table('application', 'as K')->field('K.*,A.name as classify_name')->JOIN('classify', 'as A', 'K.classify_id=A.id');
								if ($so) {
									$app = $app->where('K.name', 'like', "%{$so}%")->order('id desc');
								} else {
									$app = $app->order('id desc')->limit($bnums, $ENUMS);
								}
								$res = $app->select();
								foreach ($res as $k => $v) {
									$rows = $res[$k];
								?>
								<tr>
									<td>
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input" name="ids[]" value="<?php echo $rows['id']; ?>" id="<?php echo 'check_' . $rows['id']; ?>">
											<label class="custom-control-label" for="<?php echo 'check_' . $rows['id']; ?>"></label>
										</div>
									</td>

									<td>
										<center><img src="<?php echo $rows['icon']; ?>" style="width=70px;height:70px"></img></center>
									</td>

									<td>
										<center><?php echo $rows['name']; ?></center>
									</td>

									<td>
										<center><?php echo $rows['classify_name']; ?></center>
									</td>

									<td>
										<center><a href="javascript:void(0);" onclick="toEdit(<?php echo $rows['id']; ?>)" class="action-icon"> <i class="mdi mdi-border-color"></i></a></center>
									</td>

								</tr>
								<?php } ?>
							</tbody>
						</table>
					</div>
					<div class="progress-w-percent-s"></div>
					<div class="form-row">
						<div class="form-group col-md-6 mt-2">
							<div class="col-sm-4">
								<div class="list_footer">
									选中项：<a href="javascript:void(0);" onclick="delsubmit()" id="delsubmit">删除</a>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<nav aria-label="Page navigation example">
								<ul class="pagination justify-content-end">
									<?php if (!$so) {
										echo pagination($nums, $ENUMS, $page, $url);
									} ?>
								</ul>
							</nav>
						</div>
					</div>
				</form>
			</div> <!-- end card-body-->
		</div> <!-- end card-->
	</div> <!-- end col -->
</div>
<!-- end row -->

<script>

	function toAdd() {
		location.href = "./?application_appadd";
	}

	function toEdit(id) {
		location.href = "./?application_appedit&id=" + id;
	}

	function checkAll() {
		var code_Values = document.getElementsByTagName("input");
		var all = document.getElementById("all");
		if (code_Values.length) {
			for (i = 0; i < code_Values.length; i++) {
				if (code_Values[i].type == "checkbox") {
					code_Values[i].checked = all.checked;
				}
			}
		} else {
			if (code_Values.type == "checkbox") {
				code_Values.checked = all.checked;
			}
		}
	}

	function delsubmit() {
		var id_array = new Array();
		$("input[name='ids[]']:checked").each(function() {
			id_array.push($(this).val()); //向数组中添加元素 
		}); //获取界面复选框的所有值
		//ar chapterstr = id_array.join(',');//把复选框的值以数组形式存放
		var url = window.location.href;
		let t = window.jQuery;
		if (id_array.length <= 0) {
			t.NotificationApp.send("提示", "请选择要删除的项目", "top-center", "rgba(0,0,0,0.2)", "warning")
			return false;
		}
		document.getElementById("delsubmit").innerHTML = "<div class=\"spinner-border spinner-border-sm mr-1\" style=\"margin-bottom:2px!important\" role=\"status\"></div>删除中";
		document.getElementById("delsubmit").className = "text-title";
		$("#delsubmit").attr("disabled", true).css("pointer-events", "none");

		console.log(id_array);
		$.ajax({
			cache: false,
			type: "POST", //请求的方式
			url: "ajax.php?act=application_app_del", //请求的文件名
			data: {
				id: id_array
			},
			dataType: 'json',
			success: function(data) {
				if (data.code == 200) {
					t.NotificationApp.send("成功", data.msg, "top-center", "rgba(0,0,0,0.2)", "success")
				} else {
					t.NotificationApp.send("失败", data.msg, "top-center", "rgba(0,0,0,0.2)", "error")
				}
				//console.log(data);
				window.setTimeout("window.location='" + url + "'", 1000);
			}
		});
		return false; //重要语句：如果是像a链接那种有href属性注册的点击事件，可以阻止它跳转。
	}
</script>