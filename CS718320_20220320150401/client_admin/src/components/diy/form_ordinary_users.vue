<template>
	<div>
		<el-form-item v-if="$check_register_field('add','full_name','/ordinary_users/view')" label="姓名" prop="full_name">
		<el-input id="full_name" v-model="form['full_name']" placeholder="请输入姓名"></el-input>
		</el-form-item>
		<el-form-item v-if="$check_register_field('add','gender','/ordinary_users/view')" label="性别" prop="gender">
		<el-select id="gender" v-model="form['gender']">
			<el-option v-for="o in list_gender" :key="o" :label="o" :value="o">
			</el-option>
		</el-select>
		</el-form-item>

	</div>
</template>

<script>
	import mixin from "@/mixins/component.js";

	export default {
		mixins: [mixin],
		props:{

			form:{
				type: Object,
				default: function(){
					return {
						"full_name":'',
						"gender":'',
					}
				}
			},

		},
		data() {
			return {
				field: "ordinary_users_id",
				url_add: "~/api/ordinary_users/add?",
				url_set: "~/api/ordinary_users/set?",
				url_upload: "~/api/ordinary_users/upload?",
				list_gender: ['男','女'],

				rules: {
					"full_name": [ ],
					"gender": [ ],
				}

			}
		},
		methods: {

		},
		created() {
		}
	}
</script>
