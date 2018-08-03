import MovieModel from '../../models/in_house/movie/MovieModel';
import MovieSearchCriteriaModel from '../../models/in_house/movie/MovieSearchCriteriaModel';

export default class SampleTranslater{

	public static translateSearchResult(data:any):MovieModel[]{
		var list:SampleModel[] = []
        
        var result_data = data["result"] 

		for(var snipet in result_data){
            var item:SampleModel = new SampleModel();
                
            item.$id = result_data[snipet].id
            item.$title = result_data[snipet].title
            item.$url = result_data[snipet].url
            item.$create_at = result_data[snipet].created_at
            item.$update_at = result_data[snipet].updated_at
            item.$thumbnail = result_data[snipet].thumbnail
            item.$release_date = result_data[snipet].release_date
            item.$end_date = result_data[snipet].end_date
                
            list.push(item)
        }
		
		return list
    }
    
    public static translateSearchCriteria($route:any):SampleSearchCriteriaModel{
        var criteria : SampleSearchCriteriaModel = new SampleSearchCriteriaModel();

        criteria.$id = $route.params.id
        criteria.$key_word = $route.params.key

        return criteria
    }
}