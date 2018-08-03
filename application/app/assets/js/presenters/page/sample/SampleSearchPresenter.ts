import AbstractSamplePresenter from './AbstractSamplePresenter';
import Component from "vue-class-component";

@Component
export default class SampleSearchPresenter extends AbstractSamplePresenter{
	exec():void {
		this.$router.push('/sample/result');
	}
}