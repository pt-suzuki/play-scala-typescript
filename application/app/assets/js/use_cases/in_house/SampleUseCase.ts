import ApiConnection from '../ApiConnection';
import MiningPlanningModel from '../../models/in_house/mining_planning/MiningPlanningModel'

export default class MiningPlanningUseCase extends ApiConnection{
	async getContent(id:string){
		return new MiningPlanningModel()
	}
}