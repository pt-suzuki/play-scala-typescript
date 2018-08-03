import DropDownModel from '../../models/common/DropDownModel';

export default class DropDownTranslater{

	public static translateByEnum(data:any):DropDownModel[]{
		var list:DropDownModel[] = []

		for(var snipet in data){
			var item:DropDownModel = new DropDownModel();
			
			item.$code = data[snipet].code
			item.$name = data[snipet].name
			list.push(item)
		}

		return list
	}
}