<template>
	<el-main class="bg">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="稿件编号">
						<el-input v-model="query.manuscript_no"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="稿件名称">
						<el-input v-model="query.manuscript_name"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="稿件类型">
						<el-select v-model="query.manuscript_type">
							<el-option v-for="o in list_manuscript_type" :key="o.manuscript_type" :label="o.manuscript_type"
								:value="o.manuscript_type">
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="投稿人">
						<el-select v-model="query.contributor">
							<el-option v-for="o in list_contributor" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="稿件审核状态">
						<el-select v-model="query.manuscript_review_status">
                            <el-option v-for="o in list_manuscript_review_status" :key="o" :label="o"
                            	:value="o">
                            </el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8">
					<el-form-item label="专家用户">
						<el-select v-model="query.expert_user">
							<el-option v-for="o in list_expert_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="10" :lg="8">
					<el-form-item>
						<el-button type="primary" @click="search()">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;">重置</el-button>
						<router-link v-if="user_group == '管理员' || $check_action('/manuscript_management/table','add') || $check_action('/manuscript_management/view','add')" class="el-button el-button--default el-button--primary" to="./view?">添加
						</router-link>
						<el-button v-if="user_group == '管理员' || $check_action('/manuscript_management/table','del') || $check_action('/manuscript_management/view','del')" class="" type="danger" @click="delInfo()">删除</el-button>
					</el-form-item>
				</el-col>

			</el-row>
		</el-form>
		<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
			<el-table-column prop="manuscript_no" label="稿件编号"
				v-if="user_group == '管理员' || $check_field('get','manuscript_no')" min-width="200">
			</el-table-column>
			<el-table-column prop="manuscript_name" label="稿件名称"
				v-if="user_group == '管理员' || $check_field('get','manuscript_name')" min-width="200">
			</el-table-column>
			<el-table-column prop="manuscript_type" label="稿件类型"
				v-if="user_group == '管理员' || $check_field('get','manuscript_type')" min-width="200">
			</el-table-column>
			<el-table-column prop="contributor" label="投稿人"
				v-if="user_group == '管理员' || $check_field('get','contributor')" min-width="200">
				<template slot-scope="scope">
					{{ get_user_contributor(scope.row['contributor']) }}
				</template>
			</el-table-column>
			<el-table-column prop="upload_date" label="上传日期"
				v-if="user_group == '管理员' || $check_field('get','upload_date')" min-width="200">
                <template slot-scope="scope">
                	{{ $toTime(scope.row["upload_date"],"yyyy-MM-dd") }}
                </template>
			</el-table-column>
			<el-table-column prop="remarks" label="备注"
				v-if="user_group == '管理员' || $check_field('get','remarks')" min-width="200">
			</el-table-column>
			<el-table-column prop="manuscript_attachment" label="稿件附件"
				v-if="user_group == '管理员' || $check_field('get','manuscript_attachment')" min-width="200">
				<template slot-scope="scope">
					<a :href="$fullUrl(scope.row['manuscript_attachment'])" target="_blank" style="color: rgb(64, 158, 255);">点击下载</a>
				</template>
			</el-table-column>
			<el-table-column prop="manuscript_review_status" label="稿件审核状态"
				v-if="user_group == '管理员' || $check_field('get','manuscript_review_status')" min-width="200">
			</el-table-column>
			<el-table-column prop="expert_user" label="专家用户"
				v-if="user_group == '管理员' || $check_field('get','expert_user')" min-width="200">
				<template slot-scope="scope">
					{{ get_user_expert_user(scope.row['expert_user']) }}
				</template>
			</el-table-column>
			<el-table-column prop="introduction_to_manuscript" label="稿件简介"
				v-if="user_group == '管理员' || $check_field('get','introduction_to_manuscript')" min-width="200">
			</el-table-column>

            <el-table-column sortable prop="create_time" label="创建时间" min-width="200">
                <template slot-scope="scope">
                	{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
                </template>
            </el-table-column>

			<el-table-column sortable prop="update_time" label="更新时间" min-width="200">
                <template slot-scope="scope">
                	{{ $toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss") }}
                </template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" min-width="120" v-if="user_group == '管理员' || $check_action('/manuscript_management/table','set') || $check_action('/manuscript_management/view','set') || $check_action('/manuscript_management/view','get') || $check_action('//table','add') || $check_action('//view','add')" >


				<template slot-scope="scope">
					<router-link class="el-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
					v-if="user_group == '管理员' || $check_action('/manuscript_management/table','set') || $check_action('/manuscript_management/view','set') || $check_action('/manuscript_management/view','get')"
						:to="'./view?' + field + '=' + scope.row[field]"
						 size="small">
						<span>详情</span>
					</router-link>
				</template>
			</el-table-column>

		</el-table>

		<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
				layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		<!-- /分页器 -->

		<div class="modal_wrap" v-if="showModal">
			<div class="modal_box">
				<!-- <div class="modal_box_close" @click="closeModal">X</div> -->
				<p class="modal_box_title">重要提醒</p>
				<p class="modal_box_text">当前有数据达到预警值！</p>
				<div class="btn_box">
					<span @click="closeModal">取消</span>
					<span @click="closeModal">确定</span>
				</div>
			</div>
		</div>


	</el-main>
</template>
<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				// 弹框
				showModal: false,
				// 获取数据地址
				url_get_list: "~/api/manuscript_management/get_list?like=0",
				url_del: "~/api/manuscript_management/del?",

				// 字段ID
				field: "manuscript_management_id",

				// 查询
				query: {
					"size": 7,
					"page": 1,
					"manuscript_no": "",
					"manuscript_name": "",
					"manuscript_type": "",
					"contributor": "",
					"manuscript_review_status": "",
					"expert_user": "",
					"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},
				// 数据
				list: [],
				// 稿件类型列表
				list_manuscript_type: [],
				list_contributor: [],
				// 用户列表
				list_user_contributor: [],
				// 稿件审核状态列表
				list_manuscript_review_status: ['未审核','过期','未通过','采用'],
				list_expert_user: [],
				// 用户列表
				list_user_expert_user: [],
			}
		},
		methods: {
			// 关闭弹框
			closeModal(){
				this.showModal = false;
				},
			get_list_before(param){
				var user_group = this.user.user_group;
				if(user_group != "管理员"){
					switch(user_group){
						case "普通用户":
						param["contributor"] = this.user.user_id;
						break;
						case "专家用户":
						param["expert_user"] = this.user.user_id;
						break;
					}
				}
				return param;
			},
			/**
			 * 获取稿件类型列表
			 */
			async get_list_manuscript_type() {
				var json = await this.$get("~/api/manuscript_classification/get_list?");
				if(json.result){
					this.list_manuscript_type = json.result.list;
				}else if (json.error){
					console.log(json.error);
				}
			},
			/**
			 * 获取投稿人列表
			 */
			async get_list_contributor() {
				var json = await this.$get("~/api/user/get_list?user_group=普通用户");
				if(json.result && json.result.list){
					this.list_contributor = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			/**
			 * 获取专家用户列表
			 */
			async get_list_expert_user() {
				var json = await this.$get("~/api/user/get_list?user_group=专家用户");
				if(json.result && json.result.list){
					this.list_expert_user = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},

			open_tip() {
				const h = this.$createElement;

				var message = "";
				var list = this.list;

				var ifs = [ ];
				for (var n = 0; n < ifs.length; n++) {
					var o = ifs[n];
					for (var i = 0; i < list.length; i++) {
						var lt = list[i];
						if (o.type == "数内") {
							if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
								if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.start || o.start === 0) {
								if (lt[o.factor] > o.start) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.end || o.end === 0) {
								if (lt[o.factor] < o.end) {
									o["idx"] = o["idx"] + 1;
								}
							}
						} else if (o.type == "数外") {
							if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
								if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.start || o.start === 0) {
								if (lt[o.factor] < o.start) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.end || o.end === 0) {
								if (lt[o.factor] > o.end) {
									o["idx"] = o["idx"] + 1;
								}
							}
						} else if (o.type == "日内") {
							if ((o.start) && (o.end)) {
								if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.start) {
								if (lt[o.factor] < o.start) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.end) {
								if (lt[o.factor] > o.end) {
									o["idx"] = o["idx"] + 1;
								}
							}
						} else if (o.type == "日外") {
							if (o.start && o.end) {
								if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.start) {
								if (lt[o.factor] < o.start) {
									o["idx"] = o["idx"] + 1;
								}
							} else if (o.end) {
								if (lt[o.factor] > o.end) {
									o["idx"] = o["idx"] + 1;
								}
							}
						}
					}

					if (o["idx"]) {
						message += o.title;
						if (o["type"] == "数内") {
							if (o.start || o.start === 0) {
								message += "大于" + o.start;
							}
							if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
								message += "并且";
							}
							if (o.end || o.end === 0) {
								message += "小于" + o.end;
							}
						} else if (o["type"] == "数外") {
							if (o.start || o.start === 0) {
								message += "小于" + o.start;
							}
							if ((o.start || o.start === 0) || (o.end || o.end === 0)) {
								message += "或者";
							}
							if (o.end || o.end === 0) {
								message += "大于" + o.end;
							}
						} else if (o["type"] == "日内") {
							if (o.start) {
								message += "在" + o.start + "之后";
							}
							if (o.start && o.end) {
								message += "并且";
							}
							if (o.end) {
								message += "在" + o.end + "之前";
							}
						} else if (o["type"] == "日外") {
							if (o.start) {
								message += "在" + o.start + "之前";
							}
							if (o.start || o.end) {
								message += "或者";
							}
							if (o.end) {
								message += "在" + o.end + "之后";
							}
						}
						message += "的有" + o["idx"] + "条";
					}
				}

				if (message) {
					message += "，需要处理、请尽快处理。";
					this.showModal = true;
					// this.$notify({
					// 	title: '提醒',
					// 	dangerouslyUseHTMLString: true,
					// 	message: h('i', {
					// 		style: 'color: teal'
					// 	}, message)
					// });
				}
			},
			/**
			 * 获取普通用户用户列表
			 */
			async get_list_user_contributor() {
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
					ret = obj.nickname+"-"+obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
			},
			/**
			 * 获取专家用户用户列表
			 */
			async get_list_user_expert_user() {
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
					ret = obj.nickname+"-"+obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
			},
			deleteRow(index, rows) {
				rows.splice(index, 1);
			}

		},
		created() {
			// 初始化稿件类型列表
			this.get_list_manuscript_type();
			this.get_list_contributor();
			this.get_list_user_contributor();
			this.get_list_expert_user();
			this.get_list_user_expert_user();
			setTimeout(() => {
				this.open_tip();
			}, 1000)
		}
	}
</script>

<style type="text/css">
	.bg {
		background: white;
	}

	.form.p_4 {
		padding: 1rem;
	}

	.form .el-input {
		width: initial;
	}

	.mt {
		margin-top: 1rem;
	}

	.text_center {
		text-align: center;
	}

	.float-right {
		float: right;
	}


	.modal_wrap{
		width: 100vw;
		height: 100vh;
		position: fixed;
		top: 0;
		left: 0;
		background: rgba(0,0,0,0.5);
		z-index: 9999999999;
	}
	.modal_wrap .modal_box{
		width: 400px;
		height: 200px;
		background: url("../../assets/modal_bg.jpg") no-repeat center;
		background-size: cover;
		position: absolute;
		top: 50%;
		left: 50%;
		margin-left: -200px;
		margin-top: -100px;
		border-radius: 10px;
		}
	.modal_wrap .modal_box .modal_box_close{
		font-size: 20px;
		position: absolute;
		top: 10px;
		right: 10px;
		cursor: pointer;
		}
	.modal_wrap .modal_box .modal_box_title{
	  text-align: center;
    font-size: 18px;
    margin: 16px auto;
    color: #fff;
    border-bottom: 1px solid rgba(117, 116, 116,0.5);
    padding-bottom: 16px;
    width: 356px;
		}
	.modal_wrap .modal_box .modal_box_text{
			text-align: center;
		font-size: 15px;
		color: #fff;
		margin-top: 25px;
		}
	.modal_wrap .modal_box .btn_box{
		display: flex;
		flex-direction: row;
		justify-content: center;
		margin-top: 42px;
		}
			.modal_wrap .modal_box .btn_box span{
				display: inline-block;
				width: 80px;
				height: 30px;
				line-height: 30px;
				text-align: center;
				border: 1px solid #ccc;
				font-size: 14px;
				cursor: pointer;
				color: #fff;
			}
	.modal_wrap .modal_box .btn_box span:nth-child(2){
		background: #409EFF;
		color: #fff;
		border-color: #409EFF;
		margin-left: 15px;
	}
</style>
