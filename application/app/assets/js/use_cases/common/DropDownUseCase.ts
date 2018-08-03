import DropDownModel from '../../models/common/DropDownModel';
import ApiConnection from '../ApiConnection';
import DropDownTranslater from '../../translaters/common/DropDownTranslater'

export default class DropDownUseCase extends ApiConnection{

	async getMiningPlanningBaseCriteria() : Promise<DropDownModel[]> {
		const dropdownResponce = await this.get("enums/mining_base_criteria",new URLSearchParams())
		return DropDownTranslater.translateByEnum(dropdownResponce.data)
	}

	async getMiningPlanningCriteria(base_id:number) : Promise<DropDownModel[]> {
		const urlSearchParams = new URLSearchParams();
		urlSearchParams.set("base_id", String(base_id));

		const dropdownResponce = await this.get("enums/mining_criteria",urlSearchParams)
		return DropDownTranslater.translateByEnum(dropdownResponce.data)
	}
}