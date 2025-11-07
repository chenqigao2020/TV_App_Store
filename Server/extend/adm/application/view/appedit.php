<?php
/*
Sort:1
Hidden:true
Name:编辑APP
Url:application_appedit
Version:1.0
Author:易如意
Author QQ:51154393
Author Url:www.eruyi.cn
*/
if (!isset($islogin)) header("Location: /"); //非法访问
$id = isset($_GET['id']) ? intval($_GET['id']) : 0;
$res = Db::table('application')->where(['id' => $id])->find();
?>

<div class="row">
	<div class="col-12">
		<div class="page-title-box">
			<div class="page-title-right">
				<ol class="breadcrumb m-0">
					<li class="breadcrumb-item"><a href="javascript: void(0);">首页</a></li>
					<li class="breadcrumb-item"><a href="./?application_app">APP管理</a></li>
					<li class="breadcrumb-item active">编辑APP</li>
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

				<input class="form-control" type="number" id="id" name="id" placeholder="ID" value="<?php echo $res['id']; ?>" hidden>

				<div class="form-group">
					<label>名称</label>
					<input type="text" id="name" name="name" class="form-control" placeholder="名称" value="<?php echo $res['name']; ?>" required>
				</div>

				<div class="form-group">
					<label>包名</label>
					<input type="text" id="only" name="only" class="form-control" placeholder="包名" value="<?php echo $res['only']; ?>" required>
				</div>

				<div class="form-group">
					<label>图标链接</label>
					<input type="text" id="icon" name="icon" class="form-control" placeholder="图标链接" value="<?php echo $res['icon']; ?>" required>
				</div>

				<div class="form-group">
					<label>安装包链接</label>
					<input type="text" id="download" name="download" class="form-control" placeholder="安装包链接" value="<?php echo $res['download']; ?>" required>
				</div>

				<div class="form-group">
					<label>版本</label>
					<input type="text" id="version" name="version" class="form-control" placeholder="版本" value="<?php echo $res['version']; ?>" required>
				</div>

				<div class="form-group">
					<label>安装包大小</label>
					<input type="text" id="size" name="size" class="form-control" placeholder="安装包大小" value="<?php echo $res['size']; ?>" required>
				</div>

				<div class="form-group">
					<label>描述</label>
					<input type="text" id="details" name="details" class="form-control" placeholder="描述" value="<?php echo $res['details']; ?>" required>
				</div>

				<div class="form-group">
						<label>图片链接</label>
						<input type="text" id="pics" name="pics" class="form-control" placeholder="图片链接" value="<?php echo $res['pics']; ?>" required>
					</div>

				<div class="form-group">
					<label>绑定分类</label>
					<select class="form-control" name="classify" id="classify">
						<?php
							$classifys = Db::table('classify')->order('id desc')->select();
							foreach ($classifys as $k => $v) {
								$rows = $classifys[$k];
							?>
							<option value="<?php echo $rows['id']; ?>" <?php if ($rows['id'] == $res['classify_id']) echo 'selected'; ?> ><?php echo $rows['name']; ?></option>
						<?php } ?>
					</select>
				</div>
				
				<div id="edit" class="form-group text-center">
					<button class="btn btn-primary" type="submit" name="edit_submit" id="edit_submit" value="确定">确认编辑</button>
				</div>

			</div>
		</div>
	</div>
</div>

<script>

	$('#edit_submit').click(function() {
		let t = window.jQuery;
		var id = $("input[name='id']").val();
		var name = $("input[name='name']").val();
		var only = $("input[name='only']").val();
		var icon = $("input[name='icon']").val();
		var download = $("input[name='download']").val();
		var version = $("input[name='version']").val();
		var size = $("input[name='size']").val();
		var details = $("input[name='details']").val();
		var classify = $("select[name='classify']").val();
		var pics = $("input[name='pics']").val();
		document.getElementById('edit_submit').innerHTML = "<span class=\"spinner-border spinner-border-sm mr-1\" role=\"status\" aria-hidden=\"true\"></span>正在编辑";
		document.getElementById('edit_submit').disabled = true;

		$.ajax({
			cache: false,
			type: "POST", //请求的方式
			url: "ajax.php?act=application_app_edit", //请求的文件名
			data: {
				id: id,
				name: name,
				only: only,
				icon: icon,
				download: download,
				version: version,
				size: size,
				details: details,
				classify_id: classify,
				pics: pics
			},
			dataType: 'json',
			success: function(data) {
				console.log(data);
				document.getElementById('edit_submit').disabled = false;
				document.getElementById('edit_submit').innerHTML = "确认编辑";
				if (data.code == 200) {
					t.NotificationApp.send("成功", data.msg, "top-center", "rgba(0,0,0,0.2)", "success")
					location.href = "./?application_app";
				} else {
					t.NotificationApp.send("失败", data.msg, "top-center", "rgba(0,0,0,0.2)", "error")
				}
			}
		});
		return false; //重要语句：如果是像a链接那种有href属性注册的点击事件，可以阻止它跳转。
	});

</script>