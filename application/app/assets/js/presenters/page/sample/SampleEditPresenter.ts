import AbstractSamplePresenter from './AbstractSamplePresenter';
import Component from "vue-class-component";
import SampleModel from '../../../models/in_house/sample/SampleModel';
import SampleCriteriaModel from '../../../models/in_house/sample/SampleCriteriaModel';
import SampleUseCase from '../../../use_cases/in_house/SampleUseCase';
import DropDownModel from '../../../models/common/DropDownModel';
import DropDownUseCase from '../../../use_cases/common/DropDownUseCase'
const SampleCriteria = require("../../../components/list/SampleCriteria.vue").default;

@Component({
	components: {
		"sample_criteria":SampleCriteria
	}
})
export default class SampleEditPresenter extends AbstractSamplePresenter{
	id:string = ""
	item:SampleModel = new SampleModel()
	select_base_criteria_list:DropDownModel[] = []
	select_criteria_list :DropDownModel[] = []

	private dropDownUseCase:DropDownUseCase = new DropDownUseCase();
	private sampleUseCase:SampleUseCase = new SampleUseCase();

	async created(){
		this.id = this.$route.query.id
		this.item = await this.sampleUseCase.getContent(this.id); 

		this.select_base_criteria_list = await this.dropDownUseCase.getSampleBaseCriteria()
	}

	append(criteria :SampleCriteriaModel):void {
		if(this.item.$sample_criteria_list.length == this.item.$sample_criteria_list.indexOf(criteria) + 1){
			this.item.$sample_criteria_list.push(new SampleCriteriaModel())
			return
		}
		this.item.$sample_criteria_list.splice(this.item.$sample_criteria_list.indexOf(criteria) + 1, 0 , new SampleCriteriaModel())
	}
 
	remove(criteria :SampleCriteriaModel):void{
		if(this.item.$sample_criteria_list.length <= 1){
			return
		}
		this.item.$sample_criteria_list.splice(this.item.$sample_criteria_list.indexOf(criteria), 1)
	}

	submit(){
		this.$router.push('/sample/content');
	}	

	cancel(){
		this.$router.push('/sample/content');
	}	
}