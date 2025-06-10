<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','manuscript_no') || $check_field('add','manuscript_no') || $check_field('set','manuscript_no')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="稿件编号" prop="manuscript_no">
					<el-input id="manuscript_no" v-model="form['manuscript_no']" placeholder="请输入稿件编号"
							  v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','manuscript_no')) || (!form['manuscript_management_id'] && $check_field('add','manuscript_no'))" :disabled="true"></el-input>
					<div v-else-if="$check_field('get','manuscript_no')">{{form['manuscript_no']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','manuscript_name') || $check_field('add','manuscript_name') || $check_field('set','manuscript_name')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="稿件名称" prop="manuscript_name">
					<el-input id="manuscript_name" v-model="form['manuscript_name']" placeholder="请输入稿件名称"
							  v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','manuscript_name')) || (!form['manuscript_management_id'] && $check_field('add','manuscript_name'))" :disabled="disabledObj['manuscript_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','manuscript_name')">{{form['manuscript_name']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','manuscript_type') || $check_field('add','manuscript_type') || $check_field('set','manuscript_type')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="稿件类型" prop="manuscript_type">
					<el-select id="manuscript_type" v-model="form['manuscript_type']"
						v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','manuscript_type')) || (!form['manuscript_management_id'] && $check_field('add','manuscript_type'))">
						<el-option v-for="o in list_manuscript_type" :key="o['manuscript_type']" :label="o['manuscript_type']"
							:value="o['manuscript_type']">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','manuscript_type')">{{form['manuscript_type']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','contributor') || $check_field('add','contributor') || $check_field('set','contributor')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投稿人" prop="contributor">
						<el-select v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','contributor')) || (!form['manuscript_management_id'] && $check_field('add','contributor'))" id="contributor" v-model="form['contributor']" :disabled="disabledObj['contributor_isDisabled']">
							<el-option v-for="o in list_user_contributor" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','contributor')" id="contributor" v-model="form['contributor']" :disabled="true">
							<el-option v-for="o in list_user_contributor" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','upload_date') || $check_field('add','upload_date') || $check_field('set','upload_date')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="上传日期" prop="upload_date">
					<el-date-picker :disabled="disabledObj['upload_date_isDisabled']" v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','upload_date')) || (!form['manuscript_management_id'] && $check_field('add','upload_date'))" id="upload_date"
						v-model="form['upload_date']" type="date" placeholder="选择日期">
					</el-date-picker>
					<div v-else-if="$check_field('get','upload_date')">{{form['upload_date']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','remarks') || $check_field('add','remarks') || $check_field('set','remarks')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="备注" prop="remarks">
					<el-input id="remarks" v-model="form['remarks']" placeholder="请输入备注"
							  v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','remarks')) || (!form['manuscript_management_id'] && $check_field('add','remarks'))" :disabled="disabledObj['remarks_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','remarks')">{{form['remarks']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','manuscript_attachment') || $check_field('add','manuscript_attachment') || $check_field('set','manuscript_attachment')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="稿件附件" prop="manuscript_attachment">
					<div v-if="disabledObj['manuscript_attachment_isDisabled']">
						<div v-if="$check_field('get','manuscript_attachment')">
							<el-button type="primary" @click="$download($fullUrl(form['manuscript_attachment']),form['manuscript_attachment'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
					<div v-else>
						<el-upload v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','manuscript_attachment')) || (!form['manuscript_management_id'] && $check_field('add','manuscript_attachment'))" class="upload-demo" drag
								   action="" style="max-width: 300px;width: 100%;" :http-request="upload_manuscript_attachment" :limit="1" accept="">
							<i class="el-icon-upload"></i>
							<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
						</el-upload>
						<div v-else-if="$check_field('get','manuscript_attachment')">
							<el-button type="primary" @click="$download($fullUrl(form['manuscript_attachment']),form['manuscript_attachment'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','manuscript_review_status') || $check_field('add','manuscript_review_status') || $check_field('set','manuscript_review_status')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="稿件审核状态" prop="manuscript_review_status">
					<el-select id="manuscript_review_status" v-model="form['manuscript_review_status']"
						v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','manuscript_review_status')) || (!form['manuscript_management_id'] && $check_field('add','manuscript_review_status'))">
						<el-option v-for="o in list_manuscript_review_status" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','manuscript_review_status')">{{form['manuscript_review_status']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','expert_user') || $check_field('add','expert_user') || $check_field('set','expert_user')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="专家用户" prop="expert_user">
						<el-select v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','expert_user')) || (!form['manuscript_management_id'] && $check_field('add','expert_user'))" id="expert_user" v-model="form['expert_user']" :disabled="disabledObj['expert_user_isDisabled']">
							<el-option v-for="o in list_user_expert_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','expert_user')" id="expert_user" v-model="form['expert_user']" :disabled="true">
							<el-option v-for="o in list_user_expert_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','introduction_to_manuscript') || $check_field('add','introduction_to_manuscript') || $check_field('set','introduction_to_manuscript')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="稿件简介" prop="introduction_to_manuscript">
					<el-input type="textarea" id="introduction_to_manuscript" v-model="form['introduction_to_manuscript']" placeholder="请输入稿件简介"
						v-if="user_group === '管理员' || (form['manuscript_management_id'] && $check_field('set','introduction_to_manuscript')) || (!form['manuscript_management_id'] && $check_field('add','introduction_to_manuscript'))" :disabled="disabledObj['introduction_to_manuscript_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','introduction_to_manuscript')">{{form['introduction_to_manuscript']}}</div>
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
				field: "manuscript_management_id",
				url_add: "~/api/manuscript_management/add?",
				url_set: "~/api/manuscript_management/set?",
				url_get_obj: "~/api/manuscript_management/get_obj?",
				url_upload: "~/api/manuscript_management/upload?",

				query: {
					"manuscript_management_id": 0,
				},

				form: {
					"manuscript_no":this.$get_stamp(), // 稿件编号
					"manuscript_name":'', // 稿件名称
					"manuscript_type":'', // 稿件类型
					"contributor": 0, // 投稿人
					"upload_date":'', // 上传日期
					"remarks":'', // 备注
					"manuscript_attachment":'', // 稿件附件
					"manuscript_review_status":'', // 稿件审核状态
					"expert_user": 0, // 专家用户
					"introduction_to_manuscript":'', // 稿件简介
					"manuscript_management_id": 0, // ID

				},
				disabledObj:{
					"manuscript_no_isDisabled": false,
					"manuscript_name_isDisabled": false,
					"manuscript_type_isDisabled": false,
					"contributor_isDisabled": false,
					"upload_date_isDisabled": false,
					"remarks_isDisabled": false,
					"manuscript_attachment_isDisabled": false,
					"manuscript_review_status_isDisabled": false,
					"expert_user_isDisabled": false,
					"introduction_to_manuscript_isDisabled": false,
				},
				//稿件类型选项列表
				list_manuscript_type: [],
				// 用户列表
				list_user_contributor: [],
				//稿件审核状态选项列表
				list_manuscript_review_status: ['未审核','过期','未通过','采用'],
				// 用户列表
				list_user_expert_user: [],

			}
		},
		methods: {
			/**
			 * 获取稿件类型列表
			 */
			async get_list_manuscript_type() {
				var json = await this.$get("~/api/manuscript_classification/get_list?");
				if(json.result && json.result.list){
					this.list_manuscript_type = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			/**
			 * 获取普通用户用户列表
			 */
			async get_list_user_contributor() {
                // if(this.user_group !== "管理员" && this.form["contributor"] === 0) {
                //     this.form["contributor"] = this.user.user_id;
                // }
                var json = await this.$get("~/api/user/get_list?user_group=普通用户");
                if(json.result && json.result.list){
                    this.list_user_contributor = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
			get_user_contributor(id){
				var obj = this.list_user_contributor.getObj({"user_id":id});
				var ret = "";
				if(obj){
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
				}
				return ret;
			},
			/**
			 * 上传稿件附件
			 * @param {Object} param文件参数
			 */
			upload_manuscript_attachment(param){
				this.uploadFile(param.file, "manuscript_attachment");
			},
			/**
			 * 获取专家用户用户列表
			 */
			async get_list_user_expert_user() {
                // if(this.user_group !== "管理员" && this.form["expert_user"] === 0) {
                //     this.form["expert_user"] = this.user.user_id;
                // }
                var json = await this.$get("~/api/user/get_list?user_group=专家用户");
                if(json.result && json.result.list){
                    this.list_user_expert_user = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
			get_user_expert_user(id){
				var obj = this.list_user_expert_user.getObj({"user_id":id});
				var ret = "";
				if(obj){
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
				}
				return ret;
			},

			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
				/**
				* 请求列表前
				* @param {Object} param
				*/
				var user_group = this.user.user_group;
				if (user_group !== "管理员") {
					switch (user_group) {
						case "投稿人":
							if(param["contributor"] > 0){
								param["contributor"] = this.user.user_id;
							}
							break;
						case "专家用户":
							if(param["expert_user"] > 0){
								param["expert_user"] = this.user.user_id;
							}
							break;
					}
				}
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
        if (this.form["upload_date"].indexOf("-")===-1){
          this.form["upload_date"] = this.$toTime(parseInt(this.form["upload_date"]),"yyyy-MM-dd")
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
				if(parseInt(this.form["upload_date"]) > 9999){
					this.form["upload_date"] = this.$toTime(parseInt(this.form["upload_date"]),"yyyy-MM-dd")
				}

			},

			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/manuscript_management/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/manuscript_management/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/manuscript_management/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/manuscript_management/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/manuscript_management/view','get');
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
			this.get_list_manuscript_type();
			this.get_list_user_contributor();
			this.get_list_user_expert_user();
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
