import Vue from 'vue'
import VueRouter from 'vue-router'
import SampleEdit from "./components/page/sample/SampleEdit.vue"
import SampleSearch from "./components/page/sample/SampleSearch.vue"
import SampleSearchResult from "./components/page/sample/SampleSearchResult.vue"
import SampleContent from "./components/page/sample/SampleContent.vue"


Vue.use(VueRouter)

const router = new VueRouter({
	mode: 'history',
	routes:[
		{path: '/', component:SampleSearchResult},
		
		{path: '/sample/edit', component: SampleEdit},
		{path: '/sample/search',component: SampleSearch},
		{path: '/sample/result',component: SampleSearchResult},
		{path: '/sample', component: SampleSearchResult},
		{path: '/sample/content', component: SampleContent},
		

	]
})

export default router