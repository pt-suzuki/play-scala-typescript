import AbstractSamplePresenter from './AbstractSamplePresenter';
import Component from "vue-class-component";

@Component
export default class SampleContentPresenter extends AbstractSamplePresenter{
	edit():void {
		this.$router.push('/sample/edit');	
	}
}