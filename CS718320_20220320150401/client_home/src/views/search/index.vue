<template>
  <div class="page_search">
	<div class="warp">
	  <div class="container">
		<div class="row">
		  <div class="col-12">
			<div class="card_result_search">
			  <div class="title">搜索结果</div>
				<!-- 文章搜索结果 -->
			  <list_result_search
				:list="result_article"
				title="新闻资讯"
				source_table="article"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/expert_user/list', 'get')"
				:list="result_expert_user_name_of_expert"
				title="专家用户专家姓名"
				source_table="expert_user"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/journal_articles/list', 'get')"
				:list="result_journal_articles_title"
				title="期刊文章标题"
				source_table="journal_articles"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/journal_articles/list', 'get')"
				:list="result_journal_articles_author"
				title="期刊文章作者"
				source_table="journal_articles"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/manuscript_management/list', 'get')"
				:list="result_manuscript_management_manuscript_no"
				title="稿件管理稿件编号"
				source_table="manuscript_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/manuscript_management/list', 'get')"
				:list="result_manuscript_management_manuscript_name"
				title="稿件管理稿件名称"
				source_table="manuscript_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/manuscript_management/list', 'get')"
				:list="result_manuscript_management_manuscript_type"
				title="稿件管理稿件类型"
				source_table="manuscript_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/manuscript_management/list', 'get')"
				:list="result_manuscript_management_manuscript_review_status"
				title="稿件管理稿件审核状态"
				source_table="manuscript_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/manuscript_classification/list', 'get')"
				:list="result_manuscript_classification_manuscript_type"
				title="稿件分类稿件类型"
				source_table="manuscript_classification"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/expert_information/list', 'get')"
				:list="result_expert_information_name_of_expert"
				title="专家信息专家姓名"
				source_table="expert_information"
			  ></list_result_search>
			</div>
		  </div>
		</div>
	  </div>
	</div>
  </div>
</template>

<script>
import mixin from "../../mixins/page.js";
import list_result_search from "../../components/diy/list_result_search.vue";

export default {
  mixins: [mixin],
  data() {
	return {
	  "query": {
		word: "",
	  },
	  "result_article": [],
			"result_expert_user_name_of_expert":[],
			"result_journal_articles_title":[],
			"result_journal_articles_author":[],
			"result_manuscript_management_manuscript_no":[],
			"result_manuscript_management_manuscript_name":[],
			"result_manuscript_management_manuscript_type":[],
			"result_manuscript_management_manuscript_review_status":[],
			"result_manuscript_classification_manuscript_type":[],
			"result_expert_information_name_of_expert":[],
	};
  },
  methods: {
	/**
	 * 获取文章
	 */
	get_article() {
	  this.$get("~/api/article/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_article = json.result.list;
		}
	  });
	},
	/**
	 * 获取name_of_expert
	 */
	get_expert_user_name_of_expert(){
		this.$get("~/api/expert_user/get_list?like=0", { page: 1, size: 10, "name_of_expert": this.query.word }, (json) => {
		  if (json.result) {
			var result_expert_user_name_of_expert = json.result.list;
			result_expert_user_name_of_expert.map(o => o.title = o['name_of_expert'])
	  			this.result_expert_user_name_of_expert = result_expert_user_name_of_expert
		 	}
		});
	},
	/**
	 * 获取title
	 */
	get_journal_articles_title(){
		this.$get("~/api/journal_articles/get_list?like=0", { page: 1, size: 10, "title": this.query.word }, (json) => {
		  if (json.result) {
			var result_journal_articles_title = json.result.list;
			result_journal_articles_title.map(o => o.title = o['title'])
	  			this.result_journal_articles_title = result_journal_articles_title
		 	}
		});
	},
	/**
	 * 获取author
	 */
	get_journal_articles_author(){
		this.$get("~/api/journal_articles/get_list?like=0", { page: 1, size: 10, "author": this.query.word }, (json) => {
		  if (json.result) {
			var result_journal_articles_author = json.result.list;
			result_journal_articles_author.map(o => o.title = o['author'])
	  			this.result_journal_articles_author = result_journal_articles_author
		 	}
		});
	},
	/**
	 * 获取manuscript_no
	 */
	get_manuscript_management_manuscript_no(){
		this.$get("~/api/manuscript_management/get_list?like=0", { page: 1, size: 10, "manuscript_no": this.query.word }, (json) => {
		  if (json.result) {
			var result_manuscript_management_manuscript_no = json.result.list;
			result_manuscript_management_manuscript_no.map(o => o.title = o['manuscript_no'])
	  			this.result_manuscript_management_manuscript_no = result_manuscript_management_manuscript_no
		 	}
		});
	},
	/**
	 * 获取manuscript_name
	 */
	get_manuscript_management_manuscript_name(){
		this.$get("~/api/manuscript_management/get_list?like=0", { page: 1, size: 10, "manuscript_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_manuscript_management_manuscript_name = json.result.list;
			result_manuscript_management_manuscript_name.map(o => o.title = o['manuscript_name'])
	  			this.result_manuscript_management_manuscript_name = result_manuscript_management_manuscript_name
		 	}
		});
	},
	/**
	 * 获取manuscript_type
	 */
	get_manuscript_management_manuscript_type(){
		this.$get("~/api/manuscript_management/get_list?like=0", { page: 1, size: 10, "manuscript_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_manuscript_management_manuscript_type = json.result.list;
			result_manuscript_management_manuscript_type.map(o => o.title = o['manuscript_type'])
	  			this.result_manuscript_management_manuscript_type = result_manuscript_management_manuscript_type
		 	}
		});
	},
	/**
	 * 获取manuscript_review_status
	 */
	get_manuscript_management_manuscript_review_status(){
		this.$get("~/api/manuscript_management/get_list?like=0", { page: 1, size: 10, "manuscript_review_status": this.query.word }, (json) => {
		  if (json.result) {
			var result_manuscript_management_manuscript_review_status = json.result.list;
			result_manuscript_management_manuscript_review_status.map(o => o.title = o['manuscript_review_status'])
	  			this.result_manuscript_management_manuscript_review_status = result_manuscript_management_manuscript_review_status
		 	}
		});
	},
	/**
	 * 获取manuscript_type
	 */
	get_manuscript_classification_manuscript_type(){
		this.$get("~/api/manuscript_classification/get_list?like=0", { page: 1, size: 10, "manuscript_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_manuscript_classification_manuscript_type = json.result.list;
			result_manuscript_classification_manuscript_type.map(o => o.title = o['manuscript_type'])
	  			this.result_manuscript_classification_manuscript_type = result_manuscript_classification_manuscript_type
		 	}
		});
	},
	/**
	 * 获取name_of_expert
	 */
	get_expert_information_name_of_expert(){
		this.$get("~/api/expert_information/get_list?like=0", { page: 1, size: 10, "name_of_expert": this.query.word }, (json) => {
		  if (json.result) {
			var result_expert_information_name_of_expert = json.result.list;
			result_expert_information_name_of_expert.map(o => o.title = o['name_of_expert'])
	  			this.result_expert_information_name_of_expert = result_expert_information_name_of_expert
		 	}
		});
	},

  },
  components: { list_result_search },
	created(){
    this.query.word = this.$route.query.word || "";
  },
  mounted() {
	this.get_article();
		this.get_expert_user_name_of_expert();
		this.get_journal_articles_title();
		this.get_journal_articles_author();
		this.get_manuscript_management_manuscript_no();
		this.get_manuscript_management_manuscript_name();
		this.get_manuscript_management_manuscript_type();
		this.get_manuscript_management_manuscript_review_status();
		this.get_manuscript_classification_manuscript_type();
		this.get_expert_information_name_of_expert();
  },
  watch: {
	$route() {
	  $.push(this.query, this.$route.query);
	  this.get_article();
	  this.get_expert_user_name_of_expert();
	  this.get_journal_articles_title();
	  this.get_journal_articles_author();
	  this.get_manuscript_management_manuscript_no();
	  this.get_manuscript_management_manuscript_name();
	  this.get_manuscript_management_manuscript_type();
	  this.get_manuscript_management_manuscript_review_status();
	  this.get_manuscript_classification_manuscript_type();
	  this.get_expert_information_name_of_expert();
	},
  },
};
</script>

<style scoped>
.card_search {
  text-align: center;
}
.card_result_search>.title {
  text-align: center;
  padding: 10px 0;
}
</style>
