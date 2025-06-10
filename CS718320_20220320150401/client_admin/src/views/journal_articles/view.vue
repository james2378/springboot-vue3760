<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','title') || $check_field('add','title') || $check_field('set','title')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="标题" prop="title">
					<el-input id="title" v-model="form['title']" placeholder="请输入标题"
							  v-if="user_group === '管理员' || (form['journal_articles_id'] && $check_field('set','title')) || (!form['journal_articles_id'] && $check_field('add','title'))" :disabled="disabledObj['title_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','title')">{{form['title']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','author') || $check_field('add','author') || $check_field('set','author')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="作者" prop="author">
					<el-input id="author" v-model="form['author']" placeholder="请输入作者"
							  v-if="user_group === '管理员' || (form['journal_articles_id'] && $check_field('set','author')) || (!form['journal_articles_id'] && $check_field('add','author'))" :disabled="disabledObj['author_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','author')">{{form['author']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','release_date') || $check_field('add','release_date') || $check_field('set','release_date')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="发布日期" prop="release_date">
					<el-date-picker :disabled="disabledObj['release_date_isDisabled']" v-if="user_group === '管理员' || (form['journal_articles_id'] && $check_field('set','release_date')) || (!form['journal_articles_id'] && $check_field('add','release_date'))" id="release_date"
						v-model="form['release_date']" type="date" placeholder="选择日期">
					</el-date-picker>
					<div v-else-if="$check_field('get','release_date')">{{form['release_date']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','article_attachment') || $check_field('add','article_attachment') || $check_field('set','article_attachment')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="文章附件" prop="article_attachment">
					<div v-if="disabledObj['article_attachment_isDisabled']">
						<div v-if="$check_field('get','article_attachment')">
							<el-button type="primary" @click="$download($fullUrl(form['article_attachment']),form['article_attachment'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
					<div v-else>
						<el-upload v-if="user_group === '管理员' || (form['journal_articles_id'] && $check_field('set','article_attachment')) || (!form['journal_articles_id'] && $check_field('add','article_attachment'))" class="upload-demo" drag
								   action="" style="max-width: 300px;width: 100%;" :http-request="upload_article_attachment" :limit="1" accept="">
							<i class="el-icon-upload"></i>
							<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
						</el-upload>
						<div v-else-if="$check_field('get','article_attachment')">
							<el-button type="primary" @click="$download($fullUrl(form['article_attachment']),form['article_attachment'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','article_cover') || $check_field('add','article_cover') || $check_field('set','article_cover')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="文章封面" prop="article_cover">
					<el-upload :disabled="disabledObj['article_cover_isDisabled']" id="article_cover" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_article_cover"
						:show-file-list="false" v-if="user_group === '管理员' || (form['journal_articles_id'] && $check_field('set','article_cover')) || (!form['journal_articles_id'] && $check_field('add','article_cover'))">
						<img v-if="form['article_cover']" :src="$fullUrl(form['article_cover'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','article_cover')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['article_cover'])" :preview-src-list="[$fullUrl(form['article_cover'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','article_content') || $check_field('add','article_content') || $check_field('set','article_content')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="文章内容" prop="article_content">
					<el-input type="textarea" id="article_content" v-model="form['article_content']" placeholder="请输入文章内容"
						v-if="user_group === '管理员' || (form['journal_articles_id'] && $check_field('set','article_content')) || (!form['journal_articles_id'] && $check_field('add','article_content'))" :disabled="disabledObj['article_content_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','article_content')">{{form['article_content']}}</div>
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
				field: "journal_articles_id",
				url_add: "~/api/journal_articles/add?",
				url_set: "~/api/journal_articles/set?",
				url_get_obj: "~/api/journal_articles/get_obj?",
				url_upload: "~/api/journal_articles/upload?",

				query: {
					"journal_articles_id": 0,
				},

				form: {
					"title":'', // 标题
					"author":'', // 作者
					"release_date":'', // 发布日期
					"article_attachment":'', // 文章附件
					"article_cover":'', // 文章封面
					"article_content":'', // 文章内容
					"journal_articles_id": 0, // ID

				},
				disabledObj:{
					"title_isDisabled": false,
					"author_isDisabled": false,
					"release_date_isDisabled": false,
					"article_attachment_isDisabled": false,
					"article_cover_isDisabled": false,
					"article_content_isDisabled": false,
				},

			}
		},
		methods: {
			/**
			 * 上传文章附件
			 * @param {Object} param文件参数
			 */
			upload_article_attachment(param){
				this.uploadFile(param.file, "article_attachment");
			},
			/**
			 * 上传文章封面
			 * @param {Object} param图片参数
			 */
			upload_article_cover(param){
				this.uploadFile(param.file, "article_cover");
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
        if (this.form["release_date"].indexOf("-")===-1){
          this.form["release_date"] = this.$toTime(parseInt(this.form["release_date"]),"yyyy-MM-dd")
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
				if(parseInt(this.form["release_date"]) > 9999){
					this.form["release_date"] = this.$toTime(parseInt(this.form["release_date"]),"yyyy-MM-dd")
				}

			},

			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/journal_articles/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/journal_articles/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/journal_articles/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/journal_articles/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/journal_articles/view','get');
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
