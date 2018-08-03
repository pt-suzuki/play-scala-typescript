import AbstractSamplePresenter from './AbstractSamplePresenter';
import Component from "vue-class-component";

@Component
export default class SampleSearchResultPresenter extends AbstractSamplePresenter{
	show_detail():void {
		this.$router.push('/sample/content');
	}
}