import Vue from 'vue';
import Component from "vue-class-component";
import SampleCriteriaModel from '../../models/in_house/mining_planning/SampleCriteriaModel';
import SampleUseCase from '../../use_cases/in_house/SampleUseCase';
import DropDownModel from '../../models/common/DropDownModel';
import DropDownUseCase from '../../use_cases/common/DropDownUseCase'

@Component({
	props: {
		criteria:SampleCriteriaModel,
		select_base_criteria_list:Array
	}
})
export default class SampleCriteriaPresenter extends Vue{
	select_base_criteria_list:DropDownModel[] = [];
	select_criteria_list :DropDownModel[] = []
	criteria:SampleCriteriaModel = new SampleCriteriaModel();
	message:string = "";

	private dropDownUseCase:DropDownUseCase = new DropDownUseCase();
	private SampleUseCase:SampleUseCase = new SampleUseCase();

	async created(){
		this.select_criteria_list = await this.dropDownUseCase.getSampleCriteria(this.criteria.$mining_base_criteria)
	}

	async replacement_criteria(){
		this.select_criteria_list = await this.dropDownUseCase.getSampleCriteria(this.criteria.$mining_base_criteria)
	}
}