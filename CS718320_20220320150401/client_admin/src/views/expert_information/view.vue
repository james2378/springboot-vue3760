<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','name_of_expert') || $check_field('add','name_of_expert') || $check_field('set','name_of_expert')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="专家姓名" prop="name_of_expert">
					<el-input id="name_of_expert" v-model="form['name_of_expert']" placeholder="请输入专家姓名"
							  v-if="user_group === '管理员' || (form['expert_information_id'] && $check_field('set','name_of_expert')) || (!form['expert_information_id'] && $check_field('add','name_of_expert'))" :disabled="disabledObj['name_of_expert_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','name_of_expert')">{{form['name_of_expert']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','expert_photos') || $check_field('add','expert_photos') || $check_field('set','expert_photos')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="专家相片" prop="expert_photos">
					<el-upload :disabled="disabledObj['expert_photos_isDisabled']" id="expert_photos" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_expert_photos"
						:show-file-list="false" v-if="user_group === '管理员' || (form['expert_information_id'] && $check_field('set','expert_photos')) || (!form['expert_information_id'] && $check_field('add','expert_photos'))">
						<img v-if="form['expert_photos']" :src="$fullUrl(form['expert_photos'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','expert_photos')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['expert_photos'])" :preview-src-list="[$fullUrl(form['expert_photos'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','expert_introduction') || $check_field('add','expert_introduction') || $check_field('set','expert_introduction')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="专家介绍" prop="expert_introduction">
					<el-input type="textarea" id="expert_introduction" v-model="form['expert_introduction']" placeholder="请输入专家介绍"
						v-if="user_group === '管理员' || (form['expert_information_id'] && $check_field('set','expert_introduction')) || (!form['expert_information_id'] && $check_field('add','expert_introduction'))" :disabled="disabledObj['expert_introduction_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','expert_introduction')">{{form['expert_introduction']}}</div>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8">
				<el-form-item>
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "expert_information_id",
				url_add: "~/api/expert_information/add?",
				url_set: "~/api/expert_information/set?",
				url_get_obj: "~/api/expert_information/get_obj?",
				url_upload: "~/api/expert_information/upload?",

				query: {
					"expert_information_id": 0,
				},

				form: {
					"name_of_expert":'', // 专家姓名
					"expert_photos":'', // 专家相片
					"expert_introduction":'', // 专家介绍
					"expert_information_id": 0, // ID

				},
				disabledObj:{
					"name_of_expert_isDisabled": false,
					"expert_photos_isDisabled": false,
					"expert_introduction_isDisabled": false,
				},

			}
		},
		methods: {
			/**
			 * 上传专家相片
			 * @param {Object} param图片参数
			 */
			upload_expert_photos(param){
				this.uploadFile(param.file, "expert_photos");
			},

			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
							}
						})
					})
				}
				$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){

			},

			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/expert_information/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/expert_information/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/expert_information/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/expert_information/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/expert_information/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},

		},
		created() {
		}
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
