import Vue from 'vue'
import VueRouter from 'vue-router'
import router from './router'
import App from './components/App.vue'



new Vue({
    el: '#component',
    router,
    render:h => h(App)
});