<template>
	<div class="diy_home diy_list diy_manuscript_management" id="diy_manuscript_management_list">
		<!-- 列表 -->
		<div class="diy_view_list list list-x" v-if="show">
			<router-link class="diy_card goods diy_list_box_wrap" v-for="(o, i) in list" :key="i"
				:to="'/manuscript_management/details?manuscript_management_id=' + o['manuscript_management_id']">
				<!-- 图片 -->
				<div class="diy_list_img_box" v-if="imgList.length" >
					<div class="diy_row" v-for="(item,index) in imgList" :key="item+index" v-show="$check_field('get',item.name,'/manuscript_management/details') && +item.is_img_list">
						<div class="diy_title diy_list_img_title">
							<span>{{item.title}}:</span>
						</div>
						<div class="diy_field diy_img">
							<img :src="$fullUrl(o[item.name])" style="width:100%;height:100%" />
						</div>
					</div>
				</div>
				<!-- 内容 -->
				<div class="diy_list_item_box">
					<div class="diy_list_item_content" v-for="(item,index) in showItemList" :key="item+index">
						<div class="diy_row" :class="{[item.name]:true}" v-if="$check_field('get',item.name,'/manuscript_management/details') && +item.is_img_list">
							<div class="diy_title">
								<span>{{item.title}}:</span>
							</div>
							<div class="diy_field diy_text">
								<span v-if="item.type == 'UID'" v-text="get_user_name(o[item.name])"></span>
								<span v-else-if="item.type == '日期'" v-text="$toTime(o[item.name],'yyyy-MM-dd')"></span>
								<span v-else-if="item.type == '时间'" v-text="$toTime(o[item.name],'hh:mm:ss')"></span>
								<span v-else-if="item.type == '日长'" v-text="$toTime(o[item.name],'yyyy-MM-dd hh:mm:ss')"></span>
								<span v-else v-text="o[item.name]"></span>
							</div>
						</div>
					</div>
				</div>
			</router-link>
		</div>
		<!-- 表格 -->
		<div class="diy_view_table" v-else>
			<table class="diy_table">
				<tr class="diy_row">
					<th class="diy_title" v-if="$check_field('get','manuscript_no')">稿件编号
					</th>
					<th class="diy_title" v-if="$check_field('get','manuscript_name')">稿件名称
					</th>
					<th class="diy_title" v-if="$check_field('get','manuscript_type')">稿件类型
					</th>
					<th class="diy_title" v-if="$check_field('get','contributor')">投稿人
					</th>
					<th class="diy_title" v-if="$check_field('get','upload_date')">上传日期
					</th>
					<th class="diy_title" v-if="$check_field('get','remarks')">备注
					</th>
					<th class="diy_title" v-if="$check_field('get','manuscript_review_status')">稿件审核状态
					</th>
					<th class="diy_title" v-if="$check_field('get','expert_user')">专家用户
					</th>
					<th class="diy_title" v-if="$check_field('get','introduction_to_manuscript')">稿件简介
					</th>
				</tr>
				<tr class="diy_row" v-for="(o,i) in list" :key="o+i">
					<td class="diy_field diy_text" v-if="$check_field('get','manuscript_no')">
						<span>
							{{ o["manuscript_no"] }}
						</span>
					</td>
					<td class="diy_field diy_text" v-if="$check_field('get','manuscript_name')">
						<span>
							{{ o["manuscript_name"] }}
						</span>
					</td>
					<td class="diy_field diy_text" v-if="$check_field('get','manuscript_type')">
						<span>
							{{ o["manuscript_type"] }}
						</span>
					</td>
					<td class="diy_field diy_uid" v-if="$check_field('get','contributor')">
						<span>
							{{ get_user_name(o['contributor']) }}
						</span>
					</td>
					<td class="diy_field diy_date" v-if="$check_field('get','upload_date')">
						<span>
							{{ $toTime(o["upload_date"] ,"yyyy-MM-dd") }}
						</span>
					</td>
					<td class="diy_field diy_text" v-if="$check_field('get','remarks')">
						<span>
							{{ o["remarks"] }}
						</span>
					</td>
					<td class="diy_field diy_text" v-if="$check_field('get','manuscript_review_status')">
						<span>
							{{ o["manuscript_review_status"] }}
						</span>
					</td>
					<td class="diy_field diy_uid" v-if="$check_field('get','expert_user')">
						<span>
							{{ get_user_name(o['expert_user']) }}
						</span>
					</td>
					<td class="diy_field diy_text" v-if="$check_field('get','introduction_to_manuscript')">
						<span>
							{{ o["introduction_to_manuscript"] }}
						</span>
					</td>
				</tr>
			</table>
		</div>
	</div>
</template>

<script>
	export default {
		props: {
			list: {
				type: Array,
				default: function() {
					return [];
				},
			},
			show: {
				type: Boolean,
				default: function(){
					return true;
				}
			}
		},
		data() {
			return {
						imgList: [
						],
						itemList: [
								{
									title: "稿件编号",
									name: "manuscript_no",
									type: "文本",
									is_img_list: "0"
								},
								{
									title: "稿件名称",
									name: "manuscript_name",
									type: "文本",
									is_img_list: "0"
								},
								{
									title: "稿件类型",
									name: "manuscript_type",
									type: "下拉",
									is_img_list: "0"
								},
								{
									title: "投稿人",
									name: "contributor",
									type: "UID",
									is_img_list: "0"
								},
								{
									title: "上传日期",
									name: "upload_date",
									type: "日期",
									is_img_list: "0"
								},
								{
									title: "备注",
									name: "remarks",
									type: "文本",
									is_img_list: "0"
								},
								{
									title: "稿件附件",
									name: "manuscript_attachment",
									type: "文件",
									is_img_list: "0"
								},
								{
									title: "稿件审核状态",
									name: "manuscript_review_status",
									type: "下拉",
									is_img_list: "0"
								},
								{
									title: "专家用户",
									name: "expert_user",
									type: "UID",
									is_img_list: "0"
								},
						],
						richList: [
								{
									title: "稿件简介",
									name: "introduction_to_manuscript",
									type: "多文本"
								},
						],
				// 用户列表
				list_user_contributor: [],
				// 用户列表
				list_user_expert_user: [],
			};
		},
		methods: {
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
			get_user_name(id){
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
			get_user_name(id){
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
		},
		created() {
			this.get_list_user_contributor();
			this.get_list_user_expert_user();
		},
		computed:{
			showItemList(){
				let arr = [];
				let _type = ["视频","音频","文件"];
				this.itemList.forEach(item => {
					if(_type.indexOf(item.type) === -1 && !!+item.is_img_list){
						arr.push(item)
					}
				})
				return arr.slice(0,4);
			}
		}
	};
</script>

<style scoped>
	.media {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		flex-basis: 75%;
		min-height: 10rem;
	}

	.goods {
		display: flex;
		width: calc(25% - 1rem);
		margin: 0.5rem;
		padding: 0.5rem;
		flex-direction: column;
		justify-content: space-between;
		background-color: white;
		border-radius: 0.5rem;
	}

	.goods:hover {
		border: 0.2rem solid #909399;
		box-shadow: 0 0.1rem 0.3rem rgba(0, 0, 0, 0.15);
	}

	.goods:hover img {
		filter: blur(1px);
	}

	.price {
		font-size: 1rem;
		margin-right: 3px;
	}

	.price_ago {
		text-decoration: line-through;
		font-size: 0.5rem;
		color: #999;

	}

	.title {
		word-break: break-all;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		font-weight: 700;
		padding: .25rem;
	}

	.icon_cart {
		color: #FF5722;
		float: right;
	}

	@media (max-width: 992px) {

		.goods {
			width: calc(33% - 1rem);
			;
		}

	}

	@media (max-width: 768px) {

		.goods {
			width: calc(50% - 1rem);
			;
		}

	}
</style>

