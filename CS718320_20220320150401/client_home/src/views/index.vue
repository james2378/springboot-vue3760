<template>
	<div class="page_root" id="root_demo">
		<div class="warp">
		    <div class="container">
		        <div class="row">
		            <div class="col">
		                <div class="card_ad">
		                    <!-- 页头 -->
		                    <list_ad
		                            :list="list_ad"
		                            location="店招"
		                            v-if="$check_action('/ad/list', 'get')"
		                            style="marginbottom: 0.5rem"
		                    />
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<div class="warp">
		    <div class="container swiper_box">
		        <div class="row">
		            <div class="col-12">
		                <div class="sw_no">
		                    <!-- 轮播图组件 -->
		                    <div class="swiper_img">
		                        <swiper_img :list="list_slide"/>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<div class="warp">
		    <div class="container">
		        <div class="row">
		            <div class="col">
		                <div class="card_ad">
		                    <!-- 顶部 -->
		                    <list_ad
		                            :list="list_ad"
		                            v-if="$check_action('/ad/list', 'get')"
		                            location="顶部广告"
		                    />
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<div class="warp">
		    <div class="container">
		        <div class="row">
		            <div class="col">
		                <div class="card_ad">
		                    <!-- 中部 -->
		                    <list_ad
		                            :list="list_ad"
		                            v-if="$check_action('/ad/list', 'get')"
		                            location="中部广告"
		                    />
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		<div class="warp">
		    <!-- 容器 -->
		    <div class="container">
		        <div class="row">
		            <div class="col-12">
		                <!-- 标题栏组件 -->
		                <div class="article_recommend">
		                    <div class="title">
		                        <span>新闻资讯 </span>
		                    </div>
		                    <div class="more_box">
		                        <router-link to="/article/list" class="more">
		                            <span>更多</span>
		                        </router-link>
		                    </div>
		                    <div class="switch" @click="show_list_article = !show_list_article">
		                        <div class="switch_box">
		                            <span v-if="show_list_article"> 切换表格 </span>
		                            <span v-else> 切换列表 </span>
		                        </div>
		                    </div>
		                </div>
		                <!--新闻资讯列表组件 -->
		                <list_article v-if="show_list_article" :list="list_article" />
		                <div class="overflow-auto" v-else>
		                    <table id="list_article" role="table" aria-busy="false"
		                           :aria-colcount="fields_article.length"
		                           class="table b-table table-striped table-hover">
		                        <thead>
		                        <tr>
		                            <th v-for="(o,i) in fields_article" :key="i">
		                                {{o.label}}
		                            </th>
		                        </tr>
		                        </thead>
		                        <tbody>
		                        <tr v-for="(o, i) in list_table_article" :key="i">
		                            <td v-for="(oj,n) in fields_goods" @click="to_details('goods',o)">
		                                <img v-if="oj.type && oj.type == '图片' " :src="$fullUrl(o[oj.key])" alt=""
		                                     v-default-img="'/img/default.png'">
		                                <span v-else>{{ o[oj.key] }}</span>
		                            </td>
		                        </tr>
		                        </tbody>
		                    </table>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<div class="warp">
		    <div class="container">
		        <div class="row">
		            <div class="col">
		                <div class="card_ad">
		                    <!-- 底部 -->
		                    <list_ad
		                            :list="list_ad"
		                            v-if="$check_action('/ad/list', 'get')"
		                            location="底部广告"
		                    />
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<div class="warp">
		    <div class="container">
		        <div class="row">
		            <div class="col">
		                <div class="card_link">
		                    <!-- 底部友情链接 -->
		                    <list_link
		                            v-if="$check_action('/link/list', 'get')"
		                            :list="list_link"
		                    />
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		<img class="support_entry" src="../../static/support.jpg" @click="openSupportModal" alt="">

		<div class="support_module" v-if="showChat">
			<div class="container" :class="{small_css:isSmall,big_css:!isSmall}">
				<div class="support_module_title">
					<span class="title_btn" @click="scaleSmall">{{!isSmall? '一' : '□' }}</span>
				</div>
				<div class="content" ref="support_content">
					<div v-for="(item,index) in chatList" :key="item+index">
						<div class="item item-center" v-if="item.sendTime"><span v-text="item.sendTime"></span></div>
						<div class="item" :class="{item_left: item.isAdmin, item_right: !item.isAdmin}">
							<div class="avatar" v-if="item.isAdmin">
								<img src="../../static/system_icon.jpg"/>
							</div>
							<div class="bubble" :class="{ 'bubble-right': !item.isAdmin}" v-text="item.content"></div>
							<div class="avatar" v-if="!item.isAdmin">
								<img src="../../static/mine.jpg"/>
							</div>
						</div>
					</div>


				</div>
				<div class="input-area">
					<textarea name="text" style="height:50px;" v-model="sendValue" id="textarea"
							  placeholder="输入内容"></textarea>

				</div>
				<div class="button-area">
					<!-- <div style="width: 100px;height:50px;cursor: pointer;" @click="toToggle"></div> -->
					<button id="send-btn" @click="sendChat">发 送</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import mixin from "@/mixins/page.js";
	import bar_title from "@/components/diy/bar_title.vue";
	import list_article from "@/components/diy/list_article.vue";
	import swiper_img from "@/components/diy/swiper_img.vue";
	import list_ad from "@/components/diy/list_ad.vue";
	import list_link from "@/components/diy/list_link.vue";
	import swiper_notice from "@/components/diy/swiper_notice.vue";

	export default {
		mixins: [mixin],
		components: {
			bar_title,
			list_article,
			swiper_img,
			swiper_notice,
			list_ad,
			list_link,
		},
		data() {
			return {
				isSmall: false,
				sendValue: "",
				chatList:[],
				showChat: false,
				isAdmin: false,
				token:"",
				// 文章模型数组
				list_article: [],
				vm_arr_article: [
				    "img",
				    "title",
				    "description",
				    "hits",
				    "type",
				    "tag",
				    "source",
				    "url",
				    "create_time",
				],
				fields_article: [
				    {key: "img", label: "图片"},
				    {key: "title", label: "标题"},
				    {key: "description", label: "描述"},
				    {key: "hits", label: "点击量"},
				    {key: "type", label: "分类"},
				    {key: "tag", label: "标签"},
				    {key: "source", label: "来源"},
				    {key: "url", label: "来源地址"},
				    {key: "create_time", label: "发布时间"},
				],
				show_list_article: true,
				list_slide: [],
				list_menu: [],
				list_link: [],
				list_notice: [],
				list_ad: []
			};
		},
		created(){
			this.get_token();
			this.getChat();
		},
		methods: {
			get_token(){
				let token = this.$route.query.token
				if (token){
					$.db.set("token",token,120);
					location.href = "http://localhost:8081/"
				}
			},
					// toggle
			toToggle(){
				this.isAdmin = !this.isAdmin;
			},
			// open
			openSupportModal(){
				if(!this.token){
					return alert("请先进行登录");
				}
				console.log("打开客服")
				this.showChat = !this.showChat;
				if(this.showChat && this.chatList.length){
						setTimeout(() => {
					this.scrollBottom();
				},0)
				}

			},
			//获取用户信息
			async get_user_by_user_id(o,oj,key){
				let obj = await this.$get("~/api/user/get_obj?user_id="+o[key]);
				let ret = "";
				if(obj){
					ret = obj.result.obj.nickname+"-"+obj.result.obj.username;
					oj.value = ret;
				}
			},
			// 缩小聊天窗口
			scaleSmall(){
				this.isSmall = !this.isSmall;
			},
			// 获取聊天记录
			getChat(){
				let record = window.localStorage.getItem('supportChat');
				let userGroup = window.localStorage.getItem('user_group');
				let _token = window.localStorage.getItem('token');

				if(record){
					this.chatList = JSON.parse(record);
				}
				if(userGroup){
					let _userGroup =JSON.parse(userGroup);
					this.isAdmin = _userGroup["value"] == "管理员" ? true : false;
				}
				if(_token){
					let tokenObj =JSON.parse(_token);
					this.token = tokenObj["value"] || "";
				}

			},
			// 滚动到最底部
			scrollBottom(){
				let contentBox = this.$refs['support_content'];
					contentBox.scrollTop = contentBox.scrollHeight+500;
			},
			// 发送聊天
			sendChat(){
				let nowTime = new Date( +new Date() + 8 * 3600 * 1000 ).toJSON().substr(0,16).replace("T"," ");
				let nowTimeChuo = new Date().getTime();
				let reallyTime = "";
				let spaceTime = 12000;	//	20分钟
				if(!this.sendValue){
					return alert("不能发送空内容");
				};
		   this.chatList.length && (reallyTime =	nowTimeChuo - this.chatList[this.chatList.length-1]['nowTime'] > spaceTime ?  nowTime : "");
				!this.chatList.length && (reallyTime = nowTime);
			  let	obj = {
						isAdmin: this.isAdmin,
						content: this.sendValue,
						sendTime: reallyTime,
						nowTime: nowTimeChuo,
				}
				this.chatList.push(obj);

				setTimeout(() => {
						this.scrollBottom();
				},0)

				window.localStorage.setItem('supportChat',JSON.stringify(this.chatList));
				this.sendValue = "";
			},

			/**
			 * 当前年月日时分秒方法
			 * @param {Object} fmt
			 */
			dateFormat(fmt) {
				var myDate = new Date();
				var o = {
					"M+": myDate.getMonth() + 1, // 月份
					"d+": myDate.getDate(), // 日
					"h+": myDate.getHours(), // 小时
					"m+": myDate.getMinutes(), // 分
					"s+": myDate.getSeconds(), // 秒
					"q+": Math.floor((myDate.getMonth() + 3) / 3), // 季度
					"S": myDate.getMilliseconds() // 毫秒
				};
				if (/(y+)/.test(fmt))
					fmt = fmt.replace(RegExp.$1, (myDate.getFullYear() + "").substr(4 - RegExp.$1.length));
				for (var k in o)
					if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
						return fmt;
			},

			// 获取轮播图
			get_slides() {
			    this.$get("~/api/slides/get_list?", {}, (json) => {
			        if (json.result) {
			            this.list_slide = json.result.list;
			        }
			    });
			},
			// 获取导航栏
			get_menu() {
				var user_group = this.user_group;
			    this.$get(
			        "~/api/auth/get_list?",
			        {
			            page: "",
						user_group,
						get: 1,
						position: "top"
			        },
			        (json) => {
			            if (json.result) {
			                this.list_menu = json.result.list;
			            }
			        }
			    );
			},
			// 获取文章
			get_article() {
			    this.$get(
			        "~/api/article/get_list?",
			        {
			            page: 1,
			            size: 4,
			        },
			        (json) => {
			            if (json.result) {
			                var list_article = json.result.list;
			                this.list_article = list_article;
			            }
			        }
			    );
			},
			// 获取广告
			get_ad() {
			    this.$get(
			        "~/api/ad/get_list?",
			        {
			            orderby: '`display` desc'
			        },
			        (json) => {
			            if (json.result) {
			                var list_ad = json.result.list;
			                this.list_ad = list_ad;
			            }
			        }
			    );
			},
			// 获取链接列表
			get_link() {
			    this.$get(
			        "~/api/link/get_list?",
			        {
			            page: 1,
			            size: 8,
			        },
			        (json) => {
			            if (json.result) {
			                var list_link = json.result.list;
			                this.list_link = list_link;
			            }
			        }
			    );
			},
			// 获取公告列表
			get_notice() {
			    this.$get(
			        "~/api/notice/get_list?",
			        {
			            page: 1,
			            size: 3,
			        },
			        (json) => {
			            if (json.result) {
			                var list_notice = json.result.list;
			                this.list_notice = list_notice;
			            }
			        }
			    );
			},
			to_details(key,o,id) {
				if(!id){
					id = key + "_id";
				}
			    this.$router.push('/' + key + '/details?' + id + '=' + o[id]);
			}
		},
		mounted() {
			this.get_menu();
			this.get_slides();
			this.get_article();
			this.get_link();
			this.get_notice();
			this.get_ad();
		},
		computed: {
		    list_table_article() {
		        var list = this.list_article;
		        var list_table = [];
		        for (let i = 0; i < list.length; i++) {
		            list_table[i] = {};
		            this.vm_arr_article.map((o) => {
		                // 第二个中括号是对象的属性
		                list_table[i][o] = list[i][o] || "";
		            });
		        }
		        return list_table;
		    },
		}
	};
</script>

<style scoped>
.page_root{
	position: relative;
}
.support_entry{
	position: fixed;
	top: 400px;
	right: 20px;
	width: 80px;
	height: 80px;
	cursor: pointer;
	z-index: 99999;
}
.support_module .container{
        /* height: 650px; */
        height: 70%;
        width: 700px;
        border-radius: 4px;
        border: 0.5px solid #e0e0e0;
        background-color: #f5f5f5;
        /* display: flex;
        flex-flow: column; */
        overflow: hidden;
				position: fixed;
				/* top: 100px; */
				padding: 0;
				margin-left: -350px;
				z-index: 99999999;
    }
	.support_module	.small_css{
		height: 40px;
		width: 200px;
		right: 0;
		bottom: 0px;
	}
	.support_module	.big_css{
				top: 60px;
				left: 50%;
				}
    .support_module .content{
        width: calc(100% - 0px);
        padding: 20px 30px;
        overflow-y: scroll;
				height: 70%;
    }
		.support_module .content::-webkit-scrollbar{
	display: none
}
.support_module_title{
	text-align: right;
	height: 40px;
	line-height: 40px;
	border-bottom: 1px solid #ccc;

}
.support_module_title .title_btn{
	font-size: 20px;
	cursor: pointer;
	margin-right: 20px;
	color: #888;

}
    .support_module .content:hover::-webkit-scrollbar-thumb{
        background:rgba(0,0,0,0.1);
    }
    .support_module .bubble{
        max-width: 400px;
        padding: 10px;
        border-radius: 5px;
        position: relative;
        color: #000;
        word-wrap:break-word;
        word-break:normal;
    }
    .support_module .item_left .bubble{
        margin-left: 15px;
        background-color: #fff;
    }
    .support_module .item_left .bubble:before{
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-left: 10px solid transparent;
        border-top: 10px solid transparent;
        border-right: 10px solid #fff;
        border-bottom: 10px solid transparent;
        left: -20px;
    }
    .support_module .item_right .bubble{
        margin-right: 15px;
        background-color: #9eea6a;
    }
    .support_module .item_right .bubble:before{
        content: "";
        position: absolute;
        width: 0;
        height: 0;
        border-left: 10px solid #9eea6a;
        border-top: 10px solid transparent;
        border-right: 10px solid transparent;
        border-bottom: 10px solid transparent;
				left: none;

				        right: -20px;
    }
    .support_module .item{
        margin-top: 15px;
        display: flex;
        width: 100%;
    }
    .support_module .item.item_right{
        justify-content: flex-end;
				min-width: 400px;
    }
    .support_module .item.item-center{
        justify-content: center;
    }
    .support_module .item.item-center span{
        font-size: 12px;
        padding: 2px 4px;
        color: #fff;
        background-color: #dadada;
        border-radius: 3px;
        -moz-user-select:none; /*火狐*/
        -webkit-user-select:none; /*webkit浏览器*/
        -ms-user-select:none; /*IE10*/
        -khtml-user-select:none; /*早期浏览器*/
        user-select:none;
    }

    .support_module .avatar img{
        width: 42px;
        height: 42px;
        border-radius: 50%;
    }
    .support_module .input-area{
        border-top:0.5px solid #e0e0e0;
        height: 16%;

        background-color: #fff;
    }
    .support_module textarea{
        flex: 1;
        padding: 10px;
        font-size: 14px;
        border: none;
        overflow-y: auto;
        overflow-x: hidden;
        outline:none;
        resize:none;
				width: 102%;
				height: 100%;
				min-height: 100%;
				max-height: 100%;
    }
    .support_module .button-area{
      display: flex;
    height: 50px;
    line-height: 50px;
    padding: 5px;
    justify-content: flex-end;
    text-align: right;
    width: 100%;
		background: #fff;
    }
    .support_module .button-area button{
        width: 80px;
				height: 40px;
				line-height: 40px;
        border: none;
        outline: none;
        border-radius: 4px;
        float: right;
        cursor: pointer;
				background: #9eea6a;
				color: #333;
				margin: 0px;
    }

    /* 设置滚动条的样式 */
    ::-webkit-scrollbar {
        width:10px;
    }
    /* 滚动槽 */
    ::-webkit-scrollbar-track {
        -webkit-box-shadow:inset006pxrgba(0,0,0,0.3);
        border-radius:8px;
    }
    /* 滚动条滑块 */
    ::-webkit-scrollbar-thumb {
        border-radius:10px;
        background:rgba(0,0,0,0);
        -webkit-box-shadow:inset006pxrgba(0,0,0,0.5);
    }

	.sw_no {
	    margin: 0.5rem;
	    display: flex;
	    flex-wrap: wrap;
	}

	.swiper_img {
	    width: 60%;
	    height: 18.75rem;
	}

	.card_notice {
	    width: 40%;
	    padding-left: 1rem;
	    height: 18.75rem;
	}

	.notice_title {
	    font-size: 1.2rem;
	    font-weight: bold;
	    color: white;
	    display: flex;
		justify-content: space-between;
	    align-items: center;
	    background-color: #d2d2d2;
	    height: 3rem;
	    padding: 0 1.5rem;
	}

	.article_recommend > * {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	}

	.article_recommend {
	    display: block;
	    margin: 1.5rem 0.5rem;
	    height: 4rem;
	    border-radius: 0.5rem;
	    background-color: var(--color_primary);
	}

	.diy_recommend > * {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	}

	.diy_recommend {
	    display: block;
	    margin: 1.5rem 0.5rem;
	    height: 4rem;
	    border-radius: 0.5rem;
	    background-color: var(--color_primary);
	}

	.title {
	    float: left;
	    height: 4rem;
	    color: white;
	    font-size: 1.5rem;
	    font-weight: bold;
	    padding-left: 1rem;
	}

	.more_box {
	    float: right;
	    height: 4rem;
	    color: white;
	    font-size: 1.5rem;
	    padding-right: 1rem;
	}

	.more_box .more {
	    border-radius: 0.5rem;
	    border: 0.125rem solid white;
	    display: flex;
	    align-items: center;
	    font-size: 1.2rem;
	    justify-content: center;
	    height: 3rem;
	    width: 3.5rem;
	}

	.switch {
	    float: right;
	    height: 4rem;
	    color: white;
	    padding-right: 1rem;
	}

	.switch_box {
	    border-radius: 0.5rem;
	    border: 0.125rem solid white;
	    display: flex;
	    align-items: center;
	    font-size: 1.2rem;
	    justify-content: center;
	    height: 3rem;
	    width: 6rem;
	}

	.switch_box:hover, .more:hover {
	    background-color: white;
	    color: var(--color_primary) !important;
	}

	.overflow-auto .table th,
	.overflow-auto .table td {
	    text-align: center;
	    text-wrap: normal;
	    font-size: 1rem;
	    vertical-align: middle;
	}

	/*.page_index {*/
	/*    margin: 0 auto;*/
	/*    max-width: 1200px;*/
	/*}*/

	/*.main {*/
	/*    display: flex;*/
	/*    justify-content: space-between;*/
	/*}*/

	/*.content {*/
	/*    flex: 0 0 79%;*/
	/*}*/

	/*.aside {*/
	/*    flex: 0 0 20%;*/
	/*}*/

	.card_ad {
	    margin-top: 1rem;
	}

	@media (max-width: 996px) {
	    .swiper_img {
	        width: 100%;
	        height: 12.5rem;
	    }

	    .card_notice {
	        width: 100%;
	        padding-left: 0;
	    }
	}
</style>
